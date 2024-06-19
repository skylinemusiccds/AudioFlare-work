package com.universe.audioflare.ui.fragment.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.universe.audioflare.R
import com.universe.audioflare.common.Config
import com.universe.audioflare.databinding.FragmentSpotifyLogInBinding
import com.universe.audioflare.extension.isMyServiceRunning
import com.universe.audioflare.service.SimpleMediaService
import com.universe.audioflare.viewModel.LogInViewModel
import com.universe.audioflare.viewModel.SettingsViewModel
import com.universe.audioflare.viewModel.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

@AndroidEntryPoint
class SpotifyLogInFragment : Fragment() {

    private var _binding: FragmentSpotifyLogInBinding? = null
    private val binding get() = _binding!!

    private val viewModel by activityViewModels<LogInViewModel>()
    private val settingsViewModel by activityViewModels<SettingsViewModel>()
    private val sharedViewModel by activityViewModels<SharedViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSpotifyLogInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Assuming sp_dc token is stored locally in viewModel or settingsViewModel
        val spdcToken = viewModel.getStoredSpotifySpdc() // Replace with actual method to retrieve token

        if (spdcToken.isNullOrEmpty()) {
            Toast.makeText(
                requireContext(),
                "Spotify login token not found.",
                Toast.LENGTH_SHORT
            ).show()
            findNavController().popBackStack()
            return
        }

        // Assuming you want to save the token in settingsViewModel for further use
        settingsViewModel.setSpotifySpdcToken(spdcToken)

        // Handle login success scenario
        settingsViewModel.setSpotifyLogIn(true)
        Toast.makeText(
            requireContext(),
            R.string.login_success,
            Toast.LENGTH_SHORT
        ).show()

        // Pop back to previous fragment
        findNavController().popBackStack()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }
}
