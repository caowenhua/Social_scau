package org.social.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.social.R;
import org.social.adapter.CommentListAdapter;
import org.social.api.Api;
import org.social.base.BaseActivity;
import org.social.base.BaseTask;
import org.social.base.TaskListener;
import org.social.response.ShareDetailResponse;
import org.social.util.SpUtil;
import org.social.widget.ShareDetailHeader;
import org.social.widget.TitleBar;
import org.social.widget.dialog.LoadingDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by caowenhua on 2015/10/14.
 */
public class ShareDetailActivity extends BaseActivity implements View.OnClickListener{

    private TitleBar titleBar;
    private TextView tv_like;
    private TextView tv_comment;
    private ImageView img_like;
//    private TextView tv_more;
    private RelativeLayout rlt_like;
    private RelativeLayout rlt_comment;
//    private RelativeLayout rlt_more;
    private ListView lv_list;
    private CommentListAdapter adapter;
    private ShareDetailHeader headerView;

    private LoadingDialog loadingDialog;
    private int shareId;
    private List<ShareDetailResponse.ShareEntity.CommentsEntity> commentList;

    @Override
    protected int setLayout() {
        return R.layout.activity_share_detail;
    }

    @Override
    protected void findView() {
        titleBar = findViewByID(R.id.titlebar);
        tv_comment = findViewByID(R.id.tv_comment);
        tv_like = findViewByID(R.id.tv_like);
//        tv_more = findViewByID(R.id.tv_more);
        rlt_comment = findViewByID(R.id.rlt_comment);
        rlt_like = findViewByID(R.id.rlt_like);
        img_like = findViewByID(R.id.img_like);
//        rlt_more = findViewByID(R.id.rlt_more);
        lv_list = findViewByID(R.id.lv_list);
        headerView = new ShareDetailHeader(this);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        titleBar.left.setOnClickListener(this);
        getIntent().getIntExtra("shareId", -1);
        if(shareId == -1){
            finish();
        }
        commentList = new ArrayList<>();
        adapter = new CommentListAdapter(this, commentList);
        lv_list.addHeaderView(headerView);
//        lv_list.setHeadView(headerView);
        lv_list.setAdapter(adapter);

        startGetDetailTask();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_left:
                finish();
                break;
            case R.id.rlt_like:
                break;
            case R.id.rlt_comment:
                break;
        }
    }

    private void startGetDetailTask(){
        GetShareDetailTask task = new GetShareDetailTask();
        task.setListener(taskListener);
        task.execute();
    }

    private ShareDetailResponse shareDetailResponse;
    private class GetShareDetailTask extends BaseTask{
        @Override
        protected Object doWorkInBackground(Object... params) {
            Api api = new Api(getThis());
            shareDetailResponse = api.getShareById(SpUtil.getUserId(getThis()), shareId);
            return null;
        }
    }

    private TaskListener taskListener = new TaskListener() {
        @Override
        public void onPreExecute(BaseTask task) {
            if(task instanceof GetShareDetailTask){
                loadingDialog = new LoadingDialog(getThis(), "正在加载..");
            }

        }

        @Override
        public void onPostExecute(BaseTask task, Object result) {
            if(task instanceof GetShareDetailTask){
                loadingDialog.dismiss();
                if(shareDetailResponse.getStatus().equals("success")){
                    headerView.setData(shareDetailResponse.getShare());
                    tv_comment.setText(shareDetailResponse.getShare().getCommentCount() + "");
                    tv_like.setText(shareDetailResponse.getShare().getLikeCount() + "");
                    commentList.addAll(shareDetailResponse.getShare().getComments());
                    adapter.notifyDataSetChanged();
                    rlt_comment.setOnClickListener(ShareDetailActivity.this);
                    rlt_like.setOnClickListener(ShareDetailActivity.this);
                    if(shareDetailResponse.getShare().isLike()){
                        img_like.setImageResource(R.drawable.feed_button_like_active);
                    }
                    else{
                        img_like.setImageResource(R.drawable.feed_button_like);
                    }
                }
                else{
                    showToast(shareDetailResponse.getMessage());
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
