package cn.rushmedia.jay.tvshow.domain;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import android.app.Activity;
import android.app.Application;
import android.graphics.Bitmap;

public class AppData extends Application {
	 private static AppData instance;  
	 //单例模式中获取唯一的MyApplication实例 
     public static AppData getInstance() {  
         if(null == instance) 
       { 
          instance = new AppData(); 
       } 
         return instance;             
	    }  
	    @Override 
	   public void onCreate() {  
	     super.onCreate();  
         instance = this;  
	    }  

	    private List<Activity> activityList = new LinkedList<Activity>(); 
	    public List<Activity> getActivityList() {
			return activityList;
		}
		public void setActivityList(List<Activity> activityList) {
			this.activityList = activityList;
		}
		public AppData() 
	                {} 
	                 //添加Activity到容器中 
	                 public void addActivity(Activity activity) 
	                 { 
	                                activityList.add(activity); 
	                 } 
	                 //遍历所有Activity并finish 
	                 public void exit() 
	                 { 
	                	 
	                              for(Activity activity:activityList) 
	                             { 
	                            	
	                               activity.finish(); 
	                             } 
	                               System.exit(0); 
	                	 
	                } 

	  private static final int HARD_CACHE_CAPACITY = 30;
		/**关于图片的缓存处理,设置两个缓冲区
		   * 先从缓冲区找，没有的话在异步下载
		   */
		private  HashMap<String, Bitmap> mHardBitmapCache 
		= new LinkedHashMap<String, Bitmap>(HARD_CACHE_CAPACITY/ 2, 0.75f, true) {     
			protected boolean removeEldestEntry(LinkedHashMap.Entry<String,Bitmap>  eldest) {            
				if (size() >HARD_CACHE_CAPACITY) {                
					//当map的size大于30时，把最近不常用的key放到mSoftBitmapCache中，从而保证mHardBitmapCache的效率               
					mSoftBitmapCache.put(eldest.getKey(), new SoftReference<Bitmap>(eldest.getValue()));                 
					return true;        
					}
				else                 
					return false;       
				}    
			}; 
			/**     
			 *  当mHardBitmapCache的key大于30的时候，会根据LRU算法把最近没有被使用的key放入到这个缓存中。     
			 *  Bitmap使用了SoftReference，当内存空间不足时，此cache中的bitmap会被垃圾回收掉     
			 **/   
			private  static ConcurrentHashMap<String, SoftReference<Bitmap>> mSoftBitmapCache =
				new ConcurrentHashMap<String,SoftReference<Bitmap>>(HARD_CACHE_CAPACITY / 2); 
	
	
	public HashMap<String, Bitmap> getmHardBitmapCache() {
				return mHardBitmapCache;
			}
			public void setmHardBitmapCache(HashMap<String, Bitmap> mHardBitmapCache) {
				this.mHardBitmapCache = mHardBitmapCache;
			}
			public static ConcurrentHashMap<String, SoftReference<Bitmap>> getmSoftBitmapCache() {
				return mSoftBitmapCache;
			}
			public static void setmSoftBitmapCache(
					ConcurrentHashMap<String, SoftReference<Bitmap>> mSoftBitmapCache) {
				AppData.mSoftBitmapCache = mSoftBitmapCache;
			}


	private String loginInfo;
	public String getLoginInfo() {
		return loginInfo;
	}
	public void setLoginInfo(String loginInfo) {
		this.loginInfo = loginInfo;
	}
}
