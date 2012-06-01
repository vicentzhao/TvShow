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
		  conn.setConnectTimeout(3000);
		  InputStream is = conn.getInputStream();
		  Bitmap bitmap = BitmapFactory.decodeStream(is);
		 return bitmap;
	}
	 

}
