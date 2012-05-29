package cn.rushmedia.jay.tvshow.util;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;



public class Login {
	 public  JSONObject login(){
   String result = null;
    String url = "http://tvsrv.webhop.net:8080/api/login";
    String json = "{\"email\":\"aihua.yin@gmail.com\",\"password\":\"1\"}";
    JSONObject resultJson = null;
	HttpPost request = new HttpPost(url);
	try {
		
		// �󶨵����� Entry 
		StringEntity entity = new StringEntity(json);
		request.setEntity(entity); 
		request.setHeader("Content-Type", "application/json");
		// �������� 
		HttpResponse response = new DefaultHttpClient().execute(request); 
		// �õ�Ӧ����ַ�������Ҳ��һ�� JSON ��ʽ���������  
		 result = EntityUtils.toString(response.getEntity());  
		//System.out.println("result-->"+result);
		// ���� JSON ����  
		 resultJson = new JSONObject(result);  
		System.out.println("resultJson-->"+resultJson.toString());
		
	} catch (Exception e) {
		e.printStackTrace();
	}
	
	return resultJson;

	 }
}

