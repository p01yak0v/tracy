package io.polyakov.tracy.descriptor

import io.polyakov.tracy.matcher.CheckpointMatcher

interface EnrichableTraceDescriptor : TraceDescriptor {
    val enrichmentMatcher: CheckpointMatcher
}
