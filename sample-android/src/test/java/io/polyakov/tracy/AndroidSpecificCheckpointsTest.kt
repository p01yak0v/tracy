package io.polyakov.tracy

import io.polyakov.tracy.android.checkpoint.ActivityCheckpoint
import io.polyakov.tracy.android.checkpoint.ActivityCheckpoint.ActivityState
import io.polyakov.tracy.fixtures.CheckpointCollector
import io.polyakov.tracy.sample.android.main.MainActivity
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(application = TestSampleApplication::class)
class AndroidSpecificCheckpointsTest {

    private lateinit var checkpointCollector: CheckpointCollector

    @Before
    fun setup() {
        checkpointCollector = CheckpointCollector()
    }

    @After
    fun tearDown() {
        Tracy.removeCheckpointListener(checkpointCollector)
    }

    @Test
    fun testActivityVisibleCheckpoints() {
        Tracy.addCheckpointListener(checkpointCollector)

        val mainActivity = Robolectric.buildActivity(MainActivity::class.java)

        mainActivity.setup()

        checkpointCollector.assert {
            // during setup we have to move through onCreate -> onStart -> onResume
            has<ActivityCheckpoint> {
                activityClass == MainActivity::class && state == ActivityState.CREATED
            }
            has<ActivityCheckpoint> {
                activityClass == MainActivity::class && state == ActivityState.STARTED
            }
            has<ActivityCheckpoint> {
                activityClass == MainActivity::class && state == ActivityState.RESUMED
            }

            // no "destroy"
            hasNo<ActivityCheckpoint> {
                activityClass == MainActivity::class && state == ActivityState.PAUSED
            }
            hasNo<ActivityCheckpoint> {
                activityClass == MainActivity::class && state == ActivityState.STOPPED
            }
            hasNo<ActivityCheckpoint> {
                activityClass == MainActivity::class && state == ActivityState.DESTROYED
            }
        }
    }

    @Test
    fun testActivityInvisibleCheckpoints() {
        val mainActivity = Robolectric.buildActivity(MainActivity::class.java)

        mainActivity.setup()

        Tracy.addCheckpointListener(checkpointCollector)

        mainActivity.run {
            pause()
            stop()
            destroy()
        }

        checkpointCollector.assert {
            hasNo<ActivityCheckpoint> {
                activityClass == MainActivity::class && state == ActivityState.CREATED
            }
            hasNo<ActivityCheckpoint> {
                activityClass == MainActivity::class && state == ActivityState.STARTED
            }
            hasNo<ActivityCheckpoint> {
                activityClass == MainActivity::class && state == ActivityState.RESUMED
            }

            has<ActivityCheckpoint> {
                activityClass == MainActivity::class && state == ActivityState.PAUSED
            }
            has<ActivityCheckpoint> {
                activityClass == MainActivity::class && state == ActivityState.STOPPED
            }
            has<ActivityCheckpoint> {
                activityClass == MainActivity::class && state == ActivityState.DESTROYED
            }
        }
    }
}
