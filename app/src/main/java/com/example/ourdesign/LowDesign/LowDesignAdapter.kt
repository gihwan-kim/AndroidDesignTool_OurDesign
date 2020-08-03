package com.example.ourdesign.LowDesign

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ourdesign.R
import kotlinx.android.synthetic.main.low_design_recyclerview.view.*

/**
 *  RecylcerView : https://developer.android.com/guide/topics/ui/layout/recyclerview?hl=ko
 *  [ LowDesignAdapter ]
 *  : ViewHolder 객체를 관리하는 클래스
 *  : user 가 목록을 스크롤하면 RecyclerView 는 필요에 따라 새로운 view holder 를 만든다.
 *  : user 가 스크롤 방향을 바꾸면 화면 밖으로 스크롤됐던 뷰 홀더는 바로 되돌아올 수 있음.
 *  : 각 data item(myDataset 의 요소) 마다 view 에대한 참조를 제공한다.
 *  : 복잡한 data item 일 수록 item 하나당 여러 view 가 필요할 수도 있다.
 *
 *  [ layout manager ]
 *  1. onCreateViewHolder() 호출 : view holder 생성, 컨텐츠를 표시하기위해 사용하는 view 설정
 *  2. onBindViewHolder() 호출 : onCreateViewHolder() 를 호출하여 생성된 view holder 를 데이터에 바인딩
 */
class LowDesignAdapter (private val myDataset: Array<String>) :
                RecyclerView.Adapter<LowDesignAdapter.LowDesignViewHolder>(){
    /**
     * [ LowDesignViewHolder ]
     * : ViewHolder 객체를 관리하는 클래스
     * : view holdeer 는 view 를 사용하여 항목을 표시하는 역할
     */
    class LowDesignViewHolder(val view: View) :RecyclerView.ViewHolder(view){
        val textView: TextView = view.imageSizeOption
    }

    /** [ onCreateViewHolder : 새로운 view 생성]
     *    1. layout manager 가 호출
     *    2. view holder 생성
     *    3. LayoutInflater : fragment 의 view, RecyclerView 에서 ViewHolder 를 만들때 사용
     *    4. LayoutInflater.from() :LayoutInflater.from을 통해 LayoutInflater를 생성
     *    5. LayoutInflater.from(parent.context).inflate(resource: Int, root: ViewGroup?, attachToRoot: Boolean)
     *       : inflater 에서 view 객체를 만든다.
     *       : resource = view 를 만들고 싶은 레이아웃 파일의 id
     *       : root = 생성될 view 의 parent
     *       : attachToRoot = true 로 설정할 경우 root 의 자식 view 로 자동으로 추가됨
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LowDesignAdapter.LowDesignViewHolder {
        val textView = LayoutInflater.from(parent.context).inflate(R.layout.low_design_recyclerview,
            parent, false) as View
        // view size, margin, padding, layout 파라미터 결정

        return LowDesignViewHolder(textView)
    }
    /** [ onBindViewHolder ]
     *      1. layout manager 가 호출
     *      2. 전달받은 view holder 의 레이아웃을 데이터로 채우는 역할
     *      3. position : iterator
    */
    override fun onBindViewHolder(holder: LowDesignViewHolder, position: Int) {
        holder.textView.text = myDataset[position]
    }
    /** [ getItemCount ]
     *    1. layout manager 가 호출하는 함수
     *    2. data set 의 사이즈 반환
     */
    override fun getItemCount() = myDataset.size

}