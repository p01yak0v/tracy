package io.polyakov.tracy.annotation

import io.polyakov.tracy.destination.TraceDestination
import kotlin.reflect.KClass

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class ExcludeDestination(val destinations: Array<KClass<out TraceDestination>>)
