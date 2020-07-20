package com.example.ourdesign

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.ourdesign.databinding.ActivityLowDesignBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.low_design_bottom_sheet.*

//const val ACTION_GALLERY = "android.intent.category.APP_GALLERY"
const val REQUEST_IMAGE_GET = 1
class LowDesignActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLowDesignBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_low_design)
        // Get the Intent that started this activity and extract the string
        val message = intent.getStringExtra(EXTRA_MESSAGE)


        Log.i("hello", "default")
        val bottomSheetBehavior = BottomSheetBehavior.from(standardBottomSheet)
        binding.toggleButton.setOnCheckedChangeListener { _, isChecked ->
            Log.i("hello", "start")
            if (isChecked) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED)
                Log.i("hello", "STATE_EXPANDED")
            }
            else {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED)
                Log.i("hello", "STATE_COLLAPSED")
            }
        }

        binding.imageView.setOnClickListener {
            selectImage()
        }
    }
    private fun selectImage() {
        val intent = Intent().apply {
            action = Intent.ACTION_GET_CONTENT
            type = "image/*"
        }
        if (intent.resolveActivity(packageManager) != null) {
            startActivityForResult(intent, REQUEST_IMAGE_GET)
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
}