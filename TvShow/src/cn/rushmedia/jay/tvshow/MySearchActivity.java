package cn.rushmedia.jay.tvshow;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;
import cn.rushmedia.jay.tvshow.domain.AppData;

public class MySearchActivity extends BaseActivity  {
	private String  forWhat;
	private EditText Ekeyword;
	private RadioGroup rg;
	private Button searchButton;
	private Button dayHotPeople;
	private Button dayHotTopic;
	private Button dayHotProgram;
	private Button weekHotPeople;
	private Button weekHotTopic;
	private Button weekHotProgram;
	private String checkedtext;
	private String keyword;
	
//	Button radioSearch;
//	Button radioProgram;
//	Button radioConnple;
	   public void intiData(){
      	  Ekeyword =(EditText) findViewById(R.id.tv_search_input);
      	  rg=(RadioGroup) findViewById(R.id.tv_search_radiogroup);
      	  searchButton =(Button) findViewById(R.id.tv_search_searchbutton_001);  
      	  dayHotPeople=(Button) findViewById(R.id.tv_search_today_hotpeople);
      	  dayHotTopic=(Button) findViewById(R.id.tv_search_today_hottopic);
      	  dayHotProgram=(Button)findViewById(R.id.tv_search_day_hotprogram);
      	  weekHotPeople=(Button) findViewById(R.id.tv_search_week_hotpeople);
      	weekHotProgram=(Button) findViewById(R.id.tv_search_week_hotprogram);
      	weekHotTopic=(Button) findViewById(R.id.tv_search_week_hottopic);
      }
	  public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.search_1);
	        intiData();
	        AppData appData =(AppData) getApplication();
	        appData.addActivity(this);
	       rg.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				 int id  = group.getCheckedRadioButtonId();
				 RadioButton rb = (RadioButton) findViewById(id);
				 forWhat = (String) rb.getText();
			}
		});
	       /**
	        * 周热门话题
	        */
	       weekHotTopic.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(),HotTopicListActivity.class);
				String path = "http://tvsrv.webhop.net:8080/api/hot/topics/week";
				i.putExtra("path", path);
				startActivity(i);
				finish();
				
			}
		});
	       /**
	        * 日热门话题
	        */
	       dayHotTopic.setOnClickListener(new OnClickListener() {
	    	   
	    	   @Override
	    	   public void onClick(View v) {
	    		   Intent i = new Intent(getApplicationContext(),HotTopicListActivity.class);
	    		   String path = "http://tvsrv.webhop.net:8080/api/hot/topics/day";
	    		   i.putExtra("path", path);
	    		   startActivity(i);
	    		   finish();
	    	   }
	       });
	       /**
	        * 返回主页
	        */
	       ImageButton backtohome = (ImageButton) findViewById(R.id.backtohome);
	   	backtohome.setOnClickListener(new OnClickListener() {
	   		public void onClick(View v) {
	   			Intent i = new Intent(getApplicationContext(),TableActivity.class);
	   			startActivity(i);
	   			finish();
	   		}
	   	});
	       
	       /**
	        * 周热门节目
	        */
	       weekHotProgram.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				  Intent weekHotProgramIntent=new Intent(MySearchActivity.this,HotProgramActivity.class);
				  String path ="http://tvsrv.webhop.net:8080/api/hot/programs/week";
				  weekHotProgramIntent.putExtra("path", path);
				  startActivity(weekHotProgramIntent);
				  finish();
			}
		});
	       /**
	        * 日热门节目
	        */
	       dayHotProgram.setOnClickListener(new OnClickListener() {
	    	   @Override
	    	   public void onClick(View v) {
	    		   Intent dayHotProgramIntent=new Intent(MySearchActivity.this,HotProgramActivity.class);
	    		   String path ="http://tvsrv.webhop.net:8080/api/hot/programs/day";
	    		   dayHotProgramIntent.putExtra("path", path);
	    		   startActivity(dayHotProgramIntent);
	    		   finish();
	    	   }
	       });
	       /**
	        * 周热门人物
	        */
	       weekHotPeople.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent hotpeopleIntent=new Intent(MySearchActivity.this,HotPeopleActivity.class); 
				String path = "http://tvsrv.webhop.net:8080/api/hot/users/week";
				hotpeopleIntent.putExtra("path", path);
				startActivity(hotpeopleIntent);
				finish();
			}
		});
	       /**
	        * 日热门人物
	        */
	       dayHotPeople.setOnClickListener(new OnClickListener() {
	    	   @Override
	    	   public void onClick(View v) {
	    		   Intent hotpeopleIntent=new Intent(MySearchActivity.this,HotPeopleActivity.class); 
	    		   String path = "http://tvsrv.webhop.net:8080/api/hot/users/day";
	    		   hotpeopleIntent.putExtra("path", path);
	    		   startActivity(hotpeopleIntent);
	    		   finish();
	    	   }
	       });
	         rg = (RadioGroup)this.findViewById(R.id.tv_search_radiogroup);
	              //绑定一个匿名监听器
	          rg.setOnCheckedChangeListener(new OnCheckedChangeListener() {
	                    
	                   @Override
	                   public void onCheckedChanged(RadioGroup arg0, int arg1) {
	                        //获取变更后的选中项的ID
	                        int radioButtonId = arg0.getCheckedRadioButtonId();
	                        //根据ID获取RadioButton的实例
	                       RadioButton rb = (RadioButton)MySearchActivity.this.findViewById(radioButtonId);
	                       //更新文本内容，以符合选中项
	                       checkedtext = rb.getText().toString();
	                   }
	               });
	        
	       searchButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(checkedtext==null){
						new AlertDialog.Builder(MySearchActivity.this).setTitle("请选择搜索的类别").create().show();
					return;
				}
				if(checkedtext.equals("搜索话题")){
					Intent intent = new Intent(MySearchActivity.this,SearchTopicListActivity.class);
					keyword = Ekeyword.getText().toString();
					intent.putExtra("compath",keyword);
					startActivity(intent);
				}else if(checkedtext.equals("搜索节目")){
					Intent intent = new Intent(MySearchActivity.this,SearchChannelActivity.class);
					keyword = Ekeyword.getText().toString();
					intent.putExtra("compath",keyword);
					startActivity(intent);
					
					
				}else if(checkedtext.equals("搜索联系人")){
					Intent intent = new Intent(MySearchActivity.this,SearchPeopleActivity.class);
					keyword = Ekeyword.getText().toString();
					intent.putExtra("compath",keyword);
					startActivity(intent);
					
				}
				else if(checkedtext.equals("搜索电影")){
					Intent intent =  new Intent(MySearchActivity.this,SearchProgramActivity.class);
					keyword = Ekeyword.getText().toString();
					intent.putExtra("compath",keyword);
					startActivity(intent);
					
				}
			else{
					Toast.makeText(MySearchActivity.this, "请选择查询的范围", 1).show();
			}
			}
		});
	      	    }
	  /**
		 * 捕捉回退键
		 */
		public boolean onKeyDown(int keyCode, KeyEvent event) {
			if(keyCode==KeyEvent.KEYCODE_BACK && event.getRepeatCount()==0){
				showTips();
			return false;
			}
			return false;
			}
	
	 }


