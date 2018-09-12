package com.ha.cjy.common.util;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Base64;
import android.view.View;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by cjy on 2018/7/31.
 */

public class BitmapUtil {
    public BitmapUtil() {
    }

    public static String convertIconToString(Bitmap bitmap) {
        return Base64.encodeToString(getBitmapByte(bitmap, 100), 0);
    }

    public static String convertIconToStringForWebP(Bitmap bitmap) {
        return Base64.encodeToString(getBitmapByteForWebp(bitmap, 50), 0);
    }

    public static Bitmap optionsBitmap(Context context, Uri uri, int dw, int dh) {
        Bitmap bmp = null;

        try {
            BitmapFactory.Options factory = new BitmapFactory.Options();
            factory.inJustDecodeBounds = true;
            bmp = BitmapFactory.decodeStream(context.getContentResolver().openInputStream(uri), (Rect)null, factory);
            int hRatio = (int)Math.ceil((double)((float)factory.outHeight / (float)dh));
            int wRatio = (int)Math.ceil((double)((float)factory.outWidth / (float)dw));
            if(hRatio > 1 || wRatio > 1) {
                if(hRatio > wRatio) {
                    factory.inSampleSize = hRatio;
                } else {
                    factory.inSampleSize = wRatio;
                }
            }

            factory.inJustDecodeBounds = false;
            bmp = BitmapFactory.decodeStream(context.getContentResolver().openInputStream(uri), (Rect)null, factory);
        } catch (FileNotFoundException var8) {
            var8.printStackTrace();
        }

        return bmp;
    }

    public static Bitmap optionsBitmap2(Context ctx, Uri uri, int targetWidth, int targetHeight) throws Exception {
        Bitmap tmpBmp = null;

        try {
            if(uri == null) {
                return null;
            }

            ContentResolver cr = ctx.getContentResolver();
            BitmapFactory.Options opts = new BitmapFactory.Options();
            opts.inJustDecodeBounds = true;
            if(uri.toString().indexOf("content") != -1) {
                BitmapFactory.decodeStream(cr.openInputStream(uri), (Rect)null, opts);
            } else {
                BitmapFactory.decodeFile(uri.toString(), opts);
            }

            int imWidth = opts.outWidth;
            int imHeight = opts.outHeight;
            int scale;
            if(imWidth > imHeight) {
                scale = Math.round((float)imWidth / (float)targetWidth);
            } else {
                scale = Math.round((float)imHeight / (float)targetHeight);
            }

            scale = scale == 0?1:scale;
            opts.inJustDecodeBounds = false;
            opts.inSampleSize = scale;
            if(uri.toString().indexOf("content") != -1) {
                tmpBmp = BitmapFactory.decodeStream(cr.openInputStream(uri), (Rect)null, opts);
            } else {
                FileInputStream fis = new FileInputStream(new File(uri.toString()));
                tmpBmp = BitmapFactory.decodeStream(fis, (Rect)null, opts);
            }
        } catch (OutOfMemoryError var11) {
            var11.printStackTrace();
        }

        return tmpBmp;
    }

    public static Bitmap resizeImage(Bitmap bmp, int newWidth, int newHeiht) {
        if(bmp == null) {
            return null;
        } else {
            int originWidth = bmp.getWidth();
            int originHeight = bmp.getHeight();
            if(originWidth == newWidth && originHeight == newHeiht) {
                return bmp;
            } else {
                float scaleWidth = (float)newWidth / (float)originWidth;
                float scaleHeight = (float)newHeiht / (float)originHeight;
                Matrix matrix = new Matrix();
                matrix.postScale(scaleWidth, scaleHeight);
                Bitmap resizeBitmap = Bitmap.createBitmap(bmp, 0, 0, originWidth, originHeight, matrix, true);
                return resizeBitmap;
            }
        }
    }

    public static Bitmap resizeImage(Bitmap bmp, float scale) {
        if(bmp == null) {
            return null;
        } else {
            int originWidth = bmp.getWidth();
            int originHeight = bmp.getHeight();
            Matrix matrix = new Matrix();
            matrix.postScale(scale, scale);
            return Bitmap.createBitmap(bmp, 0, 0, originWidth, originHeight, matrix, true);
        }
    }

    public static Bitmap toSizeBitmap(Bitmap bitmapOrg, int newWidth, int newHeight) {
        if(null == bitmapOrg) {
            return null;
        } else {
            int w = bitmapOrg.getWidth();
            int h = bitmapOrg.getHeight();
            int wTemp = newWidth * h / newHeight;
            int x;
            int y;
            if(wTemp > w) {
                h = newHeight * w / newWidth;
                x = 0;
                y = (bitmapOrg.getHeight() - h) / 2;
            } else {
                w = wTemp;
                y = 0;
                x = (bitmapOrg.getWidth() - wTemp) / 2;
            }

            float scaleHeight = 0.0F;
            Matrix matrix = new Matrix();
            float scaleWidth = (float)newWidth / (float)w;
            scaleHeight = (float)newHeight / (float)h;
            matrix.postScale(scaleWidth, scaleHeight);
            Bitmap resizedBitmap = Bitmap.createBitmap(bitmapOrg, x, y, w, h, matrix, true);
            return resizedBitmap;
        }
    }

    public static Bitmap toSizeBitmapForWidth(Bitmap bitmapOrg, int newWidth, int newHeight) {
        if(null == bitmapOrg) {
            return null;
        } else {
            int w = bitmapOrg.getWidth();
            int h = bitmapOrg.getHeight();
            int wTemp = newWidth * h / newHeight;
            h = newHeight * w / newWidth;
            int x = 0;
            int y = (bitmapOrg.getHeight() - h) / 2;
            float scaleHeight = 0.0F;
            Matrix matrix = new Matrix();
            float scaleWidth = (float)newWidth / (float)w;
            scaleHeight = (float)newHeight / (float)h;
            matrix.postScale(scaleWidth, scaleHeight);
            Bitmap resizedBitmap = Bitmap.createBitmap(bitmapOrg, x, y, w, h, matrix, true);
            return resizedBitmap;
        }
    }

    public static byte[] getBitmapByte(Bitmap bitmap, int quality) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, out);

        try {
            out.flush();
            out.close();
        } catch (IOException var4) {
            var4.printStackTrace();
        }

        return out.toByteArray();
    }

    @SuppressLint({"NewApi"})
    public static byte[] getBitmapByteForWebp(Bitmap bitmap, int quality) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.WEBP, quality, out);

        try {
            out.flush();
            out.close();
        } catch (IOException var4) {
            var4.printStackTrace();
        }

        return out.toByteArray();
    }

    public static InputStream Bitmap2InputStream(Bitmap bm, int quality) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, quality, baos);
        InputStream is = new ByteArrayInputStream(baos.toByteArray());
        return is;
    }

    public static Bitmap InputStream2Bitmap(InputStream is) {
        return BitmapFactory.decodeStream(is);
    }

    public static InputStream Drawable2InputStream(Drawable d) {
        Bitmap bitmap = drawable2Bitmap(d);
        return Bitmap2InputStream(bitmap, 100);
    }

    public static Drawable InputStream2Drawable(InputStream is, Resources res) {
        Bitmap bitmap = InputStream2Bitmap(is);
        return bitmap2Drawable(bitmap, res);
    }

    public static Drawable bitmap2Drawable(Bitmap bitmap, Resources res) {
        BitmapDrawable bd = new BitmapDrawable(res, bitmap);
        return bd;
    }

    public static byte[] bitmap2Bytes(Bitmap bm) {
        ByteArrayOutputStream baos = null;

        byte[] var3;
        try {
            baos = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
            byte[] result = baos.toByteArray();
            var3 = result;
        } finally {
            if(baos != null) {
                try {
                    baos.close();
                } catch (IOException var10) {
                    var10.printStackTrace();
                }
            }

        }

        return var3;
    }

    public static byte[] drawable2Bytes(Drawable drawable) {
        ByteArrayOutputStream baos = null;

        byte[] var3;
        try {
            baos = new ByteArrayOutputStream();
            if(drawable instanceof BitmapDrawable) {
                ((BitmapDrawable)drawable).getBitmap().compress(Bitmap.CompressFormat.PNG, 100, baos);
            }

            byte[] result = baos.toByteArray();
            var3 = result;
        } finally {
            if(baos != null) {
                try {
                    baos.close();
                } catch (IOException var10) {
                    var10.printStackTrace();
                }
            }

        }

        return var3;
    }

    public static Bitmap bytes2Bitmap(byte[] b) {
        return b != null && b.length != 0?BitmapFactory.decodeByteArray(b, 0, b.length):null;
    }

    public static Bitmap drawable2Bitmap(Drawable drawable) {
        if(null == drawable) {
            return null;
        } else {
            Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), drawable.getOpacity() != PixelFormat.OPAQUE? Bitmap.Config.ARGB_8888: Bitmap.Config.RGB_565);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            drawable.draw(canvas);
            return bitmap;
        }
    }

    public static int[] getImageWH(String path) {
        int[] wh = new int[]{-1, -1};
        if(path == null) {
            return wh;
        } else {
            File file = new File(path);
            if(file.exists() && !file.isDirectory()) {
                try {
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inJustDecodeBounds = true;
                    InputStream is = new FileInputStream(path);
                    BitmapFactory.decodeStream(is, (Rect)null, options);
                    wh[0] = options.outWidth;
                    wh[1] = options.outHeight;
                } catch (Throwable var5) {
                    var5.printStackTrace();
                }
            }

            return wh;
        }
    }

    public static int[] getImageWH(InputStream is) {
        int[] wh = new int[]{-1, -1};
        if(is == null) {
            return wh;
        } else {
            try {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeStream(is, (Rect)null, options);
                wh[0] = options.outWidth;
                wh[1] = options.outHeight;
            } catch (Throwable var3) {
                var3.printStackTrace();
            }

            return wh;
        }
    }

    public static Rect caculateRect(float x, float y, float width, float height) {
        int left = Math.round(x - width / 2.0F);
        int top = Math.round(y - height / 2.0F);
        int right = Math.round((float)left + width);
        int bottom = Math.round((float)top + height);
        return new Rect(left, top, right, bottom);
    }

    public static Bitmap copyBitmap(Bitmap bitmapOrg) {
        if(null == bitmapOrg) {
            return null;
        } else {
            Bitmap resultBitmap = Bitmap.createScaledBitmap(bitmapOrg, bitmapOrg.getWidth(), bitmapOrg.getHeight(), true);
            Canvas canvas = new Canvas();
            canvas.setBitmap(resultBitmap);
            Paint paint = new Paint();
            paint.setFilterBitmap(true);
            paint.setAntiAlias(true);
            canvas.drawBitmap(bitmapOrg, 0.0F, 0.0F, paint);
            return resultBitmap;
        }
    }

    public static Bitmap createBlurBitmap(Context ctx, Bitmap sourceBmp, float bmpScale) {
        if(null == sourceBmp) {
            return null;
        } else {
            float scale = ScreenUtil.getDisplayMetrics(ctx).density;
            Canvas srcDstCanvas = new Canvas();
            srcDstCanvas.setBitmap(sourceBmp);
            srcDstCanvas.drawColor(-16777216, PorterDuff.Mode.SRC_OUT);
            BlurMaskFilter innerBlurMaskFilter = new BlurMaskFilter(scale * 2.0F, BlurMaskFilter.Blur.NORMAL);
            Paint mBlurPaint = new Paint();
            mBlurPaint.setFilterBitmap(true);
            mBlurPaint.setAntiAlias(true);
            mBlurPaint.setMaskFilter(innerBlurMaskFilter);
            int[] thickInnerBlurOffset = new int[2];
            Bitmap thickInnerBlur = sourceBmp.extractAlpha(mBlurPaint, thickInnerBlurOffset);
            Paint mErasePaint = new Paint();
            mErasePaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
            mErasePaint.setFilterBitmap(true);
            mErasePaint.setAntiAlias(true);
            srcDstCanvas.setBitmap(thickInnerBlur);
            srcDstCanvas.drawBitmap(sourceBmp, (float)(-thickInnerBlurOffset[0]), (float)(-thickInnerBlurOffset[1]), mErasePaint);
            srcDstCanvas.drawRect(0.0F, 0.0F, (float)(-thickInnerBlurOffset[0]), (float)thickInnerBlur.getHeight(), mErasePaint);
            srcDstCanvas.drawRect(0.0F, 0.0F, (float)thickInnerBlur.getWidth(), (float)(-thickInnerBlurOffset[1]), mErasePaint);
            Paint mHolographicPaint = new Paint();
            mHolographicPaint.setFilterBitmap(true);
            mHolographicPaint.setAntiAlias(true);
            mHolographicPaint.setAlpha(150);
            srcDstCanvas.setBitmap(sourceBmp);
            srcDstCanvas.drawColor(0, PorterDuff.Mode.CLEAR);
            int outlineColor = Color.parseColor("#33b5e5");
            mHolographicPaint.setColor(outlineColor);
            srcDstCanvas.drawBitmap(thickInnerBlur, (float)thickInnerBlurOffset[0], (float)thickInnerBlurOffset[1], mHolographicPaint);
            thickInnerBlur.recycle();
            BlurMaskFilter outerBlurMaskFilter = new BlurMaskFilter(scale * 2.0F, BlurMaskFilter.Blur.OUTER);
            mBlurPaint.setMaskFilter(outerBlurMaskFilter);
            int[] outerBlurOffset = new int[2];
            Bitmap thickOuterBlur = sourceBmp.extractAlpha(mBlurPaint, outerBlurOffset);
            srcDstCanvas.drawBitmap(thickOuterBlur, (float)outerBlurOffset[0], (float)outerBlurOffset[1], mHolographicPaint);
            thickOuterBlur.recycle();
            mHolographicPaint.setColor(outlineColor);
            BlurMaskFilter sThinOuterBlurMaskFilter = new BlurMaskFilter(scale * 1.0F, BlurMaskFilter.Blur.OUTER);
            mBlurPaint.setMaskFilter(sThinOuterBlurMaskFilter);
            int[] brightOutlineOffset = new int[2];
            Bitmap brightOutline = sourceBmp.extractAlpha(mBlurPaint, brightOutlineOffset);
            srcDstCanvas.drawBitmap(brightOutline, (float)brightOutlineOffset[0], (float)brightOutlineOffset[1], mHolographicPaint);
            brightOutline.recycle();
            Matrix matrix = new Matrix();
            matrix.postScale(bmpScale, bmpScale);
            Bitmap bitmap = Bitmap.createBitmap(sourceBmp, 0, 0, sourceBmp.getWidth(), sourceBmp.getHeight(), matrix, true);
            sourceBmp.recycle();
            return bitmap;
        }
    }

    public static Bitmap decodeStreamABitmap(Context context, int resId, int insampleSize) {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = false;
        opts.inSampleSize = insampleSize;
        InputStream is = context.getResources().openRawResource(resId);
        return BitmapFactory.decodeStream(is, (Rect)null, opts);
    }

    public static Bitmap decodeStreamABitmap(Context context, int resId, int targetWidth, int targetHeight) {
        InputStream is = context.getResources().openRawResource(resId);
        int[] imageWH = getImageWH(is);
        if(imageWH == null) {
            return null;
        } else {
            int scale;
            if(imageWH[0] > imageWH[1]) {
                scale = Math.round((float)imageWH[0] / (float)targetWidth);
            } else {
                scale = Math.round((float)imageWH[1] / (float)targetHeight);
            }

            scale = scale == 0?1:scale;
            return decodeStreamABitmap(context, resId, scale);
        }
    }

    public static boolean saveDrawable2file(Drawable d, String filePath) {
        Bitmap b = drawable2Bitmap(d);
        return saveBitmap2file(b, filePath, Bitmap.CompressFormat.PNG);
    }

    public static boolean saveBitmap2file(Bitmap bmp, String filePath) {
        Bitmap.CompressFormat format = Bitmap.CompressFormat.JPEG;
        int quality = 100;
        FileOutputStream stream = null;

        try {
            stream = new FileOutputStream(filePath);
        } catch (FileNotFoundException var6) {
            var6.printStackTrace();
        }

        return null == stream?false:bmp.compress(format, quality, stream);
    }

    public static boolean saveBitmap2filePng(Bitmap bmp, String filePath) {
        Bitmap.CompressFormat format = Bitmap.CompressFormat.PNG;
        int quality = 100;
        FileOutputStream stream = null;

        try {
            stream = new FileOutputStream(filePath);
        } catch (FileNotFoundException var6) {
            var6.printStackTrace();
        }

        return null == stream?false:bmp.compress(format, quality, stream);
    }

    public static boolean saveBitmap2file(Bitmap bmp, String filePath, Bitmap.CompressFormat format) {
        if(bmp != null && !bmp.isRecycled()) {
            int quality = 100;
            FileOutputStream stream = null;

            try {
                stream = new FileOutputStream(filePath);
            } catch (Exception var6) {
                var6.printStackTrace();
            }

            return null == stream?false:bmp.compress(format, quality, stream);
        } else {
            return false;
        }
    }

    public static void safeDestoryDrawable(Drawable drawable) {
        if(null != drawable) {
            drawable.setCallback((Drawable.Callback)null);
            drawable = null;
        }
    }

    public static void destoryBitmap(Bitmap bmp) {
        if(null != bmp && !bmp.isRecycled()) {
            bmp.recycle();
            bmp = null;
        }

    }

    public static void destoryDrawable(Drawable drawable) {
        if(null != drawable) {
            if(drawable instanceof BitmapDrawable) {
                BitmapDrawable b = (BitmapDrawable)drawable;
                destoryBitmap(b.getBitmap());
            }

            drawable.setCallback((Drawable.Callback)null);
            drawable = null;
        }
    }

    public static Bitmap craeteComposeBitmap(Bitmap src, Bitmap dst) {
        Bitmap newb = Bitmap.createBitmap(src.getWidth(), src.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas cv = new Canvas(newb);
        cv.drawBitmap(src, 0.0F, 0.0F, (Paint)null);
        cv.drawBitmap(dst, 0.0F, 0.0F, (Paint)null);
        return newb;
    }

    public static Bitmap getNinePatchBitmap(Context ctx, int w, int h, int resId) {
        Drawable maskDrawable = ctx.getResources().getDrawable(resId);
        Bitmap bitmap = Bitmap.createBitmap(w, h, maskDrawable.getOpacity() != -1? Bitmap.Config.ARGB_8888: Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        maskDrawable.setBounds(0, 0, w, h);
        maskDrawable.draw(canvas);
        return bitmap;
    }

    public static Bitmap readBitmap(Context context, int resId) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        InputStream is = context.getResources().openRawResource(resId);
        return BitmapFactory.decodeStream(is, (Rect)null, opt);
    }

    public static Bitmap readBitmapForView(View v) {
        v.clearFocus();
        v.setPressed(false);
        boolean willNotCache = v.willNotCacheDrawing();
        v.setWillNotCacheDrawing(false);
        int color = v.getDrawingCacheBackgroundColor();
        v.setDrawingCacheBackgroundColor(0);
        if(color != 0) {
            v.destroyDrawingCache();
        }

        v.buildDrawingCache();
        Bitmap cacheBitmap = v.getDrawingCache();
        if(cacheBitmap == null) {
            return null;
        } else {
            Bitmap bitmap = null;

            try {
                bitmap = Bitmap.createBitmap(cacheBitmap);
            } catch (Throwable var6) {
                var6.printStackTrace();
            }

            v.destroyDrawingCache();
            v.setWillNotCacheDrawing(willNotCache);
            v.setDrawingCacheBackgroundColor(color);
            return bitmap;
        }
    }

    public static Bitmap readBitmapForViewCache(View v) {
        Bitmap cacheBitmap = v.getDrawingCache();
        if(cacheBitmap != null) {
            return cacheBitmap;
        } else {
            v.clearFocus();
            v.setPressed(false);
            v.setWillNotCacheDrawing(false);
            v.setDrawingCacheEnabled(true);
            int color = v.getDrawingCacheBackgroundColor();
            if(color != 0) {
                v.destroyDrawingCache();
                v.setDrawingCacheBackgroundColor(0);
            }

            v.buildDrawingCache();
            cacheBitmap = v.getDrawingCache();
            return cacheBitmap == null?null:cacheBitmap;
        }
    }

    @SuppressLint({"WrongConstant"})
    public Bitmap toMask(Context context, Bitmap bitmap, int bgResId, int cutResId) {
        Paint paint = new Paint();
        Bitmap bgBm = decodeBitmap(context, bgResId);
        Bitmap cutBm = decodeBitmap(context, cutResId);
        int cutWidth = cutBm.getWidth();
        int cutHeight = cutBm.getHeight();
        int bgWidth = bgBm.getWidth();
        int bgHeight = bgBm.getHeight();
        Bitmap outputBm = Bitmap.createBitmap(bgWidth, bgHeight, Bitmap.Config.ARGB_8888);
        bitmap = toSizeBitmap(bitmap, cutWidth, cutHeight);
        Canvas canvas = new Canvas(outputBm);
        canvas.drawBitmap(bgBm, 0.0F, 0.0F, paint);
        int bleft = bgWidth / 2 - cutWidth / 2;
        int btop = bgHeight / 2 - cutHeight / 2;
        int saveFlags = 31;
        canvas.saveLayer((float)bleft, (float)btop, (float)(cutWidth + bleft), (float)(cutHeight + btop), (Paint)null, saveFlags);
        canvas.drawBitmap(cutBm, (float)bleft, (float)btop, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        int left = cutWidth / 2 - bitmap.getWidth() / 2;
        int top = cutHeight / 2 - bitmap.getHeight() / 2;
        canvas.drawBitmap(bitmap, (float)(bleft + left), (float)(btop + top), paint);
        paint.setXfermode((Xfermode)null);
        canvas.restore();
        return outputBm;
    }

    public static Bitmap decodeBitmap(Context context, int resId) {
        return BitmapFactory.decodeResource(context.getResources(), resId);
    }

    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, int pixels) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        int color = -12434878;
        Paint paint = new Paint();
        Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        RectF rectF = new RectF(rect);
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(-12434878);
        canvas.drawRoundRect(rectF, (float)pixels, (float)pixels, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }

    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, float roundPx) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        Bitmap output = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        int color = -12434878;
        Paint paint = new Paint();
        Rect rect = new Rect(0, 0, w, h);
        RectF rectF = new RectF(rect);
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(-12434878);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }

    public Bitmap changeBitmapColor(Bitmap bitmap, int newColor) {
        if(bitmap == null) {
            return bitmap;
        } else {
            int bitmapW = bitmap.getWidth();
            int bitmapH = bitmap.getHeight();
            int[] pixels = new int[bitmapW * bitmapH];
            bitmap.getPixels(pixels, 0, bitmapW, 0, 0, bitmapW, bitmapH);
            newColor &= 16777215;

            for(int i = 0; i < pixels.length; ++i) {
                int currColor = pixels[i];
                if(currColor != 0) {
                    int alph = currColor & -16777216;
                    if(alph != 0) {
                        currColor = newColor | alph;
                        pixels[i] = currColor;
                    }
                }
            }

            bitmap = Bitmap.createBitmap(pixels, bitmapW, bitmapH, Bitmap.Config.ARGB_8888);
            return bitmap;
        }
    }
}
