package com.zhoulesin.base;

import com.zhoulesin.base.android.Context;
import com.zhoulesin.base.android.ImageView;

/**
 * 策略接口
 * @author zhoul
 *
 */
public interface BaseImageLoaderStrategy {

	/**
	 * 默认方式加载图片
	 * @param context 上下文
	 * @param view 控件
	 * @param imgUrl 图片url
	 */
	void loadImage(Context context, ImageView view, Object imgUrl);
}
