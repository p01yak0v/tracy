package io.polyakov.tracy.model

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.collections.shouldContainOnly
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeTypeOf
import io.polyakov.tracy.model.stub.StubCheckpoint
import io.polyakov.tracy.model.stub.StubTraceDescriptor

class OperationalTraceTest : BehaviorSpec({
    Given("an operational trace") {
        val trace: OperationalTrace = TraceImpl.Factory.create(StubTraceDescriptor())
        Then("trace state is ${Trace.State.CREATED}") {
            trace.state shouldBe Trace.State.CREATED
        }

        When("start a trace") {
            val startCheckpoint = StubCheckpoint("start")
            trace.start(startCheckpoint)

            Then("trace is in ${Trace.State.STARTED} state and contains a single checkpoint") {
                trace.state shouldBe Trace.State.STARTED
                trace.checkpoints.shouldContainOnly(startCheckpoint)
            }

            And("stop a trace") {
                val stopCheckpoint = StubCheckpoint("stop")
                trace.stop(stopCheckpoint)

                Then("trace state is ${Trace.State.STOPPED}; checkpoints updated; duration calculated") {
                    trace.state shouldBe Trace.State.STOPPED
                    trace.duration shouldBe (stopCheckpoint.creationTimestamp - startCheckpoint.creationTimestamp)
                    trace.checkpoints shouldContainExactly(
                        listOf(startCheckpoint, stopCheckpoint)
                    )
                }

                And("cancel a trace") {
                    val cancelCheckpoint = StubCheckpoint("cancel")
                    val exception = shouldThrow<IllegalStateException> {
                        trace.cancel(cancelCheckpoint)
                    }

                    Then("exception is occurred") {
                        exception.shouldBeTypeOf<IllegalStateException>()
                    }
                }
            }

            And("cancel a trace") {
                val cancelCheckpoint = StubCheckpoint("cancel")
                trace.cancel(cancelCheckpoint)

                Then("trace state is ${Trace.State.CANCELLED}; checkpoints updated; duration calculated") {
                    trace.state shouldBe Trace.State.CANCELLED
                    trace.duration shouldBe 0
                    trace.checkpoints shouldContainExactly(
                        listOf(startCheckpoint, cancelCheckpoint)
                    )
                }

                And("stop a trace") {
                    val stopCheckpoint = StubCheckpoint("stop")
                    val exception = shouldThrow<IllegalStateException> {
                        trace.stop(stopCheckpoint)
                    }

                    Then("exception is occurred") {
                        exception.shouldBeTypeOf<IllegalStateException>()
                    }
                }
            }
        }

        // check "finalization" operations w/o started trace
        forAll(
            row("stop", OperationalTrace::stop),
            row("cancel", OperationalTrace::cancel)
        ) { label: String, operation: OperationalTrace.(Checkpoint) -> Unit ->
            When("$label a trace") {
                val checkpoint = StubCheckpoint(label)

                val exception = shouldThrow<IllegalStateException> {
                    trace.operation(checkpoint)
                }

                Then("exception is thrown") {
                    exception.shouldBeTypeOf<IllegalStateException>()
                }
            }
        }
    }
})
