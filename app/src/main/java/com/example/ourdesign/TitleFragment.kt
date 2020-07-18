package com.example.ourdesign

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.ourdesign.databinding.FragmentTitleBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [TitleFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TitleFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentTitleBinding>(inflater,
            R.layout.fragment_title, container, false)
        binding.highDesignButton.setOnClickListener { view : View ->
            Log.i("wow", "design button!!")
            view.findNavController().navigate(R.id.action_titleFragment_to_highDesignFragment)
        }
//        binding.lowDesignButton.setOnClickListener { view : View ->
//            view.findNavController().navigate(R.id.action_titleFragment_to_lowDesignFragment)
//        }

        /** Called when the user taps the Send button */
        /**
         * putExtra() : 데이터 유형을 extras 라는 키-값 쌍으로 전달할 수 있음
         *      1. 다음 activity 에서 키를 사용하여 텍스트 값을 검색하기 때문에 키는 공개 상수 EXTRA_MESSAGE 임
         *      2. 앱의 패키지 이름을 접두사로 사용해서 intent extras 의 키를 정의하는 것이 좋다.
         *          -> 앱이 다른 앱과 상호작용하는 경우 키가 고유하게 유지됨
         * startActivity() : Intent 로 지정된 activity 의 인스턴스를 시작한다.
         */
        binding.lowDesignButton.setOnClickListener { view : View ->
            Log.i("hello", "intent 를 사용하여 이동하였습니다.")
            val message = "hello"
            val intent = Intent(activity, LowDesignActivity::class.java).apply {
                putExtra(EXTRA_MESSAGE, message)
            }
            startActivity(intent)
        }

        binding.templateButton.setOnClickListener { view : View ->
            view.findNavController().navigate(R.id.action_titleFragment_to_templateFragment)
        }
        // fragment 에 맞는 UI 를 그리기 위해 fragment 의 레이아웃 루트 view 를 반환해야 한다.
        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment TitleFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TitleFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}