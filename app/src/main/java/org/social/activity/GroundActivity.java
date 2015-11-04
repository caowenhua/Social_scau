package org.social.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by caowenhua on 2015/10/14.
 */
public class GroundActivity extends BaseActivity {

    @Bind(R.id.img_back)
    ImageView imgBack;
    @Bind(R.id.edt_search)
    EditText edtSearch;
    @Bind(R.id.rlt_top)
    RelativeLayout rltTop;
    @Bind(R.id.tv_like)
    TextView tvLike;
    @Bind(R.id.tv_comment)
    TextView tvComment;
    @Bind(R.id.tv_new)
    TextView tvNew;
    @Bind(R.id.llt)
    LinearLayout llt;
    @Bind(R.id.v_moving)
    View vMoving;
    @Bind(R.id.lv_list)
    ListView lvList;
    @Bind(R.id.v_pull)
    PullToRefreshView vPull;
    @Bind(R.id.pb_loading)
    ProgressBar pbLoading;

    private ShareListAdapter adapter;
    private int type;//0 点赞 1 评论 2新发

    private List<SharesEntity> shares;
    private int screenWidth;


    @Override
    protected int setLayout() {
        return R.layout.activity_ground;
    }

    @Override
    protected void findView() {
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        ButterKnife.bind(this);
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        screenWidth = wm.getDefaultDisplay().getWidth();
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(screenWidth/3 ,ExplosionUtils.dp2Px(3));
        params.addRule(RelativeLayout.ALIGN_BOTTOM, R.id.llt);
        vMoving.setLayoutParams(params);
        type = 0;
        shares = new ArrayList<>();
        adapter = new ShareListAdapter(this, shares);
        lvList.setAdapter(adapter);
        startLikeTask();
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                shares.clear();
                if (type == 0) {
                    if (s.length() == 0) {
                        if(likeResponse != null)
                            shares.addAll(likeResponse.getShares());
                    } else {
                        if(likeResponse != null){
                            for (int i = 0; i < likeResponse.getShares().size(); i++) {
                                if (likeResponse.getShares().get(i).getContent().contains(s.toString()) ||
                                        likeResponse.getShares().get(i).getNickname().contains(s.toString())) {
                                    shares.add(likeResponse.getShares().get(i));
                                }
                            }
                        }
                    }
                } else if (type == 1) {
                    if (s.length() == 0) {
                        if(commentResponse != null){
                            shares.addAll(commentResponse.getShares());
                        }

                    } else {
                        if(commentResponse != null){
                            for (int i = 0; i < commentResponse.getShares().size(); i++) {
                                if (commentResponse.getShares().get(i).getContent().contains(s.toString()) ||
                                        commentResponse.getShares().get(i).getNickname().contains(s.toString())) {
                                    shares.add(commentResponse.getShares().get(i));
                                }
                            }
                        }

                    }
                } else if (type == 2) {
                    if (s.length() == 0) {
                        if(newResponse != null)
                            shares.addAll(newResponse.getShares());
                    } else {
                        if(newResponse != null){
                            for (int i = 0; i < newResponse.getShares().size(); i++) {
                                if (newResponse.getShares().get(i).getContent().contains(s.toString()) ||
                                        newResponse.getShares().get(i).getNickname().contains(s.toString())) {
                                    shares.add(newResponse.getShares().get(i));
                                }
                            }
                        }
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        vPull.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                vPull.setRefreshing(true);
                startAllTask();
            }
        });
    }

    private void startAllTask() {
        newResponse = null;
        likeResponse = null;
        commentResponse = null;
        startNewTask();
        startLikeTask();
        startCommentTask();
    }

    @OnClick({R.id.img_back, R.id.tv_like, R.id.tv_comment, R.id.tv_new})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
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
        }
    }

    private void startNewTask() {
        vMoving.animate().translationX(screenWidth*2/3).setDuration(200).start();
        if (newResponse == null) {
            GetNewShareTask task = new GetNewShareTask();
            task.setListener(taskListener);
            task.execute();
        }
        else{
            shares.clear();
            if(newResponse.getShares() != null)
                shares.addAll(newResponse.getShares());
            adapter.notifyDataSetChanged();
        }
    }

    private void startLikeTask() {
        vMoving.animate().translationX(0.0f).setDuration(200).start();
        if (likeResponse == null) {
            GetLikeShareTask task = new GetLikeShareTask();
            task.setListener(taskListener);
            task.execute();
        }else{
            shares.clear();
            if(likeResponse.getShares() != null)
                shares.addAll(likeResponse.getShares());
            adapter.notifyDataSetChanged();
        }
    }

    private void startCommentTask() {
        vMoving.animate().translationX(screenWidth/3).setDuration(200).start();
        if (commentResponse == null) {
            GetCommentShareTask task = new GetCommentShareTask();
            task.setListener(taskListener);
            task.execute();
        }else{
            shares.clear();
            if(commentResponse.getShares() != null)
                shares.addAll(commentResponse.getShares());
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
            pbLoading.setVisibility(View.VISIBLE);
            shares.clear();
            adapter.notifyDataSetChanged();
        }

        @Override
        public void onPostExecute(BaseTask task, Object result) {
            pbLoading.setVisibility(View.GONE);
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
                    vPull.setRefreshing(false);
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
