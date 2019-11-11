package uz.islom.model

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import uz.islom.IslomUzApp
import uz.islom.model.entity.AsmaUlHusna
import uz.islom.model.converter.ContentConverter
import uz.islom.model.dao.AsmaUlHusnaDao
import uz.islom.model.dao.SurahDao
import uz.islom.model.entity.Content
import uz.islom.model.entity.Surah
import java.util.concurrent.atomic.AtomicReference


@Database(entities = [AsmaUlHusna::class, Content::class,Surah::class],
        exportSchema = false,
        version = 1)

@TypeConverters(ContentConverter::class)

abstract class AppStorageManager : RoomDatabase() {

    companion object {

        private val originalInstance: AppStorageManager by lazy {
            Room.databaseBuilder(
                    IslomUzApp.getInstance(),
                    AppStorageManager::class.java,
                    "islomuz.db"
            ).fallbackToDestructiveMigration().build()
        }

        internal val shadowInstance = AtomicReference(originalInstance)

        val instance: AppStorageManager
            get() = shadowInstance.get()

//        val databaseExecutor: ExecutorService by lazy {
//            SingleThreadExecutor()
//        }

      //  val databaseScheduler = Schedulers.from(databaseExecutor)


    }

    abstract fun asmaUlHusnaDao(): AsmaUlHusnaDao
    abstract fun surahDao(): SurahDao

}
