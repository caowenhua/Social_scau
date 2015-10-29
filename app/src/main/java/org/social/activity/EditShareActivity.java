package org.social.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.util.LogUtils;

import org.social.R;
import org.social.base.BaseActivity;
import org.social.bean.UploadShareBean;
import org.social.dao.UploadShareDAO;
import org.social.service.UploadService;
import org.social.widget.NineGridlayout;
import org.social.widget.TipTwoBtnDialog;
import org.social.widget.TitleBar;
import org.social.widget.listener.OnNineGridClickListener;

import java.util.ArrayList;

/**
 * Created by caowenhua on 2015/10/13.
 */
public class EditShareActivity extends BaseActivity implements View.OnClickListener, OnNineGridClickListener{

    private TextView tv_count;
    private EditText edt_content;
    private TitleBar titleBar;
//    private NoScrollGridView gridView;
//    private GridView gridView;
    private NineGridlayout grid_nine;
    private ImageView img_switch;

    private ArrayList<String> pathList;
    private TipTwoBtnDialog dialog;
    private boolean isShare = true;

    @Override
    protected int setLayout() {
        return R.layout.activity_edit_share;
    }

    @Override
    protected void findView() {
        tv_count = findViewByID(R.id.tv_count);
        edt_content = findViewByID(R.id.edt_content);
        titleBar = findViewByID(R.id.titlebar);
//        gridView = findViewByID(R.id.grid_photo);
        grid_nine = findViewByID(R.id.grid_nine);
        grid_nine.setOnNineGridClickListener(this);
        img_switch = findViewByID(R.id.img_switch);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        titleBar.left.setOnClickListener(this);
        titleBar.right.setOnClickListener(this);
        img_switch.setOnClickListener(this);

        if(getIntent() != null && getIntent().getStringExtra("OP") != null &&
                getIntent().getStringExtra("OP").equals("FAIL")){
                UploadShareDAO dao = UploadShareDAO.getInstance(this);
            if(dao.getList() != null && dao.getList().size() > 0){
                UploadShareBean bean = dao.getList().get(dao.getList().size()-1);
                isShare = bean.isShared();
                pathList = (ArrayList<String>) bean.getPaths();
                edt_content.setText(bean.getContent());
            }
        }
        else{
            pathList = new ArrayList<>();
        }
        addPlus();

        grid_nine.setImagesData(pathList);
        refreshButton();

        edt_content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tv_count.setText(s.length() + "/" + 300);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_left:
                finish();
                break;
            case R.id.tv_right:
                UploadShareDAO dao = UploadShareDAO.getInstance(getThis());
                UploadShareBean bean = new UploadShareBean();
                if(pathList.contains("add")){
                    pathList.remove("add");
                }
                bean.setIsShared(isShare);
                bean.setContent(edt_content.getText().toString() == null ? "" : edt_content.getText().toString());
                bean.setPaths(pathList);
//                bean.setImgs(pathList);
                dao.add(bean);
                Intent intent = new Intent(getThis(), UploadService.class);
                intent.putExtra("OP", "UPLOAD");
                startService(intent);
                finish();
                //TODO 返回一哥微博到上一页面显示
                break;
            case R.id.img_switch:
                isShare = !isShare;
                refreshButton();
                break;
        }
    }

    private void refreshButton() {
        if(isShare){
            img_switch.setImageResource(R.drawable.button_open);
        }
        else{
            img_switch.setImageResource(R.drawable.button_off);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data != null){
            if(data.getStringArrayListExtra("path") != null){
                pathList.clear();
                pathList.addAll(data.getStringArrayListExtra("path"));
                addPlus();
                grid_nine.setImagesData(pathList);
            }
            else if(data.getStringExtra("camera") != null){
                pathList.add(data.getStringExtra("camera"));
                addPlus();
                grid_nine.setImagesData(pathList);
            }
            else if(data.getStringExtra("none") != null){
                addPlus();
                grid_nine.setImagesData(pathList);
            }
        }
    }


    private ArrayList<String> getPathList(){
        for (int i=0; i < pathList.size() ;i++){
            if(pathList.get(i).equals("add")){
                pathList.remove(i);
            }
        }
        return pathList;
    }

    private void addPlus(){
        if(pathList.size() < 9 && !pathList.contains("add")){
            pathList.add("add");
        }
    }

    @Override
    public void onClick(final View v, final int position) {
        LogUtils.e(v.getId() + "----------" + position + "---" + pathList.size());
        if(pathList.get(position).equals("add")){
            Bundle bundle = new Bundle();
            bundle.putStringArrayList("path", getPathList());
            startActivity(ChoosePhotoActivity.class, bundle, 50);
        }
        else{
            dialog = new TipTwoBtnDialog(getThis(), "丢弃图片", "确定丢弃图片？", "取消", "确定", null,
                    new TipTwoBtnDialog.OnButtonClickLister(){
                        @Override
                        public void onClick() {
                            pathList.remove(position);
//                            grid_nine.removeView(v);
                            addPlus();
                            grid_nine.setImagesData(pathList);
                        }
                    });
        }
    }
}
