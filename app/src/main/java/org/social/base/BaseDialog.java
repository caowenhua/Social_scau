package org.social.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import org.social.R;

/**
 * 新建完自动执行show
 * setLayout,initData等抽象方法都在super();里已经执行了
 * @author caowenhua
 *
 */
public abstract class BaseDialog extends Dialog {

	protected boolean firstClick;
	
	public BaseDialog(Context context) {
		super(context, R.style.myDialog);
		init();
	}

	public BaseDialog(Context context, boolean cancelable,
					  OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
		init();
	}

	public BaseDialog(Context context, int theme) {
		super(context, theme);
		init();
	}
	
	@SuppressWarnings("unchecked")
	protected <T extends View> T findViewByID(int id) {
		return (T) findViewById(id);
	}
	
	/**
	 * 设置布局
	 */
	protected abstract int setLayout();
	
	/**
	 * 初始化控件
	 */
	protected abstract void findView();
	
	/**
	 * 为控件设置内容或者监听器
	 */
	protected abstract void initData();
	
	@Override
	public void show() {
		try{
			super.show();
		}
		catch(Exception e){
			
		}
	}
	
	@Override
	public void dismiss() {
		try{
			super.dismiss();
		}
		catch(Exception e){
			
		}
	}

	private void init(){
		setContentView(setLayout());
		findView();
		initData();
		show();
	}
	

	/**
	 * 全局的 页面跳转方法
	 * @param dst  跳转页面
	 * @param bundle 
	 * @param requestCode  大于0有回调
	 */
	public void startActivity(Class<?> dst, Bundle bundle, int requestCode) {

		if(firstClick){
			firstClick = false;
			Intent intent = new Intent(getContext(), dst);
			
			if (bundle != null) {
				intent.putExtras(bundle);
			}

			if (requestCode > 0) {
				((Activity) getContext()).startActivityForResult(intent, requestCode);
			} else {
				getContext().startActivity(intent);
			}
		}

	}

	protected void showToast(String text){
		Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();
	}

	protected void showToastLong(String text){
		Toast.makeText(getContext(), text, Toast.LENGTH_LONG).show();
	}
}
