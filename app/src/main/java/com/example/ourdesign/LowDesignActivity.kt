package com.example.ourdesign

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.os.ParcelFileDescriptor
import android.provider.MediaStore
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import com.example.ourdesign.databinding.ActivityLowDesignBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.low_design_bottom_sheet.*
import java.io.File
import java.io.File.separator
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

//import org.opencv.android.OpenCVLoader

//const val ACTION_GALLERY = "android.intent.category.APP_GALLERY"
const val REQUEST_IMAGE_GET = 1
class LowDesignActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLowDesignBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_low_design)

        val bottomSheetBehavior = BottomSheetBehavior.from(standardBottomSheet)
        binding.toggleButton.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked)
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED)
            else
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED)
        }

//        binding.imageView.setOnClickListener {
//            selectImage()
//        }

        /**
          //check opencv is applied
          Log.i("opencv", "notstarted")
          if (!OpenCVLoader.initDebug()) {
            Log.i("opencv", "Opencv is not Loaded")
          } else {
            Log.i("opencv", "Opencv is Loaded")
          }
         */

        setSupportActionBar(binding.lowDesignToolBar)

    }

    private fun selectImage() {
        val intent = Intent().apply {
            action = Intent.ACTION_GET_CONTENT
            type = "image/*"
            putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false)
        }
        if (intent.resolveActivity(packageManager) != null) {
            // createChooser()
            //  여러개 중 하나를 선택하도록 변경
            //  새로운 Intent 객체가 반환됨
            val chooserIntent = Intent.createChooser(intent, "선택")
            startActivityForResult(chooserIntent, REQUEST_IMAGE_GET)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        // 가져운 이미지를 image View 에 설정
        if (requestCode == REQUEST_IMAGE_GET && resultCode == Activity.RESULT_OK) {
            super.onActivityResult(requestCode, resultCode, data)
            val thumbnail: Bitmap? = data?.getParcelableExtra("data")
            val fullPhotoUri: Uri? = data?.data
//            binding.imageView.setImageURI(fullPhotoUri)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.low_design_setting_toolbar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.actionToNextPage -> {
                Toast.makeText(this,"디자인이 저장되었습니다.", Toast.LENGTH_LONG).show()
//                val imageView = findViewById<ImageView>(R.id.imageView)
                val image = binding.constraintLayout2
                saveImage(convertViewToBitmap(image))

                actionLowDesignActivityToMainActivity()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun actionLowDesignActivityToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }


    private fun convertViewToBitmap(view : ConstraintLayout) :Bitmap {
        Log.i("Bitmap", view.width.toString())
        Log.i("Bitmap", view.height.toString())
        val bitmapResult = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmapResult)
        val bgDrawable = view.background
         if (bgDrawable != null) {
             bgDrawable.draw(canvas)
         } else {
             canvas.drawColor(Color.WHITE)
         }
        view.draw(canvas)
        return bitmapResult
    }


    // contentProvider :
    // 데이터를 content provider 안에 넣으려면 ContentResolver.insert() 메소드를 호출해야한다.
    // ContentResolver.insert() 메소드 : 새로운 행 삽입, 해당 열에 대한 컨텐츠 URI 반환

    private fun getImageFileName(): String {
        var currentTime =
            SimpleDateFormat(
            "yyyy.MM.dd HH:mm:ss",
            Locale.KOREA
        ).format(Calendar.getInstance().time)
        currentTime = currentTime.replace(".", "")
        currentTime = currentTime.replace(" ", ".")
        currentTime = currentTime.replace(":", "")
        return currentTime
    }

    private fun saveImage(bmp :Bitmap) {

        val resolver = applicationContext.contentResolver
        val newContentValuesUri: Uri?
        val currentTime = getImageFileName()

        val newContentValues = ContentValues().apply {
            // put 함수로 칼럼에 해당하는 값을 삽입한다.
            put(MediaStore.Images.Media.DISPLAY_NAME, "Our_Design_$currentTime.png")
            // Android 10 이상을 실행하는 기기에서 IS_PENDING 값을 다시 0으로 변경할 때까지 이 앱에서만 파일을 볼 수 있습니다.
            if (android.os.Build.VERSION.SDK_INT >= 29)
                put(MediaStore.Images.Media.IS_PENDING, 1)
            put(MediaStore.Images.Media.MIME_TYPE, "image/png")
            put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/Our Design")
        }

        if (android.os.Build.VERSION.SDK_INT <= 28)
        {
            val directory = File(Environment.getExternalStorageDirectory().toString() + separator + "Pictures")
            if (!(directory.exists())) {
                directory.mkdir()
            }
            val file = File(directory, "Our_Design_$currentTime.png")
           FileOutputStream(file).use {
               bmp.compress(Bitmap.CompressFormat.PNG, 90, it)
           }
            val hi = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, newContentValues)
        }
        else {
            val imageCollection=  MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
            // insert : provider 에 새로운 데이터를 추가하고 해당 데이터를 나타내는 uri 를 리턴
            newContentValuesUri = resolver.insert(imageCollection, newContentValues)

            // way 1
            // output stream  을 사용하여 저장할 데이터 넣기 : use 를 사용하여 자동으로 stream 을 닫아준다.
            newContentValuesUri?.let {
                resolver.openOutputStream(it).use { out ->
                    bmp.compress(Bitmap.CompressFormat.PNG, 90, out)
                }
            }
            // way 2
//            newContentValuesUri?.let {
//                resolver.openFileDescriptor(it, "w", null).use { pfd ->
//                    bmp.compress(Bitmap.CompressFormat.PNG, 90, ParcelFileDescriptor.AutoCloseOutputStream(pfd))
//                }
//            }

            // 저장이 끝났기 때문에 IS_PENDING 값을 수정하여 이미지를 확인할 수 있도록한다.
            newContentValues.clear()
            newContentValues.put(MediaStore.Images.Media.IS_PENDING, 0)

            // newContentValueUri 가 널이 아닐경우 let 함수 실행
            newContentValuesUri?.let {
                resolver.update(it, newContentValues, null, null)
            }
        }
    }
}