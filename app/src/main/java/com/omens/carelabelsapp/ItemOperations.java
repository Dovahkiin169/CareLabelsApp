package com.omens.carelabelsapp;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.common.base.CharMatcher;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ItemOperations {
    public void showProgress(final boolean show, final Context con, final Activity act, View ProgressView) {
        act.runOnUiThread(() -> {

            int shortAnimTime = con.getResources().getInteger(android.R.integer.config_shortAnimTime);


            ProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            ProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    ProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        });
    }

    //Set Value
    public void SetInvalid(TextView textView, String ErrorText) {
        textView.setText("");
        textView.setError("Invalid "+ ErrorText+", choose from list");
    }
    //HashMap get value by key
    public <T, E> T getKeyByValue(Map<T, E> map, E value) {
        for (Map.Entry<T, E> entry : map.entrySet())
            if (Objects.equals(value, entry.getValue()))
                return entry.getKey();
        return null;
    }

    public HashMap<String, ImageButton> findViewByIdAndSetListener(String RawData, char separator, View.OnClickListener ClickListener, View view) {
        String string;
        HashMap<String, ImageButton> ButtonArray = new HashMap<>();
        int CharMatchers = CharMatcher.is(separator).countIn(RawData)+1;
        for(int i=0; i<CharMatchers; i++) {
            if(RawData.contains(",")) {
                string = RawData.substring( 0, RawData.indexOf(","));
                ButtonArray.put(string, (ImageButton) view.findViewById(view.getContext().getResources().getIdentifier(string, "id", view.getContext().getPackageName())));
                RawData = RawData.substring(RawData.indexOf(",")+1);
                Objects.requireNonNull(ButtonArray.get(string)).setOnClickListener(ClickListener);
            }
            else if(!RawData.contains(",")) {
                ButtonArray.put(RawData, (ImageButton) view.findViewById(view.getContext().getResources().getIdentifier(RawData, "id", view.getContext().getPackageName())));
                Objects.requireNonNull(ButtonArray.get(RawData)).setOnClickListener(ClickListener);
            }
        }
        return ButtonArray;
    }
}
