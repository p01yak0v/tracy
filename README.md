Tracy is a simple library is intended to help you to capture performance traces in a simple way.

[Quick Start Guide](//TODO)

---

## Why Tracy?

When you want to measure duration of some chunk of code, you usually include some tracing
library to your project. It provides an ability to create some `Trace` object with a simple
API like `Trace.start()`, `Trace.stop()` and `Trace.cancel()`. After trace completion collected
data is enriched with some trace or application level parameters and being sent to some service.

What will happen if you need another trace which has a start point at the same place where another
trace is already started, but end point will be different? Well... You have to do something like this:

```kotlin
val firstTrace: Trace = SomeTracingLibrary.createTrace(/* args */)
val secondTrace: Trace = SomeTracingLibrary.createTrace(/* args */)

firstTrace.start()
secondTrace.start()

// then first trace is stopped in some place
firstTrace.stop()

// few moments later second trace is stopped
secondTrace.stop()
```

There are several problems with this chunk of code:
* If you want to capture a new trace with different finish point, you'll have to create a new `Trace`
object
* If start and stop points are located in different places, you'll have to pass exactly the same
reference of `Trace` object to both places (using DI, ServiceLocator, whatever)
* If you need to send exactly the same traces to different services (e.g. Firebase Performance + 
Custom Monitoring System), you'll have to duplicate trace for Firebase and trace for Custom Monitoring
System

So Tracy is here to help you with all the problems above.

## Concepts
There are several key entities in Tracy you have to know about.

### Checkpoint
`Checkpoint` is a place somewhere in a code which is passed at runtime. Checkpoints are used by
Tracy to identify which traces should be started/stopped/cancelled.

To create a checkpoint just extend `Checkpoint` class and name your checkpoint:
```kotlin
object ForegroundCheckpoint : Checkpoint("app-goes-foreground")
```
Let the Tracy know about this checkpoint using `Tracy.pass(Checkpoint)` method:
```kotlin
Tracy.pass(ForegroundCheckpoint)
```
You can create a checkpoint with some arguments to use them later:
```kotlin
class PaymentCompleteCheckpoint(val result: PaymentResult) : Checkpoint("payment-completed-checkpoint")

Tracy.pass(PaymentCompleteCheckpoint(PaymentResult.SUCCESS))
```

### CheckpointMatcher
`CheckpointMatcher` is used to filter flow of checkpoints for a specific trace.
```kotlin
class PaymentCheckpointMatcher(private val targetResult: PaymentResult) : CheckpointMatcher {
    
    fun matches(checkpoint: Checkpoint): Boolean {
        return checkpoint is PaymentCompleteCheckpoint && checkpoint.result == targetResult
    }
}
```
`CheckpointMatcher` is a functional interface, so you can define it "in place" like this:
```kotlin
val successPaymentMatcher = CheckpointMatcher {
    it is PaymentCompleteCheckpoint && it.result == PaymentResult.SUCCESS
}
```

### TraceDescriptor
`TraceDescriptor` is an interface where all trace information is defined: name, checkpoints to
start/stop/cancel a trace and some additional staff. 

Typical definition of `TraceDescriptor` looks like this:
```kotlin
class ActivityToFragmentTraceDescriptor : TraceDescriptor {
    override val name: String = "activity-started-to-fragment-resumed"
    
    override val startMatcher by activityState<MainActivity>(ActivityState.STARTED)
    override val stopMatcher by fragmentState<SampleFragment>(FragmentState.DESTROYED)
    override val cancelMatcher by classMatcher<BackgroundCheckpoint>()
}
```
While overriding matchers for `TraceDescriptor`, you could create `CheckpointMatcher` directly.
```kotlin
override val startMatcher = CheckpointMatcher { it is SomeCheckpoint }
```
Yet there is more convenient and "clean" way to define `CheckpointMatcher` using property delegates.
There are several predefined property delegates in Tracy:

* `activityState(...)` delegates invocation to matcher that is targeted to specific `Activity` class
with specific lifecycle state (`onCreate`, `onPause`, `onResume`, etc.)
* `fragmentState(...)` delegates invocation to matcher that is targeted to specific `Fragment` class
with specific lifecycle state (`onAttach`, `onCreateView`, `onViewCreated`, etc.)
* `classMatcher(...)` delegates invocation to matcher that is targeted to specific `Checkpoint` class.
* `none(...)` delegates invocation to matcher that matches nothing (returns `false` for every checkpoint).
It is useful when you don't want to define a matcher for a cancel state.

You can easily create your own delegates. 
[Check implementation](tracy-android/src/main/java/io/polyakov/tracy/android/matcher/ActivityCheckpointDelegate.kt)
of `ActivityCheckpointDelegate` as example.

### TraceDestination
Every time when a trace should be started, stopped or cancelled the specific method of `TraceDestination`
instance is called. `TraceDestination` is used to delegate a call to a real performance monitoring library
or to make some sort of side effect, e.g. logging.

// TODO : describe how to make your own destination 
