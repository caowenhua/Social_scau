package org.social.activity;

import android.os.Bundle;
import android.os.Handler;

import org.social.R;
import org.social.base.BaseActivity;
import org.social.util.SpUtil;

/**
 * Created by caowenhua on 2015/10/12.
 */
public class WelcomeActivity extends BaseActivity {

    @Override
    protected int setLayout() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void findView() {
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(SpUtil.isAdmin(getThis())){
                    startActivity(AdminMainActivity.class, null , 0);
                }
                else if(SpUtil.isUser(getThis())){
                    startActivity(UserMainActivity.class, null , 0);
                }
                else{
                    startActivity(TouristMainActivity.class, null , 0);
                }
            finish();
            }
        }, 1000);
    }
}
