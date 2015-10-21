package org.social.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by caowenhua on 2015/10/14.
 */
public class GridPhotoAdapter extends BaseAdapter {

    private Context context;
    private List<String> urls;

    public GridPhotoAdapter(Context context, List<String> urls) {
        this.context = context;
        this.urls = urls;
    }

    @Override
    public int getCount() {
        return urls.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView img;
        if(convertView == null){
            img = new ImageView(context);
            img.setScaleType(ImageView.ScaleType.CENTER_CROP);
            convertView.setTag(img);
        }
        else{
            img = (ImageView) convertView.getTag();
        }
        ImageLoader.getInstance().displayImage(urls.get(position) ,img);
        return convertView;
    }
}
