package io.polyakov.tracy.matcher

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.polyakov.tracy.model.TestCheckpoint

class AnyCompositeCheckpointMatcherTest : BehaviorSpec({
    Given("AnyCompositeCheckpointMatcher with two matchers for different names") {
        val checkpointName = "test-name"
        val matcher = AnyCompositeCheckpointMatcher(
            NameCheckpointMatcher(checkpointName),
            NameCheckpointMatcher("non-relevant-name")
        )
        When("checkpoint with valid name is arrived") {
            val result = matcher.matches(TestCheckpoint(checkpointName))

            Then("checkpoint matches") {
                result.shouldBeTrue()
            }
        }

        When("another checkpoint is arrived") {
            val result = matcher.matches(TestCheckpoint("completely-different-name"))

            Then("checkpoint doesn't match") {
                result.shouldBeFalse()
            }
        }
    }
    Given("AnyCompositeCheckpointMatcher has no matchers") {
        Then("instantiation failed with exception") {
            shouldThrow<IllegalArgumentException> {
                AnyCompositeCheckpointMatcher()
            }
        }
    }
})
