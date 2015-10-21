package org.social.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.social.R;
import org.social.base.BaseActivity;
import org.social.util.SpUtil;
import org.social.widget.TitleBar;

/**
 * Created by caowenhua on 2015/10/14.
 */
public class SettingActivity extends BaseActivity implements View.OnClickListener{

    private TitleBar titleBar;
    private TextView tv_about;
    private TextView tv_share;
    private ImageView img_switch;
    private TextView tv_exit;

    @Override
    protected int setLayout() {
        return R.layout.activity_setting;
    }

    @Override
    protected void findView() {
        titleBar = findViewByID(R.id.titlebar);
        tv_about = findViewByID(R.id.tv_about);
        tv_exit = findViewByID(R.id.tv_exit);
        tv_share = findViewByID(R.id.tv_share);
        img_switch = findViewByID(R.id.img_switch);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        titleBar.left.setOnClickListener(this);
        tv_exit.setOnClickListener(this);
        tv_about.setOnClickListener(this);
        img_switch.setOnClickListener(this);

        refreshImgSwitch();
        if(SpUtil.isAdmin(this)){
            tv_share.setVisibility(View.GONE);
            img_switch.setVisibility(View.GONE);
        }
    }

    private void refreshImgSwitch() {
        if(SpUtil.isRejectShared(this)){
            img_switch.setImageResource(R.drawable.button_open);
        }
        else{
            img_switch.setImageResource(R.drawable.button_off);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_left:
                finish();
                break;
            case R.id.tv_exit:
                break;
            case R.id.tv_about:
                startActivity(AboutActivity.class, null, 0);
                break;
            case R.id.img_switch:
                SpUtil.setIsRejectShared(getThis(), !SpUtil.isRejectShared(getThis()));
                refreshImgSwitch();
                break;
        }
    }
}
