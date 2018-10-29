package com.zhoulesin.base;

import com.zhoulesin.base.android.Context;
import com.zhoulesin.base.android.ImageView;
import com.zhoulesin.base.glide.ImageLoaderConfig;
import com.zhoulesin.base.glide.RequestOptions;

public class GlideLoaderStrategy implements BaseImageLoaderStrategy{
	
	private RequestOptions mOptions;
	private ImageLoaderConfig mConfig;
	
	private RequestOptions getOptions() {
		if (mOptions == null) {
			mOptions = new RequestOptions();
			mOptions.error(mConfig.getErrorPicRes())
				.placeholder(mConfig.getPlacePicRes())
				.priority(Priority.NORMAL)
				.diskCacheStrategy(DiskCacheStrategy.ALL);
		}
		return mOptions;
	}

	@Override
	public void loadImage(Context context, ImageView view, Object imgUrl) {
		Glide.with(context)
		.load(imgUrl)
		.apply(getOptions())
		.thumbnail(Constants.THUMB_SIZE)
		.into(view);
	}

}
