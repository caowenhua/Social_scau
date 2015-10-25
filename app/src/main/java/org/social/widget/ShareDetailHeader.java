package org.social.widget;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;

import org.social.R;
import org.social.activity.UserInfoActivity;
import org.social.adapter.GridPhotoAdapter;
import org.social.api.Api;
import org.social.base.BaseTask;
import org.social.base.TaskListener;
import org.social.response.BaseResponse;
import org.social.response.ShareDetailResponse;
import org.social.util.DateUtil;
import org.social.util.SpUtil;

/**
 * Created by caowenhua on 2015/10/14.
 */
public class ShareDetailHeader extends RelativeLayout implements View.OnClickListener, AdapterView.OnItemClickListener{

    private CircleImageView img_head;
    private TextView tv_name;
    private TextView tv_time;
    private TextView tv_content;
    private ImageView img_single;
    private NoScrollGridView grid_photo;
    private ImageView img_collection;
    private GridPhotoAdapter adapter;

    private ShareDetailResponse.ShareEntity entity;

    public ShareDetailHeader(Context context) {
        super(context);
        init();
    }

    private void init(){
        LayoutInflater.from(getContext()).inflate(R.layout.activity_share_detail_header, this);
        img_head = findViewByID(R.id.img_head);
        tv_content = findViewByID(R.id.tv_content);
        tv_time = findViewByID(R.id.tv_time);
        tv_name = findViewByID(R.id.tv_name);
        img_single = findViewByID(R.id.img_single);
        grid_photo = findViewByID(R.id.grid_photo);
        img_collection = findViewByID(R.id.img_collection);

        img_head.setOnClickListener(this);
        img_collection.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_head:
                if(entity != null){
                    Intent intent = new Intent(getContext(), UserInfoActivity.class);
                    intent.putExtra("userId", entity.getUserId());
                    getContext().startActivity(intent);
                }
                break;
            case R.id.img_collection:
                if(entity != null){
                    entity.setIsCollect(!entity.isCollect());
                    startTask();
                }
                break;
            case R.id.img_single:
                if(entity != null){
                    Intent intent = new Intent();
                    intent.putExtra("url", entity.getImgList().get(0));
                    getContext().startActivity(intent);
                }
                break;
        }
    }


    @SuppressWarnings("unchecked")
    protected <T extends View> T findViewByID(int id) {
        return (T) findViewById(id);
    }

    public void setData(ShareDetailResponse.ShareEntity entity){
        this.entity = entity;
        ImageLoader.getInstance().displayImage(entity.getAvatar(), img_head);
        tv_content.setText(entity.getContent());
        tv_time.setText(DateUtil.getDayByTime(entity.getShareTime()));
        tv_name.setText(entity.getNickname());
        if(entity.getImgList().size() > 1){
            img_single.setVisibility(GONE);
            adapter = new GridPhotoAdapter(getContext(), entity.getImgList());
            grid_photo.setVisibility(VISIBLE);
            grid_photo.setAdapter(adapter);
            grid_photo.setOnItemClickListener(this);
        }
        else if(entity.getImgList().size() > 0){
            img_single.setVisibility(VISIBLE);
            grid_photo.setVisibility(GONE);
            ImageLoader.getInstance().displayImage(entity.getImgList().get(0), img_single);
            img_single.setOnClickListener(this);
        }
        if(entity.isCollect()){
            img_collection.setImageResource(R.drawable.star_full);
        }
        else{
            img_collection.setImageResource(R.drawable.star_empty);
        }
    }

    private void startTask(){
        ChangeTask task = new ChangeTask();
        task.setListener(taskListener);
        task.execute();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    private class ChangeTask extends BaseTask {
        private BaseResponse response;
        public BaseResponse getResponse() {
            return response;
        }
        @Override
        protected Object doWorkInBackground(Object... params) {
            Api api = new Api(getContext());
            response = api.collect(SpUtil.getUserId(getContext()), entity.getShareId(), entity.isCollect());
            return null;
        }
    }

    private TaskListener taskListener = new TaskListener() {
        @Override
        public void onPreExecute(BaseTask task) {

        }

        @Override
        public void onPostExecute(BaseTask task, Object result) {
            if(task instanceof ChangeTask){
                ChangeTask changeTask = (ChangeTask)task;
                if(!changeTask.getResponse().getStatus().equals("success")){
                    entity.setIsCollect(!entity.isCollect());
                }
                else{
                    Toast.makeText(getContext(), changeTask.getResponse().getMessage(), Toast.LENGTH_SHORT).show();
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
