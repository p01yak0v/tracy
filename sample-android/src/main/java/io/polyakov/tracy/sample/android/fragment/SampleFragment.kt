package io.polyakov.tracy.sample.android.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.ColorRes
import androidx.fragment.app.Fragment

class SampleFragment : Fragment() {

    companion object {

        private const val KEY_COLOR = "color"

        fun create(@ColorRes backgroundColor: Int): Fragment {
            return SampleFragment().apply {
                arguments = Bundle().apply {
                    putInt(KEY_COLOR, backgroundColor)
                }
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return View(inflater.context).apply {
            layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
            )
            setBackgroundResource(requireArguments().getInt(KEY_COLOR))
        }
    }
}
