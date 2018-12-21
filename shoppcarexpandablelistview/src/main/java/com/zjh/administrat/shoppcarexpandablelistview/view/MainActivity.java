package com.zjh.administrat.shoppcarexpandablelistview.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.zjh.administrat.shoppcarexpandablelistview.R;
import com.zjh.administrat.addshoppingcarxrecycleview.adapter.MyAdapter;
import com.zjh.administrat.addshoppingcarxrecycleview.bean.ShoppingBean;
import com.zjh.administrat.addshoppingcarxrecycleview.presenter.IPresenterImpl;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements IView {

    private String urlStr = "http://www.zhaoapi.cn/product/getCarts";
    private IPresenterImpl iPresenter;
    private ExpandableListView mListView;
    private CheckBox selectAll;
    private TextView priceAll;
    private Button numberAll;
    private MyAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
        //创建我的适配器
        mAdapter = new MyAdapter(this);
        mListView.setAdapter(mAdapter);
    }

    private void initView() {
        mListView = findViewById(R.id.listView);
        selectAll = findViewById(R.id.selectAll);
        priceAll = findViewById(R.id.priceAll);
        numberAll = findViewById(R.id.numberAll);

        //全选按钮
        selectAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //底部全选按钮选中的时候所有得商品都被选中
                boolean allSelected = mAdapter.isAllSelected();
                mAdapter.changeAllStatus(!allSelected);
                mAdapter.notifyDataSetChanged();
                //刷新底部数据
                refreshData();
            }
        });

    }
    //刷新的方法  checkBox, 总价， 总数量
    private void  refreshData(){
        //判断所有商品是否被选中
        boolean allSelected = mAdapter.isAllSelected();
        //设置给全选
        selectAll.setSelected(allSelected);
        //计算总价
        float totalPrice = mAdapter.totalPrice();
        priceAll.setText("总价:￥"+totalPrice);
        //计算总数量
        int totalNumber = mAdapter.totalNumber();
        numberAll.setText("去结算("+totalNumber+")");

    }

    //处理数据
    private void initData() {
        iPresenter = new IPresenterImpl(this);
        Map<String, String> map = new HashMap<>();
        map.put("uid", "71");
        iPresenter.pRequestData(urlStr, map, ShoppingBean.class);
    }


    //返回数据i
    @Override
    public void viewData(Object object) {
        ShoppingBean shoppingBean = (ShoppingBean) object;
        mAdapter.setDataAll(shoppingBean.getData());

        //展开二级列表
        for (int p = 0; p < shoppingBean.getData().size(); p++) {
            mListView.expandGroup(p);
        }

        //回调处理
        mAdapter.setOnCartListChangeListener(new MyAdapter.OnCartListChangeListener() {
            @Override
            public void onParentCheckedChange(int i) {
                //点击商家复选框
                boolean parentSelected = mAdapter.isParentSelected(i);
                mAdapter.changeParentAllStatus(i, !parentSelected);
                mAdapter.notifyDataSetChanged();
                //刷新底部数据
                refreshData();
            }

            @Override
            public void onChildCheckedChange(int i, int i1) {
                //点击商品复选框
                mAdapter.changeChildAllStatus(i, i1);
                mAdapter.notifyDataSetChanged();
                //刷新底部数据
                refreshData();
            }

            @Override
            public void onNumberChange(int i, int i1, int number) {
                //点击加减器
                mAdapter.changeNumber(i, i1, number);
                mAdapter.notifyDataSetChanged();
                //刷新底部数据
                refreshData();
            }
        });

    }

    //处理内存
    @Override
    protected void onDestroy() {
        super.onDestroy();
        iPresenter.onDetch();
    }
}
