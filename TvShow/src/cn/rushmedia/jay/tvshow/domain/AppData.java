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
	 //����ģʽ�л�ȡΨһ��MyApplicationʵ�� 
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
	                 //���Activity�������� 
	                 public void addActivity(Activity activity) 
	                 { 
	                                activityList.add(activity); 
	                 } 
	                 //��������Activity��finish 
	                 public void exit() 
	                 { 
	                	 
	                              for(Activity activity:activityList) 
	                             { 
	                            	
	                               activity.finish(); 
	                             } 
	                               System.exit(0); 
	                	 
	                } 

	  private static final int HARD_CACHE_CAPACITY = 30;
		/**����ͼƬ�Ļ��洦��,��������������
		   * �ȴӻ������ң�û�еĻ����첽����
		   */
		private  HashMap<String, Bitmap> mHardBitmapCache 
		= new LinkedHashMap<String, Bitmap>(HARD_CACHE_CAPACITY/ 2, 0.75f, true) {     
			protected boolean removeEldestEntry(LinkedHashMap.Entry<String,Bitmap>  eldest) {            
				if (size() >HARD_CACHE_CAPACITY) {                
					//��map��size����30ʱ������������õ�key�ŵ�mSoftBitmapCache�У��Ӷ���֤mHardBitmapCache��Ч��               
					mSoftBitmapCache.put(eldest.getKey(), new SoftReference<Bitmap>(eldest.getValue()));                 
					return true;        
					}
				else                 
					return false;       
				}    
			}; 
			/**     
			 *  ��mHardBitmapCache��key����30��ʱ�򣬻����LRU�㷨�����û�б�ʹ�õ�key���뵽��������С�     
			 *  Bitmapʹ����SoftReference�����ڴ�ռ䲻��ʱ����cache�е�bitmap�ᱻ�������յ�     
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
