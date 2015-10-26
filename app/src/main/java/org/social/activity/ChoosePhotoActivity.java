package org.social.activity;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import org.social.R;
import org.social.adapter.ChoosePhotoAdapter;
import org.social.base.BaseActivity;
import org.social.util.FileUtils;
import org.social.widget.TitleBar;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by caowenhua on 2015/10/23.
 */
public class ChoosePhotoActivity extends BaseActivity implements AdapterView.OnItemClickListener, View.OnClickListener{

    public static final int CAREMA = 50;
    public static final int CHOOSE_PHOTO = 51;

    private GridView gv_photo;
    private TitleBar titleBar;

    private List<String> selectedList;
    private List<String> allList;

    private ChoosePhotoAdapter adapter;

    @Override
    protected int setLayout() {
        return R.layout.activity_choose_photo;
    }

    @Override
    protected void findView() {
        gv_photo = findViewByID(R.id.gv_photo);
        titleBar = findViewByID(R.id.titlebar);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        titleBar.left.setOnClickListener(this);
        titleBar.right.setOnClickListener(this);

        selectedList = new ArrayList<>();
        allList = new ArrayList<>();
        allList.add("camera");
        adapter = new ChoosePhotoAdapter(selectedList, allList, this);
        gv_photo.setAdapter(adapter);
        getLoaderManager().initLoader(0, null, loaderCallbacks);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(allList.get(position).equals("camera")){
            PackageManager packageManager = getPackageManager();
            boolean doesHaveCamera = packageManager
                    .hasSystemFeature(PackageManager.FEATURE_CAMERA);
            String picPath = FileUtils.setImageCacheDir(this);
            if (doesHaveCamera) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(new File(picPath)));
                startActivityForResult(intent, CAREMA);
            } else {
                showToast("没有可用相机");
            }
        }
        else{
            boolean isIn = false;
            for(int i=0 ; i<selectedList.size(); i++){
                if(selectedList.get(i).contains(allList.get(position))){
                    isIn = true;
                    selectedList.remove(i);
                    break;
                }
            }
            if(!isIn && selectedList.size() < 9){
                selectedList.add(allList.get(position));
            }
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onClick(View v) {
        if(v == titleBar.left){
            finish();
        }
        else if(v == titleBar.right){

        }
    }

    private void setRightText(String s){
        titleBar.right.setText(selectedList.size() + "/9 确定");
    }

    private LoaderManager.LoaderCallbacks loaderCallbacks = new LoaderManager.LoaderCallbacks() {
        private String[] proj = new String[]{};
        @Override
        public Loader onCreateLoader(int id, Bundle args) {
            return null;
        }

        @Override
        public void onLoadFinished(Loader loader, Object data) {

        }

        @Override
        public void onLoaderReset(Loader loader) {

        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CAREMA && resultCode == RESULT_OK){

        }
        else if(requestCode == CHOOSE_PHOTO && resultCode == RESULT_OK){
        }
    }
}