package org.social.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;

import org.social.R;
import org.social.adapter.GridPhotoAdapter;
import org.social.base.BaseActivity;
import org.social.widget.NoScrollGridView;
import org.social.widget.TitleBar;
import org.social.widget.dialog.OnButtonClickLister;
import org.social.widget.dialog.TipTwoBtnDialog;

import java.util.ArrayList;

/**
 * Created by caowenhua on 2015/10/13.
 */
public class EditShareActivity extends BaseActivity implements View.OnClickListener{

    private TextView tv_count;
    private EditText edt_content;
    private TitleBar titleBar;
    private NoScrollGridView gridView;

    private ArrayList<String> pathList;
    private GridPhotoAdapter adapter;

    @Override
    protected int setLayout() {
        return R.layout.activity_edit_share;
    }

    @Override
    protected void findView() {
        tv_count = findViewByID(R.id.tv_count);
        edt_content = findViewByID(R.id.edt_content);
        titleBar = findViewByID(R.id.titlebar);
        gridView = findViewByID(R.id.grid_photo);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        pathList = new ArrayList<>();
        pathList.add("add");
        titleBar.left.setOnClickListener(this);
        titleBar.right.setOnClickListener(this);

        adapter = new GridPhotoAdapter(this, pathList);

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

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                if(pathList.get(position).equals("add")){
                    Bundle bundle = new Bundle();
                    bundle.putStringArrayList("path", pathList);
                    startActivity(ChoosePhotoActivity.class, null, 50);
                }
                else{
                    TipTwoBtnDialog dialog = new TipTwoBtnDialog(getThis(), "删除照片", "确认删除？"
                            , "取消", "确定", null, new OnButtonClickLister() {
                        @Override
                        public void onClick() {
                            pathList.remove(position);
                            adapter.notifyDataSetChanged();
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_left:
                finish();
                break;
            case R.id.tv_right:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data != null){
            if(data.getStringArrayListExtra("path") != null){
                pathList.clear();
                pathList.addAll(data.getStringArrayListExtra("path"));
                adapter.notifyDataSetChanged();
            }
            else if(data.getStringExtra("camera") != null){
                pathList.add(data.getStringExtra("camera"));
                adapter.notifyDataSetChanged();
            }
        }
    }
}
