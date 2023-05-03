package com.example.stories101.fragments

import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import android.widget.VideoView
import androidx.fragment.app.Fragment
import com.example.stories101.R
import com.example.stories101.databinding.FragmentVideoBinding

class VideoFragment : Fragment() {

    private lateinit var videoView: VideoView
    private var videoUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_video, container, false)
        videoView = view.findViewById(R.id.video_view)

        // Set the video URI (replace "your_video_file_name" with your actual video file name)
        videoUri = Uri.parse("android.resource://" + requireActivity().packageName + "/" + R.raw.kids_story)

        // Set the media controller
        val mediaController = MediaController(requireContext())
        mediaController.setMediaPlayer(videoView)
        mediaController.setAnchorView(videoView)

        videoView.setMediaController(mediaController)

        return view
    }

    override fun onStart() {
        super.onStart()

        // Start the video
        videoView.setVideoURI(videoUri)
        videoView.start()
    }

    override fun onStop() {
        super.onStop()

        // Stop the video when the fragment is stopped
        videoView.stopPlayback()
    }
}
