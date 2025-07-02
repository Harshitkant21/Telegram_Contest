// package org.telegram.ui.components;

// import android.content.Context;
// import android.graphics.Color;
// import android.util.AttributeSet;
// import android.view.Gravity;
// import android.view.ViewGroup;
// import android.widget.FrameLayout;
// import android.view.View;
// import android.widget.TextView;
// import androidx.recyclerview.widget.LinearLayoutManager;
// import androidx.recyclerview.widget.Recyclerview;
// import org.telegram.messenger.R;
// import org.telegram.ui.ActionBar.Theme;
// import java.util.ArrayList;
// import java.util.List;
// public class GiftsTabView extends FrameLayout {
//     private Recyclerview recyclerview;
//     private GiftsAdapter adapter;
//     public GiftsTabView(Context context){
//         super(context);
//         init(context);
//     }
//     public GiftsTabView(Context, context, AttributeSet attrs) {
//         super(context, attrs);
//         init(context);
//     }
//     private void init(Context context){
//         setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
//         recyclerview = new Recyclerview(context);
//         recyclerview.setLayoutParams(new, FrameLayout.LayoutParams(
//             LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
//         recyclerview.setLayoutManager(new LinearLayoutManager(context));
//         adapter = new GiftsAdapter(mockGifts());
//         recyclerview.setAdapter(adapter);
//         addView(recyclerview);
//     }
//     private List<String> mockGifts() {
//         List<String> items = new ArrayList<>();
//         items.add("Sticker Pack: Cats Deluxe");
//         items.add("Animated Emmoji Set");
//         items.add("Gift Premium 3 months");
//         items.add("Custom Emoji Combo Pack");
//         return items;
//     }
//     private static class GiftsAdapter extends Recyclerview.Adapter<GiftViewHolder> {
//         private final List<Strinh> giftList;
//         GiftsAdapter(List<String> giftList) {
//             this.giftList = giftList;
//         }
//         @Override
//         public GiftViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
//             TextView textView= new TextView(parent.getContext());
//             Recyclerview.LayoutParams lp = new Recyclerview.LayoutParams(
//                 ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//             textView.setLayoutParams(lp);
//             textView.setPadding(32, 48, 32, 48);
//             textView.setTextColor(color,BLACK);
//             textView.setTextSize(16);
//             return new GiftViewHolder(textView);
//         }
//         @Override
//         public void onBindViewHolder(GiftViewHolder holder, int position){
//             ((TextView) holder.itemView).setText(giftList.get(position));
//         }
//         @Override
//         public int getItemCount() {
//             return giftList.size();
//         }
//     }
//     private static class GiftViewHolder extends Recyclerview.ViewHolder {
//         public GiftViewHolder(View itemView){
//             super(itemView)
//         }
//     }
// }
package org.telegram.ui.components;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import  android.widget.FrameLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.telegram.messenger.R;
import org.telegram.ui.ActionBar.Theme;

import java.util.List;
import java.util.ArrayList;


public class GiftsTabView extends ScrollView {

    private LinearLayout giftsContainer;
    private RecyclerView recyclerview;
    private GiftsAdapter adapter;

    public GiftsTabView(Context context) {
            super(context);
            init(context);
    }

    public GiftsTabView(Context context, AttributeSet attrs) {
            super(context, attrs);
            init(context);
    }

    public GiftsTabView(Context context, AttributeSet attrs, int defStyleAttr){
            super(context, attrs, defStyleAttr);
            init(context);
    }

    private void init (Context context){
            LayoutInflater.from(context).inflate(R.layout.gift_tab_view, this, true);
            giftsContainer = findViewById(R.id.giftsContainer);

            setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));


            recyclerview = new RecyclerView(context);
            recyclerview.setLayoutParams(new FrameLayout.LayoutParams(
                    LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
            recyclerview.setLayoutManager(new LinearLayoutManager(context));
            List<String> giftItems = mockGifts();
            adapter = new GiftsAdapter(giftItems);
            recyclerview.setAdapter(adapter);
            addView(recyclerview);
//            loadMockGifts(context);
    }


//    private void loadMockGifts(Context context){
//        string[] giftItems = new String[] {
//            "sticker pack",
//            //more gift pack yet to add will do that later
//        };
//    }
    private List<String> mockGifts() {
        List<String> giftItems = new ArrayList<>();
        giftItems.add("sticker pack");
        // Add more gifts as needed
        return giftItems;
    }


    private static class GiftsAdapter extends RecyclerView.Adapter<GiftViewHolder> {
        private final List<String> giftList;

        GiftsAdapter(List<String> giftList) {
            this.giftList = giftList;
        }

        @Override
        public GiftViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
            TextView textView= new TextView(parent.getContext());
            RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            textView.setLayoutParams(lp);
            textView.setPadding(32, 48, 32, 48);
            textView.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
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

    private static class GiftViewHolder extends RecyclerView.ViewHolder {
        public GiftViewHolder(View itemView){
            super(itemView);
        }
    }

    private View createGiftItemView(Context context, String giftText) {
        TextView textView = new TextView(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        );
        textView.setLayoutParams(params);
        textView.setPadding(32, 48, 32, 48);
        textView.setTextSize(16);
        textView.setTextColor(Theme.key_windowBackgroundWhiteBlackText);
        textView.setText(giftText);
        textView.setBackgroundResource(R.drawable.list_selector_ex); // clickable ripple effect
        return textView;

    }
}
