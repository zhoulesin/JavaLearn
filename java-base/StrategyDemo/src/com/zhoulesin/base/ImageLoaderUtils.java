package com.zhoulesin.base;

import javax.activation.MimetypesFileTypeMap;

import com.zhoulesin.base.android.Context;
import com.zhoulesin.base.android.ImageView;

public class ImageLoaderUtils {
	private static BaseImageLoaderStrategy mImageLoaderStrategy;
	
	private ImageLoaderUtils() {
		mImageLoaderStrategy = new UniversalLoaderStrategy();
	}
	
	public static void setImageLoaderStrategy(BaseImageLoaderStrategy strategy) {
		if (strategy != null) {
			mImageLoaderStrategy = strategy;
		}
	}
	
	public static void loadImage(Context context,ImageView view,Object imgUrl) {
		mImageLoaderStrategy.loadImage(context, view, imgUrl);
	}
}
