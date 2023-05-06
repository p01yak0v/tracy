package io.polyakov.tracy.model.stub

import io.polyakov.tracy.descriptor.TraceDescriptor
import io.polyakov.tracy.model.OperationalTrace
import io.polyakov.tracy.model.TraceFactory

internal class StubTraceFactory : TraceFactory {
    override fun create(descriptor: TraceDescriptor): OperationalTrace {
        return StubOperationalTrace(descriptor)
    }
}
