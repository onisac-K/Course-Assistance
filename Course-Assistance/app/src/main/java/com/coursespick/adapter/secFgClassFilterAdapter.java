package com.coursespick.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.coursespick.MainActivity;
import com.coursespick.R;
import com.coursespick.fragment.realCourse.realCourseData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eva on 2017/3/1.
 */

public class secFgClassFilterAdapter  extends  RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    public List<realCourseData> datashow = null;
    public secTothirdCallBack callBack;
    //type
    public static final int TYPE_EMPTY = 0xff01;
    public static final int TYPE_COURSEINFO = 0xff02;

    public secFgClassFilterAdapter(Context context,List<realCourseData> d) {
        try {
            callBack = (MainActivity)context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString()+"must imlement listener.");
        }

        this.context = context;
        if(d == null)d= new ArrayList<>();
        datashow = d;
    }
    public void replaceAllData(realCourseData[] tempDataList){
        datashow.clear();
        Log.d("tempDataList",String.valueOf(tempDataList.length));
        List<realCourseData> temp = new ArrayList<>();
        for(realCourseData r:tempDataList){
//            Log.d("")
            temp.add(r);
        }
        datashow.addAll(temp);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_COURSEINFO:
                return new HolderTypeCourseInfo(LayoutInflater.from(parent.getContext()).inflate(R.layout.second_fragment_list_item, parent, false));
            case TYPE_EMPTY:
                return new HolderTypeEmpty(LayoutInflater.from(parent.getContext()).inflate(R.layout.second_fragment_empty, parent, false));
            default:
                Log.d("error","viewholder is null");
                return null;
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof HolderTypeCourseInfo){
            bindTypeCourseInfo((HolderTypeCourseInfo)holder,position);
        }else if (holder instanceof HolderTypeEmpty) {
            bindTypeEmpty((HolderTypeEmpty) holder, position);
        }
    }

    @Override
    public int getItemCount() {
        return datashow.size();
    }

    @Override
    public int getItemViewType(int position) {
//        if(datashow.size() == 0){
//            return TYPE_EMPTY;
//        }else{
            return TYPE_COURSEINFO;
//        }

    }

    @Override
    public void onAttachedToRecyclerView(final RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if(manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    int type = getItemViewType(position);
                    switch (type){
                        case TYPE_EMPTY:
                        case TYPE_COURSEINFO:
                        default:
                            return gridManager.getSpanCount();
                    }
                }
            });
        }
    }

    /////////////////////////////
    private void bindTypeCourseInfo(HolderTypeCourseInfo holder, int position){
        //填充布局每一行的数据

        final int i = position;
        holder.seccid.setText(datashow.get(position).getcId());
//        Log.d("secfgAdapter:",datashow.get(position).getcId()+"");
        holder.seccname.setText(datashow.get(position).getcName());
        Log.d("secfgAdapter:",datashow.get(position).getcName()+"");
        holder.sectid.setText(datashow.get(position).gettId());
//        Log.d("secfgAdapter:",datashow.get(position).gettId()+"");
        holder.sectname.setText(datashow.get(position).gettName());
        Log.d("secfgAdapter:",datashow.get(position).gettName()+"");
        holder.secclasstime.setText(datashow.get(position).getcTime());
//        Log.d("secfgAdapter:",datashow.get(position).getcTime()+"");
        holder.secclassroom.setText(datashow.get(position).getcLocation());
//        Log.d("secfgAdapter:",datashow.get(position).getcLocation()+"");
        holder.btnSelectThisClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //加入待选池
                realCourseData real = new realCourseData();
                real.settId(datashow.get(i).gettId());
                real.settName(datashow.get(i).gettName());
                real.setcId(datashow.get(i).getcId());
                real.setcName(datashow.get(i).getcName());
                real.setcTime(datashow.get(i).getcTime());
                real.setcLocation(datashow.get(i).getcLocation());

                real.setcCampus(datashow.get(i).getcCampus());
                real.setcCedit(datashow.get(i).getcCredit());
                real.setcLimit(datashow.get(i).getcLimit());
                real.setcNumber(datashow.get(i).getcNumber());
                real.setcSize(datashow.get(i).getcSize());
                real.setQaLocation(datashow.get(i).getQaLocation());
                real.setQaTime(datashow.get(i).getQaTime());
//                MainActivity.realCourseDatas.
                Bundle bundle = new Bundle();
                bundle.putSerializable("realCourseData",real);
//                TextTabFragment.thirdFragment.setArguments(bundle);
//                 List<realCourseData>temp = new ArrayList<realCourseData>();
//                temp.add(real);
//                CourseModel[] afterTransfer = CourseModel.realCourseData2CourseModel(real);
//                Log.d("secFgClassFilterAdapter", "afterTransfer "+afterTransfer.length);
//                if(afterTransfer != null) {
//                    Boolean c = CourseDao.courseConflict(afterTransfer);
//                    if(c == true )
                callBack.thirdAddOne(real);
//                    else{
//                        Toast.makeText((MainActivity)context,"您选的课程与已有课程冲突",Toast)
//                    }
//                }

//                MainActivity mainActivity = (MainActivity)context;
//                mainActivity.transferealCourseData(temp);
            }
        });
    }

    private void bindTypeEmpty(HolderTypeEmpty holder, int position) {
//
    }

//    private void bindType2(final HolderType2 holder, final int position){
//        String title = datashow.get(position).getCloth_title();
//        holder.item_txt_type2.setText(title);
//        String imageUrl = "http://pica.nipic.com/2007-10-09/200710994020530_2.jpg";
//        Glide.with(context)
//        .load(imageUrl)
//        .centerCrop()
//        .into(holder.item_img_type2);
//        holder.item_img_type2.setOnClickListener(new View.OnClickListener() {
//    @Override
//    public void onClick(View v) {
//            String imageUrl = "http://pica.nipic.com/2007-10-09/200710994020530_2.jpg";
//
//            Intent intent = new Intent();
//            Bundle bundle = new Bundle();
//            bundle.putString("imageUrl",imageUrl);
//            bundle.putString("textTitle",datashow.get(position).getCloth_title());
//            intent.putExtras(bundle);
//            intent.setClass(context, ClothItemDetailsActivity.class);
//            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation((Activity)context,
//            Pair.create((View)holder.item_img_type2, "cloth_details_image"));
//        //                ,Pair.create((View)holder.item_txt_type2, "cloth_details_title")
//            context.startActivity(intent, options.toBundle());
//            }
//            });
//        }


/////////////////////////////

    public class HolderTypeEmpty extends RecyclerView.ViewHolder {
        public ImageView item_img;

        public HolderTypeEmpty(View itemView) {
            super(itemView);
//          item_img =
        }
    }

    public class HolderTypeCourseInfo extends RecyclerView.ViewHolder {
//        private String id; //课程ID
//        private String courseName;//课名
//        private String tname;//教师名
//        private String tid;//教师号
//        private int section; //从第几节课开始
//        private int sectionSpan; //跨几节课
//        private int week; //周几
//        private String classRoom; //教室
//        private int courseFlag; //课程背景颜色
//        private int necessary;//是否是必修课
//
//
//        private String necessaryteacher; //必选老师
//        private String negativeteacher;//不选老师ok
//        private int negativetime;//不选课的时间———— 1：早上1-2节  2：中午5-6节   3: 下午9——10节  4：晚上11——13节
//        private String location;//校区ok
//        private String full;//选课人数是否限制ok
//        private int uppercredit;//学分上限ok
//        private String academy;//学院ok
//        private int typeofgeneral;//通识课类别———— 1：理工  2：人文  3：经管 ok
        //list里一列的控件绑定

        private TextView seccid;
        private TextView seccname;
        private TextView sectid;
        private TextView sectname;
        private TextView secclasstime;
        private TextView secclassroom;
        private Button btnSelectThisClass;
        public HolderTypeCourseInfo(View itemView) {
            super(itemView);
//            tv = (TextView)itemView.findViewById(R.idXXX);
            seccid=(TextView)itemView.findViewById(R.id.sec_fg_cid);
            seccname=(TextView)itemView.findViewById(R.id.sec_fg_cname);
            sectid=(TextView)itemView.findViewById(R.id.sec_fg_tid);
            sectname=(TextView)itemView.findViewById(R.id.sec_fg_tname);
            secclasstime=(TextView)itemView.findViewById(R.id.sec_fg_ctime);
            secclassroom=(TextView)itemView.findViewById(R.id.sec_fg_croom);
            btnSelectThisClass = (Button)itemView.findViewById(R.id.sec_fg_btn_select);
        }
    }
    public interface secTothirdCallBack{
        void thirdAddOne(realCourseData real);
    }
}
