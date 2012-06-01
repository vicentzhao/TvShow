package cn.rushmedia.jay.tvshow;

import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.rushmedia.jay.tvshow.domain.AppData;
import cn.rushmedia.jay.tvshow.domain.Post;
import cn.rushmedia.jay.tvshow.domain.Program;
import cn.rushmedia.jay.tvshow.util.ImageDownloder;
import cn.rushmedia.jay.tvshow.util.JSONObject2Program;
import cn.rushmedia.jay.tvshow.util.JsonUtil;

public class NewFileActivity extends BaseActivity {
	public ImageView img;
	public TextView title;
	public TextView actor;
	public TextView keyword;
	public TextView desc;
	public Button tv_programedetail_btncoll;
	public Button  sayButton;
	public Button  abouybutton;
	public Button moreButton;
	public Button reButton;
	private Program movie;
	private int programid;
	private int userid;
	
	private RelativeLayout rl;
    private Button btn_movie_new_about;
    private Post post;
    private Button btn_movie_exit;
    private Button movie_more;
    private boolean collected =false;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.newfilm_1);
		 AppData appl = (AppData)getApplication();
		 rl=(RelativeLayout) findViewById(R.id.loading);
		 Intent it =getIntent();
	     post = (Post) it.getSerializableExtra("saydetial");
	     int collectedProgramId = post.getTopic().getProgramid(); 
		 try {
			   String loginInfo = appl.getLoginInfo();
			   if(loginInfo==null){
				   Log.i("TVSHOW", "没有拿到用户资料");
				   Toast.makeText(getApplicationContext(), "没有拿到用户资料", 1).show();
			   }
				JSONObject  loginuserjs = new JSONObject(loginInfo);
				userid=loginuserjs.getInt("id");
				String path ="http://tvsrv.webhop.net:8080/api/users/"+userid+"/favorite-programs";
				JsonUtil jsut = new JsonUtil();
				try {
					JSONArray source = jsut.getSource(path);
					for (int i = 0; i < source.length(); i++) {
						JSONObject jsProgram =source.getJSONObject(i);
						JSONObject2Program jp = new JSONObject2Program();
						Program program = jp.getProgram(jsProgram);
						int favId = program.getId();
						if(collectedProgramId==favId){
							collected=true;
						}
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
       initdata();
       AppData appData = (AppData) getApplication();
       appData.addActivity(this);
       ImageButton backtohome = (ImageButton) findViewById(R.id.backtohome);
   	  backtohome.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(),TableActivity.class);
				startActivity(i);
			}
		});
	    movie = post.getTopic().getProgram();
	    programid =post.getTopic().getProgramid();
	    title.setText(movie.getTitle());
	   // actor.setText(movie.getActor());
	    keyword.setText(movie.getKey());
	    desc.setText(movie.getDescription());

				Bitmap bitmap;
				try {
					ImageDownloder imageDownloder = new ImageDownloder();
					bitmap = imageDownloder.imageDownloder(movie.getImagePath());
					img.setImageBitmap(bitmap);
					
				} catch (Exception e) {
					e.printStackTrace();
				}	
	    tv_programedetail_btncoll.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(collected){
					String unPath ="http://tvsrv.webhop.net:8080/api/users/"+userid+"/unfavorite-programs";
					int code = collectProgram(unPath);
					if(code==HttpStatus.SC_OK){
						Toast.makeText(getApplicationContext(), "取消收藏成功", 1).show();
						tv_programedetail_btncoll.setText("收藏");
						collected=false;
					}else{
						Toast.makeText(getApplicationContext(), "取消收藏失败，请重试", 1).show();
					}
				}else{
				 String collectPath="http://tvsrv.webhop.net:8080/api/users/"+userid+"/favorite-programs";
                     int collectcode = collectProgram(collectPath);
							if (collectcode == HttpStatus.SC_OK){
								Toast.makeText(getApplicationContext(), "收藏成功", 1).show();
								tv_programedetail_btncoll.setText("取消收藏");
								collected=true;
							}else{
								Toast.makeText(getApplicationContext(), "收藏失败，请重试", 1).show();
							}
			}
			}
		});
	    /**
	     * 相关话题
	     */
	    btn_movie_new_about.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				  Intent sameTopicIntent = new Intent(NewFileActivity.this,SameTopicListActivity.class);
				  sameTopicIntent.putExtra("saydetial",post);
				    startActivity(sameTopicIntent);
			}
		});
	   
	    /**
	     * 
	     */
	    btn_movie_exit.setOnClickListener(new OnClickListener() {
	    	@Override
	    	public void onClick(View v) {
	    		Intent i = new Intent(NewFileActivity.this,TableActivity.class);
	    		startActivity(i);
	    		finish();
	    	}
	    });
	   /**
	    * 更多评论
	    */
	    movie_more.setOnClickListener(new OnClickListener() {
	    	@Override
	    	public void onClick(View v) {
	    		Intent sameTopicIntent = new Intent(NewFileActivity.this,MyPostActivity.class);
				sameTopicIntent.putExtra("saydetial", post);
				startActivity(sameTopicIntent);
				
	    	}
	    });
	    
	}

	  void initdata(){
			 img=(ImageView) findViewById(R.id.movie_img);
			 title=(TextView) findViewById(R.id.movie_title);
			 actor=(TextView) findViewById(R.id.movie_actor);
			 keyword =(TextView) findViewById(R.id.movie_key);
			 desc =(TextView) findViewById(R.id.movie_desc);
			 tv_programedetail_btncoll=(Button) findViewById(R.id.tv_programedetail_btncoll);
			 if(collected){
				 tv_programedetail_btncoll.setText("取消收藏");
				 System.out.println("取消收藏"+collected);
			 }else{
				 tv_programedetail_btncoll.setText("收藏");
				 System.out.println("收藏"+collected);
			 }
			 btn_movie_new_about =(Button)findViewById(R.id.btn_movie_new_about);
			 btn_movie_exit =(Button)findViewById(R.id.btn_movie_exit);
			 movie_more =(Button)findViewById(R.id.movie_more);
		 }
	  public int collectProgram(String url){
		  HttpResponse response;
		  try {
			    String json="{\"id\":\""+programid+"\"}";
				DefaultHttpClient httpClient = new DefaultHttpClient();
				HttpPost request = new HttpPost(url);
				StringEntity entity = new StringEntity(json,"utf-8");
				request.setEntity(entity); 
				request.setHeader("Content-Type", "application/json");
				response = httpClient.execute(request); 
				String result = EntityUtils.toString(response.getEntity());
				System.out.println(result+"============>>");
				int statusCode = response.getStatusLine().getStatusCode();
				
				return statusCode;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return 0;
			}
	  }
}
