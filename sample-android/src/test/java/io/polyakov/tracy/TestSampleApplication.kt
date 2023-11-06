package io.polyakov.tracy

import io.polyakov.tracy.android.destintation.SystemTraceDestination
import io.polyakov.tracy.destination.LoggingDestination
import io.polyakov.tracy.destination.TraceDestination
import io.polyakov.tracy.sample.android.SampleApplication
import org.robolectric.TestLifecycleApplication
import java.lang.reflect.Method

class TestSampleApplication : SampleApplication(), TestLifecycleApplication {
    override fun provideDestinationSet(): Set<TraceDestination> {
        return setOf(
            SystemTraceDestination(),
            LoggingDestination("TestLogging")
        )
    }

    override fun beforeTest(method: Method?) = Unit
    override fun prepareTest(test: Any?) = Unit
    override fun afterTest(method: Method?) = Unit
}
