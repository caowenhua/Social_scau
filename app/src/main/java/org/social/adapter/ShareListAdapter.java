package org.social.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;

import org.social.R;
import org.social.activity.ShareDetailActivity;
import org.social.activity.UserInfoActivity;
import org.social.api.Api;
import org.social.base.BaseTask;
import org.social.base.TaskListener;
import org.social.response.BaseResponse;
import org.social.response.SharesEntity;
import org.social.util.DateUtil;
import org.social.util.SpUtil;
import org.social.widget.CircleImageView;
import org.social.widget.NoScrollGridView;
import org.social.widget.dialog.EditDialog;
import org.social.widget.dialog.LoadingDialog;
import org.social.widget.dialog.OnEditFinishListener;

import java.util.List;

/**
 * Created by caowenhua on 2015/10/14.
 */
public class ShareListAdapter extends BaseAdapter {
    private Context context;
    private List<SharesEntity> list;

    public ShareListAdapter(Context context, List<SharesEntity> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return list.get(position).getShareId();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_share_list, null);
            holder = getViewHolder(convertView);
            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
        }
        final ViewHolder finalHolder = holder;
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v == finalHolder.img_head){
                    Intent intent = new Intent(context, UserInfoActivity.class);
                    context.startActivity(intent);
                }
                else if(v == finalHolder.rlt_comment){
                    EditDialog editDialog = new EditDialog(context, "请输入评论内容", "取消", "确定", null, new OnEditFinishListener() {
                        @Override
                        public void onFinish(String content) {
                            CommentTask task = new CommentTask(SpUtil.getUserId(context), list.get(position).getShareId(), content, position);
                            task.setListener(taskListener);
                            task.execute();
                        }
                    });
                }
                else if(v == finalHolder.rlt_like){
                    list.get(position).setIsLike(!list.get(position).isLike());
                    LikeTask task = new LikeTask(SpUtil.getUserId(context), list.get(position).getShareId(),
                            list.get(position).isLike(), position);
                    task.setListener(taskListener);
                    task.execute();
                }
                else if(v == finalHolder.rlt_share){
                    Intent intent = new Intent(context, ShareDetailActivity.class);
                    context.startActivity(intent);
                }
            }
        };
        holder.img_head.setOnClickListener(onClickListener);
        holder.rlt_comment.setOnClickListener(onClickListener);
        holder.rlt_like.setOnClickListener(onClickListener);
        holder.rlt_share.setOnClickListener(onClickListener);

        ImageLoader.getInstance().displayImage(list.get(position).getAvatar(), holder.img_head);
        holder.tv_name.setText(list.get(position).getNickname());
        holder.tv_content.setText(list.get(position).getContent());
        holder.tv_time.setText(DateUtil.getDateByTime(list.get(position).getShareTime()));
        if(list.get(position).getImgList().size() == 0){
            holder.img_single.setVisibility(View.GONE);
            holder.grid_photo.setVisibility(View.GONE);
        }
        else if(list.get(position).getImgList().size() == 1){
            holder.img_single.setVisibility(View.VISIBLE);
            holder.grid_photo.setVisibility(View.GONE);
            ImageLoader.getInstance().displayImage(list.get(position).getImgList().get(0), holder.img_single);
        }
        else{
            holder.img_single.setVisibility(View.GONE);
            holder.grid_photo.setVisibility(View.VISIBLE);
            GridPhotoAdapter gridPhotoAdapter = new GridPhotoAdapter(context, list.get(position).getImgList());
            holder.grid_photo.setAdapter(gridPhotoAdapter);
        }
        holder.tv_like.setText(list.get(position).getLikeCount() + "");
        holder.tv_comment.setText(list.get(position).getCommentCount() + "");
        if(list.get(position).isLike()){
            holder.img_like.setImageResource(R.drawable.feed_button_like_active);
        }
        else{
            holder.img_like.setImageResource(R.drawable.feed_button_like);
        }

        return convertView;
    }

    @NonNull
    private ViewHolder getViewHolder(View convertView) {
        ViewHolder holder;
        holder = new ViewHolder();
        holder.img_head = (CircleImageView) convertView.findViewById(R.id.img_head);
        holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
        holder.tv_content = (TextView) convertView.findViewById(R.id.tv_content);
        holder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
        holder.tv_like = (TextView) convertView.findViewById(R.id.tv_like);
        holder.tv_comment = (TextView) convertView.findViewById(R.id.tv_comment);
        holder.img_single = (ImageView) convertView.findViewById(R.id.img_single);
        holder.img_like = (ImageView) convertView.findViewById(R.id.img_like);
        holder.grid_photo = (NoScrollGridView) convertView.findViewById(R.id.grid_photo);
        holder.rlt_like = (RelativeLayout) convertView.findViewById(R.id.rlt_like);
        holder.rlt_comment = (RelativeLayout) convertView.findViewById(R.id.rlt_comment);
        holder.rlt_share = (RelativeLayout) convertView.findViewById(R.id.rlt_share);
        return holder;
    }

    class ViewHolder{
        RelativeLayout rlt_share;
        CircleImageView img_head;
        TextView tv_name;
        TextView tv_time;
        TextView tv_content;
        TextView tv_like;
        TextView tv_comment;
//        TextView tv_more;
        ImageView img_single;
        ImageView img_like;
        NoScrollGridView grid_photo;
        RelativeLayout rlt_like;
        RelativeLayout rlt_comment;
//        RelativeLayout rlt_more;
    }

    private BaseResponse commentResponse;
    private class CommentTask extends BaseTask{
        int userId;
        int shareId;
        String content;
        int position;
        public CommentTask(int userId, int shareId, String content, int position){
            this.userId = userId;
            this.shareId = shareId;
            this.content = content;
            this.position = position;
        }
        public int getPosition(){
            return position;
        }
        @Override
        protected Object doWorkInBackground(Object... params) {
            Api api = new Api(context);
            commentResponse = api.comment(userId, shareId, content);
            return null;
        }
    }

    private LoadingDialog loadingDialog;
    private TaskListener taskListener = new TaskListener() {
        @Override
        public void onPreExecute(BaseTask task) {
            if(task instanceof CommentTask){
                loadingDialog = new LoadingDialog(context, "正在评论..");
            }

        }

        @Override
        public void onPostExecute(BaseTask task, Object result) {
            if(task instanceof CommentTask){
                loadingDialog.dismiss();
                if(commentResponse.getStatus().equals("success")){
                    Toast.makeText(context, "评论成功", Toast.LENGTH_SHORT).show();
                    list.get(((CommentTask)task).getPosition()).setCommentCount(list.get(((CommentTask)task).getPosition()).getCommentCount()+1);
                }
                else{
                    Toast.makeText(context, commentResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            if(task instanceof LikeTask){
                LikeTask LikeTask = (LikeTask)task;
                if(!LikeTask.getResponse().getStatus().equals("success")){
                    list.get(LikeTask.getPosition()).
                            setIsLike(!list.get(LikeTask.getPosition()).isLike());
                    notifyDataSetChanged();
                    Toast.makeText(context, LikeTask.getResponse().getMessage(), Toast.LENGTH_SHORT).show();
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

    private class LikeTask extends BaseTask{
        private int position;
        private int userId;
        private boolean isLike;
        private int shareId;
        private BaseResponse response;
        public LikeTask(int userId, int shareId, boolean isLike, int position) {
            this.userId = userId;
            this.shareId = shareId;
            this.isLike = isLike;
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
            response = api.like(userId, shareId, isLike);
            return null;
        }
    }
}
