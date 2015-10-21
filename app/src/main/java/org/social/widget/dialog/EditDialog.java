package org.social.widget.dialog;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.social.R;
import org.social.base.BaseDialog;

/**
 * Created by caowenhua on 2015/10/13.
 */
public class EditDialog extends BaseDialog{
    private TextView tv_title;
    private EditText edt_content;
    private Button btn_left;
    private Button btn_right;

    private OnButtonClickLister leftButtonListener;
    private OnEditFinishListener rightButtonListener;

    /**
     *
     * @param context
     * @param title	dialog标题
     * @param button	按钮文字
     * @param listener	按钮事件监听器
     */
    public EditDialog(Context context, String title, String leftButton,
                           String rightButton, OnButtonClickLister leftListener, OnEditFinishListener rightListener) {
        super(context);
        leftButtonListener = leftListener;
        rightButtonListener = rightListener;
        tv_title.setText(title);
        btn_right.setText(rightButton);
        btn_left.setText(leftButton);
        btn_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rightButtonListener != null) {
                    String content;
                    if (edt_content.getText().length() > 0) {
                        content = edt_content.getText().toString();
                    } else {
                        content = "";
                    }
                    rightButtonListener.onFinish(content);
                }
                dismiss();
            }
        });
        btn_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (leftButtonListener != null) {
                    leftButtonListener.onClick();
                }
                dismiss();
            }
        });
    }

    @Override
    protected int setLayout() {
        return R.layout.dialog_edit;
    }

    @Override
    protected void findView() {
        tv_title = findViewByID(R.id.tv_title);
        edt_content = findViewByID(R.id.edt_content);
        btn_left = findViewByID(R.id.btn_left);
        btn_right = findViewByID(R.id.btn_right);
    }

    @Override
    protected void initData() {

    }


}
