package io.polyakov.tracy.android

import android.app.Activity
import android.app.Application.ActivityLifecycleCallbacks
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager.FragmentLifecycleCallbacks
import io.polyakov.tracy.Tracy
import io.polyakov.tracy.android.checkpoint.ActivityCheckpoint
import io.polyakov.tracy.android.checkpoint.ActivityCheckpoint.ActivityState

class TracyActivityCallbacks(isFragmentRequired: Boolean): ActivityLifecycleCallbacks {

    private val fragmentCallbacks: FragmentLifecycleCallbacks?

    init {
        fragmentCallbacks = if (isFragmentRequired) TracyFragmentCallbacks() else null
    }

    override fun onActivityCreated(activity: Activity, bundle: Bundle?) {
        passActivityCheckpoint(activity, ActivityState.CREATED)

        fragmentCallbacks?.let {
            setupFragmentLifecycleObserver(activity, it)
        }
    }

    override fun onActivityStarted(activity: Activity) {
        passActivityCheckpoint(activity, ActivityState.STARTED)
    }

    override fun onActivityResumed(activity: Activity) {
        passActivityCheckpoint(activity, ActivityState.RESUMED)
    }

    override fun onActivityPaused(activity: Activity) {
        passActivityCheckpoint(activity, ActivityState.PAUSED)
    }

    override fun onActivityStopped(activity: Activity) {
        passActivityCheckpoint(activity, ActivityState.STOPPED)
    }

    override fun onActivityDestroyed(activity: Activity) {
        passActivityCheckpoint(activity, ActivityState.DESTROYED)
    }

    override fun onActivitySaveInstanceState(activity: Activity, bundle: Bundle) = Unit

    private fun passActivityCheckpoint(activity: Activity, state: ActivityState) {
        Tracy.pass(
            ActivityCheckpoint(
                activityClass = activity::class,
                state = state
            )
        )
    }

    private fun setupFragmentLifecycleObserver(activity: Activity, fragmentCallbacks: FragmentLifecycleCallbacks) {
        if (activity is FragmentActivity) {
            activity.supportFragmentManager.registerFragmentLifecycleCallbacks(fragmentCallbacks, true)
        }
    }
}