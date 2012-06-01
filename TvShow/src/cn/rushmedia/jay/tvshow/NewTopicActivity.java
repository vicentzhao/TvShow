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
import cn.rushmedia.jay.tvshow.domain.Post;

public class NewTopicActivity extends BaseActivity {
	 private Post  homeLineDiscu;
	  private String content;
	  int  programid;
	  int  userid;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.newtopic);
		AppData appl = (AppData) getApplication();
		appl.addActivity(this);
	    Intent it =getIntent();
	      homeLineDiscu = (Post) it.getSerializableExtra("saydetial");
	      programid= homeLineDiscu.getTopic().getProgramid();
	     
	      final EditText tv_post2other_content ;
	      Button tv_post2other_btnSave;
	      Button tv_post2other_btnCancel;
	      ImageButton back =(ImageButton) findViewById(R.id.back_button);
	      back.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent i = new Intent(NewTopicActivity.this,TopicDetialActivity.class);
				i.putExtra("home",homeLineDiscu);
				startActivity(i);
			}
		});
	      ImageButton backtohome = (ImageButton) findViewById(R.id.backtohome);
	    	backtohome.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					Intent i = new Intent(getApplicationContext(),TableActivity.class);
					startActivity(i);
				}
			});
	      tv_post2other_btnSave= (Button) findViewById(R.id.tv_post2other_btnSave);
	      tv_post2other_content=(EditText) findViewById(R.id.tv_post2other_content);
	      tv_post2other_btnCancel=(Button) findViewById(R.id.tv_post2other_btnCancel);
	  
	      /**
	       * 
curl -X POST -H "Content-Type: application/json" \
 -d '{"programid":"2","name":"gone,gone,gone","createby":"2"}' \
 http://tvsrv.webhop.net:8080/api/topics
	       */
	     
			tv_post2other_btnSave.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					content = tv_post2other_content.getText().toString();
				       userid=homeLineDiscu.getTopic().getUser().getId();
					 String json="{\"programid\":\""+programid+"\",\"name\":\""+content+"\",\"createby\":\""+userid+"\"}";
						String url="http://tvsrv.webhop.net:8080/api/topics";
						DefaultHttpClient httpClient = new DefaultHttpClient();
						HttpPost request = new HttpPost(url);
						// 绑定到请求 Entry 
						try {
							StringEntity entity = new StringEntity(json,"utf-8");
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
