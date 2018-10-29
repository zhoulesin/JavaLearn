package com.zhoulesin.base;

import com.zhoulesin.base.android.Context;
import com.zhoulesin.base.android.ImageView;

public class Test {
	public static void main(String[] args) {
		ImageLoaderUtils.setImageLoaderStrategy(new GlideLoaderStrategy());
		Object imgUrl = 1;
		ImageView view = new ImageView();
		Context context = new Context();
		ImageLoaderUtils.loadImage(context, view, imgUrl);
	}
}
