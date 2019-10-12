package uz.islom.io.db

import androidx.room.Database
import androidx.room.RoomDatabase
import uz.islom.model.db.Content
import uz.islom.model.db.User

@Database(entities = [Content::class, User::class],
        exportSchema = false,
        version = 2)
abstract class AppStorage : RoomDatabase() {

}
