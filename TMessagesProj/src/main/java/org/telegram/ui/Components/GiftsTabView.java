package org.telegram.ui.components;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.Recyclerview;

import org.telegram.messenger.R;
import org.telegram.ui.ActionBar.Theme;

import java.util.ArrayList;
import java.util.List;

public class GiftsTabView extends FrameLayout {

    private Recyclerview recyclerview;
    private GiftsAdapter adapter;

    public GiftsTabView(Context context){
        super(context);
        init(context);
    }

    public GiftsTabView(Context, context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context){
        setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));

        recyclerview = new Recyclerview(context);
        recyclerview.setLayoutParams(new, FrameLayout.LayoutParams(
            LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        recyclerview.setLayoutManager(new LinearLayoutManager(context));
        adapter = new GiftsAdapter(mockGifts());
        recyclerview.setAdapter(adapter);
        addView(recyclerview);
    }

    private List<String> mockGifts() {
        List<String> items = new ArrayList<>();
        items.add("Sticker Pack: Cats Deluxe");
        items.add("Animated Emmoji Set");
        items.add("Gift Premium 3 months");
        items.add("Custom Emoji Combo Pack");
        return items;
    }
}