package com.coursespick.UI.SceneRecommand;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.coursespick.R;
import com.coursespick.entity.Cloth;

import java.util.List;

public class SeceneClothSetAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private Context context;
        private List<Cloth> datashow;
        //type
        public static final int TYPE_SORT_SPINNER = 0xff04;
        public static final int TYPE_ADD_ITEM = 0xff01;
        public static final int TYPE_ITEM = 0xff02;
        public static final int TYPE_BOTTOM_DOWN_REFRESH = 0xff03;

        public void addItems(int position,List<Cloth> list)
        {
            datashow.addAll(position,list);
//            notifyItemRangeInserted(position, list.size());
//            notifyItemRangeChanged(position + list.size(), getIteount()- list.size());
            notifyDataSetChanged();
        }

        public SeceneClothSetAdapter(Context context, List<Cloth> list) {
            this.context = context;
            datashow = list;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            switch (viewType)
            {
                case TYPE_ITEM:
                    return new HolderTypeItem(LayoutInflater.from(parent.getContext()).inflate(R.layout.secene_recommend_rv_item, parent, false));
                default:
                    Log.d("error","viewholder is null");
                    return null;
            }
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
             if (holder instanceof HolderTypeItem){
                bindTypeItem((HolderTypeItem)holder, position);
            }
        }

        @Override
        public int getItemCount() {
            return datashow.size();
        }

        @Override
        public int getItemViewType(int position) {
            return TYPE_ITEM;
        }

        @Override
        public void onAttachedToRecyclerView(final RecyclerView recyclerView) {
            super.onAttachedToRecyclerView(recyclerView);
            RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
            if(manager instanceof GridLayoutManager) {
                final GridLayoutManager gridManager = ((GridLayoutManager) manager);
                final int layoutSize = gridManager.getSpanCount();
                gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                    @Override
                    public int getSpanSize(int position) {
                    return gridManager.getSpanCount();

//                            case TYPE_SORT_SPINNER:
//                            case TYPE_BOTTOM_DOWN_REFRESH:
//                                return gridManager.getSpanCount();
//                            case TYPE_ADD_ITEM:
//                            case TYPE_ITEM:
//                                return 3;
//                            default:
//                                return 3;

                    }
                });
            }
        }

        /////////////////////////////


        private void bindTypeItem(HolderTypeItem holder, int position){
            String img = "http://pica.nipic.com/2007-10-09/200710994020530_2.jpg";
//            holder.item_img.setImageResource(datashow.get(position).getImgUrl());
            Glide.with(context)
                    .load(datashow.get(position).getImgUrl())
                    .centerCrop()
                    .into(holder.item_img);
//            holder.item_title.setText(datashow.get(position).getCloth_title());
//        x.image().bind(holder.item_img_type2, img,new ImageOptions.Builder().build(),new CustomBitmapLoadCallBack(holder.item_img_type2));
        }


        /////////////////////////////


        public class HolderTypeItem extends RecyclerView.ViewHolder {
            public ImageView item_img ;
            public HolderTypeItem(View itemView) {
                super(itemView);
                item_img  = (ImageView) itemView.findViewById(R.id.secne_recommend_rv_item_img);

            }
        }

    }