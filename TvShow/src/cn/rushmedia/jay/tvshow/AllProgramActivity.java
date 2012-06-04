package cn.rushmedia.jay.tvshow;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.rushmedia.jay.tvshow.domain.AppData;
import cn.rushmedia.jay.tvshow.domain.Post;
import cn.rushmedia.jay.tvshow.domain.Program;
import cn.rushmedia.jay.tvshow.domain.Topic;
import cn.rushmedia.jay.tvshow.util.ImageDownloder;
import cn.rushmedia.jay.tvshow.util.ImageFileCache;
import cn.rushmedia.jay.tvshow.util.JSONObject2Post;
import cn.rushmedia.jay.tvshow.util.JSONObject2Program;
import cn.rushmedia.jay.tvshow.util.JsonUtil;

public class AllProgramActivity extends BaseActivity {

	private ListView listview;
	private ViewHolder holder;
	private AppData appData;
	private String logininfo;
	private RelativeLayout rl;
	private MyAdapter adapter;
	private int page = 1;
	private int count = 10;
	private ArrayList<Post> postList;
	private LinearLayout mSubjectFooter;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.subject_2_program);
		appData = (AppData) getApplication();
		rl = (RelativeLayout) findViewById(R.id.loading);
		logininfo = appData.getLoginInfo();
		appData.addActivity(this);
		listview = (ListView) findViewById(R.id.subjectlist);
		LayoutInflater inflater = LayoutInflater.from(this);
		mSubjectFooter = (LinearLayout) inflater.inflate(
				R.layout.subject_footer, null);
		Button tv_mytopic_previewpage = (Button) mSubjectFooter
				.findViewById(R.id.tv_mytopic_previewpage);
		Button tv_mytopic_nextpage = (Button) mSubjectFooter
				.findViewById(R.id.tv_mytopic_nextpage);
		listview.addFooterView(mSubjectFooter);
		intiData(page, count);

		listview.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(getApplicationContext(),
						NewFileActivity.class);
				Post post = postList.get(position);
				intent.putExtra("saydetial", post);
				startActivity(intent);
			}

		});
		ImageButton backtohome = (ImageButton) findViewById(R.id.backtohome);
		backtohome.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(),
						TableActivity.class);
				startActivity(i);
			}
		});
		ImageButton tv_reprogram_button = (ImageButton) findViewById(R.id.tv_reprogram_button);
		tv_reprogram_button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(),
						TableActivity.class);
				startActivity(i);
			}
		});
		/**
		 * 上一页
		 */

	
		tv_mytopic_previewpage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (page == 1) {
					Toast.makeText(getApplicationContext(), "已经是第一页", 1).show();
					return;
				} else{
					page = page - 1;
				intiData(page, count);
				adapter.notifyDataSetChanged();
				}
			}
		});
		/**
		 * 下一页
		 */
		tv_mytopic_nextpage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				page = page + 1;
				intiData(page, count);
				adapter.notifyDataSetChanged();
			}
		});
	}

	private void intiData(final int page2, final int count2) {
		new AsyncTask<Void, Void, List>() {

			@Override
			protected void onPreExecute() {
				showProgress(rl);
				super.onPreExecute();
			}

			@Override
			protected void onPostExecute(List result) {
				hideProgress(rl);
				super.onPostExecute(result);
				adapter = new MyAdapter(getApplicationContext());
				listview.setAdapter(adapter);
				// adapter.notifyDataSetChanged();
			}

			@Override
			protected List doInBackground(Void... params) {
				JsonUtil jsut = new JsonUtil();
				try {
					postList= new ArrayList<Post>();
					String path = "http://tvsrv.webhop.net:8080/api/programs?page="
							+ page2 + "&count=" + count2 + "";
					JSONArray jsPostArray = jsut.getSource(path);
					System.out.println("jsPostArray"+jsPostArray);
					for (int i = 0; i < jsPostArray.length(); i++) {
					JSONObject	jsPost =jsPostArray.getJSONObject(i);
					JSONObject2Program jp = new JSONObject2Program();
					Program pg =jp.getProgram(jsPost);
					Post post = new Post();
					Topic tp = new Topic();
					tp.setProgram(pg);
					post.setTopic(tp);
					postList.add(post);
					}
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return postList;
			}

		}.execute();
	}

	public final class ViewHolder {
		public ImageView img;
		public TextView title;
		public TextView actor;
		public TextView keyword;
	}

	class MyAdapter extends BaseAdapter {

		private LayoutInflater mInflater;
		

		public MyAdapter(Context context) {
			this.mInflater = LayoutInflater.from(context);
			
		}

		@Override
		public int getCount() {
			return postList.size();
		}

		public Object getItem(int arg0) {
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			return 0;
		}

		public View getView(final int position, View convertView,
				ViewGroup parent) {
			holder = new ViewHolder();
			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.subject_replay, null);
				holder.img = (ImageView) convertView.findViewById(R.id.image);
				holder.title = (TextView) convertView.findViewById(R.id.title);
				holder.actor = (TextView) convertView.findViewById(R.id.actor);
				holder.keyword = (TextView) convertView
						.findViewById(R.id.keyword);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			ImageFileCache cache = ImageFileCache.getCashInstance();
			ImageDownloder imageDownloder = new ImageDownloder();
			Bitmap bitmap = null;
			String FileImagePath = postList.get(position).getTopic().getProgram().getImagePath();
			Bitmap FileImage = cache.getImage(FileImagePath);
			if (FileImage == null) {
                try {
					bitmap = imageDownloder.imageDownloder(FileImagePath);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				holder.img.setImageBitmap(bitmap);
				cache.saveBmpToSd(bitmap, FileImagePath);
			}
			holder.img.setImageBitmap(FileImage);
			holder.title.setText(getApplication().getResources().getString(
					R.string.see_filename)
					+ ":" + postList.get(position).getTopic().getProgram().getTitle());
			holder.actor.setText(getApplication().getResources().getString(
					R.string.see_actor)
					+ ":" + postList.get(position).getTopic().getProgram().getActor());
			holder.keyword.setText(postList.get(position).getTopic().getProgram().getKey());
			return convertView;
		}
	}
}
