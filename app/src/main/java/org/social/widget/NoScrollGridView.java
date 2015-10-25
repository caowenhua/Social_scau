package org.social.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

import org.social.util.ExplosionUtils;

/**
 * Created by caowenhua on 2015/10/13.
 */
public class NoScrollGridView extends GridView {
    public NoScrollGridView(Context context) {
        super(context);
    }

    public NoScrollGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public NoScrollGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,MeasureSpec.AT_MOST);
        if(getAdapter() != null){
            expandSpec = expandSpec + (getAdapter().getCount() * ExplosionUtils.dp2Px(2));
        }
        super.onMeasure(widthMeasureSpec, expandSpec);
    }


}
