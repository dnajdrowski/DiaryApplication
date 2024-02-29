package pl.dnajdrowski.diaryapplication.data.database

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import pl.dnajdrowski.diaryapplication.util.Constants.IMAGE_TO_DELETE_TABLE

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("CREATE TABLE `$IMAGE_TO_DELETE_TABLE` " +
                "(`id` INTEGER NOT NULL, " +
                "`remoteImagePath` TEXT NOT NULL, " +
                "PRIMARY KEY(`id`))")
    }
}