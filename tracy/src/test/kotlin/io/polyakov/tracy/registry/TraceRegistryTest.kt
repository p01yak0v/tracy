package io.polyakov.tracy.registry

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.types.shouldBeSameInstanceAs
import io.polyakov.tracy.model.stub.StubTraceDescriptor
import io.polyakov.tracy.model.stub.StubTraceFactory

class TraceRegistryTest : BehaviorSpec({
    Given("Instance of TraceRegistry") {
        val traceRegistry: TraceRegistry = TraceRegistryImpl(
            StubTraceFactory()
        )
        val descriptor = StubTraceDescriptor()

        When("Create a trace") {
            val trace = traceRegistry.createTrace(descriptor)

            Then("Trace should be created") {
                trace shouldNotBe null
            }

            And("Create a trace one more time with the same descriptor") {
                val anotherTrace = traceRegistry.createTrace(descriptor)

                Then("A new trace should not be created") {
                    anotherTrace.shouldBeNull()
                }
            }

            And("Remove this trace") {
                val removedTrace = traceRegistry.removeTrace(descriptor)

                Then("It is the same trace, as we created") {
                    removedTrace shouldBeSameInstanceAs trace
                }

                And("Get removed trace") {
                    val getRemovedTrace = traceRegistry.getTrace(descriptor)

                    Then("Trace is removed completely") {
                        getRemovedTrace.shouldBeNull()
                    }
                }

                And("Remove it again") {
                    val oneMoreRemovedTrace = traceRegistry.removeTrace(descriptor)

                    Then("Nothing to remove") {
                        oneMoreRemovedTrace.shouldBeNull()
                    }
                }
            }

            And("Get this trace") {
                val newTrace = traceRegistry.getTrace(descriptor)

                Then("Original trace and newTrace are the same") {
                    trace shouldBeSameInstanceAs newTrace
                }
            }
        }

        When("Remove a trace which is not created") {
            val removedTrace = traceRegistry.removeTrace(descriptor)

            Then("No trace is removed") {
                removedTrace.shouldBeNull()
            }
        }

        When("Get non existent trace") {
            val nonExistentTrace = traceRegistry.getTrace(descriptor)

            Then("Trace is null") {
                nonExistentTrace.shouldBeNull()
            }
        }
    }
})
