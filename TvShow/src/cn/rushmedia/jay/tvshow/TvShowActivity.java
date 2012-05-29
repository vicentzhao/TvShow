package cn.rushmedia.jay.tvshow;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import cn.rushmedia.jay.tvshow.domain.AppData;

public class TvShowActivity extends BaseActivity {
	private Button tv_tvshow_allchanles;
	private Button tv_tvshow_alltvitem;
	private Button tv_tvshow_tvitemsupply;
	private Button tv_tvshow_alltvprogram;
	private Button tv_tvshow_poptvprogramsupply;
	private Button tv_tvshow_allmovie;
	private Button tv_tvshow_popmoviesupply;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tvshow);
		initData();
		AppData ap =(AppData)getApplication();
		ap.addActivity(this);
		tv_tvshow_allchanles.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent allTvChannelIntent = new Intent(TvShowActivity.this,AllTvChannelActivity.class);
				startActivity(allTvChannelIntent);
				
			}
		});
		tv_tvshow_allmovie.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(),AllProgramActivity.class);
				startActivity(i);
			}
		});

	}
	/**
	 * 捕捉回退键
	 */
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode==KeyEvent.KEYCODE_BACK && event.getRepeatCount()==0){
			showTips();
			if(keyCode == KeyEvent.KEYCODE_BACK){
				if(!isExit){
					isExit=true;
					Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
					mHandler.sendEmptyMessageDelayed(0, 2000);
				}
				else{
					finish();
					System.exit(0);
				}
			}
			return false;
		}
		return false;
		}
	boolean isExit=false;
	Handler mHandler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			isExit=false;
		}
		
	};
	
	
	private void initData() {
		tv_tvshow_allchanles=(Button) findViewById(R.id.tv_tvshow_allchanles);
		tv_tvshow_alltvitem=(Button) findViewById(R.id.tv_tvshow_alltvitem);
		tv_tvshow_tvitemsupply=(Button) findViewById(R.id.tv_tvshow_tvitemsupply);
		tv_tvshow_alltvprogram=(Button) findViewById(R.id.tv_tvshow_alltvprogram);
		tv_tvshow_poptvprogramsupply=(Button) findViewById(R.id.tv_tvshow_poptvprogramsupply);
		tv_tvshow_allmovie=(Button) findViewById(R.id.tv_tvshow_allmovie);
		tv_tvshow_popmoviesupply=(Button) findViewById(R.id.tv_tvshow_popmoviesupply);
	}
}
