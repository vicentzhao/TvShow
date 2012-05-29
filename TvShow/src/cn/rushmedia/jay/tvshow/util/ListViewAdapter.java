package cn.rushmedia.jay.tvshow.util;
import java.util.ArrayList;
import java.util.List;

import cn.rushmedia.jay.tvshow.R;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

public class ListViewAdapter extends BaseAdapter {
   //listviewҪ��ʾ������,��Ҫ�ӷ�������ȡ��������ֱ����
	private ArrayList arrayList;
	private ListView listView;
	private LayoutInflater mInflater;
	private  LayoutInflater  inflater;
	public ListViewAdapter(Context context, ListView listView, ArrayList arrayList) {
		this.listView = listView;
		this.arrayList=arrayList;
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return arrayList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return arrayList.get(position);
	}
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	@Override
	public View getView(int position, View view, ViewGroup parent) {
		// Ĭ�ϵ�ͼƬ��ֵ
		//ViewCache viewCache;
		if (view == null) {
			view = mInflater.inflate(R.layout.subject_replay, null);
			
			

		} else {
			
		}
		TextView txtreply = (TextView) view.findViewById(R.id.replay);
		txtreply.setText(arrayList.get(0).toString());
		TextView txttime = (TextView) view
				.findViewById(R.id.curtime);
		txttime.setText(arrayList.get(1).toString());
		//������Ҫ���û���ͷ����������
		//�õ���Ӧ��ͼ���uri
		//imageUri=JsonParse(url).getimg();
		//in=down(imageUri) �õ���Ӧ������Ȼ����bitmapfactory�ķ����õ���Ӧ��ͼƬ
		//bm=bitmapParse(in);
		//Ϊ��ֹͼƬ�����ڴ��������Ҫ��ͼƬ�������ã�����α����
		/**
		 *  
           BitmapFactory.Options options = new BitmapFactory.Options();   
           options.inSampleSize=2;//ͼƬ�߿�ȶ�Ϊԭ���Ķ���֮һ����ͼƬ��СΪԭ���Ĵ�С���ķ�֮һ   
           options.inTempStorage = new byte[5*1024]; //����16MB����ʱ�洢�ռ䣨�������û�û������������֤��   
           Bitmap bitMap = BitmapFactory.decodeFile(�ļ�·��, options);    
i          mageView.setImageBitmap(bitMap);
		 */
		//ImageView userview=(ImageView) view.findViewById(R.id.user_img);  
		//userview.setImageBitmap(bm);
		//���ͼ����Ի��浽sd���У��Ա��ڽ�ʡ����
        return view;
	}

}
