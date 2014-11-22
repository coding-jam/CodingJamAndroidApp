package it.cosenonjaviste.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.squareup.picasso.Transformation;

public class CircleTransform implements Transformation {

    private final boolean showBorder;
    private int borderSize;
    private int borderColor;
    private int imageSize;

    public static CircleTransform create(Context context, int dimensionResource) {
        return new CircleTransform(context, dimensionResource, false, 0, 0);
    }

    public static CircleTransform createWithBorder(Context context, int dimensionResource, int colorRes, int borderSizeResource) {
        return new CircleTransform(context, dimensionResource, true, colorRes, borderSizeResource);
    }

    private CircleTransform(Context context, int dimensionResource, boolean showBorder, int colorRes, int borderSizeResource) {
        this.showBorder = showBorder;
        if (borderSizeResource != 0) {
            borderSize = context.getResources().getDimensionPixelSize(borderSizeResource);
        }
        imageSize = context.getResources().getDimensionPixelSize(dimensionResource);
        if (colorRes != 0) {
            borderColor = context.getResources().getColor(colorRes);
        }
    }

    @Override
    public Bitmap transform(Bitmap source) {
        if (source == null) {
            return null;
        }
        int size = Math.min(source.getWidth(), source.getHeight());
        if (size == 0) {
            return null;
        }
        Bitmap squaredBitmap = createSquaredImage(source, size);

        if (size != imageSize) {
            size = imageSize;
            squaredBitmap = screateScaledImage(squaredBitmap, size);
        }

        Bitmap bitmap = Bitmap.createBitmap(size, size, Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);

        float r = size / 2f;
        if (showBorder) {
            Paint borderPaint = new Paint();
            borderPaint.setStrokeWidth(borderSize);
            borderPaint.setColor(borderColor);
            borderPaint.setAntiAlias(true);

            float borderRadius = size / 2f;
            canvas.drawCircle(borderRadius, borderRadius, borderRadius, borderPaint);

            r = r - borderSize;
        }

        canvas.drawCircle(size / 2f, size / 2f, r, createBitmapPainter(squaredBitmap));

        squaredBitmap.recycle();
        return bitmap;
    }

    private Paint createBitmapPainter(Bitmap squaredBitmap) {
        Paint paint = new Paint();
        BitmapShader shader = new BitmapShader(squaredBitmap, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
        paint.setShader(shader);
        paint.setAntiAlias(true);
        return paint;
    }

    private Bitmap screateScaledImage(Bitmap source, int size) {
        Bitmap ret = Bitmap.createScaledBitmap(source, size, size, true);
        if (source != ret) {
            source.recycle();
        }
        return ret;
    }

    private Bitmap createSquaredImage(Bitmap source, int size) {
        int width = source.getWidth();
        int height = source.getHeight();
        if (width == size && height == size) {
            return source;
        }
        int x = Math.max(0, (width - size) / 2);
        int y = Math.max(0, (height - size) / 2);

        Bitmap squaredBitmap = Bitmap.createBitmap(source, x, y, size, size);
        if (squaredBitmap != source) {
            source.recycle();
        }
        return squaredBitmap;
    }

    @Override
    public String key() {
        return "circle" + imageSize + "_" + borderColor + "_" + showBorder;
    }
}