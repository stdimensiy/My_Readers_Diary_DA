package ru.vdv.myapp.myreadersdiary.ui.books.createbook

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.AnimatedVectorDrawable
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Build
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.appcompat.content.res.AppCompatResources
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ru.vdv.myapp.myreadersdiary.R
import ru.vdv.myapp.myreadersdiary.databinding.CreateNewBookFragmentBinding
import ru.vdv.myapp.myreadersdiary.ui.common.BaseFragment
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

class CreateNewBookFragment : BaseFragment<CreateNewBookFragmentBinding>() {

    private var image: File? = null
    private var imageUri: Uri? = null
    private lateinit var fab: FloatingActionButton
    private val avd = { iconRes: Int ->
        AppCompatResources.getDrawable(
            requireContext(),
            iconRes
        ) as AnimatedVectorDrawable
    }
    private val camera =
        registerForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap ->
            binding.ivNewBookCover.setImageBitmap(bitmap)
            val bitmap2 = (binding.ivNewBookCover.drawable as BitmapDrawable).bitmap
            saveBitmapInStorage(bitmap2, requireContext())
            binding.ivNewBookCover.setImageBitmap(bitmap2)
        }
    private val gallery =
        registerForActivityResult(ActivityResultContracts.GetContent()) { galeryImageUri ->
            binding.ivNewBookCover.setImageURI(galeryImageUri)
            Log.d("Моя проверка", "Uri объекта: $galeryImageUri")
            imageUri = galeryImageUri
            imageUri?.let { image = File(it.toString()) }
        }

    private lateinit var viewModel: CreateNewBookViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[CreateNewBookViewModel::class.java]
        binding.btnStartCamera.setOnClickListener {
            camera.launch()
        }
        binding.btnStartMedia.setOnClickListener {
            gallery.launch("image/*")
        }
        binding.btnCropImage.setOnClickListener {
            performCrop()
        }
        binding.newBookTitle.setOnFocusChangeListener { v, hasFocus ->
            if(!hasFocus){
                Log.d(TAG,"фокус смещен ввод текста закончен")
                checkIsPossibleToSave()
            }
        }
        binding.newBookAuthor.setOnFocusChangeListener { v, hasFocus ->
            if(!hasFocus){
                Log.d(TAG,"фокус смещен ввод текста закончен")
                checkIsPossibleToSave()
            }
        }

        binding.tilNewBookTitle.setEndIconOnClickListener {
            binding.newBookTitle.setText("")
            checkIsPossibleToSave()
            Log.d(TAG,"Нажата кнопка зачистки наименования книги")
        }

        binding.tilNewBookAuthor.setEndIconOnClickListener {
            binding.newBookAuthor.setText("")
            checkIsPossibleToSave()
            Log.d(TAG,"Нажата кнопка зачистки автора книги")
        }
    }

    override fun onStart() {
        super.onStart()
        fab = requireActivity().findViewById(R.id.fab)
        setFabStateLoading()
        checkIsPossibleToSave()
    }

    private fun setFabStateLoading() {
        val icon = avd(R.drawable.ic_cached_rotate_black_24dp_adv)
        fab.setImageDrawable(icon)
        icon.start()
        fab.setOnClickListener {
            //Обнуляем лиссенер на ыремя загрузки
            null
        }
    }

    private fun isPossibleToSave(): Boolean {
        Log.d(TAG, "Сработал isPossibleToSave")
        if (!binding.newBookTitle.text.isNullOrBlank() and !binding.newBookAuthor.text.isNullOrBlank()) {
            Log.d(TAG, "Условие минимальноси данных для сохранения выполнены ")
            Log.d(TAG, "Условие ${binding.newBookTitle.text}")
            Log.d(TAG, "Условие ${binding.newBookAuthor.text}")
            return true
        }
        Log.d(TAG, "Для сохранения условия недостаточны")
        return false
    }

    private fun checkIsPossibleToSave() {
        Log.d(TAG, "Сработал checkIsPossibleToSave")
        if (!isPossibleToSave()) {
            Log.d(TAG, "checkIsPossibleToSave / Наименование назнвания книги или автор нулевые")
            fab.setImageResource(R.drawable.ic_edit_24_stat)
            fab.setOnClickListener {
                //Обнуляем лиссенер на время загрузки
                Toast.makeText(
                    requireContext(),
                    "Введены не все данные достаточные для сохранения",
                    Toast.LENGTH_SHORT
                ).show()
            }
        } else {
            Log.d(TAG, "checkIsPossibleToSave / Все готово к сохранению")
            val icon = avd(R.drawable.ic_read_to_save_24dp_adv)
            fab.setImageDrawable(icon)
            icon.start()
            fab.setOnClickListener {
                //Обнуляем лиссенер на ыремя загрузки
                Log.d(TAG, "Нажата кнопка сохранения")
            }
        }
    }


    private fun saveBitmapInStorage(bitmap: Bitmap, context: Context) {
        Log.d("Моя проверка", "На обработку получена мапа: ${bitmap.config}")
        Log.d("Моя проверка", "На обработку получена мапа: ${bitmap.byteCount}")
        Log.d("Моя проверка", "На обработку получена мапа: ${bitmap.colorSpace.toString()}")
        val filename = "QR_" + "${System.currentTimeMillis()}.jpg"
        var fos: OutputStream? = null
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            context.contentResolver?.also { resolver ->
                val contentValues = ContentValues().apply {
                    put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                    put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
                    put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
                }
                imageUri =
                    resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
                fos = imageUri?.let { resolver.openOutputStream(it) }
            }
        } else {
            val imagesDir =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
            image = File(imagesDir, filename)
            fos = FileOutputStream(image)
        }
        fos?.use {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, it)
        }
        fos?.close()
    }

    //на переделку, выполнить через кастомный контракт, избавитться от startActivityForResult
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.d("Моя проверка", "Отработал onActivityResult")
        Log.d("Моя проверка", "requestCode: $requestCode")
        Log.d("Моя проверка", "resultCode: $resultCode")
        Log.d("Моя проверка", "data: $data")
        if (resultCode == Activity.RESULT_OK) {
            Log.d("Моя проверка", "Прошел Activity.RESULT_OK")
            if (requestCode == 25) {
                Log.d("Моя проверка", "Прошел requestCode == 25")
                val extras: Bundle? = data?.extras
                if (data?.type == "image/jpeg") {
                    Log.d("Моя проверка", "Есть дата")
                    val thePicUri = data.data
                    Log.d("Моя проверка", "Результат thePicUri == $thePicUri")
                    val thePic = extras?.getParcelable<Bitmap>("data")
                    Log.d("Моя проверка", "Результат thePic == $thePic")
                    binding.ivNewBookCover.setImageURI(thePicUri)
                } else {
                    val thePic = extras?.getParcelable<Bitmap>("data")
                    Log.d("Моя проверка", "Результат thePic == $thePic")
                    binding.ivNewBookCover.setImageBitmap(thePic)
                }
            }
        }
    }

    //на переделку, выполнить через кастомный контракт, избавитться от startActivityForResult
    private fun performCrop() {
        Log.d("Моя проверка", "Метод стартовал")
        try {
            val cropIntent = Intent("com.android.camera.action.CROP")
            Log.d("Моя проверка", "Передаю $imageUri")
            cropIntent.type = "image/*"
            cropIntent.data = imageUri
            cropIntent.putExtra("crop", "true")
            //cropIntent.putExtra("scale", "true");
            cropIntent.putExtra("aspectX", 1)
            cropIntent.putExtra("aspectY", 1.52)
            cropIntent.putExtra("outputX", 270)
            cropIntent.putExtra("outputY", 410)
            cropIntent.putExtra("return-data", true)
            startActivityForResult(cropIntent, 25)
        } catch (anf: ActivityNotFoundException) {
            Toast.makeText(
                requireContext(),
                "Режим кадрирования не поддерживается",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}