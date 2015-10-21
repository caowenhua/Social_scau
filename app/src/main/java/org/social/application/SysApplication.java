package org.social.application;

import android.app.Activity;
import android.app.Application;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;

import org.social.R;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by caowenhua on 2015/10/12.
 */
public class SysApplication extends Application {

        // 运用list来保存们每一个activity是关键
        private List<Activity> mList = new LinkedList<Activity>();

        // 为了实现每次使用该类时不创建新的对象而创建的静态对象
        private static SysApplication instance;

        public synchronized static SysApplication getInstance() {
                if (null == instance) {
                        instance = new SysApplication();
                }
                return instance;
        }


        @Override
        public void onCreate() {
            super.onCreate();
            setupImageLoader();
        }

        private void setupImageLoader() {
                DisplayImageOptions options = new DisplayImageOptions.Builder()
                        .showImageOnLoading(R.drawable.blank) // 加载图片时的图片
                        .showImageForEmptyUri(R.drawable.blank) // 没有图片资源时的默认图片
                        .showImageOnFail(R.drawable.blank) // 加载失败时的图片
                        .cacheInMemory(true) // 启用内存缓存
                        .cacheOnDisk(true) // 启用外存缓存
                        .considerExifParams(true) // 启用EXIF和JPEG图像格式
                        .build();

                File cacheDir = StorageUtils.getOwnCacheDirectory(this,
                        "imageloader/Cache");
                ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                        this)
                        .memoryCacheExtraOptions(2000, 2000)
                                // maxwidth, max height，即保存的每个缓存文件的最大长宽
                        .threadPoolSize(3)
                                // 线程池内加载的数量
                        .threadPriority(Thread.NORM_PRIORITY - 2)
                        .denyCacheImageMultipleSizesInMemory()
                        .memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024))
                                // You can pass your own memory cache
                                // implementation/你可以通过自己的内存缓存实现
                        .memoryCacheSize(2 * 1024 * 1024)
                        .discCacheSize(50 * 1024 * 1024)
                        .discCacheFileNameGenerator(new Md5FileNameGenerator())
                                // 将保存的时候的URI名称用MD5 加密
                        .tasksProcessingOrder(QueueProcessingType.LIFO)
                        .discCacheFileCount(200)
                                // 缓存的文件数量
                        .discCache(new UnlimitedDiscCache(cacheDir))
                                // 自定义缓存路径
                                // .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
                        .defaultDisplayImageOptions(options)
                        .imageDownloader(
                                new BaseImageDownloader(this, 5 * 1000, 30 * 1000)) // connectTimeout
                        .writeDebugLogs() // Remove for releaseapp
                        .build();// 开始构建
                ImageLoader.getInstance().init(config);
            }

        // add Activity
        public void addActivity(Activity activity) {
                mList.add(activity);
        }

        public void removeActivity(Activity activity) {
                if (mList.get(mList.size() - 1).equals(activity)) {
                        mList.remove(mList.size() - 1);
                } else {
                        for (int i = mList.size() - 1; i >= 0; i--) {
                                if (mList.get(i).equals(activity)) {
                                        mList.remove(i);
                                        break;
                                }
                        }
                }
        }

        // 关闭每一个list内的activity
        public void exit() {
                try {
                        for (Activity activity : mList) {
                                if (activity != null)
                                        activity.finish();
                        }
                } catch (Exception e) {
                        e.printStackTrace();
                } finally {
                        System.exit(0);
                }
        }

        public void cleanBottom() {
                if (mList != null) {
                        if (mList.size() > 1) {
                                for (int i = mList.size() - 2; i >= 0; i--) {
                                        mList.get(i).finish();
                                        mList.remove(i);
                                }
                        }
                }
        }

        // 杀进程
        public void onLowMemory() {
                super.onLowMemory();
                System.gc();
        }
}
