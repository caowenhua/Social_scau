package org.social.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.social.R;
import org.social.adapter.CommentListAdapter;
import org.social.api.Api;
import org.social.base.BaseActivity;
import org.social.base.BaseTask;
import org.social.response.ShareDetailResponse;
import org.social.util.SpUtil;
import org.social.widget.ShareDetailHeader;
import org.social.widget.TitleBar;

/**
 * Created by caowenhua on 2015/10/14.
 */
public class ShareDetailActivity extends BaseActivity implements View.OnClickListener{

    private TitleBar titleBar;
    private TextView tv_like;
    private TextView tv_comment;
//    private TextView tv_more;
    private RelativeLayout rlt_like;
    private RelativeLayout rlt_comment;
//    private RelativeLayout rlt_more;
    private ListView lv_list;
    private CommentListAdapter adapter;
    private ShareDetailHeader headerView;

    private int shareId;

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
//        rlt_more = findViewByID(R.id.rlt_more);
        lv_list = findViewByID(R.id.lv_list);
        headerView = new ShareDetailHeader(this);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        getIntent().getIntExtra("shareId", -1);
        if(shareId == -1){
            finish();
        }
        adapter = new CommentListAdapter(this);
        lv_list.addHeaderView(headerView);
//        lv_list.setHeadView(headerView);
        lv_list.setAdapter(adapter);

    }

    @Override
    public void onClick(View v) {

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

}
