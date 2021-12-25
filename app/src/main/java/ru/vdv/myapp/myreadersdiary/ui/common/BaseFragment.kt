package ru.vdv.myapp.myreadersdiary.ui.common

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import ru.vdv.myapp.myreadersdiary.glide.GlideImageLoader
import java.lang.reflect.ParameterizedType

@Suppress("UNCHECKED_CAST")
abstract class BaseFragment<VB : ViewBinding> : Fragment() {
    protected val imageLoader = GlideImageLoader()
    private var _binding: VB? = null
    protected val binding: VB get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val type = javaClass.genericSuperclass
        Log.d("Моя проверка", "Получен тип: $type")
        val clazz = (type as ParameterizedType).actualTypeArguments[0] as Class<VB>
        Log.d("Моя проверка", "Получен аргумент: ${(type).actualTypeArguments[0]}")
        Log.d("Моя проверка", "Получен приведенный результат: $clazz")
        val method = clazz.getMethod(
            "inflate",
            LayoutInflater::class.java,
            ViewGroup::class.java,
            Boolean::class.java
        )
        _binding = method.invoke(null, layoutInflater, container, false) as VB
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}