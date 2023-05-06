package io.polyakov.tracy.android.matcher

import android.app.Activity
import androidx.fragment.app.Fragment
import io.polyakov.tracy.android.checkpoint.ActivityCheckpoint.ActivityState
import io.polyakov.tracy.android.checkpoint.FragmentCheckpoint.FragmentState

inline fun <reified T : Activity> activityState(
    state: ActivityState
): ActivityCheckpointDelegate {
    return ActivityCheckpointDelegate(T::class, state)
}

inline fun <reified T : Fragment> fragmentState(
    state: FragmentState
): FragmentCheckpointDelegate {
    return FragmentCheckpointDelegate(T::class, state)
}
