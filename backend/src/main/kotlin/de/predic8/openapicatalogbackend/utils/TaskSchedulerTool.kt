package de.predic8.openapicatalogbackend.utils

import kotlinx.coroutines.Runnable
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler
import org.springframework.stereotype.Component
import java.time.Duration
import java.util.concurrent.ScheduledFuture

@Component
class TaskSchedulerTool(val taskScheduler: ThreadPoolTaskScheduler) {
    fun createScheduledTaskFrom(duration: Duration, runnable: Runnable): ScheduledFuture<*> = taskScheduler.scheduleWithFixedDelay(runnable, duration)
}
