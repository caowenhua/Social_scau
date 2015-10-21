package org.social.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
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
    private int type;

    private List<ShareGroundResponse.SharesEntity> shares;


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
        type = 0;
        shares = new ArrayList<>();
        startLikeTask();
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
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
        if (newResponse != null) {
            GetNewShareTask task = new GetNewShareTask();
            task.setListener(taskListener);
            task.execute();
        }
        else{
            shares.clear();
            shares.addAll(newResponse.getShares());
            adapter.notifyDataSetChanged();
        }
    }

    private void startLikeTask() {
        if (likeResponse != null) {
            GetLikeShareTask task = new GetLikeShareTask();
            task.setListener(taskListener);
            task.execute();
        }else{
            shares.clear();
            shares.addAll(likeResponse.getShares());
            adapter.notifyDataSetChanged();
        }
    }

    private void startCommentTask() {
        if (commentResponse != null) {
            GetCommentShareTask task = new GetCommentShareTask();
            task.setListener(taskListener);
            task.execute();
        }else{
            shares.clear();
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

    private TaskListener taskListener = new TaskListener() {
        @Override
        public void onPreExecute(BaseTask task) {
            pbLoading.setVisibility(View.VISIBLE);
        }

        @Override
        public void onPostExecute(BaseTask task, Object result) {
            pbLoading.setVisibility(View.GONE);
            if(task instanceof GetCommentShareTask){
                if(commentResponse.getStatus().equals("success")){

                }
                else{
                    showToast(commentResponse.getMessage());
                }
            }
            else if(task instanceof GetLikeShareTask){
                if(likeResponse.getStatus().equals("success")){

                }
                else{
                    showToast(likeResponse.getMessage());
                }
            }
            else if(task instanceof GetNewShareTask){
                if(newResponse.getStatus().equals("success")){

                }
                else{
                    showToast(newResponse.getMessage());
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
