package com.project.videoplayerapplication


import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector.SelectionOverride
import com.google.android.exoplayer2.trackselection.MappingTrackSelector.MappedTrackInfo
import com.google.android.exoplayer2.ui.StyledPlayerView
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import com.google.android.exoplayer2.util.Assertions
import com.google.android.exoplayer2.util.Util


class MainActivity : AppCompatActivity() {
    private var mPlayer: SimpleExoPlayer? = null
    private lateinit var playerView: StyledPlayerView
    private var playWhenReady = true
    private var currentWindow = 0
    private var playbackPosition: Long = 0
    private val hlsUrl =
        "https://bitmovin-a.akamaihd.net/content/sintel/hls/playlist.m3u8"
//        private val hlsUrl="https://bitdash-a.akamaihd.net/content/MI201109210084_1/m3u8s/f08e80da-bf1d-4e3d-8899-f0f6155f6efa.m3u8"



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        playerView = findViewById(R.id.playerView)


    }

    private fun initPlayer() {
        mPlayer = SimpleExoPlayer.Builder(this).build()
        // Bind the player to the view.
        playerView.player = mPlayer
        mPlayer!!.playWhenReady = true
        mPlayer!!.seekTo(playbackPosition)
        mPlayer!!.prepare(buildMediaSource(), false, false)

        println("Length of track :" + mPlayer!!.currentTrackGroups)

    }

    override fun onStart() {
        super.onStart()
        if (Util.SDK_INT >= 24) {
            initPlayer()
        }
    }

    override fun onResume() {
        super.onResume()
        if (Util.SDK_INT < 24 || mPlayer == null) {
            initPlayer()
        }
    }

    override fun onPause() {
        super.onPause()
        if (Util.SDK_INT < 24) {
            releasePlayer()
        }
    }

    override fun onStop() {
        super.onStop()
        if (Util.SDK_INT >= 24) {
            releasePlayer()
        }
    }


    private fun releasePlayer() {
        if (mPlayer == null) {
            return
        }
        playWhenReady = mPlayer!!.playWhenReady
        playbackPosition = mPlayer!!.currentPosition
        currentWindow = mPlayer!!.currentWindowIndex
        mPlayer!!.release()
        mPlayer = null
    }

    private fun buildMediaSource(): MediaSource {
        val userAgent =
            Util.getUserAgent(playerView.context, playerView.context.getString(R.string.app_name))

        val dataSourceFactory = DefaultHttpDataSourceFactory("exo-player")
        val hlsMediaSource =
            HlsMediaSource.Factory(dataSourceFactory).createMediaSource(Uri.parse(hlsUrl))

        return hlsMediaSource
    }

//    fun setAudioTrack(track: Int) {
//        println("setAudioTrack: $track")
//        val mappedTrackInfo: MappedTrackInfo =
//            Assertions.checkNotNull(trackSelector.getCurrentMappedTrackInfo())
//        val parameters: DefaultTrackSelector.Parameters = trackSelector.getParameters()
//        val builder = parameters.buildUpon()
//        for (rendererIndex in 0 until mappedTrackInfo.rendererCount) {
//            val trackType = mappedTrackInfo.getRendererType(rendererIndex)
//            if (trackType == C.TRACK_TYPE_AUDIO) {
//                builder.clearSelectionOverrides(rendererIndex)
//                    .setRendererDisabled(rendererIndex, false)
//                val groupIndex = track - 1
//                val tracks = intArrayOf(0)
//                val override = SelectionOverride(groupIndex, *tracks)
//                builder.setSelectionOverride(
//                    rendererIndex,
//                    mappedTrackInfo.getTrackGroups(rendererIndex),
//                    override
//                )
//            }
//        }
//        trackSelector.setParameters(builder)
//        curentAudioTrack = track
//    }

}