package com.sam.util.rx

import io.reactivex.Scheduler

/**
 * fiificodes 25/12/20.
 */
interface SchedulerProvider {
    fun computation(): Scheduler
    fun io(): Scheduler
    fun ui(): Scheduler
}