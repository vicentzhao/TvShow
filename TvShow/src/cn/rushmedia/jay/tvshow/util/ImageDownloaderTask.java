package cn.rushmedia.jay.tvshow.util;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

public class ImageDownloaderTask extends AsyncTask<String, Void, Bitmap> {
	 private static final int IO_BUFFER_SIZE= 4 * 1024;     
	 private String url; 
	 private final WeakReference<ImageView>  imageViewReference;  
	 private final String TAG="imageDownLoader";
	 public ImageDownloaderTask(ImageView imageView) {  
		 imageViewReference = new WeakReference<ImageView>(imageView); 
		 }         
	 @Override     
		 protected Bitmap doInBackground(String... params) {       
			 final AndroidHttpClient client =AndroidHttpClient.newInstance("Android");      
			 url = params[0];       
			 final HttpGet getRequest = new HttpGet(url);    
			 try {              
				 HttpResponse response =client.execute(getRequest);        
				 final int statusCode =response.getStatusLine().getStatusCode();      
				 if (statusCode !=HttpStatus.SC_OK) {       
					 Log.w(TAG, "从" +url + "中下载图片时出错!,错误码:" + statusCode);     
					 return null;                 }       
				 final HttpEntity entity =response.getEntity();    
				 if (entity != null) {        
					 InputStream inputStream =null;       
					 OutputStream outputStream =null;         
					 try {               
						 inputStream =entity.getContent();        
						 final ByteArrayOutputStream dataStream = new ByteArrayOutputStream();      
						 outputStream = new BufferedOutputStream(dataStream, IO_BUFFER_SIZE);    
						// copy(inputStream,outputStream);   
						 outputStream.flush();    
						 final byte[] data =dataStream.toByteArray();    
						 final Bitmap bitmap =BitmapFactory.decodeByteArray(data, 0, data.length);    
						 return bitmap;   
						 } finally {   
							 if (inputStream !=null) {    
								 inputStream.close();     
								 }          
							 if (outputStream !=null) {   
								 outputStream.close();    
								 }     
							 entity.consumeContent();       
							 }          
						 }    
				 } catch (IOException e) {      
					 getRequest.abort();     
					 Log.w(TAG, "I/O errorwhile retrieving bitmap from " + url, e);   
					 } catch (IllegalStateException e) {       
						 getRequest.abort();   
						 Log.w(TAG, "Incorrect URL:" + url);      
						 } catch (Exception e) {  
							 getRequest.abort();     
							 Log.w(TAG, "Error whileretrieving bitmap from " + url, e);    
							 } finally {  
								 if (client != null) {   
									 client.close();    
									 }      
								 }             
							 return null; 
	 }
}
