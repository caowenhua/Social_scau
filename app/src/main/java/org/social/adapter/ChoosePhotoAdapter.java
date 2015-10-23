package org.social.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import org.social.R;
import org.social.activity.ChoosePhotoActivity;

import java.util.List;

/**
 * Created by caowenhua on 2015/10/23.
 */
public class ChoosePhotoAdapter extends BaseAdapter{

    private List<String> selectedList;
    private List<String> allList;
    private Context context;
    private ChoosePhotoActivity activity;

    public ChoosePhotoAdapter(List<String> selectedList, List<String> allList, Context context){
        this.selectedList = selectedList;
        this.allList = allList;
        this.context = context;
        activity = (ChoosePhotoActivity)context;
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
        if(isIn){
//            holder.img_choose.setImageResource(R.drawable.);
        }
        else{
//            holder.img_choose.setImageResource(R.drawable.);
        }
        return convertView;
    }

    class ViewHolder{
        ImageView img_photo;
        ImageView img_choose;
    }
}
