package org.social.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.content.res.TypedArray;
import android.view.View;

import org.social.R;

/**
 * Created by caowenhua on 2015/10/12.
 */



public class TitleBar extends RelativeLayout{

    public TextView left;
    public TextView center;
    public TextView right;

    public TitleBar(Context context) {
        super(context);
        setupViews(context);
    }

    public TitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupViews(context, attrs);
    }


    public TitleBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setupViews(context, attrs);
    }

    private void setupViews(Context context, AttributeSet attrs){
        LayoutInflater.from(context).inflate(R.layout.titlebar, this);
        left = (TextView) findViewById(R.id.tv_left);
        center = (TextView) findViewById(R.id.tv_center);
        right = (TextView) findViewById(R.id.tv_right);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.widget_title_attrs);

        if(ta.getString(R.styleable.widget_title_attrs_left_text) != null){
            left.setText(ta.getString(R.styleable.widget_title_attrs_left_text));
            left.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        }
        if(ta.getString(R.styleable.widget_title_attrs_center_text) != null){
            center.setText(ta.getString(R.styleable.widget_title_attrs_center_text));
        }
        if(ta.getString(R.styleable.widget_title_attrs_right_text) != null){
            right.setText(ta.getString(R.styleable.widget_title_attrs_right_text));
            right.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        }


        if(ta.getBoolean(R.styleable.widget_title_attrs_left_visible, true)){
            left.setVisibility(View.VISIBLE);
        }else{
            left.setVisibility(View.GONE);
        }

        if(ta.getBoolean(R.styleable.widget_title_attrs_right_visible, true)){
            right.setVisibility(View.VISIBLE);
        }else{
            right.setVisibility(View.GONE);
        }

        int center_color = ta.getColor(R.styleable.widget_title_attrs_center_color, 0);
        if(center_color != 0){
            center.setTextColor(center_color);
        }

        int left_color = ta.getColor(R.styleable.widget_title_attrs_left_color, 0);
        if(left_color != 0){
            left.setTextColor(left_color);
        }
        int left_img = ta.getResourceId(R.styleable.widget_title_attrs_left_img, 0);
        if(left_img != 0){
            left.setCompoundDrawablesWithIntrinsicBounds(left_img, 0, 0, 0);
        }

        int right_color = ta.getColor(R.styleable.widget_title_attrs_right_color, 0);
        if(right_color != 0){
            right.setTextColor(right_color);
        }
        int right_img = ta.getResourceId(R.styleable.widget_title_attrs_right_img, 0);
        if(right_img != 0){
            right.setCompoundDrawablesWithIntrinsicBounds(0, 0,right_img, 0);
        }

    }

    private void setupViews(Context context){
        LayoutInflater.from(context).inflate(R.layout.titlebar, this);
        left = (TextView) findViewById(R.id.tv_left);
        center = (TextView) findViewById(R.id.tv_center);
        right = (TextView) findViewById(R.id.tv_right);
    }

    public void setRightGone(){
        right.setVisibility(View.GONE);
    }

    public void setCenterText(String string){
        center.setText(string);
    }

    public TextView getCenterTextView() {
        return center;
    }

    public TextView getLeftTextView() {
        return left;
    }

    public TextView getRightTextView() {
        return right;
    }

    public void setRightTickGone(){
        right.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
    }

}
