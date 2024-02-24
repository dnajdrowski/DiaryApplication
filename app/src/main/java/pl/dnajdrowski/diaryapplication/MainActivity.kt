package pl.dnajdrowski.diaryapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import pl.dnajdrowski.diaryapplication.ui.theme.DiaryApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DiaryApplicationTheme {

            }
        }
    }
}