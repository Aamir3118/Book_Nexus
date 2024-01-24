package com.example.books_app.ui.fragments

import android.media.AudioAttributes
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.example.books_app.R
import com.example.books_app.base.BaseFragment
import com.example.books_app.databinding.FragmentAudioPlayerBinding
import com.example.books_app.model.Episode
import com.example.books_app.ui.view_model.MainViewModel


/**
 * A simple [Fragment] subclass.
 * Use the [AudioPlayerFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AudioPlayerFragment : BaseFragment() {

    private val audioViewModel: MainViewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }

    private var selectedEpisode: Episode? = null
    private var isPlaying: Boolean = false
    private var playbackPosition: Int = 0
    lateinit var mediaPlayer: MediaPlayer
    private var binding: FragmentAudioPlayerBinding? = null
    private lateinit var nextEpisode:Episode

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            selectedEpisode = AudioPlayerFragmentArgs.fromBundle(it).selectedEpisode2
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAudioPlayerBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mediaPlayer = MediaPlayer()
        selectedEpisode?.let {
            setupAudioPlayer(it)
        }

        selectedEpisode?.let {
            val audio_len_min = (selectedEpisode!!.audioLengthSec/60)
            val remainingSeconds = (selectedEpisode!!.audioLengthSec%60)
            val final_audio_len = String.format("%02d:%02d",audio_len_min,remainingSeconds)

            binding?.bgImg?.let { it1 ->
                Glide.with(requireContext())
                    .load(it.image)
                    .into(it1)
            }

            binding?.title?.text = selectedEpisode!!.title

            binding?.icRound?.setOnClickListener {
                stopPlayback()
                val action = AudioPlayerFragmentDirections.actionAudioPlayerFragment2ToDetailPageFragment2(selectedEpisode)
                Navigation.findNavController(it).navigate(action)
            }

            binding?.end?.text = "-"+final_audio_len

            binding?.fluentSkipBack?.setOnClickListener {
                moveBackward10sec()
            }
            binding?.fluentSkipForward?.setOnClickListener {
                moveForward10Sec()
            }

            binding?.skipBack?.setOnClickListener {
                audioViewModel.observeEpisodes(this) { episodes ->
                    val currentIndex = episodes.indexOf(selectedEpisode)
                    val lastIndex = episodes.size - 1
                    val nextIndex = if (currentIndex > 0) {
                        currentIndex - 1
                    } else {
                        lastIndex
                    }
                    nextEpisode = episodes[nextIndex]
                    selectedEpisode = nextEpisode
                    setupAudioPlayer(nextEpisode)
                }
            }
            binding?.skipForward?.setOnClickListener {
                audioViewModel.observeEpisodes(this) { episodes ->
                    val currentIndex = episodes.indexOf(selectedEpisode)
                    val lastIndex = episodes.size - 1

                    val nextIndex = if (currentIndex < lastIndex) {
                        currentIndex + 1
                    } else {
                        0
                    }

                    nextEpisode = episodes[nextIndex]
                    selectedEpisode = nextEpisode
                    setupAudioPlayer(nextEpisode)
                }
            }
            binding?.frame81?.setOnClickListener {
                togglePlayBack()
            }
        }
    }

    private fun setupAudioPlayer(episode: Episode) {
        val audio_len_min = (episode.audioLengthSec / 60)
        val remainingSeconds = (episode.audioLengthSec % 60)
        val final_audio_len = String.format("%02d:%02d", audio_len_min, remainingSeconds)

        binding?.bgImg?.let { it1 ->
            Glide.with(requireContext())
                .load(episode.image)
                .into(it1)
        }

        binding?.title?.text = episode.title
        binding?.end?.text = "-$final_audio_len"
    }

    private fun togglePlayBack() {
        if (!isPlaying) {
            startPlayback()
        } else {
            pausePlayback()
        }
        isPlaying = !isPlaying
    }
    private fun startPlayback() {
        binding?.playStart?.let { img ->
            Glide.with(requireContext())
                .load(R.drawable.uil_pause)
                .into(img)
        }
        binding?.progress?.visibility = View.VISIBLE
        binding?.playStart?.visibility = View.GONE

        try {
            mediaPlayer.reset()
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)
            mediaPlayer.setDataSource(nextEpisode.audio)
            mediaPlayer.prepareAsync()

            mediaPlayer.setOnPreparedListener {
                binding?.progress?.visibility = View.GONE
                binding?.playStart?.visibility = View.VISIBLE

                // Get the total duration of the audio
                val totalDuration = mediaPlayer.duration
                binding?.progressBarHorizontal?.max = totalDuration

                binding?.progressBarHorizontal?.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
                    override fun onProgressChanged(
                        seekBar: SeekBar?,
                        progress: Int,
                        fromUser: Boolean
                    ) {
                        if (fromUser) {
                            // Update the current playback time TextView
                            val currentTime = formatTime(progress)
                            binding?.start?.text = currentTime
                            // Seek to the selected position in the audio file
                            mediaPlayer.seekTo(progress)
                        }
                    }

                    override fun onStartTrackingTouch(seekBar: SeekBar?) {

                    }

                    override fun onStopTrackingTouch(seekBar: SeekBar?) {

                    }

                })
                // Update the end time TextView
                val endTime = formatTime(totalDuration)
                binding?.end?.text = "-$endTime"

                mediaPlayer.seekTo(0)
                mediaPlayer.start()

                startProgressUpdater(totalDuration)

            }

        } catch (e: Exception) {
            Log.e("Audio", "Error preparing MediaPlayer: ${e.message}")
            binding?.progress?.visibility = View.GONE
            binding?.playStart?.visibility = View.VISIBLE
        }
    }

    private fun startProgressUpdater(totalDuration: Int) {
        val handler = android.os.Handler()
        val updateProgress = object : Runnable {
            override fun run() {
                updateProgress()
                // Update the current playback time TextView
                val currentTime = formatTime(mediaPlayer.currentPosition)
                binding?.start?.text = currentTime

                // Update the progress of the ProgressBar
                val progress = mediaPlayer.currentPosition
                binding?.progressBarHorizontal?.progress = progress

                // Check if the playback has reached the end
                if (mediaPlayer.currentPosition >= totalDuration) {
                    // Playback has reached the end, stop updating progress
                    binding?.playStart?.let { img ->
                        Glide.with(requireContext())
                            .load(R.drawable.uil_play)
                            .into(img)
                    }
                    binding?.progressBarHorizontal?.progress = 0
                } else {
                    // Schedule the next update
                    handler.postDelayed(this, 1000) // Update every second
                }
            }
        }

        // Start the progress updater
        handler.post(updateProgress)
    }

    private fun formatTime(milliseconds: Int): String {
        val minutes = (milliseconds / 1000) / 60
        val seconds = (milliseconds / 1000) % 60
        return String.format("%02d:%02d", minutes, seconds)
    }

    private fun pausePlayback() {
        binding?.playStart?.let { img ->
            Glide.with(requireContext())
                .load(R.drawable.uil_play)
                .into(img)
        }

        if (mediaPlayer.isPlaying) {
            mediaPlayer.pause()
            playbackPosition = mediaPlayer.currentPosition

            binding?.progressBarHorizontal?.progress = playbackPosition

            Log.d("Audio", "Audio paused")
        } else {
            Log.d("Audio", "Audio not played")
        }
        binding?.progress?.visibility = View.GONE
    }

    private fun stopPlayback() {
        if (mediaPlayer.isPlaying) {
            mediaPlayer.stop()
            mediaPlayer.release()
            isPlaying = false
            binding?.playStart?.let { img ->
                Glide.with(requireContext())
                    .load(R.drawable.uil_play)
                    .into(img)
            }
            binding?.progressBarHorizontal?.progress = 0
            binding?.progress?.visibility = View.GONE
        }
    }

    private fun moveForward10Sec(){
        if (mediaPlayer.isPlaying) {
            val newPosition = mediaPlayer.currentPosition + (10 * 1000) // 10 minutes in milliseconds
            if (newPosition < mediaPlayer.duration) {
                mediaPlayer.seekTo(newPosition)
                updateProgress()
            }
        }
    }
    private fun moveBackward10sec(){
        if (mediaPlayer.isPlaying) {
            val newPosition = mediaPlayer.currentPosition - (10 * 1000)
            if (newPosition >= 0) {
                mediaPlayer.seekTo(newPosition)
                updateProgress()
            } else {
                mediaPlayer.seekTo(0)
                updateProgress()
            }
        }
    }

    private fun updateProgress() {
        val currentTime = formatTime(mediaPlayer.currentPosition)
        binding?.start?.text = currentTime

        val progress = mediaPlayer.currentPosition
        binding?.progressBarHorizontal?.progress = progress
    }
}

