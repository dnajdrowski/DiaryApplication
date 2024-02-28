package pl.dnajdrowski.diaryapplication.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import pl.dnajdrowski.diaryapplication.data.database.entity.ImageToDelete
import pl.dnajdrowski.diaryapplication.data.database.entity.ImageToUpload


@Database(
    entities = [ImageToUpload::class, ImageToDelete::class],
    version = 2,
    exportSchema = false
)
abstract class ImagesDatabase: RoomDatabase() {
    abstract fun imageToUploadDao(): ImageToUploadDao
    abstract fun imageToDeleteDao(): ImageToDeleteDao
}