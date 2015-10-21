package org.social.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import org.social.R;
import org.social.api.Api;
import org.social.base.BaseActivity;
import org.social.base.BaseTask;
import org.social.base.TaskListener;
import org.social.response.UserInfoResponse;
import org.social.util.SpUtil;
import org.social.widget.CircleImageView;
import org.social.widget.TitleBar;
import org.social.widget.dialog.LoadingDialog;

/**
 * Created by caowenhua on 2015/10/14.
 */
public class UserInfoActivity extends BaseActivity implements OnClickListener{

    private TitleBar titleBar;
    private CircleImageView img_head;
    private TextView tv_name;
    private ImageView img_gender;
    private TextView tv_sign;
    private TextView tv_fan;
    private TextView tv_focus;
    private RelativeLayout rlt_new;
    private LoadingDialog loadingDialog;

    private int userId;

    @Override
    protected int setLayout() {
        return R.layout.activity_user_info;
    }

    @Override
    protected void findView() {
        titleBar = findViewByID(R.id.titlebar);
        img_head = findViewByID(R.id.img_head);
        img_gender = findViewByID(R.id.img_gender);
        tv_name = findViewByID(R.id.tv_name);
        tv_sign = findViewByID(R.id.tv_sign);
        tv_fan = findViewByID(R.id.tv_fan);
        tv_focus = findViewByID(R.id.tv_focus);
        rlt_new = findViewByID(R.id.rlt_new);

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        titleBar.left.setOnClickListener(this);
        rlt_new.setOnClickListener(this);
        tv_focus.setOnClickListener(this);
        tv_fan.setOnClickListener(this);
        img_head.setOnClickListener(this);

        userId = getIntent().getIntExtra("userId", 0);
        if(userId == SpUtil.getUserId(this)){
            titleBar.right.setOnClickListener(this);
        }
        else{
            titleBar.right.setVisibility(View.GONE);
        }

        startTask();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_left:
                finish();
                break;
            case R.id.tv_right:
                if(userInfoResponse != null){
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("user", userInfoResponse.getUser());
                    startActivity(FillInfoActivity.class, null, 0);
                }
                break;
            case R.id.rlt_new:
                if(userInfoResponse != null) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("userId", userInfoResponse.getUser().getUserId());
                    startActivity(ShareListActivity.class, bundle, 0);
                }
                break;
            case R.id.tv_focus:
                if(userInfoResponse != null) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("userId", userInfoResponse.getUser().getUserId());
                    bundle.putString(PersonListActivity.TYPE, PersonListActivity.FOLLOWER);
                    startActivity(PersonListActivity.class, bundle, 0);
                }

                break;
            case R.id.tv_fan:
                if(userInfoResponse != null) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("userId", userInfoResponse.getUser().getUserId());
                    bundle.putString(PersonListActivity.TYPE, PersonListActivity.FAN);
                    startActivity(PersonListActivity.class, bundle, 0);
                }
                break;
            case R.id.img_head:
                if(userInfoResponse != null) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("url", userInfoResponse.getUser().getAvatar());
                    startActivity(PhotoActivity.class, bundle, 0);
                }
                break;
        }
    }

    private void startTask(){
        GetInfoTask task = new GetInfoTask();
        task.setListener(taskListener);
        task.execute();
    }

    private UserInfoResponse userInfoResponse;
    private class GetInfoTask extends BaseTask{
        @Override
        protected Object doWorkInBackground(Object... params) {
            Api api = new Api(getThis());
            userInfoResponse = api.information(userId);
            return null;
        }
    }

    private TaskListener taskListener = new TaskListener() {
        @Override
        public void onPreExecute(BaseTask task) {
            loadingDialog = new LoadingDialog(getThis(), "正在加载..");
        }

        @Override
        public void onPostExecute(BaseTask task, Object result) {
            loadingDialog.dismiss();
            if(task instanceof GetInfoTask){
                if(userInfoResponse.getStatus().equals("success")){
                    tv_name.setText(userInfoResponse.getUser().getNickname());
                    ImageLoader.getInstance().displayImage(userInfoResponse.getUser().getAvatar(), img_head);
                    if(userInfoResponse.getUser().getSex() == 1){
                        img_gender.setImageResource(R.drawable.man);
                    }
                    else{
                        img_gender.setImageResource(R.drawable.woman);
                    }
                    tv_sign.setText(userInfoResponse.getUser().getSignature());
                }
                else{
                    showToast(userInfoResponse.getMessage());
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
