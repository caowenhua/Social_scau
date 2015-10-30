package org.social.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.social.R;
import org.social.adapter.AdminListAdapter;
import org.social.adapter.OnListDataChangeListener;
import org.social.api.Api;
import org.social.base.BaseActivity;
import org.social.base.BaseTask;
import org.social.base.TaskListener;
import org.social.response.UserListResponse;
import org.social.widget.CircleImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by caowenhua on 2015/10/12.
 */
public class AdminMainActivity extends BaseActivity implements View.OnClickListener{

    private TextView tv_name;
    private ImageView img_setting;
    private ImageView img_word;
    private CircleImageView img_head;
    private EditText edt_search;
    private ListView lv_result;
    private ProgressBar pb_loading;

    private List<UserListResponse.UserListEntity> userListList;
    private AdminListAdapter adapter;

    @Override
    protected int setLayout() {
        return R.layout.activity_admin_main;
    }

    @Override
    protected void findView() {
        tv_name = findViewByID(R.id.tv_name);
        img_head = findViewByID(R.id.img_head);
        img_word = findViewByID(R.id.img_word);
        img_setting = findViewByID(R.id.img_setting);
        edt_search = findViewByID(R.id.edt_search);
        lv_result = findViewByID(R.id.lv_result);
        pb_loading = findViewByID(R.id.pb_loading);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        img_setting.setOnClickListener(this);
        img_word.setOnClickListener(this);
        userListList = new ArrayList<>();
        adapter = new AdminListAdapter(this, userListList, new OnListDataChangeListener() {
            @Override
            public void onChange(int id, boolean bool) {
                if(userListResponse != null){
                    for(int i=0 ;i<userListResponse.getUserList().size() ;i++){
                        if(userListResponse.getUserList().get(i).getUserId() == id){
                            userListResponse.getUserList().get(i).setIsShare(bool);
                        }
                    }
                }
            }
        });
        lv_result.setAdapter(adapter);

        edt_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() == 0){
                    if(userListResponse != null){
                        userListList.clear();
                        userListList.addAll(userListResponse.getUserList());
                        adapter.notifyDataSetChanged();
                    }
                }
                else{
                    if(userListResponse != null){
                        userListList.clear();
                        for (int i=0 ; i<userListResponse.getUserList().size() ;i++){
                            if(userListResponse.getUserList().get(i).getNickname().equals(s.toString())){
                                userListList.add(userListResponse.getUserList().get(i));
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        startGetTask();
    }

    @Override
    public void onClick(View v) {
        if(v == img_setting){
            startActivity(SettingActivity.class, null, 0);
        }
        else if(v == img_word){
            startActivity(GagActivity.class, null, 0);
        }
    }

    private void startGetTask(){
        GetListTask task = new GetListTask();
        task.setListener(taskListener);
        task.execute();
    }

    private UserListResponse userListResponse;
    private class GetListTask extends BaseTask{
        @Override
        protected Object doWorkInBackground(Object... params) {
            Api api = new Api(getThis());
            userListResponse = api.showUserList();
            return null;
        }
    }

    private TaskListener taskListener = new TaskListener() {
        @Override
        public void onPreExecute(BaseTask task) {
        }

        @Override
        public void onPostExecute(BaseTask task, Object result) {
            if(task instanceof GetListTask){
                pb_loading.setVisibility(View.GONE);
                lv_result.setVisibility(View.VISIBLE);
                if(userListResponse.getStatus().equals("success")){
                    userListList.clear();
                    userListList.addAll(userListResponse.getUserList());
                    adapter.notifyDataSetChanged();
                }
                else{
                    showToast(userListResponse.getMessage());
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
