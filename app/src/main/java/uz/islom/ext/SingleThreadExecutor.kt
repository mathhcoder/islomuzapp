package uz.islom.ext

import java.util.concurrent.*

//class SingleThreadExecutor: ThreadPoolExecutor(1, 1, Long.MAX_VALUE, TimeUnit.NANOSECONDS, LinkedBlockingQueue<Runnable>(), SingleThreadExecutor.threadFactory) {
//
//    companion object {
//        const val executorNameFormat = "uz.islom.singleThreadExecutor"
//     //   val threadFactory: ThreadFactory = ThreadFactoryBuilder().setNameFormat(executorNameFormat + "-%d").build()
//    }
//
//    var name: String = ""
//
//    init {
//        super.execute {
//            name = Thread.currentThread().name
//        }
//    }
//
//    override fun execute(command: Runnable) {
//        if (Thread.currentThread().name == name) {
//            command.run()
//        }
//        else
//            super.execute(command)
//    }
//
//    override fun <T : Any?> submit(task: Callable<T>): Future<T> {
//        if (Thread.currentThread().name == name) {
//            return CompletedFuture(task.call())
//        }
//
//        return super.submit(task)
//    }
//
//}
//
//
//class CompletedFuture<T>(val value: T): Future<T> {
//
//    override fun isDone(): Boolean {
//        return true
//    }
//
//    override fun get(): T {
//        return value
//    }
//
//    override fun get(timeout: Long, unit: TimeUnit): T {
//        return value
//    }
//
//    override fun cancel(mayInterruptIfRunning: Boolean): Boolean {
//        return false
//    }
//
//    override fun isCancelled(): Boolean {
//        return false
//    }
//
//}
