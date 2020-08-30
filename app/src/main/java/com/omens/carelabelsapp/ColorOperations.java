package com.omens.carelabelsapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.util.StateSet;
import android.view.View;
import android.widget.ImageView;

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
        // Counting the perceptive luminance
        if (1 - (0.299 * Color.red(color) + 0.587 * Color.green(color) + 0.114 * Color.blue(color)) / 255 < 0.67)
            return Color.rgb(0, 0, 0); // bright colors - black font
        else
            return Color.rgb(255, 255, 255); // dark colors - white font
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

    public static void determinateColorOfIcons(String imageName, int color, ImageView imageView,Context context){
        if(color == Color.rgb(255, 255, 255))//White Icons
            imageView.setImageResource(context.getResources().getIdentifier(imageName + "_white", "drawable", context.getPackageName()));
        else//Black Icons
            imageView.setImageResource(context.getResources().getIdentifier(imageName, "drawable", context.getPackageName()));
    }

    public static void setCareLabelColor(View view, Integer color, boolean Stroke, boolean ifBackgroundIsWhite) {
        StateListDrawable gradientDrawable = (StateListDrawable) view.getBackground();
        DrawableContainer.DrawableContainerState drawableContainerState = (DrawableContainer.DrawableContainerState) gradientDrawable.getConstantState();
        assert drawableContainerState != null;
        Drawable[] children = drawableContainerState.getChildren();
        GradientDrawable unselectedItem = (GradientDrawable) children[1];
        unselectedItem.setColor(color);
        if(Stroke) {
            if( color  ==  Color.rgb(255, 255, 255) && color!=-1)
                unselectedItem.setStroke(dpToPx(3,view.getContext()), manipulateColor(color,1.2f));
            else
                unselectedItem.setStroke(dpToPx(3,view.getContext()), manipulateColor(color,0.9f));
        }
        else if(ifBackgroundIsWhite) {
            if(color == R.color.white||
                    color == R.color.antiqueWhiteColor||
                    color == R.color.oldLaceColor||
                    color == R.color.ivoryColor||
                    color == R.color.seashellColor||
                    color == R.color.ghostWhiteColor||
                    color == R.color.snowColor||
                    color == R.color.linenColor)
                unselectedItem.setColor(manipulateColor(view.getContext().getResources().getColor(color), (float) 0.9));
            else
                unselectedItem.setColor(view.getContext().getResources().getColor(color));
        }
    }
}
