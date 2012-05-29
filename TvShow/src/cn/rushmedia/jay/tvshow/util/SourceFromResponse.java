package cn.rushmedia.jay.tvshow.util;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.widget.Toast;

public class SourceFromResponse {
    
	 public String getSource(String json,String url){
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpPost request = new HttpPost(url);
			// 绑定到请求 Entry 
				try {
					StringEntity entity = new StringEntity(json);
					request.setEntity(entity); 
					request.setHeader("Content-Type", "application/json");
					// 发送请求 
					HttpResponse response = httpClient.execute(request); 
					String result = EntityUtils.toString(response.getEntity());
					System.out.println(result+"====================>>>");
					return result;
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					 return null;
				}
	 }
}
