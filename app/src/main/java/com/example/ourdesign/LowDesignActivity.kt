package com.example.ourdesign

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.ourdesign.databinding.ActivityLowDesignBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.low_design_bottom_sheet.*
import org.opencv.android.OpenCVLoader

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

        // check opencv is applied
        Log.i("opencv", "notstarted")
        if (!OpenCVLoader.initDebug()) {
            Log.i("opencv", "Opencv is not Loaded")
        } else {
            Log.i("opencv", "Opencv is Loaded")
        }

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
            binding.imageView.setImageURI(fullPhotoUri)
            // Do work with photo saved at fullPhotoUri
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
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}