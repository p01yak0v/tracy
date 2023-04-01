package io.polyakov.tracy.android

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentManager.FragmentLifecycleCallbacks
import io.polyakov.tracy.Tracy
import io.polyakov.tracy.android.checkpoint.FragmentCheckpoint
import io.polyakov.tracy.android.checkpoint.FragmentCheckpoint.FragmentState

internal class TracyFragmentCallbacks : FragmentLifecycleCallbacks() {

    override fun onFragmentAttached(fm: FragmentManager, f: Fragment, context: Context) {
        passFragmentCheckpoint(f, FragmentState.ATTACHED)
    }

    override fun onFragmentCreated(fm: FragmentManager, f: Fragment, savedInstanceState: Bundle?) {
        passFragmentCheckpoint(f, FragmentState.CREATED)
    }

    override fun onFragmentDestroyed(fm: FragmentManager, f: Fragment) {
        passFragmentCheckpoint(f, FragmentState.DESTROYED)
    }

    override fun onFragmentDetached(fm: FragmentManager, f: Fragment) {
        passFragmentCheckpoint(f, FragmentState.DETACHED)
    }

    override fun onFragmentPaused(fm: FragmentManager, f: Fragment) {
        passFragmentCheckpoint(f, FragmentState.PAUSED)
    }

    override fun onFragmentResumed(fm: FragmentManager, f: Fragment) {
        passFragmentCheckpoint(f, FragmentState.RESUMED)
    }

    override fun onFragmentStarted(fm: FragmentManager, f: Fragment) {
        passFragmentCheckpoint(f, FragmentState.STARTED)
    }

    override fun onFragmentStopped(fm: FragmentManager, f: Fragment) {
        passFragmentCheckpoint(f, FragmentState.STOPPED)
    }

    override fun onFragmentViewCreated(fm: FragmentManager, f: Fragment, v: View, savedInstanceState: Bundle?) {
        passFragmentCheckpoint(f, FragmentState.VIEW_CREATED)
    }

    override fun onFragmentViewDestroyed(fm: FragmentManager, f: Fragment) {
        passFragmentCheckpoint(f, FragmentState.VIEW_DESTROYED)
    }

    private fun passFragmentCheckpoint(fragment: Fragment, state: FragmentState) {
        Tracy.pass(
            FragmentCheckpoint(
                fragmentClass = fragment::class,
                state = state
            )
        )
    }
}