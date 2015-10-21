package org.social.base;

public interface TaskListener {

	void onPreExecute(BaseTask task);

	void onPostExecute(BaseTask task, Object result);

	void onProgressUpdate(BaseTask task, Object param);

	void onCancelled(BaseTask task);
}
