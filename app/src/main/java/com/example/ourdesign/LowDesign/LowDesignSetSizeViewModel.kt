package com.example.ourdesign.LowDesign

import android.util.Log
import androidx.lifecycle.ViewModel

class LowDesignSetSizeViewModel: ViewModel() {
    var height = ""
    var width = ""
    init {
        Log.i("ViewModel", "LowDesignSetSizeViewModel is created")
    }

    // viewModel 이 파괴되는 경우  : fragment 가 연결이 끊기거나 activity 가 끝날 때
    // viewModel 이 파괴되기전에 onCleared() 콜백 함수를 호출하여 resource 를 비움
    override fun onCleared() {
        super.onCleared()
        Log.i("GameViewModel", "GameViewModel destroyed!")
    }
}