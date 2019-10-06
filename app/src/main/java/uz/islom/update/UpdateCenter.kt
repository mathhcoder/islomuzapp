package uz.islom.update

import androidx.annotation.AnyThread
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import timber.log.Timber
import uz.islom.vm.BaseViewModel
import java.util.concurrent.Executors
import kotlin.reflect.KClass

object UpdateCenter {

    private val activeSubjects: HashMap<KClass<*>, Pair<Subject<Any>, HashMap<String, Subject<Any>>>> = HashMap(4)

    private val executor by lazy {
        Executors.newSingleThreadExecutor()
    }

    private val scheduler by lazy {
        Schedulers.from(executor)
    }


    @AnyThread
    fun <T : Any> post(value: T, path: UpdatePath<T>) {
        executor.execute {
            Timber.i("Posting notification: ${path::class.simpleName}, value: ${value::class.simpleName}")

            val clazz = path::class
            val subjectsByPath = activeSubjects[clazz] ?: return@execute

            subjectsByPath.first.onNext(value)

            val key = path.key() ?: return@execute

            val subjectsByKey = subjectsByPath.second[key] ?: return@execute

            subjectsByKey.onNext(value)
        }
    }

    @AnyThread
    fun <T : Any> subscribeTo(path: UpdatePath<T>): Flowable<T> {

        val disposables = CompositeDisposable()

        return Flowable.create<T>({ emitter ->

            val clazz = path::class

            val subjectsByPath = activeSubjects.getOrPut(clazz) {
                val subject: Subject<Any> = PublishSubject.create()
                subject to HashMap()
            }

            val key = path.key()

            if (key == null) {
                val disposable = subjectsByPath.first.subscribe {
                    if (emitter.isCancelled) return@subscribe
                    emitter.onNext(it as T)
                }
                disposables.add(disposable)

                return@create
            }

            val subjectsByKey = subjectsByPath.second.getOrPut(key) { PublishSubject.create() }

            val disposable = subjectsByKey.subscribe {
                if (emitter.isCancelled) return@subscribe
                emitter.onNext(it as T)
            }

            disposables.add(disposable)

        }, BackpressureStrategy.LATEST).doOnCancel {

            executor.execute {

                Timber.i("Cleaning up if needed")
                disposables.dispose()
                val iterator = activeSubjects.iterator()

                while (iterator.hasNext()) {

                    val classPair = iterator.next().value
                    val keyIterator = classPair.second.iterator()
                    var keySubjectsEmpty = true

                    while (keyIterator.hasNext()) {
                        val keySubject = keyIterator.next().value
                        if (keySubject.hasObservers()) keySubjectsEmpty = false
                        else keyIterator.remove()
                    }

                    if (keySubjectsEmpty && !classPair.first.hasObservers()) {
                        iterator.remove()
                    }
                }
            }

        }.subscribeOn(scheduler)
    }

}