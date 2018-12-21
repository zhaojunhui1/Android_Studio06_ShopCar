package com.zjh.administrat.addshoppingcarxrecycleview.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.widget.Toast;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.zjh.administrat.addshoppingcarxrecycleview.R;
import com.zjh.administrat.addshoppingcarxrecycleview.adapter.GoodsAdapter;
import com.zjh.administrat.addshoppingcarxrecycleview.bean.DatilBean;
import com.zjh.administrat.addshoppingcarxrecycleview.bean.GoodsBean;
import com.zjh.administrat.addshoppingcarxrecycleview.presenter.IPresenterImpl;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements IView{

    private String urlStr = "http://www.zhaoapi.cn/product/searchProducts?keywords=%E6%89%8B%E6%9C%BA";
    private String path = "http://www.zhaoapi.cn/product/addCart";
    private XRecyclerView xRecyclerView;
    private IPresenterImpl iPresenter;
    private GoodsAdapter mAdapter;
    private int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initLinear();
    }
     //实话化view
    private void initView() {
        xRecyclerView = findViewById(R.id.xrecycleView);
        iPresenter = new IPresenterImpl(this);
        mAdapter = new GoodsAdapter(this);
        xRecyclerView.setAdapter(mAdapter);
        page = 1;
        xRecyclerView.setPullRefreshEnabled(true);
        xRecyclerView.setLoadingMoreEnabled(true);
        xRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page = 1;
                initData();
            }

            @Override
            public void onLoadMore() {
                initData();
            }
        });
        initData();
    }

    private void initData() {
        Map<String, String> map = new HashMap<>();
        map.put("page", "1");
        iPresenter.pRequestData(urlStr, map, GoodsBean.class);
    }
    private void initLinear(){
        //布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        xRecyclerView.setLayoutManager(linearLayoutManager);
        //分割线
        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        xRecyclerView.addItemDecoration(itemDecoration);
    }

    //获取回传的数据
    @Override
    public void viewDatas(Object object) {
        if (object instanceof GoodsBean){
            GoodsBean goodsBean = (GoodsBean) object;
            if (page == 1){
                mAdapter.setDatas(goodsBean.getData());
            }else {
                mAdapter.addDatas(goodsBean.getData());
            }
            page ++;
            xRecyclerView.refreshComplete();
            xRecyclerView.loadMoreComplete();
        }else if (object instanceof DatilBean){
            DatilBean datilBean = (DatilBean) object;
            Toast.makeText(MainActivity.this, datilBean.getMsg(),Toast.LENGTH_SHORT).show();
            startActivity(new Intent(MainActivity.this, ShopCarActivity.class));
        }

        //实现接口
        mAdapter.setClickListener(new GoodsAdapter.ClickListener() {
            @Override
            public void OnClick(String pid) {
                addCartData(pid);
            }
        });
    }
    //加入购物车
    private void addCartData(String pid) {

        Map<String, String> map = new HashMap<>();
        map.put("uid", "24198");
        map.put("pid", pid);
        iPresenter.pRequestData(path, map, DatilBean.class);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        iPresenter.onDetch();
    }
}
