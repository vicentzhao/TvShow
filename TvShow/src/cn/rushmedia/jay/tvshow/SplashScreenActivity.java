package cn.rushmedia.jay.tvshow;



import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import cn.rushmedia.jay.tvshow.R;

public class SplashScreenActivity extends Activity{

	@Override
	public void onCreate(Bundle icicle) {
		// TODO Auto-generated method stub
		super.onCreate(icicle);
		getWindow().setFormat(PixelFormat.RGBA_8888);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_DITHER);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.splashscreen);
		new Handler().postDelayed(new Runnable() {
			public void run() {
				Intent mainIntent = null;
				mainIntent = new Intent(SplashScreenActivity.this,
						LoginActivity.class);
				SplashScreenActivity.this.startActivity(mainIntent);
				SplashScreenActivity.this.finish();
			}
		}, 1500); // 2900 for release
         
	}
	}

