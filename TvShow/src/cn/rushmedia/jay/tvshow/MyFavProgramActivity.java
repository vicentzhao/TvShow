package cn.rushmedia.jay.tvshow;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
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
import cn.rushmedia.jay.tvshow.domain.User;
import cn.rushmedia.jay.tvshow.util.ImageDownloder;
import cn.rushmedia.jay.tvshow.util.ImageFileCache;
import cn.rushmedia.jay.tvshow.util.JsonUtil;

public class MyFavProgramActivity extends BaseActivity {

	private List<Program> mData;
	private ListView listview;
	private ViewHolder holder;
	private AppData appData;
	private String logininfo;
	private RelativeLayout rl;
	private MyAdapter adapter;
	private int page = 1;
	private int count = 10;
	private int id;
	private User userinfo;
	private Button tv_mytopic_previewpage;
	private Button tv_mytopic_nextpage;
	private  List<Program> myProgramList ;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.subject_2_program);
		appData = (AppData) getApplication();
		logininfo = appData.getLoginInfo();
		appData.addActivity(this);
		initView();
		Intent i = getIntent();
		userinfo = (User) i.getSerializableExtra("userinfo");
		id = userinfo.getId();
		List<Program> programList = intiData(page, count);
		listview.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(getApplicationContext(),
						NewFileActivity.class);
				Program movie = myProgramList.get(position);
				Post home = new Post();
				Topic t = new Topic();
				t.setProgramid(movie.getId());
				t.setProgram(movie);
				home.setTopic(t);
				intent.putExtra("saydetial", home);
				startActivity(intent);
			}

		});
		adapter = new MyAdapter(getApplicationContext(), programList);
		listview.setAdapter(adapter);
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
		 if(page==1){
		 Toast.makeText(getApplicationContext(), "已经是第一页", 1).show();
		 }else
		 page =page-1;
		 intiData(page, count);
		 adapter.notifyDataSetChanged();
		 }
		 });
		 /**
		 * 下一页
		 */
		tv_mytopic_nextpage.setOnClickListener(new OnClickListener() {
		 @Override
		 public void onClick(View v) {
		 page =page+1;
		 List<Program> intiData = intiData(page, count);
		 if(intiData!=null&&"[]".equals(intiData)){
			 listview.setAdapter(new MyAdapter(MyFavProgramActivity.this, intiData));
		 }else{
			 page=page-1;
			 Toast.makeText(MyFavProgramActivity.this, "这是最后一页", 1).show();
		 }
		 }
		 });
	}
	private List<Program> intiData(final int page2, final int count2) {
		myProgramList= new ArrayList<Program>();

				JsonUtil jsut = new JsonUtil();
					String path = "http://tvsrv.webhop.net:8080/api/users/"
							+ id + "/favorite-programs?page=" + page2
							+ "&count=" + count2 + "";
					try {
						myProgramList = jsut.getMovie(path);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					for (int i = 0; i < myProgramList.size(); i++) {
						Program p = myProgramList.get(i);
						if ((p.getId()) == 0) {
							myProgramList.remove(i);
						}
					}
		return myProgramList;
	}

	public final class ViewHolder {
		public ImageView img;
		public TextView title;
		public TextView actor;
		public TextView keyword;
	}

	class MyAdapter extends BaseAdapter {

		private LayoutInflater mInflater;
		private List<Program> myProgramList;

		public MyAdapter(Context context, List result) {
			this.mInflater = LayoutInflater.from(context);
			this.myProgramList = result;
		}

		@Override
		public int getCount() {
			return myProgramList.size();
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
			Bitmap bitmap = cache.getImage(myProgramList.get(position).getImagePath());
			ImageDownloder imageDownloder = new ImageDownloder();
			
			if (bitmap != null) {
				holder.img.setImageBitmap(bitmap);
			} else {
				try {
				Bitmap	dowmBitmap = imageDownloder.imageDownloder(myProgramList.get(position)
							.getImagePath());
				holder.img.setImageBitmap(dowmBitmap);
				cache.saveBmpToSd(dowmBitmap, myProgramList.get(position).getImagePath());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			holder.title.setText(getApplication().getResources().getString(
					R.string.see_filename)
					+ ":" + (String) myProgramList.get(position).getTitle());
			holder.actor.setText(getApplication().getResources().getString(
					R.string.see_actor)
					+ ":" + (String) myProgramList.get(position).getActor());
			holder.keyword.setText(myProgramList.get(position).getKey());

			return convertView;
		}
	}
	void initView() {
		listview = (ListView) findViewById(R.id.subjectlist);
		LayoutInflater inflater = LayoutInflater.from(this);
		LinearLayout mSubjectFooter = (LinearLayout) inflater.inflate(
				R.layout.subject_footer, null);
		tv_mytopic_previewpage = (Button) mSubjectFooter
				.findViewById(R.id.tv_mytopic_previewpage);
		tv_mytopic_nextpage = (Button) mSubjectFooter
				.findViewById(R.id.tv_mytopic_nextpage);
		listview.addFooterView(mSubjectFooter);
		rl = (RelativeLayout) findViewById(R.id.loading);
	}

}
