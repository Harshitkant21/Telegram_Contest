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

import org.telegram.messenger.ImageLoader;
import org.telegram.messenger.R;
import org.telegram.messenger.UserConfig;
import org.telegram.tgnet.TLRPC;
import org.telegram.messenger.NotificationCenter;
import org.telegram.ui.ActionBar.Theme;

public class UserProfileActivity extends Activity {

    private ImageView profileAvatar;
    private TextView profileName;
    private Button messageButton;
    private Button callButton;
    private Button giftButton;

    private TLRPC.User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(org.telegram.ui.ActionBar.Theme.getCurrentThemeResId());
        setContentView(R.layout.User_profile_layout)

        initViews();
        loadCurrentUser();
        bindUserData();
        setupListeners();

        Button toggleThemeButton = findViewById(R.id.toggleThemeButton);
        toggleThemeButton.setOnClickListener(v -> toggleTheme())
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
        int userId = UserConfig.getInstance(UserConfig.selectedAccount).getClientUserId();
        currentUser = org.telegram.messenger.MessageController.getInstance(UserConfig.selectedAccount).getUser(userId);
        if (currentUser == null) {
            Toast.makeText(this,"Failed to load user", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void bindUserData() {
        profileName.setText(currentUser.first_name + " "+ currentUser.last_name);
        profileBio.setText(currentUser.about != null ? currentUser.about : "No bio available");

        if (currentUser.photo != null && currentUser.photo.photo_small != null){
            String photoUrl = currentUser.photo.photo_small.volume_id +"_" + currentUser.photo.photo_small.local_id;
            ImageLoader.getInstance().setImage(
                profileAvatar,
                "https://cdn.telegram.org/file/" + photoUrl,
                null,
                null
            );
        } else {
            profileAvatar.setImageResource(R.drawable.avatar_placeholder);
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

        giftButton.setOnClickListener(v -> {
            Intent intent = new Intent(this,GiftSendActivity.class);
            intent.putExtra("user_id", currentUser.id);
            startActivity(intent);
        });
    }

    private void toggleTheme() {
        boolean isCurrentThemeDay = Theme.isCurrentThemeDay();

        Theme.applyTheme(Theme.getActiveTheme(), !isCurrentlyDay);
        Theme.saveTheme(Theme.getActiveTheme(), !isCurrentlyDay);

        NotificationCenter.getGlobalInstance().postNotificationName(
            NotificationCenter.needSetDayNightTheme,
            Theme.getActiveTheme(),
            !isCurrentlyDay,
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
