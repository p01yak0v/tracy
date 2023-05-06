package io.polyakov.tracy.attribute

fun String.asAttribute(name: String) = TraceAttribute.StringAttribute(name, this)

fun Float.asAttribute(name: String) = TraceAttribute.FloatAttribute(name, this)

fun Long.asAttribute(name: String) = TraceAttribute.LongAttribute(name, this)

infix fun String.attr(value: String) = TraceAttribute.StringAttribute(this, value)

infix fun String.attr(value: Float) = TraceAttribute.FloatAttribute(this, value)

infix fun String.attr(value: Long) = TraceAttribute.LongAttribute(this, value)
