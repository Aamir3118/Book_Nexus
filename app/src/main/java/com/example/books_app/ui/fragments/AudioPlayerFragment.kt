package com.example.books_app.ui.fragments

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

class AudioPlayerFragment : BaseFragment() {

    private val audioViewModel: MainViewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }

    private var selectedEpisode: Episode? = null
    private var isPrepared: Boolean = false
    private var isPlaying: Boolean = false
    private var playbackPosition: Int = 0
    private var start_txt:String = "0:00"
    lateinit var mediaPlayer: MediaPlayer
    private var binding: FragmentAudioPlayerBinding? = null
    private lateinit var nextEpisode: Episode

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
            val audio_len_min = (selectedEpisode!!.audioLengthSec / 60)
            val remainingSeconds = (selectedEpisode!!.audioLengthSec % 60)
            val final_audio_len = String.format("%02d:%02d", audio_len_min, remainingSeconds)

            binding?.bgImg?.let { it1 ->
                Glide.with(requireContext())
                    .load(it.image)
                    .into(it1)
            }

            binding?.title?.text = selectedEpisode!!.title

            binding?.icRound?.setOnClickListener {
                val action =
                    AudioPlayerFragmentDirections.actionAudioPlayerFragment2ToDetailPageFragment2(
                        selectedEpisode
                    )
                Navigation.findNavController(it).navigate(action)
            }

            binding?.end?.text = "-$final_audio_len"

            binding?.fluentSkipBack?.setOnClickListener {
                moveBackward10sec()
            }
            binding?.fluentSkipForward?.setOnClickListener {
                moveForward10Sec()
            }

            binding?.skipBack?.setOnClickListener {
                mediaPlayer.stop()
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
                mediaPlayer.stop()
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
        nextEpisode = episode
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
        binding?.progress?.visibility = View.VISIBLE
        binding?.playStart?.visibility = View.GONE
        binding?.playStart?.let { img ->
            Glide.with(requireContext())
                .load(R.drawable.uil_pause)
                .into(img)
        }

        mediaPlayer.setOnCompletionListener {
            // Called when the playback is completed
            isPlaying = false
            binding?.playStart?.let { img ->
                Glide.with(requireContext())
                    .load(R.drawable.uil_play)
                    .into(img)
            }
            binding?.progressBarHorizontal?.progress = 0
            binding?.start?.text = start_txt
        }

        try {
            mediaPlayer.reset()
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)
            mediaPlayer.setDataSource(nextEpisode.audio)
            mediaPlayer.prepareAsync()

            mediaPlayer.setOnPreparedListener {
                isPrepared = true
                binding?.progress?.visibility = View.GONE
                binding?.playStart?.visibility = View.VISIBLE

                // Get the total duration of the audio
                val totalDuration = mediaPlayer.duration
                binding?.progressBarHorizontal?.max = totalDuration

                binding?.progressBarHorizontal?.setOnSeekBarChangeListener(object :
                    SeekBar.OnSeekBarChangeListener {
                    override fun onProgressChanged(
                        seekBar: SeekBar?,
                        progress: Int,
                        fromUser: Boolean
                    ) {
                        if (fromUser) {
                            val currentTime = formatTime(progress)
                            binding?.start?.text = currentTime
                            mediaPlayer.seekTo(progress)
                        }
                    }

                    override fun onStartTrackingTouch(seekBar: SeekBar?) {}

                    override fun onStopTrackingTouch(seekBar: SeekBar?) {}

                })
                val endTime = formatTime(totalDuration)
                binding?.end?.text = "-$endTime"
                if (playbackPosition > 0) {
                    mediaPlayer.seekTo(playbackPosition)
                    playbackPosition = 0
                } else {
                    mediaPlayer.seekTo(0)
                }
                mediaPlayer.start()

                startProgressUpdater(totalDuration)
            }
            mediaPlayer.setOnErrorListener { _, _, _ ->
                // Handle error
                Log.e("Audio", "Error during MediaPlayer preparation")
                isPrepared = false
                binding?.progress?.visibility = View.GONE
                binding?.playStart?.visibility = View.VISIBLE
                false
            }
        } catch (e: Exception) {
            Log.e("Audio", "Error preparing MediaPlayer: ${e.message}")
            isPrepared = false
            binding?.progress?.visibility = View.GONE
            binding?.playStart?.visibility = View.VISIBLE
        }
    }

    private fun startProgressUpdater(totalDuration: Int) {
        val handler = android.os.Handler()
        val updateProgress = object : Runnable {
            override fun run() {
                updateProgress()
                val currentTime = formatTime(mediaPlayer.currentPosition)
                binding?.start?.text = currentTime

                val progress = mediaPlayer.currentPosition
                binding?.progressBarHorizontal?.progress = progress

                if (mediaPlayer.currentPosition >= totalDuration) {
                    binding?.playStart?.let { img ->
                        Glide.with(requireContext())
                            .load(R.drawable.uil_play)
                            .into(img)
                    }
                    binding?.progressBarHorizontal?.progress = 0
                } else {
                    handler.postDelayed(this, 1000)
                }
            }
        }

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
            playbackPosition = mediaPlayer.currentPosition
            mediaPlayer.pause()
            binding?.progressBarHorizontal?.progress = playbackPosition
            Log.d("Audio", "Audio paused")
        } else {
            Log.d("Audio", "Audio not played")
        }
        binding?.progress?.visibility = View.GONE
    }

    private fun moveForward10Sec() {
        if (mediaPlayer.isPlaying) {
            val newPosition = mediaPlayer.currentPosition + (10 * 1000)
            if (newPosition < mediaPlayer.duration) {
                mediaPlayer.seekTo(newPosition)
                updateProgress()
            }
        }
    }

    private fun moveBackward10sec() {
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
