package com.zhoulesin.base;

import com.zhoulesin.base.android.Context;
import com.zhoulesin.base.android.ImageView;
import com.zhoulesin.base.imageloader.DisplayImageOptions;
import com.zhoulesin.base.imageloader.ImageLoader;
import com.zhoulesin.base.imageloader.ImageLoaderConfiguration;

public class UniversalLoaderStrategy implements BaseImageLoaderStrategy{
	
	private DisplayImageOptions mImageOptions;
	
	private void initOptions() {
		ImageLoaderConfiguration configuration = ImageLoaderConfiguration.createDefault(this);
		ImageLoader.getInstance().init(configuration);
		
		mImageOptions = DisplayImageOptions.createSimple();
	}

	@Override
	public void loadImage(Context context, ImageView view, Object imgUrl) {
		ImageLoader.getInstance().displayImage(imgUrl,view,mImageOptions);
	}

}
