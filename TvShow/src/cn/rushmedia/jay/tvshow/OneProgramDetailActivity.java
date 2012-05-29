package cn.rushmedia.jay.tvshow;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import cn.rushmedia.jay.tvshow.domain.AppData;
import cn.rushmedia.jay.tvshow.domain.Program;
import cn.rushmedia.jay.tvshow.util.ImageDownloder;
import cn.rushmedia.jay.tvshow.util.JsonUtil;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class OneProgramDetailActivity extends BaseActivity {
	public ImageView img;
	public TextView title;
	public TextView actor;
	public TextView keyword;
	public TextView desc;
	public Button  sayButton;
	public Button  abouybutton;
	public Button moreButton;
	public Button reButton;
	private   int programeid ;
	private JSONObject  jb ;
	private String programtitle;
	private String programkey;
	private String programdesc;
	private String programactor;
	private String programPath;
    private Button tv_programedetail_btncoll;
    private HttpResponse response;
    private int userid;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.newfilm_1);
		AppData appl = (AppData)getApplication();
		 
		 try {
			String loginInfo = appl.getLoginInfo();
			 
				JSONObject  loginuserjs = new JSONObject(loginInfo);
				userid=loginuserjs.getInt("id");
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			Intent it =getIntent();
			programeid = it.getIntExtra("programid", 0);
			String path ="http://tvsrv.webhop.net:8080/api/programs/"+programeid;
			JsonUtil js = new JsonUtil();
			String stringSource = js.getStringSource(path);
			jb= new JSONObject(stringSource);
			programtitle=jb.getString("title");
			programkey= jb.getString("keyword");
			programdesc=jb.getString("description");
			 programactor = jb.getString("actor");
			 programPath=jb.getString("image");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        initdata();
	 AppData ap = (AppData) getApplication();
	 ap.addActivity(this);
	    title.setText(programtitle);
	   actor.setText(programactor);
	    keyword.setText(programkey);
	    desc.setText(programdesc);
	    new AsyncTask<Void, Void, Bitmap>(){
	    	protected void onPostExecute(Bitmap result) {
				img.setImageBitmap(result);
				super.onPostExecute(result);
			}
			@Override
			protected Bitmap doInBackground(Void... params) {
				Bitmap bitmap;
				try {
					ImageDownloder imageDownloder = new ImageDownloder();
					bitmap = imageDownloder.imageDownloder(programPath);
					return bitmap;
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}	
			}
	    }.execute();
   tv_programedetail_btncoll.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				 String json="{\"id\":\""+programeid+"\"}";
					String url="http://tvsrv.webhop.net:8080/api/users/"+userid+"/favorite-programs";
					DefaultHttpClient httpClient = new DefaultHttpClient();
					HttpPost request = new HttpPost(url);
					// 绑定到请求 Entry 
						
							// 发送请求 
							
							try {
								StringEntity entity = new StringEntity(json);
								request.setEntity(entity); 
								request.setHeader("Content-Type", "application/json");
								response = httpClient.execute(request); 
								String result = EntityUtils.toString(response.getEntity());
								System.out.println(result+"============>>");
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
								Toast.makeText(getApplicationContext(), "提交成功", 1).show();
								tv_programedetail_btncoll.setText("已收藏");
							}
			}
		});
   ImageButton backtohome = (ImageButton) findViewById(R.id.backtohome);
	backtohome.setOnClickListener(new OnClickListener() {
		public void onClick(View v) {
			Intent i = new Intent(getApplicationContext(),TableActivity.class);
			startActivity(i);
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

	  void initdata(){
			 img=(ImageView) findViewById(R.id.movie_img);
			 title=(TextView) findViewById(R.id.movie_title);
			 actor=(TextView) findViewById(R.id.movie_actor);
			 keyword =(TextView) findViewById(R.id.movie_key);
			 desc =(TextView) findViewById(R.id.movie_desc);
			 tv_programedetail_btncoll=(Button) findViewById(R.id.tv_programedetail_btncoll);
		 }
}
