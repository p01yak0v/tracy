package io.polyakov.tracy.model

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.collections.shouldContainOnly
import io.kotest.matchers.shouldBe
import io.polyakov.tracy.model.stub.StubTraceDescriptor

class OperationalTraceTest : BehaviorSpec({
    Given("an operational trace") {
        val trace: OperationalTrace = TraceImpl.Factory.create(StubTraceDescriptor())
        Then("trace state is ${Trace.State.CREATED}") {
            trace.state shouldBe Trace.State.CREATED
        }

        When("start a trace") {
            val startCheckpoint = TestCheckpoint("start")
            trace.start(startCheckpoint)

            Then("trace is in ${Trace.State.STARTED} state and contains a single checkpoint") {
                trace.state shouldBe Trace.State.STARTED
                trace.checkpoints.shouldContainOnly(startCheckpoint)
            }

            And("stop a trace") {
                val stopCheckpoint = TestCheckpoint("stop")
                trace.stop(stopCheckpoint)

                Then("trace state is ${Trace.State.STOPPED}; checkpoints updated; duration calculated") {
                    trace.state shouldBe Trace.State.STOPPED
                    val nsDuration = stopCheckpoint.creationTimestamp - startCheckpoint.creationTimestamp
                    trace.duration shouldBe (nsDuration / 1_000_000)
                    trace.checkpoints shouldContainExactly listOf(startCheckpoint, stopCheckpoint)
                }

                And("cancel a trace") {
                    val cancelResult = trace.cancel(TestCheckpoint("cancel"))

                    Then("cancel is not occurred") {
                        cancelResult shouldBe false
                    }
                }
            }

            And("cancel a trace") {
                val cancelCheckpoint = TestCheckpoint("cancel")
                trace.cancel(cancelCheckpoint)

                Then("trace state is ${Trace.State.CANCELLED}; checkpoints updated; duration calculated") {
                    trace.state shouldBe Trace.State.CANCELLED
                    trace.duration shouldBe 0
                    trace.checkpoints shouldContainExactly(
                        listOf(startCheckpoint, cancelCheckpoint)
                        )
                }

                And("stop a trace") {
                    val stopResult = trace.stop(TestCheckpoint("stop"))

                    Then("trace is not stopped") {
                        stopResult shouldBe false
                    }
                }
            }
        }

        // check "finalization" operations w/o started trace
        forAll(
            row("stop", OperationalTrace::stop),
            row("cancel", OperationalTrace::cancel)
        ) { label: String, operation: OperationalTrace.(Checkpoint) -> Boolean ->
            When("$label a trace") {
                val operationResult = trace.operation(TestCheckpoint(label))

                Then("$label operation is not executed") {
                    operationResult shouldBe false
                }
            }
        }
    }
})
