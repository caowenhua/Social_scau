package org.social.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.social.R;
import org.social.base.BaseActivity;
import org.social.base.BaseTask;
import org.social.response.UserInfoResponse;
import org.social.util.FileUtils;
import org.social.util.SpUtil;
import org.social.widget.CircleImageView;
import org.social.widget.TitleBar;
import org.social.widget.dialog.ChoosePhotoDialog;
import org.social.widget.dialog.ChooseSexDialog;
import org.social.widget.dialog.EditDialog;
import org.social.widget.dialog.LoadingDialog;
import org.social.widget.dialog.OnEditFinishListener;

import java.io.File;

/**
 * Created by caowenhua on 2015/10/14.
 */
public class FillInfoActivity extends BaseActivity implements View.OnClickListener {

    private TitleBar titleBar;
    private CircleImageView img_head;
    private TextView tv_name;
    private TextView tv_gender;
    private TextView tv_sign;
    private TextView tv_phone;
    private TextView tv_mail;
    private RelativeLayout rlt_phone;
    private RelativeLayout rlt_mail;
    private RelativeLayout rlt_name;
    private RelativeLayout rlt_gender;
    private RelativeLayout rlt_sign;

    private String name;
    private int gender;
    private String sign;
    private String phone;
    private String mail;
    private String avatar;

    private LoadingDialog loadingDialog;

    @Override
    protected int setLayout() {
        return R.layout.activity_fill_info;
    }

    @Override
    protected void findView() {
        titleBar = findViewByID(R.id.titlebar);
        img_head = findViewByID(R.id.img_head);
        tv_gender = findViewByID(R.id.tv_gender);
        tv_name = findViewByID(R.id.tv_name);
        tv_sign = findViewByID(R.id.tv_sign);
        tv_phone = findViewByID(R.id.tv_phone);
        tv_mail = findViewByID(R.id.tv_mail);
        rlt_phone = findViewByID(R.id.rlt_phone);
        rlt_mail = findViewByID(R.id.rlt_mail);
        rlt_gender = findViewByID(R.id.rlt_gender);
        rlt_name = findViewByID(R.id.rlt_name);
        rlt_sign = findViewByID(R.id.rlt_sign);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        titleBar.left.setOnClickListener(this);
        titleBar.right.setOnClickListener(this);
        rlt_sign.setOnClickListener(this);
        rlt_name.setOnClickListener(this);
        rlt_gender.setOnClickListener(this);
        rlt_phone.setOnClickListener(this);
        rlt_mail.setOnClickListener(this);
        img_head.setOnClickListener(this);

        UserInfoResponse.UserEntity entity = (UserInfoResponse.UserEntity) getIntent().getSerializableExtra("user");
        if(entity == null){
            finish();
        }
        else{
            setupParams(entity);
        }
    }

    private void setupParams(UserInfoResponse.UserEntity entity) {
        name = entity.getNickname();
        gender = entity.getSex();
        phone = entity.getPhone();
        mail = entity.getEmail();
        sign = entity.getSignature();
        tv_gender.setText((gender == 1) ? "男" : "女");
        tv_mail.setText(mail);
        tv_phone.setText(phone);
        tv_sign.setText(sign);
        tv_name.setText(name);
        ImageLoader.getInstance().displayImage(entity.getAvatar(), img_head);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_left:
                finish();
                break;
            case R.id.tv_right:
                if(name.equals("")){
                    showToast("昵称不可为空");
                }
                else{
                    startFillTask();
                }
                break;
            case R.id.rlt_name:
                EditDialog editNameDialog = new EditDialog(getThis(), "请输入名称", "取消", "确定", null, new OnEditFinishListener() {
                    @Override
                    public void onFinish(String content) {
                        name = content;
                        tv_name.setText(content);
                    }
                });
                break;
            case R.id.rlt_gender:
                ChooseSexDialog chooseSexDialog = new ChooseSexDialog(getThis(), new ChooseSexDialog.OnChooseSexListener() {
                    @Override
                    public void onChoose(int sex) {
                        gender = sex;
                        tv_gender.setText((gender==1)? "男":"女");
                    }
                });
                break;
            case R.id.rlt_sign:
                EditDialog editSignDialog = new EditDialog(getThis(), "请填写个性签名", "取消", "确定", null, new OnEditFinishListener() {
                    @Override
                    public void onFinish(String content) {
                        sign = content;
                        tv_sign.setText(content);
                    }
                });
                break;
            case R.id.rlt_phone:
                EditDialog phoneDialog = new EditDialog(getThis(), "请输入手机", "取消", "确定", null, new OnEditFinishListener() {
                    @Override
                    public void onFinish(String content) {
                        phone = content;
                        tv_phone.setText(phone);
                    }
                });
                break;
            case R.id.rlt_mail:
                EditDialog mailDialog = new EditDialog(getThis(), "请输入邮箱", "取消", "确定", null, new OnEditFinishListener() {
                    @Override
                    public void onFinish(String content) {
                        mail = content;
                        tv_mail.setText(mail);
                    }
                });
                break;
            case R.id.img_head:
                ChoosePhotoDialog choosePhotoDialog = new ChoosePhotoDialog(getThis(), getThis(), new ChoosePhotoDialog.OnChooseCameraListener() {
                    @Override
                    public void onChooseCamera(String path) {
                        avatar = path;
                        img_head.setImageURI(Uri.parse(path));
                    }
                });
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == ChoosePhotoDialog.CAREMA && resultCode == RESULT_OK){
        }
        else if(requestCode == ChoosePhotoDialog.CHOOSE_PHOTO && resultCode == RESULT_OK){
            if(data != null){
                Uri imageUri = data.getData();
                if(imageUri != null){
                    avatar = FileUtils.getFilePathByURI(getThis(), imageUri);
                }
            }
        }
    }

    private void startFillTask(){
        FillTask task = new FillTask();
        task.execute();
    }

    private class FillTask extends BaseTask {
        @Override
        protected Object doWorkInBackground(Object... params) {
            RequestParams requestParams = new RequestParams();
            requestParams.addBodyParameter("userId", SpUtil.getUserId(getThis()) + "");
            requestParams.addBodyParameter("sex", gender + "");
            requestParams.addBodyParameter("nickname", name);
            requestParams.addBodyParameter("sign", sign);
            requestParams.addBodyParameter("phone", phone);
            requestParams.addBodyParameter("email", mail);
            requestParams.addBodyParameter("avatar", new File(avatar));
            HttpUtils http = new HttpUtils();
            http.send(HttpRequest.HttpMethod.POST,
                    "host/api/user/changeInformation",
                    requestParams,
                    new RequestCallBack() {
                        @Override
                        public void onStart() {
                            loadingDialog = new LoadingDialog(getThis(), "正在修改..");
                        }
                        @Override
                        public void onLoading(long total, long current, boolean isUploading) {
                        }
                        @Override
                        public void onSuccess(ResponseInfo responseInfo) {
                            loadingDialog.dismiss();

                        }
                        @Override
                        public void onFailure(HttpException e, String s) {
                            loadingDialog.dismiss();
                        }
                    });
            return null;
        }
    }
}
