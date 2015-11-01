package org.social.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Toast;

import org.social.widget.listener.OnEventEndListener;
import org.social.widget.photoview.ItemGag;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by caowenhua on 2015/11/1.
 */
public class GagAdapter extends BaseAdapter{

    private List<String> list;
    private Context context;
    private List<OnEventEndListener> listeners;

    public GagAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
        listeners = new ArrayList<>();
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
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ItemGag gag;
        if(convertView == null){
            gag = new ItemGag(context);
            convertView = gag;
            listeners.add(gag.getOnEventEndListener());
            convertView.setTag(gag);
        }
        else{
            gag = (ItemGag) convertView.getTag();
            gag.resetPostion();
        }
        gag.setText(list.get(position));
        gag.setBtnOnClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "posution" + position, Toast.LENGTH_SHORT).show();
            }
        });
        return convertView;
    }


    public void endEvent(){
        for (int i=0; i<listeners.size() ;i++){
            listeners.get(i).onEventEnd();
        }
    }
}
