package cn.rushmedia.jay.tvshow.util;

import cn.rushmedia.jay.tvshow.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ImageButton extends LinearLayout {
    private ImageView imageView;
    private TextView textView;
	public ImageButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		imageView =new ImageView(context, attrs);
	    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon);
	    imageView.setImageBitmap(bitmap);
	    imageView.setPadding(1, 2, 3, 4);
	    textView =new TextView(context, attrs);
	    textView.setText("ªÿ∏¥Œ“");
	    textView.setPadding(0, 0, 0, 0);
	    setClickable(true);
	    setFocusable(true);
	    setOrientation(LinearLayout.HORIZONTAL);
	    addView(imageView);
	    addView(textView);
	}
}
