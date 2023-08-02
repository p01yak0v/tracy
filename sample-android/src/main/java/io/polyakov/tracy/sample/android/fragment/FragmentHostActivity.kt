package io.polyakov.tracy.sample.android.fragment

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import io.polyakov.tracy.sample.android.R
import io.polyakov.tracy.sample.android.fragment.base.BaseFragment
import kotlin.reflect.KClass
import kotlin.reflect.full.createInstance

class FragmentHostActivity : AppCompatActivity(R.layout.not_really_main_activity) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val menuFragmentsMap = mapOf(
            R.id.first_screen to FirstFragment::class,
            R.id.second_screen to SecondFragment::class,
            R.id.third_screen to ThirdFragment::class
        )
        showFragmentWithBackground(menuFragmentsMap.values.first(), replace = false)

        val bottomMenu = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomMenu.setOnItemSelectedListener { menuItem ->
            showFragmentWithBackground(menuFragmentsMap.getValue(menuItem.itemId), replace = true)
            return@setOnItemSelectedListener true
        }
    }

    private fun showFragmentWithBackground(fragmentClass: KClass<out BaseFragment>, replace: Boolean) {
        supportFragmentManager.beginTransaction()
            .run {
                val fragment = fragmentClass.createInstance()
                if (replace) {
                    replace(R.id.container, fragment)
                } else {
                    add(R.id.container, fragment)
                }
            }
            .commitNow()
    }
}
