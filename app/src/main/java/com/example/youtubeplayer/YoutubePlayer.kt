package com.example.youtubeplayer

import android.view.ViewGroup
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.LifecycleOwner
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

@Composable
fun YoutubePlayer(
    youtubeVideoId: String,
    lifecycleOwner: LifecycleOwner
) {

    AndroidView(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
            .clip(RoundedCornerShape(16.dp)),
        factory = { context ->

            val iFramePlayerOptions = IFramePlayerOptions.Builder()
                .controls(0)
                .rel(0)
                .ivLoadPolicy(1)
                .ccLoadPolicy(1)
                .build()

            YouTubePlayerView(context = context).apply {
                this.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
                lifecycleOwner.lifecycle.addObserver(this)

                this.enableAutomaticInitialization = false

                initialize(
                    youTubePlayerListener = object : AbstractYouTubePlayerListener() {
                        override fun onReady(youTubePlayer: YouTubePlayer) {
                            youTubePlayer.loadVideo(youtubeVideoId, 0f)
                        }
                    },
                    handleNetworkEvents = true,
                    playerOptions = iFramePlayerOptions
                )
            }
        })
}