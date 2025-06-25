package org.telegram.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.telegram.messenger.ImageLoader;
import org.telegram.messenger.R;
import org.telegram.messenger.UserConfig;
import org.telegram.tgnet.TLRPC;
import org.telegram.messenger.NotificationCenter;
import org.telegram.ui.ActionBar.Theme;
// import org.telegram.ui.components.GiftsTabView; // yet to create this file 

public class UserProfileActivity extends Activity {

    private ImageView profileAvatar;
    private TextView profileName;
    private TextView profileBio;
    private Button messageButton;
    private Button callButton;
    private Button giftButton;

    private TLRPC.User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setTheme(org.telegram.ui.ActionBar.Theme.getCurrentThemeResId());
        setContentView(R.layout.user_profile_layout);

        initViews();
        loadCurrentUser();
        bindUserData();
        setupListeners();

        Button toggleThemeButton = findViewById(R.id.toggleThemeButton);
        toggleThemeButton.setOnClickListener(v -> toggleTheme());


        // FrameLayout giftTbContainer = findViewById(R.id.giftTbContainer);
        // GiftsTabView giftsTabView = new GiftsTabView(this);
        // giftTabContainer.addView(giftsTabView);

    }

    private void initViews(){
        profileAvatar = findViewById(R.id.profileAvatar);
        profileName = findViewById(R.id.profileName);
        profileBio = findViewById(R.id.profileBio);
        messageButton = findViewById(R.id.messageButton);
        callButton = findViewById(R.id.callButton);
        giftButton = findViewById(R.id.giftButton);
    }

    private void loadCurrentUser() {
        long userId = UserConfig.getInstance(UserConfig.selectedAccount).getClientUserId();
        currentUser = org.telegram.messenger.MessagesController.getInstance(UserConfig.selectedAccount).getUser(userId);
        if (currentUser == null) {
            Toast.makeText(this,"Failed to load user", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void bindUserData() {

        profileName.setText(currentUser.first_name + " "+ currentUser.last_name);
//        profileBio.setText(currentUser.about != null ? currentUser.about : "No bio available");
        System.out.println("Current user: " + currentUser);
        if (currentUser.photo != null && currentUser.photo.photo_small != null){
            String photoUrl = currentUser.photo.photo_small.volume_id +"_" + currentUser.photo.photo_small.local_id;
//            ImageLoader.getInstance().setImage(
//                profileAvatar,
//                    "https://cdn.telegram.org/file/" + photoUrl,
//                    null,
//                    null
//            );
        } else {
            // profileAvatar.setImageResource(R.drawable.avatar_placeholder);
            profileAvatar.setImageResource(R.drawable.photo_rectangle_fill); // Placeholder image
        }
    }

    private void setupListeners() {
        messageButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, ChatActivity.class);
            intent.putExtra("user_id", currentUser.id);
            startActivity(intent);
        });

        callButton.setOnClickListener(v -> {
            Toast.makeText(this, "Calling" + currentUser.first_name, Toast.LENGTH_SHORT).show();
        });

//        giftButton.setOnClickListener(v -> {
//            Intent intent = new Intent(this,GiftSendActivity.class);
//            intent.putExtra("user_id", currentUser.id);
//            startActivity(intent);
//        });
    }

    private void toggleTheme() {
        boolean isCurrentThemeDay = Theme.isCurrentThemeDay();

        Theme.applyTheme(Theme.getActiveTheme(), !isCurrentThemeDay);
        // Theme.saveTheme(Theme.getActiveTheme(), !isCurrentThemeDay);

        NotificationCenter.getGlobalInstance().postNotificationName(
            NotificationCenter.needSetDayNightTheme,
            Theme.getActiveTheme(),
            !isCurrentThemeDay,
            null,-1
        );
        recreate();
    }
}

// UI binding 
// image loading 
// structure 
// themes setup 
// navigation 
// user object from app core
// added day night theme
// added the gifts tab view in this file only yet to create the other file directory TMessageproj/org/telegram/ui/components/GiftsTabView.java