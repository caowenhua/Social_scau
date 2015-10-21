package org.social.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import org.social.R;
import org.social.adapter.PersonListAdapter;
import org.social.api.Api;
import org.social.base.BaseActivity;
import org.social.base.BaseTask;
import org.social.base.TaskListener;
import org.social.response.FanListResponse;
import org.social.response.FollowListResponse;
import org.social.util.SpUtil;
import org.social.widget.PullToRefreshView;
import org.social.widget.TitleBar;
import org.social.widget.dialog.LoadingDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by caowenhua on 2015/10/14.
 */
public class PersonListActivity extends BaseActivity implements View.OnClickListener{

    public static final String TYPE = "T";
    public static final String FAN = "N";
    public static final String FOLLOWER = "F";

    private TitleBar titleBar;
    private ListView lv_list;
    private PersonListAdapter adapter;
    private PullToRefreshView v_pull;
    private String type;
    private int userId;

    private List<FanListResponse.UserListEntity> fanList;
    private List<FollowListResponse.UserListEntity> followList;
    private LoadingDialog loadingDialog;

    @Override
    protected int setLayout() {
        return R.layout.activity_person_list;
    }

    @Override
    protected void findView() {
        titleBar = findViewByID(R.id.titlebar);
        lv_list = findViewByID(R.id.lv_list);
        v_pull = findViewByID(R.id.v_pull);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        type = FAN;
        type = getIntent().getStringExtra(TYPE);
        userId = getIntent().getIntExtra("userId", 0);
        titleBar.left.setOnClickListener(this);
        adapter = new PersonListAdapter(this);
        if(type == FAN){
            titleBar.setCenterText("粉丝");
            fanList = new ArrayList<>();
            adapter.setFanList(fanList);
            startFanTask();
        }
        else{
            titleBar.setCenterText("关注");
            followList = new ArrayList<>();
            adapter.setFollowList(followList);
            startFollowTask();
        }
        lv_list.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        if(v == titleBar){
            finish();
        }
    }

    private void startFanTask(){
        GetFanTask task = new GetFanTask();
        task.setListener(taskListener);
        task.execute();
    }

    private void startFollowTask(){
        GetFollowTask task = new GetFollowTask();
        task.setListener(taskListener);
        task.execute();
    }

    private FanListResponse fanListResponse;
    private class GetFanTask extends BaseTask{
        @Override
        protected Object doWorkInBackground(Object... params) {
            Api api = new Api(getThis());
            fanListResponse = api.showFans(userId);
            return null;
        }
    }

    private FollowListResponse followListResponse;
    private class GetFollowTask extends BaseTask{
        @Override
        protected Object doWorkInBackground(Object... params) {
            Api api = new Api(getThis());
            followListResponse = api.showAttention(SpUtil.getUserId(getThis()));
            return null;
        }
    }

    private TaskListener taskListener = new TaskListener() {
        @Override
        public void onPreExecute(BaseTask task) {
            loadingDialog = new LoadingDialog(getThis(), "正在获取..");
        }

        @Override
        public void onPostExecute(BaseTask task, Object result) {
            loadingDialog.dismiss();
            if(task instanceof GetFanTask){
                if(fanListResponse.getStatus().equals("success")){
                    fanList.clear();
                    fanList.addAll(fanListResponse.getUserList());
                    adapter.notifyDataSetChanged();
                }
                else{
                    showToast(fanListResponse.getMessage());
                }
            }
            else if(task instanceof GetFollowTask){
                if(followListResponse.getStatus().equals("success")){
                    followList.clear();
                    followList.addAll(followListResponse.getUserList());
                    adapter.notifyDataSetChanged();
                }
                else{
                    showToast(followListResponse.getMessage());
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
