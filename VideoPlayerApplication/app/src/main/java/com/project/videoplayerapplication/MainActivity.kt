package com.project.videoplayerapplication


import android.app.Activity
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.hls.HlsMediaSource
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
        NetworkLiveData.observe(this, { status ->
            if (status)
                mPlayer!!.prepare(buildMediaSource(), false, false)
            else
                Toast.makeText(this,"No network available",Toast.LENGTH_LONG).show()
        })

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

        val dataSourceFactory = DefaultHttpDataSourceFactory("exo-player")
        val hlsMediaSource =
            HlsMediaSource.Factory(dataSourceFactory).createMediaSource(Uri.parse(hlsUrl))

        return hlsMediaSource
    }

}