package com.zjh.administrat.addshoppingcarxrecycleview.model;

import com.zjh.administrat.addshoppingcarxrecycleview.callback.ICallBack;
import com.zjh.administrat.addshoppingcarxrecycleview.callback.MyCallBack;
import com.zjh.administrat.addshoppingcarxrecycleview.utils.OkHttps;

import java.util.Map;

public class IModelImpl implements IModel {


    @Override
    public void mRequestData(String urlStr, Map<String, String> params, Class clazz, final MyCallBack myCallBack) {
        OkHttps.getInstance().postRequest(urlStr, params, clazz, new ICallBack() {
            @Override
            public void success(Object object) {
                myCallBack.OnSuccess(object);
            }

            @Override
            public void fails(Exception e) {

            }
        });
    }

}
