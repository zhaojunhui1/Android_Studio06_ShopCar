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

public class ChildAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<ShopCarBean.DataBean.ListBean> mList;
    private Context mContext;

    public ChildAdapter(Context mContext) {
        this.mContext = mContext;
        mList = new ArrayList<>();
    }

    public void setDatas(List<ShopCarBean.DataBean.ListBean> list) {
        if (list != null){
            mList.addAll(list);
        }
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.child_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder mHolder = (ViewHolder) viewHolder;
        mHolder.title.setText(mList.get(i).getTitle());
        mHolder.price.setText("￥"+mList.get(i).getPrice()+"");
        for (int j = 0; j < mList.size(); j++) {
            double totalPrice = 0;
            double price = mList.get(j).getPrice();
            int num = mList.get(j).getNum();
            totalPrice += price * num;
            //mHolder.number.setText(num);
            mHolder.number.setText("小计:￥"+totalPrice);
        }


        //截取图片集
        String str = "";
        int length = mList.get(i).getImages().length();
        for (int j = 0; j < length; j++) {
            if(mList.get(i).getImages().substring(j, j+1).equals("|")){
                str = mList.get(i).getImages().substring(j+1, length).trim();
            }
        }
        Glide.with(mContext).load(str).into(mHolder.imageView);

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private CheckBox child_box;
        private ImageView imageView;
        private TextView title, price, number, priceAll;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            child_box = itemView.findViewById(R.id.child_box);
            imageView = itemView.findViewById(R.id.imageView);
            title = itemView.findViewById(R.id.title);
            price = itemView.findViewById(R.id.price);
            number = itemView.findViewById(R.id.num);
            priceAll = itemView.findViewById(R.id.priceAll);
        }
    }

}
