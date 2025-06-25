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

    private static class GiftsAdapter extends Recyclerview.Adapter<GiftViewHolder> {
        private final List<Strinh> giftList;

        GiftsAdapter(List<String> giftList) {
            this.giftList = giftList;
        }

        @Override
        public GiftViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
            TextView textView= new TextView(parent.getContext());
            Recyclerview.LayoutParams lp = new Recyclerview.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            textView.setLayoutParams(lp);
            textView.setPadding(32, 48, 32, 48);
            textView.setTextColor(color,BLACK);
            textView.setTextSize(16);
            return new GiftViewHolder(textView);
        }

        @Override
        public void onBindViewHolder(GiftViewHolder holder, int position){
            ((TextView) holder.itemView).setText(giftList.get(position));
        }

        @Override
        public int getItemCount() {
            return giftList.size();
        }
    }

    private static class GiftViewHolder extends Recyclerview.ViewHolder {
        public GiftViewHolder(View itemView){
            super(itemView)
        }
    }
}