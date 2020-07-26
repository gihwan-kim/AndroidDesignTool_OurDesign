package com.example.ourdesign.LowDesign

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ourdesign.R
import com.example.ourdesign.databinding.ActivityLowDesignSetSizeBinding
import kotlinx.android.synthetic.main.activity_low_design_set_size.view.*
import java.lang.StringBuilder

class LowDesignSetSizeActivity : AppCompatActivity() ,
    CustomImageSizeDialogFragment.CustomImageSizeDialogListener {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var binding: ActivityLowDesignSetSizeBinding
    private lateinit var setSizeViewModel: LowDesignSetSizeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val imageSizeOptionList = arrayOf("imageSizeOption 1", "imageSizeOption 2", "imageSizeOption 3", "imageSizeOption 4")
        binding = DataBindingUtil.setContentView(this, R.layout.activity_low_design_set_size)

        // create viewModel 객체 생성
        setSizeViewModel = ViewModelProvider(this).get(LowDesignSetSizeViewModel::class.java)

        // bottom sheet : image size Option List 표시하는 recyclerView 생성
        viewManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        viewAdapter = LowDesignAdapter(imageSizeOptionList)
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

        // 이전 페이지로 이동
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // bottom sheet 의 custom image size dialog
        binding.standardBottomSheet.linearLayout.customImageSize.setOnClickListener {
            val customImageSizeDialogFragment = CustomImageSizeDialogFragment()
            customImageSizeDialogFragment.show(supportFragmentManager, "custom image size dialog")
        }

    }

    // CustomImageSizeDialogFragment.kt 에서 호출한 함수가 실행되는 곳
    override fun onDialogNegativeClick(dialog: DialogFragment) {
        Log.i("setSizeActivity",  "onDialogNegativeClick")
    }

    // CustomImageSizeDialogFragment.kt 에서 호출한 함수가 실행되는 곳
    override fun onDialogPositiveClick(width: String, height: String) {
        updateImageSize(width, height)
        Log.i("setSizeActivity",  "custom dialoge 로 정한 이미지 사이즈 : width : " + width + "height : " + height)
    }

    private fun updateImageSize(width: String, height: String)
    {
        setSizeViewModel.height = height
        setSizeViewModel.width = width
    }

    /**
     * [ onCreateOptionsMenu() ]
     *      res/menu 에서 커스텀한  아이템들을 Tool bar 에 표시되도록 만드는 함수
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.low_design_setting_toolbar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    /**
     * [ onOptionsItemSelected() ]
     *      1. Tool bar 의 버튼 클릭 리스너 함수
     *      2. Tool bar item 중 하나를 선택하면 시스템에서는 앱 activity 의 onOptionsItemSelected()
     *         콜백 메서드를 호출하고 클릭된 항목을 나타내는 MenuItem 객체를 전달
     *      3. item.itemId
     *          : id getter 함수
     *          : 어느 항목이 눌렀는지 알 수 있는 값
     *          : 대응 하는 item 의 id 값
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.actionToNextPage -> {
                Log.i("setSizeActivity", "intent 를 사용하여 Low-Design set layout page 로 이동하였습니다.")
                Toast.makeText(this,"image 크기가 결정되었습니다.", LENGTH_LONG).show()
                val intent = Intent(this, LowDesignSetLayoutActivity::class.java)
                intent.putExtra("image_width", setSizeViewModel.width)
                intent.putExtra("image_height", setSizeViewModel.height)
                Log.i("setSizeActivity",  "viewModel 에 전달받은 이미지 사이즈 : width : " + setSizeViewModel.width + "height : " + setSizeViewModel.height)
                startActivity(intent)
            }
            else -> super.onOptionsItemSelected(item)
        }
        return super.onOptionsItemSelected(item)
    }
}