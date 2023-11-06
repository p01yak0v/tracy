package io.polyakov.tracy.destination

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldContainExactly
import io.polyakov.tracy.destination.stub.StubTraceDestination
import io.polyakov.tracy.destination.stub.YetAnotherStubTraceDestination
import io.polyakov.tracy.model.Trace
import io.polyakov.tracy.model.TraceImpl
import io.polyakov.tracy.model.stub.StubTraceDescriptor
import kotlin.reflect.KClass

internal class DestinationTraceStateDelegateTest : BehaviorSpec({
    Given("instance of DestinationTraceStateDelegate with several destinations") {
        val includedDestination = StubTraceDestination()
        val excludedDestination = YetAnotherStubTraceDestination()
        val destinationTraceStateDelegate = DestinationTraceStateDelegate(
            setOf(includedDestination, excludedDestination)
        )

        And("trace with one excluded destination") {
            val trace = TraceImpl.create(
                DestinationTestTraceDescriptor(YetAnotherStubTraceDestination::class)
            )

            forAll(
                row(Trace.State.STARTED, StubTraceDestination::startedTraces),
                row(Trace.State.CANCELLED, StubTraceDestination::cancelledTraces),
                row(Trace.State.STOPPED, StubTraceDestination::stoppedTraces)
            ) { newState: Trace.State, targetCollection: StubTraceDestination.() -> List<Trace> ->
                When("trace is changed to $newState state") {
                    val emptyCollections = listOf(
                        StubTraceDestination::startedTraces,
                        StubTraceDestination::cancelledTraces,
                        StubTraceDestination::stoppedTraces
                    ) - targetCollection

                    destinationTraceStateDelegate.onTraceStateChanged(trace, newState)

                    Then("one destination is executed; excluded is ignored") {
                        includedDestination.targetCollection().shouldContainExactly(trace)
                        excludedDestination.targetCollection().shouldBeEmpty()

                        emptyCollections.forEach {
                            includedDestination.it().shouldBeEmpty()
                            excludedDestination.it().shouldBeEmpty()
                        }
                    }
                }
            }

            When("trace is changed to stopped state") {
                destinationTraceStateDelegate.onTraceStateChanged(trace, Trace.State.STOPPED)

                Then("trace arguments are collected for one destination") {
                    includedDestination.attributeEvents.shouldContainExactly(trace)
                    excludedDestination.attributeEvents.shouldBeEmpty()
                }
            }
        }
    }
})

class DestinationTestTraceDescriptor(
    excludedDestination: KClass<out TraceDestination>
) : StubTraceDescriptor() {
    override val excludedDestinations = listOf(excludedDestination)
}
