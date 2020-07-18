package com.example.ourdesign

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.ourdesign.databinding.ActivityLowDesignBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.low_design_bottom_sheet.*

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

    }
}