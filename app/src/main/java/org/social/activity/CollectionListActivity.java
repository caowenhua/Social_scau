package org.social.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import org.social.R;
import org.social.adapter.ShareListAdapter;
import org.social.api.Api;
import org.social.base.BaseActivity;
import org.social.base.BaseTask;
import org.social.base.TaskListener;
import org.social.response.AllShareResponse;
import org.social.response.SharesEntity;
import org.social.util.SpUtil;
import org.social.widget.PullToRefreshView;
import org.social.widget.TitleBar;
import org.social.widget.dialog.LoadingDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by caowenhua on 2015/10/14.
 */
public class CollectionListActivity extends BaseActivity implements View.OnClickListener{

    private TitleBar titleBar;
    private ListView lv_list;
    private PullToRefreshView v_pull;
    private ShareListAdapter adapter;
    private List<SharesEntity> list;
    private LoadingDialog loadingDialog;

    @Override
    protected int setLayout() {
        return R.layout.activity_dynamic_list;
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
        list = new ArrayList<>();

        v_pull.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                v_pull.setRefreshing(true);
                startGetShareTask();
            }
        });

        adapter = new ShareListAdapter(this, list);
        lv_list.setAdapter(adapter);

        startGetShareTask();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_left:
                finish();
                break;
        }
    }

    private void startGetShareTask(){
        GetShareTask task = new GetShareTask();
        task.setListener(taskListener);
        task.execute();
    }

    private AllShareResponse allShareResponse;
    private class GetShareTask extends BaseTask {
        @Override
        protected Object doWorkInBackground(Object... params) {
            Api api = new Api(getThis());
            allShareResponse = api.getCollectByUserId(SpUtil.getUserId(getThis()));
            return null;
        }
    }

    private TaskListener taskListener = new TaskListener() {
        @Override
        public void onPreExecute(BaseTask task) {
            if(task instanceof GetShareTask)
                loadingDialog = new LoadingDialog(getThis(), "正在加载..");
        }

        @Override
        public void onPostExecute(BaseTask task, Object result) {
            if(task instanceof GetShareTask){
                loadingDialog.dismiss();
                v_pull.setRefreshing(false);
                if(allShareResponse.getStatus().equals("success")){
                    list.clear();
                    list.addAll(allShareResponse.getShares());
                    adapter.notifyDataSetChanged();
                }
                else{
                    showToast(allShareResponse.getMessage());
                }
            }
        }

        @Override
        public void onProgressUpdate(BaseTask task, Object param) {

        }

        @Override
        public void onCancelled(BaseTask task) {

        }
    };
}
