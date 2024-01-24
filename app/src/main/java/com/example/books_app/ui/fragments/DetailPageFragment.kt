package com.example.books_app.ui.fragments

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.books_app.R
import com.example.books_app.base.BaseFragment
import com.example.books_app.databinding.FragmentDetailPageBinding
import com.example.books_app.model.Episode
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * A simple [Fragment] subclass.
 * Use the [DetailPageFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DetailPageFragment : BaseFragment() {
    private var binding: FragmentDetailPageBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDetailPageBinding.inflate(inflater, container, false)
        return binding!!.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val args = DetailPageFragmentArgs.fromBundle(requireArguments())
        var selectedEpisode = args.selectedEpisode
        super.onViewCreated(view, savedInstanceState)
        binding?.frame742?.setOnClickListener {
            val action = DetailPageFragmentDirections.actionDetailPageFragment2ToAudioPlayerFragment2(selectedEpisode)
            Navigation.findNavController(it).navigate(action)
        }

        binding?.icRound?.setOnClickListener {
            val action = DetailPageFragmentDirections.actionDetailPageFragment2ToHomeScreen1Fragment()
            Navigation.findNavController(it).navigate(action)
        }

        if (selectedEpisode != null) {
            Log.d("Selected_episode",selectedEpisode.title)
            binding?.let {
                Glide.with(requireContext())
                    .load(selectedEpisode.image)
                    .into(it.book1)
            }

            binding?.projectMan?.text = selectedEpisode.title
            binding?.koryKogon?.text = "Published on:"

            val timestamp = selectedEpisode.pubDateMs
            val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val date = Date(timestamp)
            val final_date = sdf.format(date)
            binding?.aFanklinco?.text = final_date

            val audio_len_min = (selectedEpisode.audioLengthSec/60)
            val remainingSeconds = (selectedEpisode.audioLengthSec%60)
            val final_audio_len = String.format("%02d:%02d",audio_len_min,remainingSeconds)
            binding?.min?.text = "$final_audio_len min"

            binding?.desc?.text = selectedEpisode.description

            binding?.bgBook?.let { relativeLayout ->
            Glide.with(requireContext())
                .load(selectedEpisode.image)
                .into(object : CustomTarget<Drawable>() {
                    override fun onResourceReady(
                        resource: Drawable,
                        transition: Transition<in Drawable>?
                    ) {
                        relativeLayout.background = resource
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {
                        // Handle cleanup, if necessary.
                    }
                })
            }
        }
    }
}