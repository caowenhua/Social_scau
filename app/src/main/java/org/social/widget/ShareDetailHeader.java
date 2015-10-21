package org.social.widget;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.social.R;
import org.social.activity.UserInfoActivity;

/**
 * Created by caowenhua on 2015/10/14.
 */
public class ShareDetailHeader extends RelativeLayout implements View.OnClickListener{

    private CircleImageView img_head;
    private TextView tv_name;
    private TextView tv_time;
    private TextView tv_content;
    private ImageView img_single;
    private NoScrollGridView grid_photo;
    private ImageView img_collection;

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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_head:
                Intent intent = new Intent(getContext(), UserInfoActivity.class);
                getContext().startActivity(intent);
                break;
        }
    }


    @SuppressWarnings("unchecked")
    protected <T extends View> T findViewByID(int id) {
        return (T) findViewById(id);
    }
}
