package cn.rushmedia.jay.tvshow;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import cn.rushmedia.jay.tvshow.domain.AppData;
import cn.rushmedia.jay.tvshow.util.SourcefromService;

public class LoginActivity extends BaseActivity {

	private Button btnButton;
	private String password;
	private String name;
	private Button exitButton;
	private TextView editName;
	private TextView editPassword;
	private String data;
	private String imagePath;

	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		getWindow().setFormat(PixelFormat.RGBA_8888);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_DITHER);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.login);
		NetworkInfo info = getAvailableNetWorkInfo(this);
		if(info==null){
			Toast.makeText(this, "没有网络，请检查", Toast.LENGTH_LONG).show();
			setNetWork();
		}
		editName = (TextView) findViewById(R.id.Editname);
		name = editName.toString();
		editPassword = (TextView) findViewById(R.id.EditTextPassword);
		password = editPassword.toString();
		btnButton = (Button) findViewById(R.id.btnLogin);
		btnButton.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				NetworkInfo info = getAvailableNetWorkInfo(LoginActivity.this);
				if(info==null){
					setNetWork();
				}else{
				final ProgressDialog pd = new ProgressDialog(LoginActivity.this);
				pd.setMessage("正在登陆");
				pd.show();
				new AsyncTask<Void, Void, String>() {

					@Override
					protected void onPostExecute(String result) {
						pd.dismiss();
						AppData dataApp = (AppData) getApplication();
						dataApp.setLoginInfo(result);
						Intent intent = new Intent(LoginActivity.this,
								TableActivity.class);
						startActivity(intent);
						finish();
						super.onPostExecute(result);
					}

					@Override
					protected String doInBackground(Void... params) {
						String path = "{\"email\":\"aihua.yin@gmail.com\",\"password\":\"1\"}";
						String url = "http://tvsrv.webhop.net:8080/api/login";
						SourcefromService sf = new SourcefromService();
						String loginInfo = sf.getSource(url, path);
						pd.dismiss();
						return loginInfo;
					}
				}.execute();
				}	}
		});
		
		exitButton = (Button) findViewById(R.id.btnExit);
		exitButton.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				editName.setText("");
				editPassword.setText("");
				Toast.makeText(LoginActivity.this, "清除数据", Toast.LENGTH_SHORT);
			}
		});
	}

	/**
	 * 捕捉回退键
	 */
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			showTips();
			return false;
		}
		return false;
	}
   //判断网络连接
	public static NetworkInfo getAvailableNetWorkInfo(Context context) {
		if (context == null) {
			return null;
		}
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
		if (activeNetInfo != null && activeNetInfo.isAvailable()) {
			return activeNetInfo;
		} else {
			return null;
		}
	}
    public void setNetWork(){
    	Builder b = new AlertDialog.Builder(LoginActivity.this).setTitle("没有可用的网络")
                .setMessage("是否对网络进行设置？");
        b.setPositiveButton("是", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            	startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
            }
        }).setNeutralButton("否", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.cancel();
            }
        }).show();
    }
 
}
