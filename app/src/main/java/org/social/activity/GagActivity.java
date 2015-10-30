package org.social.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import org.social.R;
import org.social.base.BaseActivity;
import org.social.widget.PullToRefreshView;
import org.social.widget.TitleBar;

/**
 * Created by caowenhua on 2015/10/30.
 */
public class GagActivity extends BaseActivity implements View.OnClickListener{

    private TitleBar titleBar;
    private ListView lv_list;
    private PullToRefreshView v_pull;

    @Override
    protected int setLayout() {
        return R.layout.activity_gag;
    }

    @Override
    protected void findView() {
        titleBar = findViewByID(R.id.titlebar);
        lv_list = findViewByID(R.id.lv_list);
        v_pull = findViewByID(R.id.v_pull);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        titleBar.left.setOnClickListener(this);
        v_pull.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                v_pull.setRefreshing(true);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_left:
                finish();
                break;
        }
    }
}
