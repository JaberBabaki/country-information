package com.jaber.country;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;


public class CircleRoundedImageView extends ImageView {

    private static int    StrokeWidth = 0;
    private static String StrokeColor = "#d7d7d7";


    public CircleRoundedImageView(Context context) {
        super(context);
    }


    public CircleRoundedImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public CircleRoundedImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    @Override
    protected void onDraw(Canvas canvas) {

        Drawable drawable = getDrawable();

        if (drawable == null) {
            return;
        }

        if (getWidth() == 0 || getHeight() == 0) {
            return;
        }
        Bitmap b = ((BitmapDrawable) drawable).getBitmap();
        Bitmap bitmap = b.copy(Bitmap.Config.ARGB_8888, true);

        int w = getWidth(), h = getHeight();

        Bitmap roundBitmap = getCroppedBitmap(bitmap, w);
        canvas.drawBitmap(roundBitmap, 0, 0, null);

    }


    public static void setStrockWidth(int value) {
        StrokeWidth = value;
    }


    public static void setStrockWidth(String value) {
        StrokeColor = value;
    }


    public static Bitmap getCroppedBitmap(Bitmap bmp, int width) {
        Bitmap sbmp;
        if (bmp != null) {
            if (bmp.getWidth() != width || bmp.getHeight() != width) {
                float smallest = Math.min(bmp.getWidth(), bmp.getHeight());
                float factor = smallest / width;
                sbmp = Bitmap.createScaledBitmap(bmp, (int) (bmp.getWidth() / factor), (int) (bmp.getHeight() / factor), false);
            } else {
                sbmp = bmp;
            }
            Bitmap output = Bitmap.createBitmap(width, width, Config.ARGB_8888);
            Canvas canvas = new Canvas(output);

            final int color = 0xffa19774;
            final Paint paint = new Paint();
            final Rect rect = new Rect(0, 0, width, width);

            paint.setAntiAlias(true);
            paint.setFilterBitmap(true);
            paint.setDither(true);
            paint.setStyle(Style.FILL_AND_STROKE);
            canvas.drawARGB(0, 0, 0, 0);
            paint.setColor(Color.parseColor("#ff0000"));

            canvas.drawCircle(width / 2, width / 2, width / 2, paint);
            paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
            canvas.drawBitmap(sbmp, rect, rect, paint);

            /*  Paint CirclePaintBg = new Paint();
              CirclePaintBg.setColor(Color.parseColor(StrokeColor));
              CirclePaintBg.setAntiAlias(true);
              CirclePaintBg.setStrokeWidth(StrokeWidth);
              CirclePaintBg.setStyle(Style.STROKE);
              canvas.drawCircle(width / 2, width / 2, width / 2 - 3, CirclePaintBg);*/

            return output;
        } else {
            return null;
        }
    }

}