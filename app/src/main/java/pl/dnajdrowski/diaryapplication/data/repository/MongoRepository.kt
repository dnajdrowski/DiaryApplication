package pl.dnajdrowski.diaryapplication.data.repository

import kotlinx.coroutines.flow.Flow
import org.mongodb.kbson.ObjectId
import pl.dnajdrowski.diaryapplication.model.Diary
import pl.dnajdrowski.diaryapplication.util.RequestState
import java.time.LocalDate

typealias Diaries = RequestState<Map<LocalDate, List<Diary>>>

interface MongoRepository {
    fun configureTheRealm()
    fun getAllDiaries(): Flow<Diaries>
    fun getSelectedDiary(diaryId: ObjectId): RequestState<Diary>
}