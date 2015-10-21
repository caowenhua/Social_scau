package org.social.widget.dialog;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import org.social.R;
import org.social.base.BaseDialog;

/**
 * Created by caowenhua on 2015/10/14.
 */
public class ChooseSexDialog extends BaseDialog implements View.OnClickListener{

    private TextView tv_man;
    private TextView tv_woman;
    private OnChooseSexListener listener;

    public ChooseSexDialog(Context context, OnChooseSexListener listener) {
        super(context);
        this.listener = listener;
    }

    @Override
    protected int setLayout() {
        return R.layout.dialog_choose_sex;
    }

    @Override
    protected void findView() {
        tv_man = findViewByID(R.id.tv_man);
        tv_woman = findViewByID(R.id.tv_woman);
    }

    @Override
    protected void initData() {
        tv_woman.setOnClickListener(this);
        tv_man.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == tv_man){
            if(listener != null){
                listener.onChoose(1);
                dismiss();
            }
        }
        else{
            if(listener != null){
                listener.onChoose(0);
                dismiss();
            }
        }
    }

    public interface OnChooseSexListener{
        void onChoose(int sex);
    }
}
