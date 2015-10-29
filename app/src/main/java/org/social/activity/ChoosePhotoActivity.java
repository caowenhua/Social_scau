package org.social.activity;

import android.app.LoaderManager;
import android.content.ContentResolver;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.pm.PackageManager;
import android.database.Cursor;
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

    private ArrayList<String> selectedList;
    private List<String> allList;

    private ChoosePhotoAdapter adapter;
    private String cameraPath;

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

//        selectedList = new ArrayList<>();
        selectedList = getIntent().getStringArrayListExtra("path");
        allList = new ArrayList<>();
        allList.add("camera");
        adapter = new ChoosePhotoAdapter(selectedList, allList, this);
        gv_photo.setAdapter(adapter);
        gv_photo.setOnItemClickListener(this);
        getLoaderManager().initLoader(0, null, loaderCallbacks);
        setRightText();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(allList.get(position).equals("camera")){
            PackageManager packageManager = getPackageManager();
            boolean doesHaveCamera = packageManager
                    .hasSystemFeature(PackageManager.FEATURE_CAMERA);
            cameraPath = FileUtils.setImageCacheDir(this);
            if (doesHaveCamera) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(new File(cameraPath)));
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
            setRightText();
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onClick(View v) {
        if(v == titleBar.left){
            Intent intent = new Intent();
            intent.putExtra("path", "none");
            setResult(RESULT_OK, intent);
            finish();
        }
        else if(v == titleBar.right){
            Intent intent = new Intent();
            intent.putExtra("path", selectedList);
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent();
        intent.putExtra("path", "none");
        setResult(RESULT_OK, intent);
        finish();
    }

    private void setRightText(){
        titleBar.right.setText(selectedList.size() + "/9 确定");
    }

    private LoaderManager.LoaderCallbacks<Cursor> loaderCallbacks = new LoaderManager.LoaderCallbacks<Cursor>() {
//        String[] projection = { MediaStore.Images.Thumbnails._ID, MediaStore.Images.Thumbnails.IMAGE_ID,
//                MediaStore.Images.Thumbnails.DATA };
        private final String[] IMAGE_PROJECTION = {
                MediaStore.Images.Media.DATA,
                MediaStore.Images.Media.DATE_ADDED, MediaStore.Images.Media._ID };
        @Override
        public Loader onCreateLoader(int id, Bundle args) {
            CursorLoader cursorLoader = new CursorLoader(
                    getThis(),
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    IMAGE_PROJECTION, null, null, IMAGE_PROJECTION[1] + " DESC");
//            CursorLoader cursorLoader = new CursorLoader(
//                    getThis(),
//                    MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI,
//                    projection, null, null, projection[1] + " DESC");
            return cursorLoader;
        }

        @Override
        public void onLoadFinished(Loader loader, Cursor data) {
            if (data != null) {
                // List<String> imagePath = new ArrayList<String>();
                int count = data.getCount();
                if (count > 0) {
                    data.moveToFirst();
                    do {
                        allList.add(data.getString(data
                                .getColumnIndexOrThrow(IMAGE_PROJECTION[0])));
//                        String path = thumb2Path(data.getString(data.getColumnIndexOrThrow(projection[1])));
//                        if(path != null)
//                            allList.add(path);
                    } while (data.moveToNext());
                    adapter.notifyDataSetChanged();
                }
            }
        }

        @Override
        public void onLoaderReset(Loader loader) {

        }
    };

    private String thumb2Path(String image_id){
        ContentResolver cr = getContentResolver();
        String[] projection = { MediaStore.Images.Media._ID, MediaStore.Images.Media.DATA };
        Cursor cursor = cr.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection,
                MediaStore.Images.Media._ID + "=" + image_id, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            return path;
         }
        return null;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CAREMA && resultCode == RESULT_OK){
            Intent intent = new Intent();
            intent.putExtra("camera", cameraPath);
            setResult(RESULT_OK, intent);
            finish();
        }
    }
}
