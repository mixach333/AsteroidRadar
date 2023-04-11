package com.udacity.asteroidradar.presetnation.login

import android.app.Activity.RESULT_OK
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.FirebaseAuth
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.FragmentLoginBinding
import com.udacity.asteroidradar.presetnation.MainViewModel


class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val viewModel: MainViewModel by activityViewModels()

    private val signInLauncher = registerForActivityResult(
        FirebaseAuthUIActivityResultContract()
    ) { res ->
        this.onSignInResult(res)
    }
    private val providers = arrayListOf(
        AuthUI.IdpConfig.GoogleBuilder().build(), AuthUI.IdpConfig.AnonymousBuilder().build()
    )


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeAuthenticationState()
    }


    private fun launchSignInFlow() {
        val signInIntent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .build()
        signInLauncher.launch(signInIntent)
        // Give users the option to sign in / register with their email or Google account.
        // If users choose to register with their email,
        // they will need to create a password as well.
    }

    private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {
        if (result.resultCode == RESULT_OK) {
            Log.d(
                "onActivityResultLogin",
                "Login successful ${FirebaseAuth.getInstance().currentUser?.displayName}"
            )
        } else {
            Log.d("onActivityResultLogin", "Login unsuccessful: ${result.idpResponse?.error}")
        }

    }

    private fun observeAuthenticationState() {
        binding.loginLogoutButton.setOnClickListener {
            launchSignInFlow()
        }
        viewModel.authenticationState.observe(viewLifecycleOwner) { authenticationState ->
            if (authenticationState == AuthenticationState.AUTHENTICATED) findNavController().navigate(R.id.action_loginFragment_to_mainFragment)
        }
    }
}