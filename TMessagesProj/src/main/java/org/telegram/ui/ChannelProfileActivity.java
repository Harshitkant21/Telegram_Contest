package org.telegram.ui;
import android.app.Activity;
import android.os.Bundle;
import android.View.view;
import android.widget.Button;
import android.widget.imageView;
import android.widget.TextView;
import android.widget.Toast;

import org.telegram.messenger.ImageLoader;
import org.telegram.messenger.R;
import org.telegram.tgnet.TLRPC;

public class ChannelProfileActivity extends Activity{

    private ImageView channelAvater;
    private TextView channelName;
    private TextView channelDescription;
    private TextView channelSubcriberCount;
    private Button joinButton;
    private Button muteButton;

    private TLRPC.chat currentChannel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(org.telegram.ui.ActionBar.Theme.getCurrentThemeResId());
        setContentView(R.layout.channel_profile_layout);

        initViews();
        loadMockChannel();
        bindChannelData();
        setupListeners();
    }

    private void initViews(){
        channelAvatar = findViewById(R.id.channelAvatar);
        channelName = findViewById(R.id.channelName);
        channelDescription = findViewById(R.id.Description);
        channelSubcriberCount = findViewById(R.id.channelSubcriberCount);
        joinButton = findViewById(R.id.joinButton);
        muteButton = findViewById(R.id.muteButton);
    }

    private void loadMockChannel() {
        currentChannel = new TLRPC.TL_channel();
        currentChannel.title = "Telegram news";
        currentChannel.participants_count= 523000;
        currentChannel.about = "Official updates and announcments"; // later add it 
        // for demo purpose, just to run without error , skipping real avatar loading
    }

    private void bindChannelData(){
        channelName.setText(currentChannel.title);
        channelDescription.setText(currentChannel.about);
        channelSubcriberCount.setText("Subscribers: "+ currentChannel.participants_count);

        channelAvatar.setImageResource(R.drawable.channel_avatar_placeholder);
        //ImageLoader.getInstance().setImage(channelAvatar, avatarUrl, null, null);
    }

    private void setupListeners() {
        joinButton.setOnClickListener(v -> Toast.makeText(this, "join channel", Toast.LENGTH_SHORT).show());

        muteButton.setOnClickListener(v -> Toast.makeText(this,"Mute Notification", Toast.LENGTH_SHORT).show());
    }
}