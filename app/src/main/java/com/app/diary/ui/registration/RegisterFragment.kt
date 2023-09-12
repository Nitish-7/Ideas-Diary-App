package com.app.diary.ui.registration

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.app.diary.R
import com.app.diary.databinding.FragmentRegisterBinding
import com.app.diary.models.UserRequest
import com.app.diary.util.ApiResultHandler
import com.app.diary.util.Helper
import com.app.diary.util.UserTokenManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private val registrationViewModel by viewModels<RegistrationViewModel>()

    @Inject
    lateinit var userTokenManager: UserTokenManager
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        if(userTokenManager.getToken()!=null){
            findNavController().navigate(R.id.action_registerFragment_to_notesFragment)
        }
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner=viewLifecycleOwner
        binding.registrationViewModel=registrationViewModel
        handleCLicks()
        bindObservers()
    }

    private fun bindObservers() {
        registrationViewModel.userResponseLiveData.observe(viewLifecycleOwner, Observer {
            binding.progressBar.isVisible=false
            when(it){
                is ApiResultHandler.Failure -> {
                    binding.txtError.text=it.message
                }
                is ApiResultHandler.Loading -> {
                    binding.progressBar.isVisible=true
                }
                is ApiResultHandler.Success -> {
                    userTokenManager.saveToken(it.data!!.token)
                    findNavController().navigate(R.id.action_registerFragment_to_notesFragment)
                }
            }
        })
    }

    private fun handleCLicks() {
        binding.btnLogin.setOnClickListener { findNavController().navigate(R.id.action_registerFragment_to_loginFragment) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
