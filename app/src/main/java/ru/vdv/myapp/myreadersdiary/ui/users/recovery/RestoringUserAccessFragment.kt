package ru.vdv.myapp.myreadersdiary.ui.users.recovery

import android.graphics.drawable.AnimatedVectorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.appcompat.content.res.AppCompatResources
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ru.vdv.myapp.myreadersdiary.R
import ru.vdv.myapp.myreadersdiary.databinding.FragmentUserRestoringAccessBinding
import ru.vdv.myapp.myreadersdiary.ui.common.BaseFragment
import ru.vdv.myapp.myreadersdiary.ui.common.afterTextChanged

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
        setFabStateBack()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btnStartRestoring = binding.btnRestoringUserAccess
        val username = binding.username

        //подписываемся на изменения статуса формы
        viewModel.loginFormState.observe(viewLifecycleOwner, Observer {
            if (it.isDataValid) {
                username.error = null
                username.setEndIconDrawable(R.drawable.baseline_check_black_24dp)
                btnStartRestoring.isEnabled = true
            } else {
                btnStartRestoring.isEnabled = false
                Log.d(TAG, "Показываю ошибку")
                username.error = it?.usernameError?.let(this::getString)
            }
        })

        username.editText?.apply {
            afterTextChanged {
                Log.d(TAG, "Меняю на крутилку")
                username.error = null
                val icon = avd(R.drawable.ic_cached_rotate_black_24dp_adv)
                username.endIconDrawable = icon
                icon.start()
                viewModel.loginDataChanged(username.editText?.text.toString())
            }

            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE -> {
                        viewModel.startRestoreAccess(
                            username.editText?.text.toString()
                        )
                    }
                }
                false
            }
        }
    }

//    private fun setFabStateLoading() {
//        val icon = avd(R.drawable.ic_cached_rotate_black_24dp_adv)
//        fab.setImageDrawable(icon)
//        icon.start()
//        fab.setOnClickListener {
//            //никаких действий на режим загрузки не назначено
//        }
//    }
//
//    private fun setFabStateOk(username: String) {
//        val icon = avd(R.drawable.ic_cached_rotate_black_24dp_adv)
//        fab.setImageDrawable(icon)
//        icon.start()
//        fab.setOnClickListener {
//            viewModel.startRestoreAccess(username)
//        }
//    }

    private fun setFabStateBack() {
        val icon = avd(R.drawable.ic_cached_to_back_black_24dp_adv)
        fab.setImageDrawable(icon)
        icon.start()
        fab.setOnClickListener {
            view?.findNavController()?.navigateUp()
        }
    }
}