package com.example.youtubeplayer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import com.example.youtubeplayer.ui.theme.YouTubePlayerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val videos = listOf(
            "squtTxG0nCg",
            "zNI1SXzDKko",
            "LK86rn3viww",
            "DlZ4Fvh_Qy4",
            "tJ-F031lG_I"
        )
        setContent {
            YouTubePlayerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CubePager(
                        pageCount = videos.size - 1
                    ) {
                        YoutubePlayer(
                            youtubeVideoId = videos[it],
                            lifecycleOwner = LocalLifecycleOwner.current
                        )
                    }
                }
            }
        }
    }
}