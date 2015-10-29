package org.social.dao;

import android.content.Context;

import com.j256.ormlite.dao.Dao;

import org.social.bean.UploadShareBean;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by caowenhua on 2015/10/26.
 */
public class UploadShareDAO {
    private Dao<UploadShareBean, Integer> dao;
    private DatabaseHelper helper;

    private static UploadShareDAO uploadShareDAO;

    public static UploadShareDAO getInstance(Context context){
        if(uploadShareDAO == null){
            uploadShareDAO = new UploadShareDAO(context);
        }
        return uploadShareDAO;
    }

    private UploadShareDAO(Context context) {
        try
        {
            helper = DatabaseHelper.getHelper(context);
            dao = helper.getDao(UploadShareBean.class);
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
    }

    public List<UploadShareBean> getList(){
        try {
            return dao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public void add(UploadShareBean bean){
        try {
            UploadShareBean tmpBean = dao.queryForId(bean.getId());
            if(tmpBean != null){
                dao.delete(tmpBean);
            }
            dao.create(bean);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(UploadShareBean bean){
        try {
            dao.delete(bean);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
