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
import android.provider.MediaStore
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.ourdesign.databinding.ActivityLowDesignBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.low_design_bottom_sheet.*
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

        binding.imageView.setOnClickListener {
            selectImage()
        }

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
        if (requestCode == REQUEST_IMAGE_GET && resultCode == Activity.RESULT_OK) {
            super.onActivityResult(requestCode, resultCode, data)
            val thumbnail: Bitmap? = data?.getParcelableExtra("data")
            val fullPhotoUri: Uri? = data?.data

            // 가져운 이미지를 image View 에 설정
            binding.imageView.setImageURI(fullPhotoUri)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.low_design_setting_toolbar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.actionToNextPage -> {
                Log.i("hello", "intent 를 사용하여 Low-Design page 로 이동하였습니다.")
                Toast.makeText(this,"디자인이 저장되었습니다.", Toast.LENGTH_LONG).show()
                val imageView = findViewById<ImageView>(R.id.imageView)
                saveImage(convertViewToBitmap(imageView))
                actionLowDesignActivityToMainActivity()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun actionLowDesignActivityToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }


    private fun convertViewToBitmap(view : View) :Bitmap {
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
    private fun saveImage(bmp :Bitmap) {

        val resolver = applicationContext.contentResolver

        // Find all audio files on the primary external storage device.
        // On API <= 28, use VOLUME_EXTERNAL instead
        val imageCollection = MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
        var currentTime = SimpleDateFormat("yyyy.MM.dd HH:mm:ss", Locale.KOREA).format(Calendar.getInstance().time)
        currentTime = currentTime.replace(".", "")
        currentTime = currentTime.replace(" ", ".")
        currentTime = currentTime.replace(":", "")

        // Android Q 에서는 원하는 폴더에 바로 접근해서 데이터를 쓸 수 없기 때문에
        // MediaStore.Images.Media.RELATIVE_PATH 를 사용하여 이미지의 경로를 정할 수 있다.
        // newContentValues : insert 할 데이터의 값들을 저장하는 객체
        val newContentValues  = ContentValues().apply {
            // put 함수로 칼럼에 해당하는 값을 삽입한다.
            put(MediaStore.Images.Media.DISPLAY_NAME, "Our_Design_$currentTime.png")

            // Android 10 이상을 실행하는 기기에서 IS_PENDING 값을 다시 0으로 변경할 때까지 이 앱에서만 파일을 볼 수 있습니다.
            put(MediaStore.Images.Media.IS_PENDING, 1)
            put(MediaStore.Images.Media.MIME_TYPE, "image/png")
            put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/Our Design")

        }

        // [ 데이터 추가 ]
        // provider 에 새로운 데이터를 추가하고 해당 데이터를 나타내는 uri 를 리턴
        val newContentValuesUri = resolver.insert(imageCollection, newContentValues)

        // output stream  을 사용하여 저장할 데이터 넣기
        newContentValuesUri?.let { resolver.openOutputStream(it).use{
            out -> bmp.compress(Bitmap.CompressFormat.PNG, 90, out)
        } }

        // 저장이 끝났기 때문에 IS_PENDING 값을 수정하여 이미지를 확인할 수 있도록한다.
        newContentValues.clear()
        newContentValues.put(MediaStore.Images.Media.IS_PENDING, 0)
        newContentValuesUri?.let {
            resolver.update(it, newContentValues, null, null)
            actionLowDesignActivityToMainActivity()
        }
    }
}