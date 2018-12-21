package com.zjh.administrat.addshoppingcarxrecycleview.model;

import com.zjh.administrat.addshoppingcarxrecycleview.callback.MyCallBack;

import java.util.Map;

public interface IModel {
    void mRequestData(String urlStr, Map<String, String> params, Class clazz, MyCallBack myCallBack);
}
