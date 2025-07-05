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
//             textView.setTextColor(color.BLACK);
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
import android.widget.ImageView;
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
    // private RecyclerView recyclerview;
    // private GiftsAdapter adapter;
    private Context context;

    public GiftsTabView(Context context) {
            super(context);
            init(context);
    }

    public GiftsTabView(Context context, android.util.AttributeSet attrs) {
            super(context, attrs);
            init(context);
    }

    public GiftsTabView(Context context, android.util.AttributeSet attrs, int defStyleAttr){
            super(context, attrs, defStyleAttr);
            init(context);
    }

    private void init (Context ctx){
        this.context = ctx;
        LayoutInflater.from(context).inflate(R.layout.gifts_tab_view, this , true);
        giftsContainer = findViewById(R.id.giftsContainer);
        setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));

        renderGifts();


            // recyclerview = new RecyclerView(context);
            // recyclerview.setLayoutParams(new FrameLayout.LayoutParams(
            //         LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
            // recyclerview.setLayoutManager(new LinearLayoutManager(context));
            // List<String> giftItems = mockGifts();
            // adapter = new GiftsAdapter(giftItems);
            // recyclerview.setAdapter(adapter);
            // addView(recyclerview);
//            loadMockGifts(context);
            
    }

    private void renderGifts(){
        giftsContainer.removeAllViews();

        //section1 
        addSectionTitle("Gifts you received");
        addRecyclerView(mockReceivedGifts());

        //section2
        addSectionTitle("gifts you sent");
        addRecyclerView(mockSentGifts());
    }


//    private void loadMockGifts(Context context){
//        string[] giftItems = new String[] {
//            "sticker pack",
//            //more gift pack yet to add will do that later
//        };
//    }
    // private List<String> mockGifts() {
    //     List<String> giftItems = new ArrayList<>();
    //     giftItems.add("sticker pack");
    //     // Add more gifts as needed
    //     return giftItems;
    // }

    private void addSectionTitle(String titleText) {
        TextView title = new TextView(conetext);
        title.setText(titleText);
        title.setTextSize(16);
        title.setPadding(24, 32, 24, 16);
        title.setTypeFace(null, android.graphics.TypeFace.BOLD);
        title.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
        giftsContainer.addView(title);
    }

    private void addRecyclerView(List<GiftItem> giftList){
        RecyclerView recycler = new RecyclerView(context);
        recycler.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        recycler.setNestedScrollingEnabled(false);
        recycler.setLayoutManager(new LinearLayoutManager(conetext));
        recycler.setAdapter(new GiftsAdapter(giftList));
        giftsContainer.addView(recycler);
    }

    private List<GiftItem> mockReceivedGifts() {
        List<GiftItem> items = new ArrayList<>();
        items.add(new GiftItem("Sticker pack", "Cats Deluxe",R.drawable.ic_gift));
        items.add(new GiftItem("premium 3 months", "Gifted premium",R.drawable.ic_premium_gift));
        return items;
    }

    private List<GiftItem> mockSentGifts() {
        List<GiftItem> items= new ArrayList<>();
        items.add(new GiftItem("Animated Emoji Set","Space Reactions", R.drawable.ic_emoji_gift));
        items.add(new GiftItem("Theme Pack","Dark Mode Galaxy", R.drawable.ic_theme_gift));
    }

    private static class GiftsAdapter extends RecyclerView.Adapter<GiftsAdapter.GiftViewHolder> {
        private final List<String> giftList;

        GiftsAdapter(List<String> giftList) {
            this.giftList = giftList;
        }

        @Override
        public GiftViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gift_item, parent, false);
            return new GiftViewHolder(view);
        }

        @Override
        public void onBindViewHolder(GiftViewHolder holder, int position){
            GiftItem gift= giftList.get(position);
            holder.title.setText(gift.title);
            holder.subtitle.setText(gift.subtitle);
            holder.icon.setImageResource(R.iconResId); // maybe an error in this line
        }


        // @Override
        // public GiftViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        //     TextView textView= new TextView(parent.getContext());
        //     RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(
        //         ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //     textView.setLayoutParams(lp);
        //     textView.setPadding(32, 48, 32, 48);
        //     textView.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
        //     textView.setTextSize(16);
        //     return new GiftViewHolder(textView);
        // }

    //     @Override
    //     public void onBindViewHolder(GiftViewHolder holder, int position){
    //         ((TextView) holder.itemView).setText(giftList.get(position));
    //     }

        @Override
        public int getItemCount() {
            return giftList.size();
        }

        static class GiftViewHolder extends RecyclerView.ViewHolder {
            TextView title, subtitle;
            ImageView icon;

            GiftViewHolder(View itemView){
                super(itemView);
                title = itemView.findViewById(R.id.giftTitle);
                subtitle = itemView.findViewById(R.id.giftSubtitle);
                icon = itemView.findViewById(R.id.giftIcon);
            }
        }
    }

    // private static class GiftViewHolder extends RecyclerView.ViewHolder {
    //     public GiftViewHolder(View itemView){
    //         super(itemView);
    //     }
    // }

    // private View createGiftItemView(Context context, String giftText) {
    //     TextView textView = new TextView(context);
    //     LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
    //         LinearLayout.LayoutParams.MATCH_PARENT,
    //         LinearLayout.LayoutParams.WRAP_CONTENT
    //     );
    //     textView.setLayoutParams(params);
    //     textView.setPadding(32, 48, 32, 48);
    //     textView.setTextSize(16);
    //     textView.setTextColor(Theme.key_windowBackgroundWhiteBlackText);
    //     textView.setText(giftText);
    //     textView.setBackgroundResource(R.drawable.list_selector_ex); // clickable ripple effect
    //     return textView;

    // }

    private static class GiftItem{
        final String title;
        final String subtitle;
        final int iconResId;

        GiftItem(String title, String subtitle, int iconResId){
            this.title = title;
            this.subtitle = subtitle;
            this.iconResId = iconResId;
        }
    }


}
