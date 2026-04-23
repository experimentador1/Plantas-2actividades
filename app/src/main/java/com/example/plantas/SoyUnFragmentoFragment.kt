package com.example.plantas

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.google.android.material.button.MaterialButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SoyUnFragmentoFragment : Fragment(R.layout.fragment_soy_un_fragmento) {

    private val viewModel: Actividad2ViewModel by activityViewModels()
    private var currentVideoId: String = ""
    private var youtubePlayer: YouTubePlayer? = null
    private var requiresExternalPlayback: Boolean = false
    private var youtubePlayerView: YouTubePlayerView? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val txtTitulo = view.findViewById<TextView>(R.id.txtTitulo)
        val btnAbrirVideoYoutube = view.findViewById<MaterialButton>(R.id.btnAbrirVideoYoutube)
        val fabPlayVideo = view.findViewById<FloatingActionButton>(R.id.fabPlayVideo)
        val playerView = view.findViewById<YouTubePlayerView>(R.id.youtubePlayerView)
        youtubePlayerView = playerView
        currentVideoId = viewModel.uiState.value.videoId
        lifecycle.addObserver(playerView)

        playerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(inicializadoPlayer: YouTubePlayer) {
                youtubePlayer = inicializadoPlayer
                inicializadoPlayer.cueVideo(currentVideoId, 0f)
                btnAbrirVideoYoutube.isEnabled = true
                fabPlayVideo.isEnabled = true
            }

            override fun onStateChange(
                youTubePlayer: YouTubePlayer,
                state: PlayerConstants.PlayerState
            ) {
                if (state == PlayerConstants.PlayerState.PLAYING) {
                    fabPlayVideo.visibility = View.GONE
                } else if (
                    state == PlayerConstants.PlayerState.PAUSED ||
                    state == PlayerConstants.PlayerState.ENDED ||
                    state == PlayerConstants.PlayerState.VIDEO_CUED
                ) {
                    fabPlayVideo.visibility = View.VISIBLE
                }
            }

            override fun onError(
                youTubePlayer: YouTubePlayer,
                error: PlayerConstants.PlayerError
            ) {
                if (error == PlayerConstants.PlayerError.VIDEO_NOT_PLAYABLE_IN_EMBEDDED_PLAYER) {
                    requiresExternalPlayback = true
                    btnAbrirVideoYoutube.text = getString(R.string.abrir_video_en_youtube)
                    fabPlayVideo.visibility = View.VISIBLE
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.video_no_permite_embebido),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        })

        btnAbrirVideoYoutube.isEnabled = false
        fabPlayVideo.isEnabled = false

        fun reproducirVideo() {
            val player = youtubePlayer
            if (requiresExternalPlayback) {
                abrirVideoExterno(currentVideoId)
            } else if (player != null) {
                player.loadVideo(currentVideoId, 0f)
            } else {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.no_se_pudo_abrir_video),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        btnAbrirVideoYoutube.setOnClickListener {
            reproducirVideo()
        }
        fabPlayVideo.setOnClickListener { reproducirVideo() }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState
                .flowWithLifecycle(viewLifecycleOwner.lifecycle)
                .collect { state ->
                    txtTitulo.text = state.titulo
                    currentVideoId = state.videoId
                    requiresExternalPlayback = false
                    btnAbrirVideoYoutube.text = getString(R.string.reproducir_video_embebido)
                    fabPlayVideo.visibility = View.VISIBLE
                    youtubePlayer?.cueVideo(state.videoId, 0f)
                }
        }
    }

    private fun abrirVideoExterno(videoId: String) {
        val appIntent = Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:$videoId"))
        val webIntent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse("https://www.youtube.com/watch?v=$videoId")
        )

        try {
            startActivity(appIntent)
        } catch (_: ActivityNotFoundException) {
            try {
                startActivity(webIntent)
            } catch (_: ActivityNotFoundException) {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.no_se_pudo_abrir_video),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onDestroyView() {
        youtubePlayerView?.let { playerView ->
            lifecycle.removeObserver(playerView)
            playerView.release()
        }
        youtubePlayerView = null
        youtubePlayer = null
        super.onDestroyView()
    }
}
