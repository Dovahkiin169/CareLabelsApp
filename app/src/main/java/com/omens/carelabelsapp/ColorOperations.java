package com.omens.carelabelsapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.StateListDrawable;
import android.util.StateSet;

import androidx.annotation.ColorInt;

public class ColorOperations {

    public StateListDrawable convertColorIntoBitmap(int pressedColor, int normalColor, Context Cont){
        StateListDrawable stateListDrawable= new StateListDrawable();
        stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, new BitmapDrawable(Cont.getResources(),ColorForDrawable(pressedColor)));
        stateListDrawable.addState(StateSet.WILD_CARD, new BitmapDrawable(Cont.getResources(),ColorForDrawable(normalColor)));

        return stateListDrawable;

    }
    public Bitmap ColorForDrawable(int color) {
        Rect rect = new Rect(0, 0, 1, 1);
        Bitmap image = Bitmap.createBitmap(rect.width(), rect.height(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(image);
        Paint paint = new Paint();
        paint.setColor(color);
        canvas.drawRect(rect, paint);
        return image;
    }

    @ColorInt
    public static int getContrastColor(@ColorInt int color) {
        // Counting the perceptive luminance - human eye favors green color...
        double a = 1 - (0.299 * Color.red(color) + 0.587 * Color.green(color) + 0.114 * Color.blue(color)) / 255;

        int d;
        if (a < 0.6) {
            d = 0; // bright colors - black font
        } else {
            d = 255; // dark colors - white font
        }

        return Color.rgb(d, d, d);
    }


    public static int manipulateColor(int color, float factor) {
        return Color.argb(Color.alpha(color),
                Math.min(Math.round(Color.red(color) * factor),255),
                Math.min(Math.round(Color.green(color) * factor),255),
                Math.min(Math.round(Color.blue(color) * factor),255));
    }

    public static int dpToPx(int dp, Context context) {
        float density = context.getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }
}