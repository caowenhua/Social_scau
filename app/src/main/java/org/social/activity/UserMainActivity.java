package org.social.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.social.R;
import org.social.adapter.ShareListAdapter;
import org.social.base.BaseActivity;
import org.social.widget.PullToRefreshView;

/**
 * Created by caowenhua on 2015/10/12.
 */
public class UserMainActivity extends BaseActivity implements View.OnClickListener{

    private ImageView img_me;
    private ImageView img_search;
    private ImageView img_setting;
    private ImageView img_camera;
    private TextView tv_name;
    private ListView lv_list;
    private PullToRefreshView v_pull;
    private ShareListAdapter adapter;

    @Override
    protected int setLayout() {
        return R.layout.activity_user_main;
    }

    @Override
    protected void findView() {
        img_camera = findViewByID(R.id.img_camera);
        img_search = findViewByID(R.id.img_search);
        img_setting = findViewByID(R.id.img_setting);
        img_me = findViewByID(R.id.img_me);
        tv_name = findViewByID(R.id.tv_name);
        lv_list = findViewByID(R.id.lv_list);
        v_pull = findViewByID(R.id.v_pull);
    }


    @Override
    protected void initData(Bundle savedInstanceState) {
        img_camera.setOnClickListener(this);
        img_search.setOnClickListener(this);
        img_setting.setOnClickListener(this);
        img_me.setOnClickListener(this);

        v_pull.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {

            }
        });

        adapter = new ShareListAdapter(this);
        lv_list.setAdapter(adapter);

        lv_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(ShareDetailActivity.class, null, 0);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_camera:
                startActivity(EditShareActivity.class, null, 0);
                break;
            case R.id.img_search:
                startActivity(GroundActivity.class, null, 0);
                break;
            case R.id.img_setting:
                startActivity(SettingActivity.class, null, 0);
                break;
            case R.id.img_me:
                startActivity(UserInfoActivity.class, null, 0);
                break;
        }
    }
}
