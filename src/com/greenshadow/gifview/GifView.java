package com.greenshadow.gifview;

import java.io.InputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Movie;
import android.util.AttributeSet;
import android.view.View;

public class GifView extends View {

	private long movieStart;
	private Movie movie = null;
	private InputStream is = null;
	float locationX;
	float locationY;

	public GifView(Context context, AttributeSet attrs) {
		super(context, attrs);
		int resouceID = attrs.getAttributeResourceValue(null, "Src", 0);
		locationX = attrs.getAttributeResourceValue(null, "locationX", 0);
		locationY = attrs.getAttributeResourceValue(null, "locationY", 0);
		if (resouceID != 0) {
			is = getResources().openRawResource(resouceID);
			movie = Movie.decodeStream(is);
		}
	}

	@Override
	protected void onDraw(Canvas canvas) {
		long curTime = android.os.SystemClock.uptimeMillis();
		if (movieStart == 0) {
			movieStart = curTime;
		}
		if (movie != null) {
			int duraction = movie.duration();
			int relTime = (int) ((curTime - movieStart) % duraction);
			movie.setTime(relTime);
			movie.draw(canvas, locationX, locationY);
			invalidate();
		}
		super.onDraw(canvas);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		if (is != null) {
			Bitmap bmp = BitmapFactory.decodeStream(is);
			int width = bmp.getWidth();
			int height = bmp.getHeight();
			// super.onMeasure(width, height);
			setMeasuredDimension(width, height);
		} else
			super.onMeasure(0, 0);
	}
}
