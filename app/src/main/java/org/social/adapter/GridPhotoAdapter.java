package org.social.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.download.ImageDownloader;

import org.social.R;
import org.social.api.Api;

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
        ViewHolder holder = null;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_grid_photo, null);
            holder.img_photo = (ImageView) convertView.findViewById(R.id.img_photo);
            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
        }
        if(urls.get(position).equals("add")){
            holder.img_photo.setImageResource(R.drawable.add_photo_plus);
        }
        else{
            if(urls.get(position).contains("Share")){
                ImageLoader.getInstance().displayImage(Api.IP+urls.get(position) ,holder.img_photo);
            }
            else{
                ImageLoader.getInstance().displayImage(ImageDownloader.Scheme.FILE.wrap(urls.get(position)),
                        holder.img_photo);
            }
        }
        return convertView;
    }

    class ViewHolder{
        ImageView img_photo;
    }
}
