package org.social.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuqg on 2015/10/12.
 */
public class TouristMainActivity extends BaseActivity implements View.OnClickListener{

    private ImageView img_login;
    private ImageView img_search;
    private ListView lv_list;
    private PullToRefreshView v_pull;
    private ShareListAdapter adapter;
    private List<SharesEntity> list;

    @Override
    protected int setLayout() {
        return R.layout.activity_tourist_main;
    }

    @Override
    protected void findView() {
        img_login = findViewByID(R.id.img_login);
        img_search = findViewByID(R.id.img_search);
        lv_list = findViewByID(R.id.lv_list);
        v_pull = findViewByID(R.id.v_pull);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        img_login.setOnClickListener(this);
        img_search.setOnClickListener(this);
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

        lv_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(ShareDetailActivity.class, null, 0);
            }
        });

        startGetShareTask();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_login:
                startActivity(LoginActivity.class, null, 0);
                break;
            case R.id.img_search:
                startActivity(GroundActivity.class, null, 0);
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
            allShareResponse = api.main(0);
            return null;
        }
    }

    private TaskListener taskListener = new TaskListener() {
        @Override
        public void onPreExecute(BaseTask task) {
        }

        @Override
        public void onPostExecute(BaseTask task, Object result) {
            if(task instanceof GetShareTask){
                v_pull.setRefreshing(false);
                if(allShareResponse.getStatus().equals("success")){
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
