package org.social.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Toast;

import org.social.api.Api;
import org.social.base.BaseTask;
import org.social.base.TaskListener;
import org.social.response.BaseResponse;
import org.social.response.GagEntity;
import org.social.widget.dialog.LoadingDialog;
import org.social.widget.listener.OnEventEndListener;
import org.social.widget.photoview.ItemGag;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by caowenhua on 2015/11/1.
 */
public class GagAdapter extends BaseAdapter{

    private List<GagEntity> list;
    private Context context;
    private List<OnEventEndListener> listeners;

    private int id;
    private int positio;

    public GagAdapter(Context context, List<GagEntity> list) {
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
        gag.setText(list.get(position).getKeyWord());
        gag.setBtnOnClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                positio = position;
                id = list.get(position).getId();
                startTask();
            }
        });
        return convertView;
    }


    public void endEvent(){
        for (int i=0; i<listeners.size() ;i++){
            listeners.get(i).onEventEnd();
        }
    }

    private void startTask(){
        GagTask task = new GagTask();
        task.setListener(taskListener);
        task.execute();
    }

    private LoadingDialog loadingDialog;
    private BaseResponse shutResponse;
    private class GagTask extends BaseTask{
        @Override
        protected Object doWorkInBackground(Object... params) {
            Api api = new Api(context);
            shutResponse = api.deleteKeyWord(id);
            return null;
        }
    }

    private TaskListener taskListener = new TaskListener() {
        @Override
        public void onPreExecute(BaseTask task) {
            loadingDialog = new LoadingDialog(context, "正在删除..");
        }

        @Override
        public void onPostExecute(BaseTask task, Object result) {
            loadingDialog.dismiss();
            if(shutResponse.getStatus().equals("success")){
                list.remove(positio);
                notifyDataSetChanged();
            }
            else{
                Toast.makeText(context, shutResponse.getMessage(), Toast.LENGTH_SHORT).show();
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
