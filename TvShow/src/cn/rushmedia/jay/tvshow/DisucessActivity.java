package cn.rushmedia.jay.tvshow;

import java.util.ArrayList;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import cn.rushmedia.jay.tvshow.domain.AppData;
import cn.rushmedia.jay.tvshow.domain.Program;
import cn.rushmedia.jay.tvshow.util.ImageDownloder;
import cn.rushmedia.jay.tvshow.util.JsonUtil;

public class DisucessActivity extends BaseActivity {

	private ImageView imageView;
	private TextView textView;
	private TextView textView2;
	private AppData appdata;
	private ImageDownloder imageDownloder;
	private Bitmap bitmap;
	private ArrayList<Program> arrayList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.subject);
		appdata.addActivity(this);
		JsonUtil jsut = new JsonUtil();
		try {
			//arrayList = jsut.getMovie();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		listAdapter adapter = new listAdapter();
		ImageButton backtohome = (ImageButton) findViewById(R.id.backtohome);
    	backtohome.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(),TableActivity.class);
				startActivity(i);
			}
		});
		
	}
//	/**
//	 * ²¶×½»ØÍË¼ü
//	 */
//	public boolean onKeyDown(int keyCode, KeyEvent event) {
//		if(keyCode==KeyEvent.KEYCODE_BACK && event.getRepeatCount()==0){
//			showTips();
//		return false;
//		}
//		return false;
//		}
	private class listAdapter extends BaseAdapter {


		@Override
		public int getCount() {
			return arrayList.size();
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView( int position, View v, ViewGroup parent) {
			Program movie =arrayList.get(position);
			LayoutInflater inflater =(LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
//			if (view == null) {
			  View	view = inflater.inflate(R.layout.subject_replay, null);
	//		}
				textView = (TextView) view.findViewById(R.id.tvTitle);
				textView2 = (TextView) view.findViewById(R.id.ivIcon);
				imageView = (ImageView) view.findViewById(R.id.tv_home_user_img);
				textView.setText(movie.getTitle());
				textView2.setText(movie.getDescription());
				try {
					bitmap = imageDownloder.imageDownloder(arrayList.get(position).getImagePath());
					imageView.setImageBitmap(bitmap);
				} catch (Exception e) {
					e.printStackTrace();
				}
//				new AsyncTask<Void, Void, Bitmap>(){
//
//					@Override
//					protected Bitmap doInBackground(Void... params) {
//						try {
//							bitmap = imageDownloder.imageDownloder(arrayList.get(position).getImagePath());
//							return bitmap;
//						} catch (Exception e) {
//							e.printStackTrace();
//							return null;
//						}
//					}
//					@Override
//					protected void onPostExecute(Bitmap result) {
//						imageView.setImageBitmap(result);
//						super.onPostExecute(result);
//					}
//				}.execute();
			
			return view;
		}
	}
}
