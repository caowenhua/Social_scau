package org.social.widget.dialog;

import android.content.Context;
import android.widget.TextView;

import org.social.R;
import org.social.base.BaseDialog;

/**
 * Created by caowenhua on 2015/10/12.
 */
public class LoadingDialog extends BaseDialog{
    private TextView tv_content;

    public LoadingDialog(Context context) {
        super(context);
    }

    /**
     *
     * @param context
     * @param content	显示内容
     */
    public LoadingDialog(Context context, String content) {
        super(context);
        tv_content.setText(content);
    }

    @Override
    protected int setLayout() {
        return R.layout.dialog_loading;
    }

    @Override
    protected void findView() {
        tv_content = findViewByID(R.id.tv_content);
    }

    @Override
    protected void initData() {
    }
}
