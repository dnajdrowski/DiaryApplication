package pl.dnajdrowski.diaryapplication.presentation.screens.write

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.mongodb.kbson.ObjectId
import pl.dnajdrowski.diaryapplication.data.repository.MongoDB
import pl.dnajdrowski.diaryapplication.model.Diary
import pl.dnajdrowski.diaryapplication.model.Mood
import pl.dnajdrowski.diaryapplication.util.Constants.WRITE_SCREEN_ARGUMENT_KEY
import pl.dnajdrowski.diaryapplication.util.RequestState

class WriteViewModel(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    var uiState by mutableStateOf(UiState())
        private set

    init {
        getDiaryIdArgument()
        fetchSelectedDiary()
    }

    private fun getDiaryIdArgument() {
        uiState = uiState.copy(
            selectedDiaryId = savedStateHandle.get<String>(
                key = WRITE_SCREEN_ARGUMENT_KEY
            )
        )
    }

    private fun fetchSelectedDiary() {
        if (uiState.selectedDiaryId != null) {
            viewModelScope.launch {
                val diary = MongoDB.getSelectedDiary(
                    diaryId = ObjectId.invoke(uiState.selectedDiaryId!!)
                )
                if (diary is RequestState.Success) {
                    setSelectedDiary(diary = diary.data)
                    setTitle(title = diary.data.title)
                    setDescription(description = diary.data.description)
                    setMood(mood = Mood.valueOf(diary.data.mood))
                } else {
                    Log.d("DanielTest", diary.toString())
                }
            }
        }
    }

    fun setSelectedDiary(diary: Diary) {
        uiState = uiState.copy(
            selectedDiary = diary
        )
    }

    fun setTitle(title: String) {
        uiState = uiState.copy(
            title = title
        )
    }

    fun setDescription(description: String) {
        uiState = uiState.copy(
            description = description
        )
    }

    fun setMood(mood: Mood) {
        uiState = uiState.copy(
            mood = mood
        )
    }
}

data class UiState(
    val selectedDiaryId: String? = null,
    val selectedDiary: Diary? = null,
    val title: String = "",
    val description: String = "",
    val mood: Mood = Mood.Neutral
)