package cn.rushmedia.jay.tvshow.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

public class SourcefromService {
    
	   public String getSource(String url,String json){
		   String result;
		  
			try {
				HttpPost request = new HttpPost(url);
					// �󶨵����� Entry 
					StringEntity entity = new StringEntity(json);
					request.setEntity(entity); 
					request.setHeader("Content-Type", "application/json");
					// �������� 
					HttpResponse response = new DefaultHttpClient().execute(request); 
					// �õ�Ӧ����ַ�������Ҳ��һ�� JSON ��ʽ���������  
					 result = EntityUtils.toString(response.getEntity());
					 return result;

			} catch (Exception e) {
				
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}     
	   }   
}
