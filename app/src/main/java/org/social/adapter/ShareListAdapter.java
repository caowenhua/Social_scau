package org.social.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.social.R;
import org.social.activity.UserInfoActivity;
import org.social.widget.CircleImageView;
import org.social.widget.NoScrollGridView;

/**
 * Created by caowenhua on 2015/10/14.
 */
public class ShareListAdapter extends BaseAdapter {
    private Context context;

    public  ShareListAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 30;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_share_list, null);
            holder = new ViewHolder();
            holder.img_head = (CircleImageView) convertView.findViewById(R.id.img_head);
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            holder.tv_content = (TextView) convertView.findViewById(R.id.tv_content);
            holder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
            holder.tv_like = (TextView) convertView.findViewById(R.id.tv_like);
            holder.tv_comment = (TextView) convertView.findViewById(R.id.tv_comment);
//            holder.tv_more = (TextView) convertView.findViewById(R.id.tv_more);
            holder.img_single = (ImageView) convertView.findViewById(R.id.img_single);
            holder.grid_photo = (NoScrollGridView) convertView.findViewById(R.id.grid_photo);
            holder.rlt_like = (RelativeLayout) convertView.findViewById(R.id.rlt_like);
            holder.rlt_comment = (RelativeLayout) convertView.findViewById(R.id.rlt_comment);
//            holder.rlt_more = (RelativeLayout) convertView.findViewById(R.id.rlt_more);
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
            }
        };
        holder.img_head.setOnClickListener(onClickListener);
        return convertView;
    }

    class ViewHolder{
        CircleImageView img_head;
        TextView tv_name;
        TextView tv_time;
        TextView tv_content;
        TextView tv_like;
        TextView tv_comment;
//        TextView tv_more;
        ImageView img_single;
        NoScrollGridView grid_photo;
        RelativeLayout rlt_like;
        RelativeLayout rlt_comment;
//        RelativeLayout rlt_more;
    }
}
