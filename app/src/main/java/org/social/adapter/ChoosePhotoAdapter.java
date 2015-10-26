package org.social.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.download.ImageDownloader;

import org.social.R;
import org.social.activity.ChoosePhotoActivity;
import org.social.util.ScreenTools;

import java.util.List;

/**
 * Created by caowenhua on 2015/10/23.
 */
public class ChoosePhotoAdapter extends BaseAdapter{

    private List<String> selectedList;
    private List<String> allList;
    private Context context;
    private ChoosePhotoActivity activity;
    private ScreenTools screenTools;

    public ChoosePhotoAdapter(List<String> selectedList, List<String> allList, Context context){
        this.selectedList = selectedList;
        this.allList = allList;
        this.context = context;
        activity = (ChoosePhotoActivity)context;
        screenTools = ScreenTools.instance(context);
    }

    @Override
    public int getCount() {
        return allList.size();
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_choose_photo, null);
            AbsListView.LayoutParams params = new AbsListView.LayoutParams(screenTools.getScreenWidth()/4, screenTools.getScreenWidth()/4);
            convertView.setLayoutParams(params);
            holder = new ViewHolder();
            holder.img_choose = (ImageView) convertView.findViewById(R.id.img_choose);
            holder.img_photo = (ImageView) convertView.findViewById(R.id.img_photo);
            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
        }
        boolean isIn = false;
        for(int i=0 ; i<selectedList.size(); i++){
            if(selectedList.get(i).contains(allList.get(position))){
                isIn = true;
                break;
            }
        }
        if(allList.get(position).equals("camera")){
            holder.img_photo.setImageResource(R.drawable.camera);
        }
        else{
            if(isIn){
                holder.img_choose.setImageResource(R.drawable.common_check_icon);
            }
            else{
                holder.img_choose.setImageResource(R.drawable.common_uncheck_icon);
            }
            ImageLoader.getInstance().displayImage(ImageDownloader.Scheme.FILE.wrap(allList.get(position)),
                    holder.img_photo);
        }
        return convertView;
    }

    class ViewHolder{
        ImageView img_photo;
        ImageView img_choose;
    }
}
