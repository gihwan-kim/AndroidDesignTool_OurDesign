package com.example.ourdesign.LowDesign

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ourdesign.LowDesignActivity
import com.example.ourdesign.R
import com.example.ourdesign.databinding.ActivityLowDesignSetLayoutBinding

class LowDesignSetLayoutActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var binding: ActivityLowDesignSetLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_low_design_set_layout)

        // LinearLayoutManager(Context context, layout 방향 수평 or 수직, layout 순서를 반대로 할것인지)
        viewManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        // bottom sheet : layout Option List 표시하는 recyclerView 생성
        val imageLayoutOptionList = arrayOf("data1", "data2")
        viewAdapter = LowDesignAdapter(imageLayoutOptionList)
        recyclerView = binding.LowDesignRecyclerView.apply {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)

            // use a linear layout manager
            layoutManager = viewManager

            // specify an viewAdapter (see also next example)
            adapter = viewAdapter

        }
        setSupportActionBar(binding.lowDesignToolBar)

        // : 상위 activity 로 뒤로가기
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.low_design_setting_toolbar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.actionToNextPage -> {
                Log.i("hello", "intent 를 사용하여 Low-Design page 로 이동하였습니다.")
                Toast.makeText(this,"image 레이아웃이 결정되었습니다.", Toast.LENGTH_LONG).show()
                val intent = Intent(this, LowDesignActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

}