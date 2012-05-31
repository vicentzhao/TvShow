package cn.rushmedia.jay.tvshow;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import cn.rushmedia.jay.tvshow.domain.AppData;
import cn.rushmedia.jay.tvshow.domain.Post2;

public class Post2ProgramActivity extends BaseActivity {
	
	 private Post2  homeLineDiscu;
	  private String content;
	  int p ;
	  int u;
	  int t;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.postotherpost);
		AppData app = (AppData) getApplication();
		app.addActivity(this);
	    Intent it =getIntent();
	      homeLineDiscu = (Post2) it.getSerializableExtra("saydetial");
	       p= homeLineDiscu.getP();
	       u = homeLineDiscu.getU();
	       t = homeLineDiscu.getT();
	       ImageButton backtohome = (ImageButton) findViewById(R.id.backtohome);
	    	backtohome.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					Intent i = new Intent(getApplicationContext(),TableActivity.class);
					startActivity(i);
				}
			});
	      final EditText tv_post2other_content ;
	      Button tv_post2other_btnSave;
	      Button tv_post2other_btnCancel;
	      tv_post2other_btnSave= (Button) findViewById(R.id.tv_post2other_btnSave);
	      tv_post2other_content=(EditText) findViewById(R.id.tv_post2other_content);
	      tv_post2other_btnCancel=(Button) findViewById(R.id.tv_post2other_btnCancel);
	      content = tv_post2other_content.getText().toString();
	    //  String path="{\"email\":\"aihua.yin@gmail.com\",\"password\":\"1\"}";
	     
			tv_post2other_btnSave.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					 String json="{\"c\":\""+content+"\",\"u\":\""+u+"\",\"p\":\""+p+"\", \"t\": \""+t+"\"}";
						String url= "http://tvsrv.webhop.net:8080/api/posts/"+p+"/comments";
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
							if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
								Toast.makeText(getApplicationContext(), "提交成功", 1).show();
								finish();
								android.os.Process.killProcess(android.os.Process.myPid());
							}
							System.out.println(result+"====================>>>");
						} catch (Exception e) {
							Toast.makeText(getApplicationContext(), "出现异常，请重新填写", 1).show();
							tv_post2other_content.setText("");
							e.printStackTrace();
						}
				}
			});
	    
	}
//	/**
//	 * 捕捉回退键
//	 */
//	public boolean onKeyDown(int keyCode, KeyEvent event) {
//		if(keyCode==KeyEvent.KEYCODE_BACK && event.getRepeatCount()==0){
//			showTips();
//		return false;
//		}
//		return false;
//		}

}
