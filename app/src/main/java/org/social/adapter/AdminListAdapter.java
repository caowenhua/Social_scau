package org.social.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;

import org.social.R;
import org.social.activity.UserInfoActivity;
import org.social.api.Api;
import org.social.base.BaseTask;
import org.social.base.TaskListener;
import org.social.response.BaseResponse;
import org.social.response.UserListResponse;
import org.social.util.SpUtil;
import org.social.widget.CircleImageView;

import java.util.List;

/**
 * Created by caowenhua on 2015/10/14.
 */
public class AdminListAdapter extends BaseAdapter {

    private Context context;
    private List<UserListResponse.UserListEntity> userListList;
    private OnListDataChangeListener listener;

    public AdminListAdapter(Context context, List<UserListResponse.UserListEntity> userListList,
                            OnListDataChangeListener listener) {
        this.context = context;
        this.userListList = userListList;
        this.listener = listener;
    }

    @Override
    public int getCount() {
        return (userListList==null)? 0:userListList.size();
    }

    @Override
    public Object getItem(int position) {
        return (userListList==null)? null:userListList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_admin_list, null);
            holder = new ViewHolder();
            holder.btn_shut = (Button) convertView.findViewById(R.id.btn_shut);
            holder.img_head = (CircleImageView) convertView.findViewById(R.id.img_head);
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_name.setText(userListList.get(position).getNickname());
        if(userListList.get(position).isShare()){
            holder.btn_shut.setText("normal");
        }
        else{
            holder.btn_shut.setText("silence");
        }
        ImageLoader.getInstance().displayImage(userListList.get(position).getAvatar(), holder.img_head);
        final ViewHolder finalHolder = holder;
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v == finalHolder.img_head){
                    Intent intent = new Intent(context, UserInfoActivity.class);
                    intent.putExtra("userId", userListList.get(position).getUserId());
                    context.startActivity(intent);
                }
                else if(v == finalHolder.btn_shut){
                    userListList.get(position).setIsShare(!userListList.get(position).isShare());
                    if(listener != null){
                        listener.onChange(userListList.get(position).getUserId(),
                                userListList.get(position).getIsShare());
                    }
                    notifyDataSetChanged();
                    ChangeTask changeTask = new ChangeTask(position);
                    changeTask.setListener(taskListener);
                    changeTask.execute();
                }
            }
        };
        holder.img_head.setOnClickListener(onClickListener);
        return convertView;
    }

    class ViewHolder{
        CircleImageView img_head;
        TextView tv_name;
        Button btn_shut;
    }


    private class ChangeTask extends BaseTask{
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
            response = api.shieldUser(userListList.get(position).getUserId(), SpUtil.getUserId(context),
                    userListList.get(position).isShare());
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
                    userListList.get(changeTask.getPosition()).
                            setIsShare(!userListList.get(changeTask.getPosition()).isShare());
                    if(listener != null){
                        listener.onChange(userListList.get(changeTask.getPosition()).getUserId(),
                                userListList.get(changeTask.getPosition()).getIsShare());
                    }
                    notifyDataSetChanged();
                    Toast.makeText(context, changeTask.getResponse().getMessage(), Toast.LENGTH_SHORT).show();
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
