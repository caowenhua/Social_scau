package org.social.bean;

import android.util.Log;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by caowenhua on 2015/10/25.
 */
@DatabaseTable(tableName = "tb_upload")
public class UploadShareBean implements Serializable {
    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField(columnName = "content")
    private String content;
    @DatabaseField(columnName = "imgs")
    private String imgs;
    @DatabaseField(columnName = "isShared")
    private boolean isShared;
    private List<String> paths;

    public UploadShareBean() {
        paths = new ArrayList<>();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isShared() {
        return isShared;
    }

    public void setIsShared(boolean isShared) {
        this.isShared = isShared;
    }

    public List<String> getPaths() {
        paths = new ArrayList<>();
        String[] strings = imgs.split(",");
        for (int i=0 ; i<strings.length ; i++){
            if(strings[i].length() > 4){
                paths.add(strings[i]);
            }
        }
        Log.e("bean--->getImgs", this.imgs + "--" + strings.length + "--" + paths.size());
        return paths;
    }

    public void setPaths(List<String> paths) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < paths.size() ; i++){
            stringBuilder.append(paths.get(i));
            if(i < paths.size() - 1){
                stringBuilder.append(",");
            }
        }
        setImgs(stringBuilder.toString());
//        this.imgs = stringBuilder.toString();
        Log.e("bean--->string img", this.imgs);
    }

    public String getImgs() {
        return imgs;
//        if(imgs.length() == 0){
//            return new ArrayList<>();
//        }
//        else{
//            paths = new ArrayList<>();
//            String[] strings = imgs.split("||");
//            Log.e("bean--->getImgs", this.imgs + "--" + strings.length);
//            for (int i=0 ; i<strings.length ; i++){
//                paths.add(strings[i]);
//            }
//            return paths;
//        }
    }

    public void setImgs(String img) {
        imgs = img;
//        StringBuilder stringBuilder = new StringBuilder();
//        for (int i = 0; i < imgs.size() ; i++){
//            stringBuilder.append(imgs.get(i));
//            if(i < imgs.size() - 1){
//                stringBuilder.append("||");
//            }
//        }
//        this.imgs = stringBuilder.toString();
//        Log.e("bean--->string img", this.imgs);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
