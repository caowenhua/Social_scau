package org.social.base;


import android.os.AsyncTask;


/**
 * 在 doWorkInBackground 实现
 * 设置task监听器
 * @author caowenhua
 *
 */
public abstract class BaseTask extends AsyncTask<Object, Object, Object>{
	private TaskListener mListener = null;

	abstract protected Object doWorkInBackground(Object... params);

	public void setListener(TaskListener taskListener) {
		mListener = taskListener;
	}

	public TaskListener getListener() {
		return mListener;
	}

	@Override
	protected void onCancelled() {
		super.onCancelled();
		if (mListener != null) {
			mListener.onCancelled(this);
		}
	}

	@Override
	protected void onPostExecute(Object result) {
		super.onPostExecute(result);

		if (mListener != null) {
			mListener.onPostExecute(this, result);
		}
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();

		if (mListener != null) {
			mListener.onPreExecute(this);
		}
	}

	@Override
	protected void onProgressUpdate(Object... values) {
		super.onProgressUpdate(values);

		if (mListener != null) {
			if (values != null && values.length > 0) {
				mListener.onProgressUpdate(this, values[0]);
			}
		}
	}

	@Override
	protected Object doInBackground(Object... params) {
		return doWorkInBackground(params);
	}

}
