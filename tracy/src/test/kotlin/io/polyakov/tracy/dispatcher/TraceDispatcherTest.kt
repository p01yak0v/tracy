package io.polyakov.tracy.dispatcher

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.core.spec.style.scopes.BehaviorSpecGivenContainerScope
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe
import io.polyakov.tracy.dispatcher.stub.AccumulatorTraceStateDelegate
import io.polyakov.tracy.model.Checkpoint
import io.polyakov.tracy.model.OperationalTrace
import io.polyakov.tracy.model.TestCheckpoint
import io.polyakov.tracy.model.Trace
import io.polyakov.tracy.model.TraceImpl
import io.polyakov.tracy.model.stub.StubTraceDescriptor

class TraceDispatcherTest : BehaviorSpec({
    Given("a trace dispatcher") {
        val traceStateDelegate = AccumulatorTraceStateDelegate()
        val traceDispatcher: TraceDispatcher = TraceDispatcherImpl(traceStateDelegate)

        And("trace is in created state") {
            runTestsForCreatedTrace(traceDispatcher, traceStateDelegate)
        }

        And("trace is in started state") {
            runTestsForStartedTrace(traceDispatcher, traceStateDelegate)
        }

        And("trace is in stopped state") {
            runTestsForTerminatedTrace(
                traceDispatcher,
                traceStateDelegate,
                TraceDispatcher::dispatchStop,
                Trace.State.STOPPED
            )
        }

        And("trace is in cancelled state") {
            runTestsForTerminatedTrace(
                traceDispatcher,
                traceStateDelegate,
                TraceDispatcher::dispatchCancel,
                Trace.State.CANCELLED
            )
        }

        And("trace is mediated state") {
            runTestsForMediatedTrace(traceDispatcher, traceStateDelegate)
        }
    }
})

private suspend fun BehaviorSpecGivenContainerScope.runTestsForCreatedTrace(
    traceDispatcher: TraceDispatcher,
    traceStateDelegate: AccumulatorTraceStateDelegate
) {
    val trace = TraceImpl.create(StubTraceDescriptor())
    val checkpoint = TestCheckpoint("some-checkpoint")

    When("start trace using dispatcher") {
        traceDispatcher.dispatchStart(trace, checkpoint)

        Then("trace state has changed to ${Trace.State.STARTED}") {
            traceStateDelegate.changes shouldContain (trace to Trace.State.STARTED)
            trace.state shouldBe Trace.State.STARTED
        }
    }

    forAll(
        row("stop", TraceDispatcher::dispatchStop),
        row("cancel", TraceDispatcher::dispatchCancel),
        row("mediate", TraceDispatcher::dispatchEnrichment)
    ) { label: String, operation: TraceDispatcher.(OperationalTrace, Checkpoint) -> Unit ->
        When("$label a trace") {
            traceDispatcher.operation(trace, checkpoint)

            Then("trace is in the same state; no changes dispatched") {
                trace.state shouldBe Trace.State.CREATED
                traceStateDelegate.changes.shouldBeEmpty()
            }
        }
    }
}

private suspend fun BehaviorSpecGivenContainerScope.runTestsForStartedTrace(
    traceDispatcher: TraceDispatcher,
    traceStateDelegate: AccumulatorTraceStateDelegate
) {
    val trace = TraceImpl.create(StubTraceDescriptor())
    val checkpoint = TestCheckpoint("some-checkpoint")
    traceDispatcher.dispatchStart(trace, TestCheckpoint("start"))

    When("start already started trace") {
        traceDispatcher.dispatchStart(trace, checkpoint)

        Then("trace is in the same state; no changes dispatched") {
            trace.state shouldBe Trace.State.STARTED
            traceStateDelegate.changes.size shouldBe 1
        }
    }

    forAll(
        row("stop", TraceDispatcher::dispatchStop, Trace.State.STOPPED),
        row("cancel", TraceDispatcher::dispatchCancel, Trace.State.CANCELLED),
    ) { label: String, operation: TraceDispatcher.(OperationalTrace, Checkpoint) -> Unit, newState: Trace.State ->
        When("$label a trace") {
            traceDispatcher.operation(trace, checkpoint)

            Then("trace is in $newState state; change dispatched") {
                trace.state shouldBe newState
                traceStateDelegate.changes shouldContainExactly listOfNotNull(
                    trace to Trace.State.STARTED,
                    trace to newState
                )
                trace.checkpoints.last() shouldBe checkpoint
            }
        }
    }

    When("mediate a trace") {
        traceDispatcher.dispatchEnrichment(trace, checkpoint)

        Then("trace state stays unchanged; change not dispatched; checkpoint is added") {
            trace.state shouldBe Trace.State.STARTED
            traceStateDelegate.changes shouldContainExactly listOf(
                trace to Trace.State.STARTED
            )
            trace.checkpoints.last() shouldBe checkpoint
        }
    }
}

private suspend fun BehaviorSpecGivenContainerScope.runTestsForTerminatedTrace(
    traceDispatcher: TraceDispatcher,
    traceStateDelegate: AccumulatorTraceStateDelegate,
    initialOperation: TraceDispatcher.(OperationalTrace, Checkpoint) -> Unit,
    expectedState: Trace.State
) {
    val trace = TraceImpl.create(StubTraceDescriptor())
    val checkpoint = TestCheckpoint("some-checkpoint")

    val startCheckpoint = TestCheckpoint("start")
    traceDispatcher.dispatchStart(trace, startCheckpoint)

    val stopCheckpoint = TestCheckpoint(expectedState.name)
    traceDispatcher.initialOperation(trace, stopCheckpoint)

    forAll(
        row("start", TraceDispatcher::dispatchStart),
        row("stop", TraceDispatcher::dispatchStop),
        row("cancel", TraceDispatcher::dispatchCancel),
        row("mediate", TraceDispatcher::dispatchEnrichment)
    ) { label: String, operation: TraceDispatcher.(OperationalTrace, Checkpoint) -> Unit ->
        When("$label a trace") {
            traceDispatcher.operation(trace, checkpoint)

            Then("trace stays in $expectedState state; no changes dispatched") {
                trace.state shouldBe expectedState
                traceStateDelegate.changes shouldContainExactly listOfNotNull(
                    trace to Trace.State.STARTED,
                    trace to expectedState
                )
                trace.checkpoints shouldContainExactly listOf(
                    startCheckpoint,
                    stopCheckpoint
                )
            }
        }
    }
}

private suspend fun BehaviorSpecGivenContainerScope.runTestsForMediatedTrace(
    traceDispatcher: TraceDispatcher,
    traceStateDelegate: AccumulatorTraceStateDelegate
) {
    val trace = TraceImpl.create(StubTraceDescriptor())

    val startCheckpoint = TestCheckpoint("start")
    traceDispatcher.dispatchStart(trace, startCheckpoint)

    val mediateCheckpoint = TestCheckpoint("some-checkpoint")
    traceDispatcher.dispatchEnrichment(trace, mediateCheckpoint)

    When("start mediated trace") {
        traceDispatcher.dispatchStart(trace, startCheckpoint)

        Then("trace is in the same state; no changes dispatched") {
            trace.state shouldBe Trace.State.STARTED
            traceStateDelegate.changes.size shouldBe 1
            trace.checkpoints shouldContainExactly listOf(startCheckpoint, mediateCheckpoint)
        }
    }

    forAll(
        row("stop", TraceDispatcher::dispatchStop, Trace.State.STOPPED),
        row("cancel", TraceDispatcher::dispatchCancel, Trace.State.CANCELLED),
    ) { label: String, operation: TraceDispatcher.(OperationalTrace, Checkpoint) -> Unit, newState: Trace.State ->
        When("$label a trace") {
            val finalCheckpoint = TestCheckpoint(label)
            traceDispatcher.operation(trace, finalCheckpoint)

            Then("trace is in $newState state; change dispatched") {
                trace.state shouldBe newState
                traceStateDelegate.changes shouldContainExactly listOfNotNull(
                    trace to Trace.State.STARTED,
                    trace to newState
                )
                trace.checkpoints shouldContainExactly listOf(
                    startCheckpoint,
                    mediateCheckpoint,
                    finalCheckpoint
                )
            }
        }
    }

    When("mediate a trace again") {
        val anotherMediateCheckpoint = TestCheckpoint("another-mediate-checkpoint")
        traceDispatcher.dispatchEnrichment(trace, anotherMediateCheckpoint)

        Then("trace state stays unchanged; change not dispatched; checkpoint is added") {
            trace.state shouldBe Trace.State.STARTED
            traceStateDelegate.changes shouldContainExactly listOf(
                trace to Trace.State.STARTED
            )
            trace.checkpoints shouldContainExactly listOf(startCheckpoint, mediateCheckpoint, anotherMediateCheckpoint)
        }
    }
}
