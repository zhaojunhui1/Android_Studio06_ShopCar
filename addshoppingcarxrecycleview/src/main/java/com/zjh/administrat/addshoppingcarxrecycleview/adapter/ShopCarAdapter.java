package com.zjh.administrat.addshoppingcarxrecycleview.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zjh.administrat.addshoppingcarxrecycleview.R;
import com.zjh.administrat.addshoppingcarxrecycleview.bean.DatilBean;
import com.zjh.administrat.addshoppingcarxrecycleview.bean.ShopCarBean;

import java.util.ArrayList;
import java.util.List;

public class ShopCarAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<ShopCarBean.DataBean> mData;
    private Context mContext;

    public ShopCarAdapter(Context mContext) {
        this.mContext = mContext;
        mData = new ArrayList<>();
    }

    public void setDatas(List<ShopCarBean.DataBean> data) {
        if (data != null){
            mData.addAll(data);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
       View view = LayoutInflater.from(mContext).inflate(R.layout.parent_item, viewGroup, false);
       return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder mHolder = (ViewHolder) viewHolder;
        mHolder.title.setText(mData.get(i).getSellerName());

        //商品列表
        ChildAdapter cAdapter = new ChildAdapter(mContext);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        mHolder.recyclerView.setLayoutManager(linearLayoutManager);
        mHolder.recyclerView.setAdapter(cAdapter);
        cAdapter.setDatas(mData.get(i).getList());

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public RecyclerView recyclerView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            recyclerView = itemView.findViewById(R.id.recycleView1);
        }

    }

}
