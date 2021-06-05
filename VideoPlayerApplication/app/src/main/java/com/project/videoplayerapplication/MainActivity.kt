package com.project.videoplayerapplication


import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.extractor.ExtractorsFactory
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.trackselection.TrackSelector
import com.google.android.exoplayer2.upstream.BandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import com.project.videoplayerapplication.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var exoPlayer: SimpleExoPlayer
//    private val videoURL = "https://bitmovin-a.akamaihd.net/content/sintel/hls/playlist.m3u8"
    val videoURL =
        "https://media.geeksforgeeks.org/wp-content/uploads/20201217163353/Screenrecorder-2020-12-17-16-32-03-350.mp4";


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        try {
//            val trackSelector = DefaultTrackSelector()
//            val exoPlayer = SimpleExoPlayer.newSimpleInstance(baseContext,trackSelector)
//            exoPlayerView?.player = exoPlayer



            //bandwisthmeter is used for getting default bandwidth
            val bandwidthMeter = DefaultBandwidthMeter()
            // track selector is used to navigate between video using a default seekbar.
            val trackSelector =
                DefaultTrackSelector()
            //we are ading our track selector to exoplayer.
            exoPlayer = ExoPlayerFactory.newSimpleInstance(this, trackSelector)
            // we are parsing a video url and parsing its video uri.
            val videouri = Uri.parse(videoURL)
            // we are creating a variable for datasource factory and setting its user agent as 'exoplayer_view'
            val dataSourceFactory = DefaultHttpDataSourceFactory("exoplayer_video")
            // we are creating a variable for extractor factory and setting it to default extractor factory.
            val extractorsFactory: ExtractorsFactory = DefaultExtractorsFactory()
            //we are creating a media source with above variables and passing our event handler as null,
//            val mediaSource = HlsMediaSource(videouri, dataSourceFactory, null, null)
            val mediaSource = ExtractorMediaSource(videouri, dataSourceFactory,extractorsFactory, null, null)
            //inside our exoplayer view we are setting our player
            binding.exoplayerView.player = exoPlayer
            //we are preparing our exoplayer with media source.
            exoPlayer.prepare(mediaSource)
            //we are setting our exoplayer when it is ready.
            exoPlayer.playWhenReady = true
        } catch (e: Exception) {
            // below line is used for handling our errors.
            Log.e("TAG", "Error : $e")
        }
    }

}