package cn.rushmedia.jay.tvshow;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import cn.rushmedia.jay.tvshow.domain.AppData;

public class BaseActivity extends Activity {
	
	ProgressDialog pd;
	// �û��ɼ���ʱ�� ִ��
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		pd = new ProgressDialog(this);
		SharedPreferences sp = getSharedPreferences("setting",
				Context.MODE_PRIVATE);
		String accesstoken = sp.getString("accesstoken", null);
		String tokensecret = sp.getString("tokensecret", null);
		super.onCreate(savedInstanceState);
	}
	/**
	 * �����Ի���,�˳�Ӧ�ó���
	 */
	protected void showTips(){
		AlertDialog alertDialog = new AlertDialog.Builder(BaseActivity.this)
		.setTitle("�˳�����")
		.setMessage("�Ƿ��˳�����")
		.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which){
				AppData appdata = (AppData) getApplication();
				appdata.exit();
			}
		}).setNegativeButton("ȡ��",
				new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which){
				return;
			}}).create();  //�����Ի���
		alertDialog.show(); // ��ʾ�Ի���
	}
	@Override
	protected void onStart() {
		ImageButton ib = (ImageButton) this.findViewById(R.id.back_button);
		if (ib != null) {
			ib.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					// TODO Auto-generated method stub
					finish();
				}
			});
		}
		super.onStart();
	}

	// �򿪽�����
	public void showProgress(RelativeLayout rl) {
		// �ѽ��ȶԻ�����ʾ����
		AlphaAnimation aa = new AlphaAnimation(0, 1);
		aa.setDuration(2000);
		rl.setAnimation(aa);
		rl.setAnimationCacheEnabled(false);
		rl.setVisibility(View.VISIBLE);
	}

	// �رս�����
	public void hideProgress(RelativeLayout rl) {
		AlphaAnimation aa = new AlphaAnimation(1, 0);
		aa.setDuration(2000);
		rl.setAnimation(aa);
		rl.setAnimationCacheEnabled(false);
		rl.setVisibility(View.INVISIBLE);
	}

	public void showProgressDialog(String message) {
		pd.setMessage(message);
		pd.show();
	}
	public void dismissProgressDialog() {
		pd.dismiss();
	}

}
