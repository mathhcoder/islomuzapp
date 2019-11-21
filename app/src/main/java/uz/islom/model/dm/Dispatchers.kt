package uz.islom.model.dm

import io.reactivex.Scheduler

data class Dispatchers(val main: Scheduler,
                       val calculator: Scheduler,
                       val db: Scheduler,
                       val network: Scheduler,
                       val preference: Scheduler)