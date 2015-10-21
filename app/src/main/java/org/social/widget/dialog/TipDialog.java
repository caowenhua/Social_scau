package org.social.widget.dialog;

import android.content.Context;
import android.text.SpannableString;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.social.R;
import org.social.base.BaseDialog;


public class TipDialog extends BaseDialog {

	private TextView tv_title;
	private TextView tv_content;
	private Button btn_commit;
	
	private OnButtonClickLister buttonListener;
	
	/**
	 * 
	 * @param context
	 * @param title	dialog标题
	 * @param content	内容
	 * @param button	按钮文字
	 * @param listener	按钮事件监听器
	 */
	public TipDialog(Context context, String title, String content, String button, OnButtonClickLister listener) {
		super(context);
		this.buttonListener = listener;
		tv_title.setText(title);
		tv_content.setText(content);
		btn_commit.setText(button);
		btn_commit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(buttonListener != null){
					buttonListener.onClick();
				}
				dismiss();
			}
		});
	}
	
	/**
	 * 
	 * @param context
	 * @param title	dialog标题
	 * @param content	内容
	 * @param button	按钮文字
	 * @param listener	按钮事件监听器
	 */
	public TipDialog(Context context, String title, SpannableString content, String button, OnButtonClickLister listener) {
		super(context);
		this.buttonListener = listener;
		tv_title.setText(title);
		tv_content.setText(content);
		btn_commit.setText(button);
		btn_commit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(buttonListener != null){
					buttonListener.onClick();
				}
				dismiss();
			}
		});
	}
	
	@Override
	protected int setLayout() {
		return R.layout.dialog_tip;
	}

	@Override
	protected void findView() {
		tv_title = findViewByID(R.id.tv_title);
		tv_content = findViewByID(R.id.tv_content);
		btn_commit = findViewByID(R.id.btn_commit);
	}

	@Override
	protected void initData() {
		
	}
}
