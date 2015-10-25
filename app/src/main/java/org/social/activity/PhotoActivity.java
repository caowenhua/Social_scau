package org.social.activity;

import android.os.Bundle;

import com.nostra13.universalimageloader.core.ImageLoader;

import org.social.R;
import org.social.api.Api;
import org.social.base.BaseActivity;
import org.social.widget.photoview.PhotoView;

/**
 * Created by caowenhua on 2015/10/14.
 */
public class PhotoActivity extends BaseActivity {

    private PhotoView img_photo;

    @Override
    protected int setLayout() {
        return R.layout.activity_photo;
    }

    @Override
    protected void findView() {
        img_photo = findViewByID(R.id.img_photo);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        String url = getIntent().getStringExtra("url");
        if(url == null || url.equals("")){
            showToast("图片路径读取出错");
            finish();
        }
        else{
            ImageLoader.getInstance().displayImage(Api.IP+url, img_photo);
        }
    }
}
