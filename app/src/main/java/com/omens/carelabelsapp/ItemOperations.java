package com.omens.carelabelsapp;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.TextView;
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


}
