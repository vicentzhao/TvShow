package cn.rushmedia.jay.tvshow;

import cn.rushmedia.jay.tvshow.R;
import cn.rushmedia.jay.tvshow.domain.AppData;
import android.app.AlertDialog;
import android.app.TabActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

public class TableActivity extends TabActivity {
     private  TabHost mTabHost;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		//不要标题栏
		//requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab_main);
		//  getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.titlebar); 
		////wojskjfks
		//获取数据
		intiTableHost();
	  AppData ap = (AppData) getApplication();	
	  ap.addActivity(this);
	}

	private View prepareTabView(Context context, int titleId, int drawable) {
		View view = LayoutInflater.from(context).inflate(R.layout.tab_main_nav,
				null);
		TextView tv = (TextView) view.findViewById(R.id.tvTitle);
		tv.setText(getText(titleId).toString());
		ImageView iv = (ImageView) view.findViewById(R.id.ivIcon);
		iv.setImageResource(drawable);
		return view;
	}
	private void intiTableHost() {
		if (mTabHost != null) {
			throw new IllegalStateException(
					"Trying to intialize already initializd TabHost");
	}
		mTabHost=getTabHost();
		Intent filmDisucess=new Intent(this,MyPostActivity_1.class);
		Intent myHome=new Intent(this,MyHomeActivity.class);
		Intent searchIntent= new Intent(this,MySearchActivity.class);
		Intent tvShowIntent= new Intent(this,TvShowActivity.class);
		mTabHost.addTab(mTabHost.newTabSpec("one").setIndicator(
				prepareTabView(mTabHost.getContext(),
						R.string.discuss,
						R.drawable.tab_main_nav_me_selector)).setContent(
				filmDisucess));
		mTabHost.addTab(mTabHost.newTabSpec("two").setIndicator(
				prepareTabView(mTabHost.getContext(), 
				R.string.myhome,
				R.drawable.home)).
				setContent(myHome));
	
		mTabHost.addTab(mTabHost.newTabSpec("three").setIndicator(
				prepareTabView(mTabHost.getContext(), 
				R.string.search_button,
				R.drawable.search_main)).setContent(searchIntent));
		mTabHost.addTab(mTabHost.newTabSpec("four").setIndicator(
				prepareTabView(mTabHost.getContext(), 
				R.string.tv_show,
				R.drawable.tv_show)).setContent(tvShowIntent));
		
	}	
}
