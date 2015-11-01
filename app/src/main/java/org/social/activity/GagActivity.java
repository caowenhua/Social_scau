package org.social.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import org.social.R;
import org.social.adapter.GagAdapter;
import org.social.base.BaseActivity;
import org.social.widget.PullToRefreshView;
import org.social.widget.TitleBar;
import org.social.widget.dialog.EditDialog;
import org.social.widget.listener.OnEditFinishListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by caowenhua on 2015/10/30.
 */
public class GagActivity extends BaseActivity implements View.OnClickListener{

    private TitleBar titleBar;
    private ListView lv_list;
    private PullToRefreshView v_pull;
    private GagAdapter gagAdapter;


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
        titleBar.right.setOnClickListener(this);
        v_pull.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                v_pull.setRefreshing(true);
            }
        });
        List<String> l = new ArrayList<>();
        for(int i=0; i <40 ;i++){
            l.add("hhahah    " + i);
        }
        gagAdapter = new GagAdapter(this, l);
        lv_list.setAdapter(gagAdapter);
        lv_list.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                gagAdapter.endEvent();
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                gagAdapter.endEvent();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_left:
                finish();
                break;
            case R.id.tv_right:
                EditDialog editDialog = new EditDialog(getThis(), "请输入禁言关键字", "取消", "确定", null,
                        new OnEditFinishListener() {
                            @Override
                            public void onFinish(String content) {

                            }
                        });
                break;
        }
    }
}
