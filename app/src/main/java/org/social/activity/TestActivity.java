package org.social.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.social.R;
import org.social.base.BaseActivity;
import org.social.widget.PullToRefreshView;

/**
 * Created by caowenhua on 2015/10/14.
 */
public class TestActivity extends BaseActivity {
    private PullToRefreshView lvp;
    private ListView lv;

    @Override
    protected int setLayout() {
        return R.layout.activity_test;
    }

    @Override
    protected void findView() {
        lvp = findViewByID(R.id.lvp);
        lv = findViewByID(R.id.lv);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        lvp.setRefreshStyle(0);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, new String[]{"管理员","游客","会员"});
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0){
                    startActivity(AdminMainActivity.class, null, 0);
                }
                else if(position == 1){
                    startActivity(TouristMainActivity.class, null, 0);
                }
                else{
                    startActivity(UserMainActivity.class, null, 0);
                }
            }
        });
    }
}
