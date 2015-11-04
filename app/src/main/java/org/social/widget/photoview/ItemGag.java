package org.social.widget.photoview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.social.R;
import org.social.util.ExplosionUtils;
import org.social.widget.listener.OnEventEndListener;

/**
 * Created by caowenhua on 2015/11/1.
 */
public class ItemGag extends RelativeLayout implements View.OnClickListener{


    private int btnWidth;
    private float downX;

    private TextView tv_text;
    private Button btn_del;

    public ItemGag(Context context) {
        super(context);
        init();
    }

    public ItemGag(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){
        LayoutInflater.from(getContext()).inflate(R.layout.item_gag, this);
        tv_text = (TextView) findViewById(R.id.tv_text);
        btn_del = (Button) findViewById(R.id.btn_del);
        btnWidth = ExplosionUtils.dp2Px(120);
        RelativeLayout.LayoutParams params = new LayoutParams(btnWidth, RelativeLayout.LayoutParams.MATCH_PARENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, TRUE);
        btn_del.setLayoutParams(params);
        tv_text.setOnClickListener(this);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                downX = event.getX();
                tv_text.animate().cancel();
                break;
            case MotionEvent.ACTION_MOVE:
//                if(Math.abs(event.getX() - moveX) > btnWidth / 20){
//                    Log.e("moveeeeee", event.getX() + "  ");
                if(event.getX() > downX){
                    if(getX() < 0f){
                        tv_text.setX(0f);
                    }
                }
                else{
                    if(event.getX() - downX < -btnWidth){
//                            tv_text.setX(-btnWidth);
                    }
                    else{
                        tv_text.setX(event.getX() - downX);
                    }

                }
//                }
                break;
            case MotionEvent.ACTION_UP:
                upEvent();
                break;
        }
        return super.dispatchTouchEvent(event);
    }

    private void upEvent() {
        if(-tv_text.getX() < btnWidth/2 && -tv_text.getX() > 0){
            tv_text.animate().translationX(0f).setDuration(150).start();
        }
        else if( -tv_text.getX() < btnWidth &&  -tv_text.getX() >= btnWidth/2){
            tv_text.animate().translationX(-btnWidth).setDuration(150).start();
        }
    }

    //    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//
//        return super.onTouchEvent(event);
//    }

    public void setText(String text){
        if(text == null){
            tv_text.setText("");
        }
        else{
            tv_text.setText(text);
        }
    }

    public void setTextOnClick(OnClickListener onClickListener){
        tv_text.setOnClickListener(onClickListener);
    }

    public void setBtnOnClick(OnClickListener onClickListener){
        btn_del.setOnClickListener(onClickListener);
    }

    public void resetPostion(){
        if(tv_text.getX() != 0){
            tv_text.setX(0f);
        }
    }


    @Override
    public void onClick(View v) {
        if(v == tv_text){

        }
    }

    private OnEventEndListener onEventEndListener = new OnEventEndListener() {
        @Override
        public void onEventEnd() {
            upEvent();
        }
    };

    public OnEventEndListener getOnEventEndListener(){
        return onEventEndListener;
    }
}
