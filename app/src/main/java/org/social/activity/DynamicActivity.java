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
import org.social.widget.PullToRefreshView;
import org.social.widget.TitleBar;
import org.social.widget.dialog.LoadingDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by caowenhua on 2015/10/30.
 */
public class DynamicActivity extends BaseActivity implements View.OnClickListener{

    private TitleBar titleBar;
    private ListView lv_list;
    private PullToRefreshView v_pull;
    private ShareListAdapter adapter;
    private List<SharesEntity> list;
    private LoadingDialog loadingDialog;
    private int userId;

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
        userId = getIntent().getIntExtra("userId", 0);
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

//        lv_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Bundle bundle = new Bundle();
//                bundle.putInt("shareId", list.get(position).getShareId());
//                startActivity(ShareDetailActivity.class, null, 0);
//            }
//        });

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
            allShareResponse = api.getSharesByUserId(userId);
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
