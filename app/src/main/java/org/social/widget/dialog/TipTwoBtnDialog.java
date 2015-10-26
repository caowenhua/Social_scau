package org.social.widget.dialog;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.social.R;
import org.social.base.BaseDialog;
import org.social.widget.listener.OnButtonClickLister;


public class TipTwoBtnDialog extends BaseDialog {

	private TextView tv_title;
	private TextView tv_content;
	private Button btn_left;
	private Button btn_right;
	
	private OnButtonClickLister leftButtonListener;
	private OnButtonClickLister rightButtonListener;
	
	/**
	 * 
	 * @param context
	 * @param title	dialog标题
	 * @param content	内容
	 * @param button	按钮文字
	 * @param listener	按钮事件监听器
	 */
	public TipTwoBtnDialog(Context context, String title, String content, String leftButton, 
			String rightButton, OnButtonClickLister leftListener, OnButtonClickLister rightListener) {
		super(context);
		leftButtonListener = leftListener;
		rightButtonListener = rightListener;
		tv_title.setText(title);
		tv_content.setText(content);
		btn_right.setText(rightButton);
		btn_right.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(rightButtonListener != null){
					rightButtonListener.onClick();
				}
				dismiss();
			}
		});
		btn_left.setText(leftButton);
		btn_left.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(leftButtonListener != null){
					leftButtonListener.onClick();
				}
				dismiss();
			}
		});
	}
	
	@Override
	protected int setLayout() {
		return R.layout.dialog_tip_two_btn;
	}

	@Override
	protected void findView() {
		tv_title = findViewByID(R.id.tv_title);
		tv_content = findViewByID(R.id.tv_content);
		btn_left = findViewByID(R.id.btn_left);
		btn_right = findViewByID(R.id.btn_right);
	}

	@Override
	protected void initData() {

	}
}
