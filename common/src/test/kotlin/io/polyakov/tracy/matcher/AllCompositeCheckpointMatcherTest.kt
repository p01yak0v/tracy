package io.polyakov.tracy.matcher

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.polyakov.tracy.model.TestCheckpoint

class AllCompositeCheckpointMatcherTest : BehaviorSpec({
    Given("AllCompositeCheckpointMatcher with by name and by class matcher") {
        val checkpointName = "test-name"
        val originalCheckpoint = TestCheckpoint(checkpointName)
        val matcher = AllCompositeCheckpointMatcher(
            NameCheckpointMatcher(checkpointName),
            CheckpointMatcher { it is TestCheckpoint }, // by class matcher
            CheckpointMatcher { it === originalCheckpoint } // by reference matcher
        )
        When("the same checkpoint is arrived") {
            val result = matcher.matches(originalCheckpoint)

            Then("checkpoint matches") {
                result.shouldBeTrue()
            }
        }

        When("another checkpoint is arrived") {
            val result = matcher.matches(TestCheckpoint(checkpointName))

            Then("checkpoint doesn't match") {
                result.shouldBeFalse()
            }
        }
    }
    Given("AllCompositeCheckpointMatcher has no matchers") {
        val matcher = AllCompositeCheckpointMatcher()

        When("some checkpoint is arrived") {
            val result = matcher.matches(TestCheckpoint("test"))

            Then("checkpoint matches since there are no delegated matchers") {
                result.shouldBeTrue()
            }
        }
    }
})
