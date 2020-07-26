package com.example.ourdesign.LowDesign

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.example.ourdesign.R
import com.example.ourdesign.databinding.FragmentCustomImageSizeDialogBinding
import com.google.android.material.textfield.TextInputEditText
import java.lang.ClassCastException


/**
 * [ CustomImageSizeDialogFragment ]
 *  : image size page 에서 이미지 사이즈 사용자 지정 형식 dialog
 *  : 이미지의 width, height 값을 지정합니다.
 *  : res/layout/fragment_custom_image_size_dialog.xml 을 사용하여 dialog 를 구성합니다.
 *      -> layout xml 파일을 inflate 한다음 생성된 view 를 setView 메소드로 dialog 에 추가합니다.
 *  < 사용법 >
 *      사용할 위치의 activity 에서 dialog fragment 객체를 생성하고 show 메소드를 호출합니다.
 *
 */
class CustomImageSizeDialogFragment : DialogFragment() {
    // TODO: Rename and change types of parameters

    // internal: 같은 모듈 안에서만 볼 수 있다.
    internal lateinit var listener: CustomImageSizeDialogListener
//    var imageSizeHeight: TextInputEditText? = null
//    var imageSizeWidth: TextInputEditText? = null
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            // Use the Builder class for convenient dialog construction
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater
            val view =inflater.inflate(R.layout.fragment_custom_image_size_dialog, null)
            builder.setView(view).setPositiveButton(R.string.positive,
            DialogInterface.OnClickListener { dialog, id ->
                val imageSizeHeight = view.findViewById<TextInputEditText>(R.id.image_height)
                val imageSizeWidth = view.findViewById<TextInputEditText>(R.id.image_width)
                Log.i("setSizeActivity", imageSizeHeight!!.text.toString() + " : " + imageSizeWidth!!.text.toString())
                listener.onDialogPositiveClick(imageSizeHeight!!.text.toString(), imageSizeWidth!!.text.toString())
            })
            .setNegativeButton(R.string.negative,
            DialogInterface.OnClickListener { dialog, id ->
                Log.i("dialog", "negative")
                dialog.cancel()
            })
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    /**
     *  [ CustomImageSizeDialogListener interface]
     *  1. dialog 를 사용하는 activity 나 fragment 에 이벤틀르 직접 사용하고 싶은 경우
     *  이벤트를 수신할 호스트 구성요소에서 사용할 인터페이스를 구현해주면된다.
     *  2. 사용법
     *  dialog 를 사용하는 호스트는 dialog fragment 의 생성자를 사용하여 dialog instance 를 생성하고
     *  interface 의 listener 를 구현하여 dialog 의 이벤트를 수신하면된다.
     */
    interface CustomImageSizeDialogListener {
        fun onDialogPositiveClick(width: String, height: String)
        fun onDialogNegativeClick(dialog : DialogFragment)
    }

    // onAttach() : CustomImageSizeDialogListener 를 인스턴스화 한다.
    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            // as : 명시적 타입 캐스팅
            // CustomImageSizeDialogListener 를 인스턴스화 -> host 에게 event 보낼 수 있음
            listener = context as CustomImageSizeDialogListener
        } catch (e: ClassCastException) {
            throw ClassCastException((context.toString() +
                    " must implement NoticeDialogListener"))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentCustomImageSizeDialogBinding>(inflater,
            R.layout.fragment_custom_image_size_dialog, container, false)
        return binding.root
    }
}

