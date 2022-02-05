package ru.vdv.myapp.myreadersdiary.ui.users.recovery

import android.graphics.drawable.AnimatedVectorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ru.vdv.myapp.myreadersdiary.R
import ru.vdv.myapp.myreadersdiary.databinding.FragmentUserRestoringAccessBinding
import ru.vdv.myapp.myreadersdiary.ui.common.BaseFragment

class RestoringUserAccessFragment : BaseFragment<FragmentUserRestoringAccessBinding>() {
    private lateinit var viewModel: RestoringUserAccessViewModel
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
        viewModel = ViewModelProvider(this)[RestoringUserAccessViewModel::class.java]
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
        val btnStartRestoring = binding.btnRestoringUserAccess
        val username = binding.username

        //подписываемся на изменения статуса формы
        viewModel.loginFormState.observe(viewLifecycleOwner, Observer {
            btnStartRestoring.isEnabled = it.isDataValid
            username.error = it?.usernameError?.let(this::getString)
        })
    }

    private fun setFabStateLoading() {
        val icon = avd(R.drawable.ic_cached_rotate_black_24dp_adv)
        fab.setImageDrawable(icon)
        icon.start()
        fab.setOnClickListener {
            //никаких действий на режим загрузки не назначено
        }
    }
}