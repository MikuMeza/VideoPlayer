package com.project.videoplayerapplication

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.project.videoplayerapplication.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity(), Player.Listener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var simpleExoplayer: SimpleExoPlayer

    private lateinit var videoView: PlayerView
    private lateinit var exoPlayer: ExoPlayer
    private lateinit var context: Context

    private var playbackPosition: Long = 0
    private val mp4Url = "https://bitmovin-a.akamaihd.net/content/sintel/hls/playlist.m3u8"

    //    private val dashUrl = "https://storage.googleapis.com/wvmedia/clear/vp9/tears/tears_uhd.mpd"
    private val urlList = listOf(mp4Url to "default")

    private val dataSourceFactory: DataSource.Factory by lazy {
        DefaultDataSourceFactory(this, "exoplayer-sample")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

//        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
        initializePlayer()
    }

    override fun onStop() {
        super.onStop()
        releasePlayer()
    }

    private fun initializePlayer() {
        simpleExoplayer = SimpleExoPlayer.Builder(this).build()
//        val randomUrl = urlList.random()
        preparePlayer(mp4Url, " ")
        binding.exoplayerView.player = simpleExoplayer
        simpleExoplayer.seekTo(playbackPosition)
        simpleExoplayer.playWhenReady = true
        simpleExoplayer.addListener(this)
    }

    private fun buildMediaSource(uri: Uri, type: String): MediaSource {
//        return if (type == "dash") {
//           return DashMediaSource.Factory(dataSourceFactory)
//                    .createMediaSource(uri)
//        } else {
        return ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(MediaItem.fromUri(uri))
//        }
    }

    private fun preparePlayer(videoUrl: String, type: String) {
//        val uri = Uri.parse(videoUrl)
//        val mediaSource = buildMediaSource(uri, type)
//        simpleExoplayer.setMediaSource(mediaSource)
//        simpleExoplayer.prepare()

        // Build the media item.
        // Build the media item.
        val mediaItem: MediaItem = MediaItem.fromUri(videoUrl)
// Set the media item to be played.
// Set the media item to be played.
        simpleExoplayer.setMediaItem(mediaItem)
// Prepare the player.
// Prepare the player.
        simpleExoplayer.prepare()
// Start the playback.
// Start the playback.
        simpleExoplayer.play()
    }

    private fun releasePlayer() {
        playbackPosition = simpleExoplayer.currentPosition
        simpleExoplayer.release()
    }

    override fun onPlayerError(error: ExoPlaybackException) {
        // handle error
    }

    override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
        if (playbackState == Player.STATE_BUFFERING)
            binding.progressBar.visibility = View.VISIBLE
        else if (playbackState == Player.STATE_READY || playbackState == Player.STATE_ENDED)
            binding.progressBar.visibility = View.INVISIBLE
    }

//    override fun onIsPlayingChanged(isPlaying: Boolean) {
//        if(isPlaying)
//            binding.progressBar.visibility = View.INVISIBLE
//        else
//            binding.progressBar.visibility = View.VISIBLE
//    }
}

    //Setting Up Exoplayer
//

//}  private fun SetupPlayer() {
//        val simpleExoPlayer: SimpleExoPlayer
//        // Create a data source factory.
//        dataSourceFactory = DefaultHttpDataSourceFactory(Util.getUserAgent(this, applicationInfo.loadLabel(packageManager).toString()))
//        // Passing Load Control
//        var loadControl = DefaultLoadControl.Builder()
//                .setBufferDurationsMs(25000, 50000, 1500, 2000).createDefaultLoadControl()
//        @ExtensionRendererMode val extensionRendererMode = DefaultRenderersFactory.EXTENSION_RENDERER_MODE_PREFER
//        var renderersFactory = DefaultRenderersFactory(this).setExtensionRendererMode(extensionRendererMode)
//
//// Create a progressive media source pointing to a stream uri.
//        var mediaSource = ProgressiveMediaSource.Factory(dataSourceFactory)
//                .createMediaSource(Uri.parse(url_to_stream))
//        // Create a player instance.
//        simpleExoPlayer = SimpleExoPlayer.Builder(this, renderersFactory).setLoadControl(loadControl).build()
//        // Prepare the player with the media source.
//        simpleExoPlayer.prepare(mediaSource, true, true)
//    }