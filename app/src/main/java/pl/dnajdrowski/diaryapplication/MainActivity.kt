package pl.dnajdrowski.diaryapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.google.firebase.FirebaseApp
import dagger.hilt.android.AndroidEntryPoint
import io.realm.kotlin.mongodb.App
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pl.dnajdrowski.diaryapplication.data.database.ImageToDeleteDao
import pl.dnajdrowski.diaryapplication.data.database.ImageToUploadDao
import pl.dnajdrowski.diaryapplication.navigation.Screen
import pl.dnajdrowski.diaryapplication.navigation.SetupNavGraph
import pl.dnajdrowski.diaryapplication.ui.theme.DiaryApplicationTheme
import pl.dnajdrowski.diaryapplication.util.Constants.APP_ID
import pl.dnajdrowski.diaryapplication.util.retryDeleteImageFromFirebase
import pl.dnajdrowski.diaryapplication.util.retryUploadingImageToFirebase
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var imageToUploadDao: ImageToUploadDao

    @Inject
    lateinit var imageToDeleteDao: ImageToDeleteDao

    private var keepSplashOpened = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().setKeepOnScreenCondition {
            keepSplashOpened
        }
        WindowCompat.setDecorFitsSystemWindows(window, false)
        FirebaseApp.initializeApp(this)
        setContent {
            DiaryApplicationTheme {
                val navController = rememberNavController()
                SetupNavGraph(
                    startDestination = getStartDestination(),
                    navController = navController,
                    onDataLoaded = {
                        keepSplashOpened = false
                    }
                )
            }
        }

        cleanupCheck(scope = lifecycleScope, imageToUploadDao = imageToUploadDao, imageToDeleteDao)
    }
}

private fun cleanupCheck(
    scope: CoroutineScope,
    imageToUploadDao: ImageToUploadDao,
    imageToDeleteDao: ImageToDeleteDao
) {
   scope.launch(Dispatchers.IO) {
       val result = imageToUploadDao.getAllImages()
       result.forEach {  imageToUpload ->
            retryUploadingImageToFirebase(
                imageToUpload = imageToUpload,
                onSuccess = {
                    scope.launch(Dispatchers.IO) {
                        imageToUploadDao.cleanupImage(imageToUpload.id)
                    }
                }
            )
       }

       val resulImagesToDelete = imageToDeleteDao.getAllImages()
       resulImagesToDelete.forEach {  imageToDelete ->
            retryDeleteImageFromFirebase(
                imageToDelete = imageToDelete,
                onSuccess = {
                    scope.launch(Dispatchers.IO) {
                        imageToDeleteDao.cleanupImage(imageToDelete.id)
                    }
                }
            )
       }
   }

}

private fun getStartDestination(): String {
    val user = App.Companion.create(APP_ID).currentUser
    return if (user != null && user.loggedIn) Screen.Home.route
    else Screen.Authentication.route
}