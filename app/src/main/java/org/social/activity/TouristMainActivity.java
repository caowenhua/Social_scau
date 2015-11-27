package org.social.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.social.R;
import org.social.adapter.ShareListAdapter;
import org.social.api.Api;
import org.social.base.BaseActivity;
import org.social.base.BaseTask;
import org.social.base.TaskListener;
import org.social.response.ShareGroundResponse;
import org.social.response.SharesEntity;
import org.social.util.ExplosionUtils;
import org.social.util.SpUtil;
import org.social.widget.PullToRefreshView;
import org.social.widget.dialog.EditDialog;
import org.social.widget.listener.OnEditFinishListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuqg on 2015/10/12.
 */
public class TouristMainActivity extends BaseActivity implements View.OnClickListener{

    private ImageView img_login;
    private ImageView img_setting;
    private ListView lv_list;
    private PullToRefreshView v_pull;
    private ProgressBar pb_loading;
    private View v_moving;
    private TextView tv_new;
    private TextView tv_like;
    private TextView tv_comment;


    private ShareListAdapter adapter;
    private List<SharesEntity> list;

    private int type;//0 点赞 1 评论 2新发

    private int screenWidth;

    @Override
    protected int setLayout() {
        return R.layout.activity_tourist_main;
    }

    @Override
    protected void findView() {
        img_login = findViewByID(R.id.img_login);
        img_setting = findViewByID(R.id.img_setting);
        lv_list = findViewByID(R.id.lv_list);
        v_pull = findViewByID(R.id.v_pull);
        v_moving = findViewByID(R.id.v_moving);
        pb_loading = findViewByID(R.id.pb_loading);
        tv_new = findViewByID(R.id.tv_new);
        tv_like = findViewByID(R.id.tv_like);
        tv_comment = findViewByID(R.id.tv_comment);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        screenWidth = wm.getDefaultDisplay().getWidth();
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(screenWidth/3 , ExplosionUtils.dp2Px(3));
        params.addRule(RelativeLayout.ALIGN_BOTTOM, R.id.llt);
        v_moving.setLayoutParams(params);
        type = 0;

        img_login.setOnClickListener(this);
        img_setting.setOnClickListener(this);
        tv_comment.setOnClickListener(this);
        tv_like.setOnClickListener(this);
        tv_new.setOnClickListener(this);

        list = new ArrayList<>();

        v_pull.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                v_pull.setRefreshing(true);
                startAllTask();
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

        startLikeTask();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_login:
                startActivity(LoginActivity.class, null, 0);
                break;
            case R.id.tv_like:
                startLikeTask();
                break;
            case R.id.tv_comment:
                startCommentTask();
                break;
            case R.id.tv_new:
                startNewTask();
                break;
            case R.id.img_setting:
                EditDialog editDialog = new EditDialog(getThis(), "修改IP", Api.IP, "取消",
                    "确定", null, new OnEditFinishListener() {
                @Override
                public void onFinish(String content) {
                    SpUtil.setIp(getThis(), content);
                    Api.IP = SpUtil.getIp(getThis());
                }
            });
                break;
        }
    }

    private void startAllTask() {
        AllShareTask task = new AllShareTask();
        task.setListener(taskListener);
        task.execute();
    }

    private void startNewTask() {
        v_moving.animate().translationX(screenWidth*2/3).setDuration(200).start();
        if (newResponse == null) {
            GetNewShareTask task = new GetNewShareTask();
            task.setListener(taskListener);
            task.execute();
        }
        else{
            list.clear();
            if(newResponse.getShares() != null)
                list.addAll(newResponse.getShares());
            adapter.notifyDataSetChanged();
        }
    }

    private void startLikeTask() {
        v_moving.animate().translationX(0.0f).setDuration(200).start();
        if (likeResponse == null) {
            GetLikeShareTask task = new GetLikeShareTask();
            task.setListener(taskListener);
            task.execute();
        }else{
            list.clear();
            if(likeResponse.getShares() != null)
                list.addAll(likeResponse.getShares());
            adapter.notifyDataSetChanged();
        }
    }

    private void startCommentTask() {
        v_moving.animate().translationX(screenWidth/3).setDuration(200).start();
        if (commentResponse == null) {
            GetCommentShareTask task = new GetCommentShareTask();
            task.setListener(taskListener);
            task.execute();
        }else{
            list.clear();
            if(commentResponse.getShares() != null)
                list.addAll(commentResponse.getShares());
            adapter.notifyDataSetChanged();
        }
    }

    private ShareGroundResponse newResponse;
    private ShareGroundResponse likeResponse;
    private ShareGroundResponse commentResponse;

    private class GetNewShareTask extends BaseTask {
        @Override
        protected Object doWorkInBackground(Object... params) {
            Api api = new Api(getThis());
            newResponse = api.getList(SpUtil.getUserId(getThis()), 1);
            return null;
        }
    }

    private class GetLikeShareTask extends BaseTask {
        @Override
        protected Object doWorkInBackground(Object... params) {
            Api api = new Api(getThis());
            likeResponse = api.getList(SpUtil.getUserId(getThis()), 2);
            return null;
        }
    }

    private class GetCommentShareTask extends BaseTask {
        @Override
        protected Object doWorkInBackground(Object... params) {
            Api api = new Api(getThis());
            commentResponse = api.getList(SpUtil.getUserId(getThis()), 3);
            return null;
        }
    }

    private class AllShareTask extends BaseTask {
        @Override
        protected Object doWorkInBackground(Object... params) {
            Api api = new Api(getThis());
            commentResponse = api.getList(SpUtil.getUserId(getThis()), 3);
            likeResponse = api.getList(SpUtil.getUserId(getThis()), 2);
            newResponse = api.getList(SpUtil.getUserId(getThis()), 1);
            return null;
        }
    }

    private TaskListener taskListener = new TaskListener() {
        @Override
        public void onPreExecute(BaseTask task) {
            pb_loading.setVisibility(View.VISIBLE);
            list.clear();
            adapter.notifyDataSetChanged();
        }

        @Override
        public void onPostExecute(BaseTask task, Object result) {
            pb_loading.setVisibility(View.GONE);
            if(task instanceof GetCommentShareTask){
                if(commentResponse.getStatus().equals("success")){
                    startCommentTask();
                }
                else{
                    showToast(commentResponse.getMessage());
                }
            }
            else if(task instanceof GetLikeShareTask){
                if(likeResponse.getStatus().equals("success")){
                    startLikeTask();
                }
                else{
                    showToast(likeResponse.getMessage());
                }
            }
            else if(task instanceof GetNewShareTask){
                if(newResponse.getStatus().equals("success")){
                    startNewTask();
                }
                else{
                    showToast(newResponse.getMessage());
                }
            }
            if(task instanceof AllShareTask){
                if(likeResponse != null && newResponse != null && commentResponse != null){
                    v_pull.setRefreshing(false);
                    if(type == 0){
                        startLikeTask();
                    }
                    else if(type == 1){
                        startCommentTask();
                    }
                    else{
                        startNewTask();
                    }
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
