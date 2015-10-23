package org.social.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.social.R;
import org.social.adapter.ShareListAdapter;
import org.social.base.BaseActivity;
import org.social.widget.PullToRefreshView;
import org.social.widget.TitleBar;

/**
 * Created by caowenhua on 2015/10/14.
 */
public class ShareListActivity extends BaseActivity implements View.OnClickListener{

    private TitleBar titleBar;
    private ListView lv_list;
    private PullToRefreshView v_pull;
    private ShareListAdapter adapter;

    @Override
    protected int setLayout() {
        return R.layout.activity_share_list;
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

            }
        });

        adapter = new ShareListAdapter(this);
        lv_list.setAdapter(adapter);

        lv_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(ShareDetailActivity.class, null, 0);
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