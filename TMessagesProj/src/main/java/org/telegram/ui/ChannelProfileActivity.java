package org.telegram.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.telegram.messenger.ImageLoader;
import org.telegram.messenger.ImageReceiver;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.R;
import org.telegram.messenger.UserConfig;
import org.telegram.tgnet.TLRPC;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.messenger.MessagesController;

public class ChannelProfileActivity extends Activity{

    private ImageView channelAvatar;
    private TextView channelName;
    private TextView channelDescription;
    private TextView channelSubscriberCount;
    private Button joinButton;
    private Button muteButton;
    private Button inviteButton;
    private ImageReceiver avatarImageReceiver;

    private TLRPC.TL_channel currentChannel;
    private boolean isMuted= false;
    private boolean isMember= false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setTheme(org.telegram.ui.ActionBar.Theme.getCurrentThemeResId());
        setContentView(R.layout.channel_profile_layout);

        initViews();
//        loadMockChannel();
        loadChannelData(); 
        bindChannelData();
        setupListeners();
    }

    private void initViews(){
        channelAvatar = findViewById(R.id.channelAvatar);
        channelName = findViewById(R.id.channelName);
        channelDescription = findViewById(R.id.channelDescription);
        channelSubscriberCount = findViewById(R.id.channelSubscriberCount);
        joinButton = findViewById(R.id.joinButton);
        muteButton = findViewById(R.id.muteButton);
        inviteButton = findViewById(R.id.inviteButton);

        // avatarImageReceiver = new ImageReceiver(this);
        //avatarImageReceiver.setImage(channelAvatar);
    }

    // private void loadMockChannel() {
    //     currentChannel = new TLRPC.TL_channel();
    //     currentChannel.title = "Telegram news";
    //     currentChannel.participants_count= 523000;
    //     currentChannel.about = "Official updates and announcments"; // later add it 
    //     // for demo purpose, just to run without error , skipping real avatar loading
    // }

    private void loadChannelData(){
        currentChannel = new TLRPC.TL_channel();
        currentChannel.title = "Telegram";
        // currentChannel.about = "Official page";
        currentChannel.participants_count = 123456;

        currentChannel.photo= new TLRPC.TL_chatPhoto();
        currentChannel.photo.photo_small = new TLRPC.TL_fileLocationToBeDeprecated();
        currentChannel.photo.photo_small.volume_id= 123456789;
        currentChannel.photo.photo_small.local_id= 123456789;

        isMember = false;
        isMuted = false;
    }

    private void bindChannelData(){
        channelName.setText(currentChannel.title);
        // channelDescription.setText(currentChannel.about);
        TLRPC.ChatFull channelFull= MessagesController.getInstance(UserConfig.selectedAccount).getChatFull(currentChannel.id);
        if (channelFull != null && channelFull.about != null && !channelFull.about.isEmpty()){
            channelDescription.setText(channelFull.about);
        }else{
            channelDescription.setText("No bio available");
        }
        channelSubscriberCount.setText("Subscribers: "+ currentChannel.participants_count);

//         if (currentChannel.photo != null && currentChannel.photo.photo_small != null){
//             String photoUrl = "https://upload.wikimedia.org/wikipedia/commons/a/a7/React-icon.svg" //mock image
//             //ImageLoader.getInstance().setImage(channelAvatar,"https://cdn.telegram.org/file"+photoUrl);
//             Drawable placeholder = Theme.getThemedDrawable(this, R.drawable.photo_rectangle_fill, Theme.key_avatar_background2Blue);
//             avatarImageReceiver.setImage(photoUrl, null, placeholder);
//         } else{
// //            channelAvatar.setImageResource(R.drawable.channel_avatar_placeholder);
//             // // channelAvatar.setImageResource(R.drawable.photo_rectangle_fill);
//             // channelAvatar.setImageDrawable(getResources().getDrawable(R.drawable.photo_rectangle_fill));
//             // ImageLoader.getInstance().setImage(channelAvatar,"https://cdn.telegram.org/file/"+ photoUrl, null,
//             // Theme.getThemedDrawable(this,R.drawable.photo_rectangle_fill,Theme.key_avatar_background2Blue));
//             channelAvatar.setImageDrawable(Theme.getThemedDrawable(this, R.drawable.photo_rectangle_fill, Theme.key_avatar_background2Blue));
//         }
//        else{
////            channelAvatar.setImageResource(R.drawable.channel_avatar_placeholder);
//            // channelAvatar.setImageResource(R.drawable.photo_rectangle_fill);
//            channelAvatar.setImageDrawable(Theme.getThemedDrawable(this,R.drawable.photo_rectangle_fill, Theme.key_avatar_background2Blue));
//        }


        updateJoinButton();
        updateMuteButton();
    }

    private void setupListeners() {
        // joinButton.setOnClickListener(v -> Toast.makeText(this, "join channel", Toast.LENGTH_SHORT).show());

        // muteButton.setOnClickListener(v -> Toast.makeText(this,"Mute Notification", Toast.LENGTH_SHORT).show());

        joinButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                handleJoinLeave();
            }
        });

        muteButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                toggleMute();
            }
        });

//        inviteButton.setOnClickListener(new View.OnClickListener(){
//            Toast.makeText(ChannelProfileActivity.this,"Invite sent!",Toast.LENGTH_SHORT).show();
//        });
    }

    private void handleJoinLeave() {
        if(isMember){
            isMember = false;
            currentChannel.participants_count -= 1;
            Toast.makeText(this,"Left channel: "+ currentChannel.title, Toast.LENGTH_SHORT).show();
        }else {
            isMember= true;
            currentChannel.participants_count += 1;
            Toast.makeText(this,"Joined channel: " + currentChannel.title, Toast.LENGTH_SHORT).show();
        }

        updateJoinButton();
        bindChannelData();
        NotificationCenter.getGlobalInstance().postNotificationName(NotificationCenter.updateInterfaces);
    }

    private void toggleMute() {
        isMuted = !isMuted;
        String message = isMuted ? "Notifications muted for": "Notifications unmuted for";
        Toast.makeText(this, message + currentChannel.title, Toast.LENGTH_SHORT).show();
        updateMuteButton();
    }

    private void updateJoinButton() {
        if(isMember) {
            joinButton.setText("Leave");
        }else {
            joinButton.setText("Join");
        }
    }

    private void updateMuteButton() {
        if(isMuted) {
            muteButton.setText("Unmute");
        }else {
            muteButton.setText("Mute");
        }
    }
}