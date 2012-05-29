package cn.rushmedia.jay.tvshow.util;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class ImageDownloder {
	
	public Bitmap imageDownloder(String imagePath) throws Exception{
		  URL url = new URL(imagePath);
		  HttpURLConnection  conn =(HttpURLConnection) url.openConnection();
		  InputStream is = conn.getInputStream();
		  Bitmap bitmap = BitmapFactory.decodeStream(is);
		  BitmapFactory.Options options =new BitmapFactory.Options();
		//  options.inSampleSize=2;//ͼƬ�߿�ȶ�Ϊԭ���Ķ���֮һ����ͼƬ��СΪԭ���Ĵ�С���ķ�֮һ  
		//  options.inTempStorage = new byte[5*1024];
		 return bitmap;
	}
	 

}
