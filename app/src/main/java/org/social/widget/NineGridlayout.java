package org.social.widget;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.download.ImageDownloader;

import org.social.R;
import org.social.activity.ChoosePhotoActivity;
import org.social.activity.EditShareActivity;
import org.social.util.ScreenTools;

import java.util.List;


/**
 * Created by Pan_ on 2015/2/2.
 */
public class NineGridlayout extends ViewGroup implements OnClickListener{

    /**
     * 图片之间的间隔
     */
    private int gap = 5;
    private int columns;//
    private int rows;//
    private List<String> listData;
    private int totalWidth;
    private int oldViewCount;

    private EditShareActivity activity;
    private TipTwoBtnDialog dialog;

    public NineGridlayout(Context context) {
        super(context);
    }

    public NineGridlayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        ScreenTools screenTools=ScreenTools.instance(getContext());
        totalWidth=screenTools.getScreenWidth()-screenTools.dip2px(80);
        activity = (EditShareActivity)getContext();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }
    private void layoutChildrenView(){
        int childrenCount = listData.size();

        int singleWidth = (totalWidth - gap * (3 - 1)) / 3;
        int singleHeight = singleWidth;

        //根据子view数量确定高度
        ViewGroup.LayoutParams params = getLayoutParams();
        params.height = singleHeight * rows + gap * (rows - 1);
        setLayoutParams(params);

        for (int i = 0; i < childrenCount; i++) {
            ImageView childrenView = (ImageView) getChildAt(i);
            int[] position = findPosition(i);
            int left = (singleWidth + gap) * position[1];
            int top = (singleHeight + gap) * position[0];
            int right = left + singleWidth;
            int bottom = top + singleHeight;

            childrenView.layout(left, top, right, bottom);
            if(listData.get(i).equals("add")){
                childrenView.setImageResource(R.drawable.add_photo_plus);
            }
            else if(listData.get(i).contains("http")){
                ImageLoader.getInstance().displayImage(listData.get(i), childrenView);
            }
            else{
                ImageLoader.getInstance().displayImage(ImageDownloader.Scheme.FILE.wrap(listData.get(i)), childrenView);
            }
        }

    }


    private int[] findPosition(int childNum) {
        int[] position = new int[2];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if ((i * columns + j) == childNum) {
                    position[0] = i;//行
                    position[1] = j;//列
                    break;
                }
            }
        }
        return position;
    }

    public int getGap() {
        return gap;
    }

    public void setGap(int gap) {
        this.gap = gap;
    }


    public void setImagesData(List<String> lists) {
        if (lists == null || lists.isEmpty()) {
            return;
        }
        //初始化布局
        generateChildrenLayout(lists.size());
//        imageViewList.clear();
        //这里做一个重用view的处理
        if (listData == null) {
            int i = 0;
            while (i < lists.size()) {
                ImageView iv = generateImageView();
                addView(iv,generateDefaultLayoutParams());
                i++;
            }
        } else {
//            int oldViewCount = listData.size();
            int newViewCount = lists.size();
            if (oldViewCount > newViewCount) {
                removeViews(newViewCount - 1, oldViewCount - newViewCount);
            } else if (oldViewCount < newViewCount) {
                for (int i = 0; i < newViewCount - oldViewCount; i++) {
                    ImageView iv = generateImageView();
                    addView(iv,generateDefaultLayoutParams());
                }
            }
        }
        listData = lists;
        oldViewCount = listData.size();
        layoutChildrenView();
    }


    /**
     * 根据图片个数确定行列数量
     * 对应关系如下
     * num        row        column
     * 1           1        1
     * 2           1        2
     * 3           1        3
     * 4           2        2
     * 5           2        3
     * 6           2        3
     * 7           3        3
     * 8           3        3
     * 9           3        3
     *
     * @param length
     */
    private void generateChildrenLayout(int length) {
        if (length <= 3) {
            rows = 1;
            columns = length;
        } else if (length <= 6) {
            rows = 2;
            columns = 3;
            if (length == 4) {
                columns = 2;
            }
        } else {
            rows = 3;
            columns = 3;
        }
    }

    private ImageView generateImageView() {
        ImageView iv = new ImageView(getContext());
        iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
        iv.setOnClickListener(this);
        return iv;
    }


    @Override
    public void onClick(final View v) {
        int size = -1;
        for(int i=0 ; i<getChildCount() ; i++){
            if(getChildAt(i).equals(v)){
                size = i;
            }
        }
        if(size != -1){
            if(listData.get(size).equals("add")){
                Bundle bundle = new Bundle();
                bundle.putStringArrayList("path", activity.getPathList());
                activity.startActivity(ChoosePhotoActivity.class, bundle, 50);
            }
            else{
                final int finalSize = size;
                dialog = new TipTwoBtnDialog(getContext(), "丢弃图片", "确定丢弃图片？", "取消", "确定", null,
                        new TipTwoBtnDialog.OnButtonClickLister(){
                            @Override
                            public void onClick() {
                                listData.remove(finalSize);
                                removeView(v);
                                layoutChildrenView();
                            }
                        });
            }
        }
    }
}