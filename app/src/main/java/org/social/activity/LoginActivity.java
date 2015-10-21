package org.social.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.social.R;
import org.social.api.Api;
import org.social.application.SysApplication;
import org.social.base.BaseActivity;
import org.social.base.BaseTask;
import org.social.base.TaskListener;
import org.social.response.SignInResponse;
import org.social.response.UserInfoResponse;
import org.social.util.SpUtil;
import org.social.widget.TitleBar;
import org.social.widget.dialog.LoadingDialog;

/**
 * Created by caowenhua on 2015/10/14.
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener{

    private TitleBar titleBar;
    private EditText edt_name;
    private EditText edt_password;
    private Button btn_register;
    private Button btn_login;

    private String username;
    private String password;

    private LoadingDialog loadingDialog;

    @Override
    protected int setLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void findView() {
        titleBar = findViewByID(R.id.titlebar);
        edt_name = findViewByID(R.id.edt_name);
        edt_password = findViewByID(R.id.edt_password);
        btn_login = findViewByID(R.id.btn_login);
        btn_register = findViewByID(R.id.btn_register);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        titleBar.left.setOnClickListener(this);
        btn_register.setOnClickListener(this);
        btn_login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_left:
                finish();
                break;
            case R.id.btn_register:
                if(isInputFilled()){
                    startSignIn();
                }
                break;
            case R.id.btn_login:
                if(isInputFilled()){
                    startLogin();
                }
                break;
        }
    }

    private boolean isInputFilled(){
        if(edt_name.getText().length() == 0){
            showToast("请输入用户名");
            return false;
        }
        else if(edt_password.getText().length() == 0){
            showToast("请输入密码");
            return false;
        }
        else{
            username = edt_name.getText().toString();
            password = edt_password.getText().toString();
            return true;
        }
    }

    private void startLogin(){
        LoginTask task = new LoginTask();
        task.setListener(taskListener);
        task.execute();
    }

    private void startSignIn(){
        SignInTask task = new SignInTask();
        task.setListener(taskListener);
        task.execute();
    }

    private SignInResponse loginResponse;
    private class LoginTask extends BaseTask{
        @Override
        protected Object doWorkInBackground(Object... params) {
            Api api = new Api(getThis());
            loginResponse = api.login(username, password);
            return null;
        }
    }

    private SignInResponse signInResponse;
    private class SignInTask extends BaseTask{
        @Override
        protected Object doWorkInBackground(Object... params) {
            Api api = new Api(getThis());
            signInResponse = api.signIn(username, password);
            return null;
        }
    }

    private TaskListener taskListener = new TaskListener() {
        @Override
        public void onPreExecute(BaseTask task) {
            if(task instanceof SignInTask){
                loadingDialog = new LoadingDialog(getThis(), "正在注册..");
            }

            else if(task instanceof LoginTask){
                loadingDialog = new LoadingDialog(getThis(), "正在登录..");
            }
        }

        @Override
        public void onPostExecute(BaseTask task, Object result) {
            loadingDialog.dismiss();
            if(task instanceof SignInTask){
                if(signInResponse.getStatus().equals("success")){
                    UserInfoResponse.UserEntity entity = new UserInfoResponse.UserEntity();
                    entity.setSex(1);
                    entity.setAvatar("");
                    entity.setEmail("");
                    entity.setPhone("");
                    entity.setSignature("");
                    entity.setNickname(username);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("user", entity);
                    startActivity(FillInfoActivity.class, bundle, 0);
                    SpUtil.setIsUser(getThis(), true);
                    SpUtil.setUserId(getThis(), signInResponse.getUserId());
                    SysApplication.getInstance().cleanBottom();
                    finish();
                }
                else{
                    showToast(signInResponse.getMessage());
                }
            }

            else if(task instanceof LoginTask){
                if(loginResponse.getStatus().equals("success")){
                    SpUtil.setUserId(getThis(), signInResponse.getUserId());
                    startActivity(UserMainActivity.class, null, 0);
                    SysApplication.getInstance().cleanBottom();
                    finish();
                }
                else{
                    showToast(loginResponse.getMessage());
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
