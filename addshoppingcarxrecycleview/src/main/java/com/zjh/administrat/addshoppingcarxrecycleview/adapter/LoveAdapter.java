package com.zjh.administrat.addshoppingcarxrecycleview.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zjh.administrat.addshoppingcarxrecycleview.R;
import com.zjh.administrat.addshoppingcarxrecycleview.bean.ShopCarBean;

import java.util.ArrayList;
import java.util.List;

public class LoveAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<ShopCarBean.DataBean> mLove;
    private Context mContext;

    public LoveAdapter(Context mContext) {
        this.mContext = mContext;
        mLove = new ArrayList<>();
    }

    public void setDatas(List<ShopCarBean.DataBean> list) {
        if (list != null){
            mLove.addAll(list);
        }
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.love_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder mHolder = (ViewHolder) viewHolder;
        for (int l = 0; l < mLove.get(i).getList().size(); l++) {
            mHolder.title.setText(mLove.get(i).getList().get(l).getTitle());
            mHolder.price.setText("￥"+mLove.get(i).getList().get(l).getPrice());
            //截取图片集
            String str = "";
            int length = mLove.get(i).getList().get(l).getImages().length();
            for (int j = 0; j < length; j++) {
                if(mLove.get(i).getList().get(l).getImages().substring(j, j+1).equals("|")){
                    str = mLove.get(i).getList().get(l).getImages().substring(j+1, length).trim();
                }
            }
            Glide.with(mContext).load(str).into(mHolder.imageView);

        }

    }

    @Override
    public int getItemCount() {
        return mLove.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView title, price;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            title = itemView.findViewById(R.id.title);
            price = itemView.findViewById(R.id.price);

        }
    }
}
