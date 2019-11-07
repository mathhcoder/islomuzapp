package uz.islom.model

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import uz.islom.IslomUzApp
import uz.islom.model.entity.AsmaUlHusna
import uz.islom.model.convertor.ContentConvertor
import uz.islom.model.dao.AsmaUlHusnaDao
import uz.islom.model.entity.Content
import java.util.concurrent.atomic.AtomicReference


@Database(entities = [AsmaUlHusna::class, Content::class],
        exportSchema = false,
        version = 1)

@TypeConverters(ContentConvertor::class)

abstract class AppStorage : RoomDatabase() {

    companion object {

        private val originalInstance: AppStorage by lazy {
            Room.databaseBuilder(
                    IslomUzApp.getInstance(),
                    AppStorage::class.java,
                    "islomuz.db"
            ).fallbackToDestructiveMigration().build()
        }

        internal val shadowInstance = AtomicReference(originalInstance)

        val instance: AppStorage
            get() = shadowInstance.get()

//        val databaseExecutor: ExecutorService by lazy {
//            SingleThreadExecutor()
//        }

      //  val databaseScheduler = Schedulers.from(databaseExecutor)


    }

    abstract fun asmaUlHusnaDao(): AsmaUlHusnaDao

}

