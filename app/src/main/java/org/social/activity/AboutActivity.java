package org.social.activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.social.R;
import org.social.base.BaseActivity;
import org.social.widget.ExplosionField;
import org.social.widget.TitleBar;

/**
 * Created by caowenhua on 2015/10/12.
 */
public class AboutActivity extends BaseActivity implements View.OnClickListener{

    private TitleBar titleBar;
    private ImageView img_logo;
    private TextView tv_cwh;
    private TextView tv_cjwei;
    private TextView tv_cjwen;
    private TextView tv_cjl;

    private ExplosionField mExplosionField;

    @Override
    protected int setLayout() {
        return R.layout.activity_about;
    }

    @Override
    protected void findView() {
        titleBar = findViewByID(R.id.titlebar);
        img_logo = findViewByID(R.id.img_logo);
        tv_cjl = findViewByID(R.id.tv_cjl);
        tv_cjwei = findViewByID(R.id.tv_cjwei);
        tv_cjwen = findViewByID(R.id.tv_cjwen);
        tv_cwh = findViewByID(R.id.tv_cwh);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mExplosionField = ExplosionField.attach2Window(this);
        tv_cwh.setOnClickListener(this);
        tv_cjwen.setOnClickListener(this);
        tv_cjwei.setOnClickListener(this);
        tv_cjl.setOnClickListener(this);
        titleBar.left.setOnClickListener(this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mExplosionField.explode(img_logo);
            }
        }, 200);
    }

    @Override
    public void onClick(View v) {
        if(v == titleBar.left){
            finish();
        }
        else{
            mExplosionField.explode(v);
            v.setOnClickListener(null);
        }
    }
}
