package org.social.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.social.R;
import org.social.response.ShareDetailResponse;
import org.social.widget.CircleImageView;

import java.util.List;

/**
 * Created by caowenhua on 2015/10/14.
 */
public class CommentListAdapter extends BaseAdapter {
    private Context context;
    private List<ShareDetailResponse.ShareEntity.CommentsEntity> comments;

    public CommentListAdapter(Context context, List<ShareDetailResponse.ShareEntity.CommentsEntity> comments) {
        this.context = context;
        this.comments = comments;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_comment_list, null);
            holder = new ViewHolder();
            holder.img_head = (CircleImageView) convertView.findViewById(R.id.img_head);
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            holder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
            holder.tv_content = (TextView) convertView.findViewById(R.id.tv_content);
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
//                    Intent intent = new Intent(context, UserInfoActivity.class);
//                    intent.putExtra("userId", comments.get(position).)
//                    context.startActivity(intent);
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
    }
}
