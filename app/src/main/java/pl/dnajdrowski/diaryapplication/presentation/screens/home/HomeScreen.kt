package pl.dnajdrowski.diaryapplication.presentation.screens.home

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    onMenuClicked: () -> Unit,
    navigateToWrite: () -> Unit
) {
    Scaffold(modifier = Modifier
        .background(MaterialTheme.colorScheme.surface)
        .statusBarsPadding()
        .navigationBarsPadding(), topBar = {
        HomeTopBar(onMenuClicked = onMenuClicked)
    }, floatingActionButton = {
        FloatingActionButton(onClick = navigateToWrite) {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = "New Diary Icon"
            )
        }
    }, content = {

    })
}