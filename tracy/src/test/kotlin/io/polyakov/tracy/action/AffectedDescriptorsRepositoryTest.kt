package io.polyakov.tracy.action

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.collections.shouldContainExactly
import io.polyakov.tracy.descriptor.TraceDescriptor
import io.polyakov.tracy.descriptor.TraceDescriptorProvider
import io.polyakov.tracy.model.stub.StubCheckpoint

class AffectedDescriptorsRepositoryTest : BehaviorSpec({
    Given("instance of affected descriptors repository") {
        val firstTraceDescriptor = DynamicTraceDescriptor("first")
        val secondTraceDescriptor = DynamicTraceDescriptor("second")

        val descriptorsProvider = object : TraceDescriptorProvider {
            override fun provide() = setOf<TraceDescriptor>(
                firstTraceDescriptor,
                secondTraceDescriptor
            )
        }

        val affectedDescriptorsRepository: AffectedDescriptorsRepository =
            DefaultAffectedDescriptorsRepository(descriptorsProvider)

        forAll(
            row("start", firstTraceDescriptor.startCheckpointName, firstTraceDescriptor, TraceAction.START),
            row("cancel", firstTraceDescriptor.cancelCheckpointName, firstTraceDescriptor, TraceAction.CANCEL),
            row("stop", firstTraceDescriptor.stopCheckpointName, firstTraceDescriptor, TraceAction.STOP),
            row("start", secondTraceDescriptor.startCheckpointName, secondTraceDescriptor, TraceAction.START),
            row("cancel", secondTraceDescriptor.cancelCheckpointName, secondTraceDescriptor, TraceAction.CANCEL),
            row("stop", secondTraceDescriptor.stopCheckpointName, secondTraceDescriptor, TraceAction.STOP),
        ) { label, checkpointName, targetDescriptor, targetAction ->
            When("$label checkpoint for ${targetDescriptor.name} is arrived") {
                val affectedTraces = affectedDescriptorsRepository.getAffectedTraces(
                    StubCheckpoint(checkpointName)
                )
                Then("single trace affected only") {
                    affectedTraces.shouldContainExactly(
                        listOf(targetDescriptor to targetAction)
                    )
                }
            }
        }

        forAll(
            row("stop",
                DynamicTraceDescriptor::stopMatcher,
                firstTraceDescriptor.stopCheckpointName,
                TraceAction.STOP
            ),
            row(
                "start",
                DynamicTraceDescriptor::startMatcher,
                firstTraceDescriptor.startCheckpointName,
                TraceAction.START
            ),
            row(
                "cancel",
                DynamicTraceDescriptor::cancelMatcher,
                firstTraceDescriptor.cancelCheckpointName,
                TraceAction.CANCEL
            ),
        ) { label: String, matcher: DynamicTraceDescriptor.() -> DynamicNameCheckpointMatcher,
            firstCheckpointName: String, action: TraceAction ->
            And("second trace has the same $label checkpoint as the first one") {
                secondTraceDescriptor.matcher().checkpointName = firstCheckpointName

                When("$label checkpoint associated with two descriptors is arrived") {
                    val affectedTraces = affectedDescriptorsRepository.getAffectedTraces(
                        StubCheckpoint(firstCheckpointName)
                    )

                    Then("both descriptors are affected") {
                        affectedTraces.shouldContainExactly(
                            listOf(
                                firstTraceDescriptor to action,
                                secondTraceDescriptor to action
                            )
                        )
                    }
                }
            }
        }
    }
})
