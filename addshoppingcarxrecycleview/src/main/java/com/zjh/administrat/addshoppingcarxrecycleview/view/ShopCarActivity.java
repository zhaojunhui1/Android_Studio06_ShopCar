package com.zjh.administrat.addshoppingcarxrecycleview.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;

import com.zjh.administrat.addshoppingcarxrecycleview.R;
import com.zjh.administrat.addshoppingcarxrecycleview.adapter.LoveAdapter;
import com.zjh.administrat.addshoppingcarxrecycleview.adapter.ShopCarAdapter;
import com.zjh.administrat.addshoppingcarxrecycleview.bean.ShopCarBean;
import com.zjh.administrat.addshoppingcarxrecycleview.presenter.IPresenterImpl;

import java.util.HashMap;
import java.util.Map;

public class ShopCarActivity extends AppCompatActivity implements IView {

    private String urlShop = "http://www.zhaoapi.cn/product/getCarts";
    private IPresenterImpl iPresenter;
    private RecyclerView recyclerView, loveView;
    private ShopCarAdapter mAdapter;
    private LoveAdapter lAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_car);
        initView();
    }

    //初始化View
    private void initView() {
        iPresenter = new IPresenterImpl(this);
        loveView = findViewById(R.id.loveView);
        recyclerView = findViewById(R.id.recycleView);
        mAdapter = new ShopCarAdapter(this);
        recyclerView.setAdapter(mAdapter);

        Map<String, String> map = new HashMap<>();
        map.put("uid", "71");
        iPresenter.pRequestData(urlShop, map, ShopCarBean.class);
        //布局
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);


        lAdapter = new LoveAdapter(this);
        loveView.setAdapter(lAdapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        gridLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        loveView.setLayoutManager(gridLayoutManager);
    }


    //获取数据
    @Override
    public void viewDatas(Object object) {
        ShopCarBean shopCarBean = (ShopCarBean) object;
        mAdapter.setDatas(shopCarBean.getData());

        lAdapter.setDatas(shopCarBean.getData());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        iPresenter.onDetch();
    }
}
