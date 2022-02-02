package ru.vdv.myapp.myreadersdiary.ui.users.login

import android.graphics.drawable.AnimatedVectorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ru.vdv.myapp.myreadersdiary.R
import ru.vdv.myapp.myreadersdiary.databinding.FragmentUserLoginBinding
import ru.vdv.myapp.myreadersdiary.ui.common.BaseFragment

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

    override fun onStart() {
        super.onStart()
        fab = requireActivity().findViewById(R.id.fab)
        bottomAppBar = requireActivity().findViewById(R.id.bottomAppBar)
        setFabStateLoading()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnRegister.setOnClickListener {
            view.findNavController().navigate(R.id.nav_user_registration_fragment)
        }
        binding.btnRestoreAccess.setOnClickListener {
            view.findNavController().navigate(R.id.nav_restoring_user_access_fragment)
        }
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