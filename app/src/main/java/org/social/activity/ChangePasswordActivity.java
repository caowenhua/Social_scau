package org.social.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.social.R;
import org.social.api.Api;
import org.social.base.BaseActivity;
import org.social.base.BaseTask;
import org.social.base.TaskListener;
import org.social.response.BaseResponse;
import org.social.util.SpUtil;
import org.social.widget.TitleBar;
import org.social.widget.dialog.LoadingDialog;

public class ChangePasswordActivity extends BaseActivity implements View.OnClickListener,View.OnFocusChangeListener {

    private TitleBar titleBar;
    private EditText edt_old_password;
    private EditText edt_new_password_1;
    private EditText edt_new_password_2;
    private TextView tv_show_tips;
    private Button btn_change_password;
    private LoadingDialog loadingDialog;
    private BaseResponse baseResponse;


    @Override
    protected int setLayout() {
        return R.layout.activity_change_password;
    }

    @Override
    protected void findView() {
        titleBar = findViewByID(R.id.titlebar);
        edt_old_password = findViewByID(R.id.edt_old_password);
        edt_new_password_1 =findViewByID(R.id.edt_new_password_1);
        edt_new_password_2 =findViewByID(R.id.edt_new_password_2);
        tv_show_tips = findViewByID(R.id.tv_show_tips);
        btn_change_password = findViewByID(R.id.btn_change_password);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        titleBar.left.setOnClickListener(this);
        btn_change_password.setOnClickListener(this);
        edt_old_password.setOnFocusChangeListener(this);
        edt_new_password_1.setOnFocusChangeListener(this);
        edt_new_password_2.setOnFocusChangeListener(this);
    }

    @Override
    public void onClick(View v) {
    switch (v.getId()){
        case R.id.tv_left:
            finish();
            break;
        case R.id.btn_change_password:
            if(edt_old_password.getText().length()>0 && edt_new_password_1.getText().length()>0 &&
                    edt_new_password_1.getText().toString().trim()
                            .equals(edt_new_password_2.getText().toString().trim())){
                startTask();
                tv_show_tips.setText("");
            }else if(edt_old_password.getText().length()<=0){
                tv_show_tips.setText("旧密码不能为空");
            }else if(edt_new_password_1.getText().length()<=0 || edt_new_password_2.getText().length()<=0){
                tv_show_tips.setText("新密码不能为空");
            }else{
                tv_show_tips.setText("两次输入的密码不一致");
            }
            break;
        default:
            break;
    }
    }



    private void startTask(){
        ChangePasswordTask task = new ChangePasswordTask();
        task.setListener(taskListener);
        task.execute();
    }


    private class ChangePasswordTask extends BaseTask{
        @Override
        protected Object doWorkInBackground(Object... params) {
            Api api = new Api(getThis());
            baseResponse = api.changePassword(edt_old_password.getText().toString().trim(), edt_new_password_1.getText().toString().trim(), SpUtil.getUserId(getThis()));
            return null;
        }
    }

    private TaskListener taskListener = new TaskListener() {
        @Override
        public void onPreExecute(BaseTask task) {
            loadingDialog = new LoadingDialog(getThis(), "正在修改密碼..");
        }

        @Override
        public void onPostExecute(BaseTask task, Object result) {
            loadingDialog.dismiss();
            if(task instanceof ChangePasswordTask){
                if(baseResponse.getStatus().equals("success")){
                    showToast("success to change password!");
                }
                else{
                    showToast(baseResponse.getMessage());
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


    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch (v.getId()){

            case R.id.edt_old_password:
                showLog("enter edt_old_password!");
                tv_show_tips.setText("");
                break;

            case R.id.edt_new_password_1:
                showLog("enter edt_new_password_1!");
                tv_show_tips.setText("");
                if(edt_old_password.getText().length()<=0){
                    tv_show_tips.setText("旧密码不能为空");
                }
                break;

            case R.id.edt_new_password_2:
                showLog("enter edt_new_password_2!");
                tv_show_tips.setText("");
                if( edt_new_password_1.getText().length()<=0){
                    tv_show_tips.setText("新密码不能为空");
                }else if(edt_new_password_1.getText().length()<6){
                    tv_show_tips.setText("密码位数不低于6");
                }
                break;
            default:
                break;
        }
    }
}
