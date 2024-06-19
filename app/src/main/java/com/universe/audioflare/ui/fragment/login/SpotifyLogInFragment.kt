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

    // Assuming you have a variable to hold the sp_dc token
    private lateinit var spdcToken: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSpotifyLogInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Assign the sp_dc token value
        spdcToken = "SP_DC_TOKEN_CHANGE_IT_FROM_HERE" // Replace with actual token value

        // Example of how you might use the token:
        // Save it in settingsViewModel for further use
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
