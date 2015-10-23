package org.social.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.social.base.BaseTask;
import org.social.util.SpUtil;

import java.io.File;

/**
 * Created by caowenhua on 2015/10/14.
 */
public class UploadService extends Service {

    public static final String CONTENT = "C";
    public static final String IS_SHARE = "I";
    public static final String IMG = "M";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startFillTask(intent);
        return super.onStartCommand(intent, flags, startId);
    }

    private void startFillTask(Intent intent){
        if(intent != null){
            if(intent.getStringExtra(CONTENT) == null && intent.getStringArrayExtra(IMG) == null){
                FillTask task = new FillTask(intent.getStringArrayExtra(IMG),
                        intent.getStringExtra(CONTENT), intent.getBooleanExtra(IS_SHARE, true));
                task.execute();
            }
        }

    }

    private class FillTask extends BaseTask {
        private String[] imgs;
        private String content;
        private boolean isShare;
        public FillTask(String[] imgs, String content, boolean isShare) {
            if(content == null){
                content = "";
            }
            this.imgs = imgs;
            this.content = content;
            this.isShare = isShare;
        }
        @Override
        protected Object doWorkInBackground(Object... params) {
            RequestParams requestParams = new RequestParams();
            requestParams.addBodyParameter("userId", SpUtil.getUserId(UploadService.this) + "");
            requestParams.addBodyParameter("content", content);
            requestParams.addBodyParameter("isShare", isShare + "");
            if(imgs != null){
                for(int i=0 ; i<imgs.length ; i++){
                    requestParams.addBodyParameter("img" + (i+1), new File(imgs[i]));
                }
            }
            HttpUtils http = new HttpUtils();
            http.send(HttpRequest.HttpMethod.POST,
                    "host/api/share/share",
                    requestParams,
                    new RequestCallBack() {
                        @Override
                        public void onStart() {
                        }
                        @Override
                        public void onLoading(long total, long current, boolean isUploading) {
                        }
                        @Override
                        public void onSuccess(ResponseInfo responseInfo) {

                        }
                        @Override
                        public void onFailure(HttpException e, String s) {
                            //TODO pujiele
                        }
                    });
            return null;
        }
    }

    private void releaseFailed(String[] imgs, String content, boolean isShare){

    }
}
