package cn.rushmedia.jay.tvshow.util;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

import cn.rushmedia.jay.tvshow.domain.AppData;

import android.graphics.Bitmap;

public class ImageCash {
	
	ConcurrentHashMap<String,SoftReference<Bitmap>> mSoftBitmapCache;
	HashMap<String, Bitmap> mHardBitmapCache;
		public Bitmap getBitmapFromCache(String url) {      
			AppData appData = AppData.getInstance();
			mSoftBitmapCache = appData.getmSoftBitmapCache();
			mHardBitmapCache = appData.getmHardBitmapCache();
			// 先从mHardBitmapCache缓存中获取         
			synchronized (mHardBitmapCache) {            
				final Bitmap bitmap =mHardBitmapCache.get(url);            
				if (bitmap != null) {              
					//如果找到的话，把元素移到linkedhashmap的最前面，从而保证在LRU算法中是最后被删除          
					mHardBitmapCache.remove(url);                
					mHardBitmapCache.put(url,bitmap);              
					return bitmap;             }     
   }         //如果mHardBitmapCache中找不到，到mSoftBitmapCache中找  
			SoftReference<Bitmap> bitmapReference = mSoftBitmapCache.get(url);        
			if (bitmapReference != null) {             
				final Bitmap bitmap =bitmapReference.get();         
				if (bitmap != null) {           
					return bitmap;           
					} else {              
						mSoftBitmapCache.remove(url);     
						}        
				}       
			return null;    
			} 
		}

 

