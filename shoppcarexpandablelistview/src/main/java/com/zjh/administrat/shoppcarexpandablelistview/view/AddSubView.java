package com.zjh.administrat.shoppcarexpandablelistview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zjh.administrat.shoppcarexpandablelistview.R;

public class AddSubView extends LinearLayout implements View.OnClickListener {
    private int number = 1;
    private TextView number_jian,number_number, number_add;

    public AddSubView(Context context) {
        this(context, null);
    }

    public AddSubView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AddSubView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View view = inflate(context, R.layout.add_remove_number, this);
        number_jian = view.findViewById(R.id.number_jian);
        number_number = view.findViewById(R.id.number_number);
        number_add = view.findViewById(R.id.number_add);

        number_jian.setOnClickListener(this);
        number_add.setOnClickListener(this);
    }

    //点击事件
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.number_jian:
                if (number > 1) {
                    -- number;
                    number_number.setText(number + "");
                    if (mNumberChangeListener != null) {
                        mNumberChangeListener.OnNumberChange(number);
                    }
                } else {
                    Toast.makeText(getContext(), "我是有底线的!", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.number_add:
                if (number < 9) {
                    ++ number;
                    number_number.setText(number+"");
                    if (mNumberChangeListener != null) {
                        mNumberChangeListener.OnNumberChange(number);
                    }
                } else {
                    Toast.makeText(getContext(), "每人限购9件!", Toast.LENGTH_SHORT).show();
                }
            default:
                break;
        }
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
        number_number.setText(number+"");
    }

    //成员变量
    OnNumberChangeListener mNumberChangeListener;
    //set方法

    public void setNumberChangeListener(OnNumberChangeListener onNumberChangeListener) {
        this.mNumberChangeListener = onNumberChangeListener;
    }

    //定义个接口
    public interface OnNumberChangeListener{
        void OnNumberChange(int number);
    }

}
