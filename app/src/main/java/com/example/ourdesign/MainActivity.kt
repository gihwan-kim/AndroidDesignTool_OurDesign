package com.example.ourdesign

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.ourdesign.databinding.ActivityMainBinding

const val EXTRA_MESSAGE = "com.example.ourdesign.MESSAGE"
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var drawerLayout: DrawerLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val navController = this.findNavController(R.id.myNavHostFragment)
        drawerLayout = binding.drawerLayout

        // up navigation bar
        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)

        // actionBar 이름 변경
        val actionBar = supportActionBar
        actionBar!!.title = "모두의 디자인"

        // drawer navigation bar
        NavigationUI.setupWithNavController(binding.navView, navController)


    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.myNavHostFragment)
        return NavigationUI.navigateUp(navController, drawerLayout)
    }

}