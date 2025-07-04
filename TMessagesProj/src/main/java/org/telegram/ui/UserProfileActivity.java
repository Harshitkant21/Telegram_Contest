// package org.telegram.ui;

// import android.app.Activity;
// import android.os.Bundle;
// import android.view.View;
// import android.widget.Button;
// import org.telegram.messenger.NotificationCenter;
// import org.telegram.ui.ActionBar.Theme;

// public class UserProfileActivity extends Activity{

//     @Override
//     protected void onCreate(Bundle savedInstanceState) {
//         super.onCreate(savedInstanceState);
//         setContentView(R.layout.activity_user_profile);

//         Button toggleThemeButton = findViewById(R.id.toggleThemeButton);
//         toggleThemeButton.setOnClickListener(new View.OnClickListener() {
//             @Override
//             public void onClick(View v) {
//                 boolean isDay = Theme.isCurrentThemeDay();
//                 NotificationCenter.getGlobalInstance().postNotificationName(
//                     NotificationCenter.needSetDayNightTheme,
//                     Theme.getActiveTheme(),
//                     !isDay,
//                     null, -1
//                 );
//             }
//         });
//     }
// }
package org.telegram.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.FrameLayout;

import org.telegram.messenger.ImageLoader;
import org.telegram.messenger.R;
import org.telegram.messenger.UserConfig;
import org.telegram.tgnet.TLRPC;
import org.telegram.messenger.NotificationCenter;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.components.GiftsTabView; // yet to create this file
import org.telegram.ui.MessagesController;

public class UserProfileActivity extends Activity {

    private ImageView profileAvatar;
    private TextView profileName;
    private TextView userPhone;
    private TextView userUsername;
    private TextView userStatus;
    private TextView profileBio;

    private Button messageButton;
    private Button callButton;
    private Button giftButton;
    private Button muteButton;

    private TLRPC.User currentUser;
    private boolean isMuted= false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setTheme(org.telegram.ui.ActionBar.Theme.getCurrentThemeResId());
        setContentView(R.layout.user_profile_layout);

        initViews();
        loadCurrentUser();
        bindUserData();
        setupListeners();

        Button toggleThemeButton = findViewById(R.id.toggleThemeButton);
        toggleThemeButton.setOnClickListener(v -> toggleTheme());

        FrameLayout giftTabContainer = findViewById(R.id.giftTbContainer);
        GiftsTabView giftsTabView = new GiftsTabView(this);
        giftTabContainer.addView(giftsTabView);
    }

    private void initViews(){
        profileAvatar = findViewById(R.id.profileAvatar);
        profileName = findViewById(R.id.profileName);
        profileBio = findViewById(R.id.profileBio);
        userPhone = findViewById(R.id.profilePhone);
        userUsername = findViewById(R.id.profileUsername);
        userStatus = findViewById(R.id.profileStatus);
        messageButton = findViewById(R.id.messageButton);
        callButton = findViewById(R.id.callButton);
        giftButton = findViewById(R.id.giftButton);
        muteButton = findViewById(R.id.muteButton);
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
//         profileName.setText(currentUser.first_name + " "+ currentUser.last_name);
// //        profileBio.setText(currentUser.about != null ? currentUser.about : "No bio available");

//         if (currentUser.photo != null && currentUser.photo.photo_small != null){
//             String photoUrl = currentUser.photo.photo_small.volume_id +"_" + currentUser.photo.photo_small.local_id;
//             ImageLoader.getInstance().setImage(
//                 profileAvatar,
//                 "https://cdn.telegram.org/file/" + photoUrl,
//                 null,
//                 null
//             );
//         } else {
// //            profileAvatar.setImageResource(R.drawable.avatar_placeholder);
//             profileAvatar.setImageResource(R.drawable.photo_rectangle_fill);
//         }
        String fullname = (currentUser.first_name != null ? currentUser.first_name : "") +
                (currentUser.last_name != null ? " "+ currentUser.last_name: "");
        profileName.setText(fullname.trim().isEmpty() ? "Telegram User": fullname.trim());

        if (currentUser.about != null && !currentUser.about.isEmpty()){
            profileBio.setText(currentUser.about);
        }else{
            profileBio.setText("No Bio Availabble");
        }

        if (currentUser.phone != null && !currentUser.phone.isEmpty()){
            userPhone.setText("+" + currentUser.phone);
        }else {
            userPhone.setText("phone number hidden");
        }

        if (currentUser.username !=null && !currentUser.username.isEmpty()){
            userUsername.setText("@" + currentUser.username);
        }else {
            userUsername.setText("No username");
        }

        userStatus.setText(currentUser.status != null ? "Online": "Offline");

        if(currentUser.photo != null && currentUser.photo.photo_small != null){
            String photoUrl = currentUser.photo.photo_small.volume_id + "_" + currentUser.photo.photo_small.local_id;
            ImageLoader.getInstance().setImage(
                profileAvatar,
                "https://cdn.telegram.org/file" + photoUrl,
                null,
                null
            );
        }else {
            profileAvatar.setImageResource(R.drawable.avatar_placeholder);
        }

        updateMuteButton();
            
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

        giftButton.setOnClickListener(v -> {
            Intent intent = new Intent(this,GiftSendActivity.class);
            intent.putExtra("user_id", currentUser.id);
            startActivity(intent);
        });

        muteButton.setOnClickListener(v -> {
            isMuted = !isMuted;
            updateMuteButton();
            Toast.makeText(this, (isMuted ? "Muted" : "Unmuted")+ profileName.getText(), Toast.LENGTH_SHORT).show();
            NotificationCenter.getGlobalInstance().postNotificationName(NotificationCenter.updateInterfaces);
        });
    }

    private void updateMuteButton() {
        muteButton.setText(isMuted ? "Unmute" : "Mute");
    }

    private void toggleTheme() {
        boolean isCurrentlyDay = Theme.isCurrentThemeDay();

        Theme.applyTheme(Theme.getActiveTheme(), !isCurrentlyDay);
//        Theme.saveTheme(Theme.getActiveTheme(), !isCurrentlyDay);

        NotificationCenter.getGlobalInstance().postNotificationName(
            NotificationCenter.needSetDayNightTheme,
            Theme.getActiveTheme(),
            !Theme.isCurrentThemeDay(),
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