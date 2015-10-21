package org.social.widget.dialog;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;

import org.social.R;
import org.social.base.BaseDialog;
import org.social.util.FileUtils;

import java.io.File;

public class ChoosePhotoDialog extends BaseDialog implements OnClickListener {

	public static final int CAREMA = 50;
	public static final int CHOOSE_PHOTO = 51;
	
    private Button btn_camera;
    private Button btn_choose;
    private Button btn_cancel;
    
    private Activity activity;
    private OnChooseCameraListener onChooseCameraListener;
    
    public ChoosePhotoDialog(Context context, Activity activity, OnChooseCameraListener listener) {
//		super(context, R.style.DefaultFullScreenDialogAnimation);
		super(context);
		this.activity = activity;
		onChooseCameraListener = listener;
		
		WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
		getWindow().setAttributes(lp);
	}
	

	@Override
	protected int setLayout() {
		return R.layout.dialog_upload_photo;
	}

	@Override
	protected void findView() {
        btn_camera = (Button) findViewById(R.id.btn_camera);
        btn_choose = (Button) findViewById(R.id.btn_choose);
        btn_cancel = (Button) findViewById(R.id.btn_cancel);
	}

	@Override
	protected void initData() {
		setCanceledOnTouchOutside(true);
		btn_camera.setOnClickListener(this);
        btn_choose.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);
	}

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_camera:
                dismiss();
            	PackageManager packageManager = getContext().getPackageManager();
				boolean doesHaveCamera = packageManager
						.hasSystemFeature(PackageManager.FEATURE_CAMERA);
				String picPath = FileUtils.setImageCacheDir(getContext());
				if(onChooseCameraListener != null){
					onChooseCameraListener.onChooseCamera(picPath);
				}
				if (doesHaveCamera) {
					Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
					intent.putExtra(MediaStore.EXTRA_OUTPUT,
							Uri.fromFile(new File(picPath)));
					activity.startActivityForResult(intent, CAREMA);
				} else {
					showToast("没有可用相机");
				}
                break;
            case R.id.btn_cancel:
                dismiss();
                break;
            case R.id.btn_choose:
                dismiss();
            	try {
					// 选择照片的时候也一样，我们用Action为Intent.ACTION_GET_CONTENT，
					// 有些人使用其他的Action但我发现在有些机子中会出问题，所以优先选择这个
					Intent intent = new Intent();
					intent.setType("image/*");
					intent.setAction(Intent.ACTION_GET_CONTENT);
					activity.startActivityForResult(intent, CHOOSE_PHOTO);
				} catch (ActivityNotFoundException e) {
				}
                break;
        }
    }
    
    public interface OnChooseCameraListener{
    	void onChooseCamera(String path);
    }
}
