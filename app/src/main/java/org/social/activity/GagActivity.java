package org.social.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import org.social.R;
import org.social.adapter.GagAdapter;
import org.social.api.Api;
import org.social.base.BaseActivity;
import org.social.base.BaseTask;
import org.social.base.TaskListener;
import org.social.response.BaseResponse;
import org.social.response.GagEntity;
import org.social.response.GagResponse;
import org.social.widget.TitleBar;
import org.social.widget.dialog.EditDialog;
import org.social.widget.dialog.LoadingDialog;
import org.social.widget.listener.OnEditFinishListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by caowenhua on 2015/10/30.
 */
public class GagActivity extends BaseActivity implements View.OnClickListener{

    private TitleBar titleBar;
    private ListView lv_list;
    private GagAdapter gagAdapter;

    private List<GagEntity> list;

    @Override
    protected int setLayout() {
        return R.layout.activity_gag;
    }

    @Override
    protected void findView() {
        titleBar = findViewByID(R.id.titlebar);
        lv_list = findViewByID(R.id.lv_list);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        titleBar.left.setOnClickListener(this);
        titleBar.right.setOnClickListener(this);
        list = new ArrayList<>();
        gagAdapter = new GagAdapter(this, list);
        lv_list.setAdapter(gagAdapter);
        lv_list.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                gagAdapter.endEvent();
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                gagAdapter.endEvent();
            }
        });

        startGetTask();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_left:
                finish();
                break;
            case R.id.tv_right:
                EditDialog editDialog = new EditDialog(getThis(), "请输入禁言关键字", "取消", "确定", null,
                        new OnEditFinishListener() {
                            @Override
                            public void onFinish(String content) {
                                AddTask task = new AddTask(content);
                                task.setListener(taskListener);
                                task.execute();
                            }
                        });
                break;
        }
    }

    private void startGetTask(){
        GetGagTask task = new GetGagTask();
        task.setListener(taskListener);
        task.execute();
    }

    private BaseResponse addResponse;
    private class AddTask extends BaseTask{
        private String keyWord;
        public AddTask(String s) {
            keyWord = s;
        }

        @Override
        protected Object doWorkInBackground(Object... params) {
            Api api = new Api(getThis());
            addResponse = api.addKeyWord(keyWord);
            return null;
        }
    }

    private LoadingDialog loadingDialog;
    private GagResponse gagResponse;
    private class GetGagTask extends BaseTask{
        @Override
        protected Object doWorkInBackground(Object... params) {
            Api api = new Api(getThis());
            gagResponse = api.queryKeyWord();
            return null;
        }
    }

    private TaskListener taskListener = new TaskListener() {
        @Override
        public void onPreExecute(BaseTask task) {
            if(task instanceof AddTask){
                loadingDialog = new LoadingDialog(getThis(), "正在增加..");
            }
        }

        @Override
        public void onPostExecute(BaseTask task, Object result) {
            if(task instanceof GetGagTask){
                if(gagResponse.getStatus().equals("success")){
                    list.clear();
                    list.addAll(gagResponse.getShutUpList());
                    gagAdapter.notifyDataSetChanged();
                }
            }
            else if(task instanceof AddTask){
                loadingDialog.dismiss();
                if(addResponse.getStatus().equals("success")){
                    showToast("增加成功");
                    startGetTask();
                }
                else{
                    showToast(addResponse.getMessage());
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
