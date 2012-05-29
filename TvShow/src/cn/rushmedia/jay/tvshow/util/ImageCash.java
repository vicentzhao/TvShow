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
			// �ȴ�mHardBitmapCache�����л�ȡ         
			synchronized (mHardBitmapCache) {            
				final Bitmap bitmap =mHardBitmapCache.get(url);            
				if (bitmap != null) {              
					//����ҵ��Ļ�����Ԫ���Ƶ�linkedhashmap����ǰ�棬�Ӷ���֤��LRU�㷨�������ɾ��          
					mHardBitmapCache.remove(url);                
					mHardBitmapCache.put(url,bitmap);              
					return bitmap;             }     
   }         //���mHardBitmapCache���Ҳ�������mSoftBitmapCache����  
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

 

