package ru.vdv.myapp.myreadersdiary.ui.users.login

import android.graphics.drawable.AnimatedVectorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.annotation.StringRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ru.vdv.myapp.myreadersdiary.R
import ru.vdv.myapp.myreadersdiary.databinding.FragmentUserLoginBinding
import ru.vdv.myapp.myreadersdiary.ui.common.BaseFragment
import ru.vdv.myapp.myreadersdiary.ui.common.afterTextChanged

class UserLoginFragment : BaseFragment<FragmentUserLoginBinding>() {
    private lateinit var viewModel: UserLoginViewModel
    private lateinit var fab: FloatingActionButton
    private lateinit var bottomAppBar: BottomAppBar
    private val avd = { iconRes: Int ->
        AppCompatResources.getDrawable(
            requireContext(),
            iconRes
        ) as AnimatedVectorDrawable
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this)[UserLoginViewModel::class.java]
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btnLogin = binding.btnLogin
        val username = binding.username
        val password = binding.password

        //подписываемся на изменения статуса формы
        viewModel.loginFormState.observe(viewLifecycleOwner, Observer {
            btnLogin.isEnabled = it.isDataValid
            username.error = it?.usernameError?.let(this::getString)
            password.error = it?.passwordError?.let(this::getString)
        })

        binding.username.editText?.afterTextChanged {
            Log.d(TAG, "Сработал afterTextChanged на USERNAME")
            viewModel.loginDataChanged(
                username.editText?.text.toString(),
                password.editText?.text.toString()
            )
        }

        binding.password.editText?.apply {
            afterTextChanged {
                Log.d(TAG, "Сработал afterTextChanged на password")
                viewModel.loginDataChanged(
                    username.editText?.text.toString(),
                    password.editText?.text.toString()
                )
            }

            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE -> {
                        Log.d(TAG, "Сработал setOnEditorActionListenerна password")
                        viewModel.login(
                            username.editText?.text.toString(),
                            password.editText?.text.toString()
                        )
                    }
                }
                false
            }
        }

        binding.btnRegister.setOnClickListener {
            view.findNavController().navigate(R.id.nav_user_registration_fragment)
        }
        binding.btnRestoreAccess.setOnClickListener {
            view.findNavController().navigate(R.id.nav_restoring_user_access_fragment)
        }
    }

    override fun onStart() {
        super.onStart()
        fab = requireActivity().findViewById(R.id.fab)
        bottomAppBar = requireActivity().findViewById(R.id.bottomAppBar)
        setFabStateLoading()
    }

    private fun setFabStateLoading() {
        val icon = avd(R.drawable.ic_cached_rotate_black_24dp_adv)
        fab.setImageDrawable(icon)
        icon.start()
        fab.setOnClickListener {
            //никаких действий на режим загрузки не назначено
        }
    }

    private fun showLoginFailed(@StringRes errorString: Int) {
        //выводится сообщение о неверном вводе логина или пароля
    }
}