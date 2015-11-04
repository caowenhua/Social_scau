package org.social.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.social.R;
import org.social.api.Api;
import org.social.application.SysApplication;
import org.social.base.BaseActivity;
import org.social.util.SpUtil;
import org.social.widget.TitleBar;
import org.social.widget.dialog.EditDialog;
import org.social.widget.listener.OnEditFinishListener;

/**
 * Created by caowenhua on 2015/10/14.
 */
public class SettingActivity extends BaseActivity implements View.OnClickListener{

    private TitleBar titleBar;
    private TextView tv_about;
    private TextView tv_exit;
    private TextView tv_ip;

    @Override
    protected int setLayout() {
        return R.layout.activity_setting;
    }

    @Override
    protected void findView() {
        titleBar = findViewByID(R.id.titlebar);
        tv_about = findViewByID(R.id.tv_about);
        tv_exit = findViewByID(R.id.tv_exit);
        tv_ip = findViewByID(R.id.tv_ip);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        titleBar.left.setOnClickListener(this);
        tv_exit.setOnClickListener(this);
        tv_about.setOnClickListener(this);
        tv_ip.setOnClickListener(this);

        tv_ip.setText(SpUtil.getIp(this));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_left:
                finish();
                break;
            case R.id.tv_exit:
                SpUtil.exitLogin(getThis());
                startActivity(LoginActivity.class, null, 0);
                SysApplication.getInstance().cleanBottom();
                finish();
                break;
            case R.id.tv_about:
                startActivity(AboutActivity.class, null, 0);
                break;
            case R.id.tv_ip:
                EditDialog editDialog = new EditDialog(getThis(), "修改IP", tv_ip.getText().toString(), "取消",
                        "确定", null, new OnEditFinishListener() {
                            @Override
                            public void onFinish(String content) {
                                SpUtil.setIp(getThis(), content);
                                tv_ip.setText(content);
                                Api.IP = SpUtil.getIp(getThis());
                            }
                        });
                break;
        }
    }
}
