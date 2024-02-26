package pl.dnajdrowski.diaryapplication.presentation.screens.write

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import pl.dnajdrowski.diaryapplication.model.Diary

@OptIn(ExperimentalFoundationApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun WriteScreen(
    pagerState: PagerState,
    selectedDiary: Diary?,
    onDeleteConfirmed: () -> Unit,
    onBackPressed: () -> Unit
) {
    Scaffold(
        topBar = {
            WriteTopBar(
                selectedDiary = selectedDiary,
                onBackPressed = onBackPressed,
                onDeleteConfirmed = onDeleteConfirmed
            )
        },
        content = {
            WriteContent(
                parerState = pagerState,
                paddingValues = it,
                title = "",
                onTitleChanged = {},
                description = "",
                onDescriptionChanged = {}
            )

        }
    )

}