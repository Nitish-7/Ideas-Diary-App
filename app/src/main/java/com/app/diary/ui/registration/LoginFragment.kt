package com.app.diary.ui.registration

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.app.diary.R
import com.app.diary.databinding.FragmentLoginBinding
import com.app.diary.models.UserRequest
import com.app.diary.util.ApiResultHandler
import com.app.diary.util.Helper
import com.app.diary.util.UserTokenManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val registrationViewModel by viewModels<RegistrationViewModel>()

    @Inject
    lateinit var userTokenManager: UserTokenManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.navController=findNavController()
        binding.registrationViewModel=registrationViewModel
        binding.lifecycleOwner=viewLifecycleOwner
        bindObservers()
    }

    private fun bindObservers() {
        registrationViewModel.userResponseLiveData.observe(viewLifecycleOwner, Observer {
            binding.progressBar.isVisible = false
            when (it) {
                is ApiResultHandler.Failure -> {
                    binding.txtError.text = it.message
                }
                is ApiResultHandler.Loading -> {
                    binding.progressBar.isVisible = true
                }
                is ApiResultHandler.Success -> {
                    userTokenManager.saveToken(it.data!!.token)
                    findNavController().navigate(R.id.action_loginFragment_to_notesFragment)
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}