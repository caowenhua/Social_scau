package org.social.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.util.LogUtils;

import org.social.activity.EditShareActivity;
import org.social.api.Api;
import org.social.bean.UploadShareBean;
import org.social.dao.UploadShareDAO;
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
            if(intent.getStringExtra("OP") != null && intent.getStringExtra("OP").equals("UPLOAD")){
                xUpload();
            }
        }
    }

    private void xUpload() {
        final UploadShareDAO dao = UploadShareDAO.getInstance(this);
        if(dao.getList() != null && dao.getList().size() > 0){
            final UploadShareBean bean = dao.getList().get(dao.getList().size()-1);
            String address;
            RequestParams requestParams = new RequestParams();
            requestParams.addBodyParameter("userId", SpUtil.getUserId(UploadService.this) + "");
            requestParams.addBodyParameter("content", bean.getContent());
            requestParams.addBodyParameter("isShare", bean.isShared() + "");
            if (bean.getPaths() != null && bean.getPaths().size() > 0) {
                for (int i = 0; i < bean.getPaths().size(); i++) {
                    requestParams.addBodyParameter("img" + (i + 1), new File(bean.getPaths().get(i)));
                }
                address = "/Share/share/share";
            }
            else{
                address = "/Share/share/share1";
            }
            HttpUtils http = new HttpUtils();
            http.send(HttpRequest.HttpMethod.POST, Api.IP + address,
                    requestParams,
                    new RequestCallBack() {
                        @Override
                        public void onStart() {
                            LogUtils.e("starttttttttttt");
                        }

                        @Override
                        public void onLoading(long total, long current, boolean isUploading) {
                            LogUtils.e("total=" + total + "--current" + current + "--" + isUploading);
                        }

                        @Override
                        public void onSuccess(ResponseInfo responseInfo) {
                            dao.delete(bean);
                            LogUtils.e("sucessssssssssssss");
                        }

                        @Override
                        public void onFailure(HttpException e, String s) {
                            releaseFailed();
                            LogUtils.e(s);
                        }
                    });
        }
    }

    private void releaseFailed(){
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext());
        Intent intent = new Intent(getApplicationContext(), EditShareActivity.class);
        intent.putExtra("OP", "FAIL");
        PendingIntent pendingIntent = PendingIntent.getActivity(
                getApplicationContext(), 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentTitle("分享失败了~")
                .setSmallIcon(getApplicationInfo().icon)
                .setContentIntent(pendingIntent)
                .setDefaults(Notification.DEFAULT_ALL)
                .setAutoCancel(true);
        builder.setTicker("抱歉！分享失败了！");

        Notification notification = builder.build();
        notificationManager.notify(50, notification);
    }
}
