package org.social.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;

import org.social.R;
import org.social.activity.UserInfoActivity;
import org.social.api.Api;
import org.social.base.BaseTask;
import org.social.base.TaskListener;
import org.social.response.BaseResponse;
import org.social.response.FanListResponse;
import org.social.response.FollowListResponse;
import org.social.util.SpUtil;
import org.social.widget.CircleImageView;

import java.util.List;

/**
 * Created by caowenhua on 2015/10/14.
 */
public class PersonListAdapter extends BaseAdapter {

    private Context context;
    private List<FanListResponse.UserListEntity> fanList;
    private List<FollowListResponse.UserListEntity> followList;

    private int type;

    public PersonListAdapter(Context context) {
        this.context = context;
    }

    public void setFanList(List<FanListResponse.UserListEntity> fanList){
        this.fanList = fanList;
        type = 0;
    }

    public void setFollowList(List<FollowListResponse.UserListEntity> followList){
        this.followList = followList;
        type = 1;
    }


    @Override
    public int getCount() {
        if(type == 0){
            return fanList.size();
        }
        else{
            return followList.size();
        }
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_person_list, null);
            holder = new ViewHolder();
            holder.btn_follow = (Button) convertView.findViewById(R.id.btn_follow);
            holder.img_head = (CircleImageView) convertView.findViewById(R.id.img_head);
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            holder.tv_sign = (TextView) convertView.findViewById(R.id.tv_sign);
            holder.rlt = (RelativeLayout) convertView.findViewById(R.id.rlt);
            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
        }
        if(type == 0){
            holder.tv_name.setText(fanList.get(position).getNickname());
            holder.tv_sign.setText(fanList.get(position).getSignature());
            ImageLoader.getInstance().displayImage(Api.IP+fanList.get(position).getAvatar(), holder.img_head);
            if(fanList.get(position).isFollow()){
                holder.btn_follow.setText("取消关注");
            }
            else{
                holder.btn_follow.setText("关注");
            }
        }
        else{
            holder.tv_name.setText(followList.get(position).getNickname());
            holder.tv_sign.setText(followList.get(position).getSignature());
            ImageLoader.getInstance().displayImage(Api.IP+followList.get(position).getAvatar(), holder.img_head);
            if(followList.get(position).isAttention()){
                holder.btn_follow.setText("取消关注");
            }
            else{
                holder.btn_follow.setText("关注");
            }
        }
        final ViewHolder finalHolder = holder;
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v == finalHolder.img_head){
                    Intent intent = new Intent(context, UserInfoActivity.class);
                    if(type == 0){
                        intent.putExtra("userId", fanList.get(position).getUserId());
                    }
                    else{
                        intent.putExtra("userId", followList.get(position).getUserId());
                    }
                    context.startActivity(intent);
                }
                else if(v == finalHolder.btn_follow){
                    if(type == 0){
                        fanList.get(position).setIsFollow(!fanList.get(position).isFollow());
                        if(fanList.get(position).isFollow()){
                            finalHolder.btn_follow.setText("取消关注");
                        }
                        else{
                            finalHolder.btn_follow.setText("关注");
                        }
                    }
                    else{
                        followList.get(position).setIsAttention(!followList.get(position).isAttention());
                    }
                    ChangeTask task = new ChangeTask(position);
                    task.setListener(taskListener);
                    task.execute();
                    notifyDataSetChanged();
                }
            }
        };
        holder.rlt.setOnClickListener(onClickListener);
        holder.btn_follow.setOnClickListener(onClickListener);
        return convertView;
    }

    class ViewHolder{
        CircleImageView img_head;
        RelativeLayout rlt;
        TextView tv_name;
        TextView tv_sign;
        Button btn_follow;
    }


    private class ChangeTask extends BaseTask {
        private int position;
        private BaseResponse response;
        public ChangeTask(int position) {
            this.position = position;
        }
        public int getPosition() {
            return position;
        }
        public BaseResponse getResponse() {
            return response;
        }
        @Override
        protected Object doWorkInBackground(Object... params) {
            Api api = new Api(context);
            if(type == 0){
                response = api.follow(SpUtil.getUserId(context), fanList.get(position).getUserId(),
                        fanList.get(position).isFollow());
            }
            else{
                response = api.follow(SpUtil.getUserId(context), followList.get(position).getUserId(),
                        followList.get(position).isAttention());
            }
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
                    if(type == 0){
                        fanList.get(changeTask.getPosition()).setIsFollow(
                                !fanList.get(changeTask.getPosition()).isFollow());
                    }
                    else{
                        followList.get(changeTask.getPosition()).setIsAttention(
                                !followList.get(changeTask.getPosition()).isAttention());
                    }
                    notifyDataSetChanged();
                    Toast.makeText(context, changeTask.getResponse().getMessage(), Toast.LENGTH_SHORT).show();
                }
                else{
//                    notifyDataSetChanged();
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
