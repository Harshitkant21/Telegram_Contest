package org.telegram.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.telegram.messenger.ImageLoader;
import org.telegram.messenger.R;
import org.telegram.tgnet.TLRPC;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.UserConfig;

public class GroupProfileActivity extends Activity {

    private ImageView groupAvatar;  
    private TextView groupName; 
    private TextView groupDescription;
    private TextView groupMemberCount;
    private TextView groupOnlineCount;
    private Button joinButton;
    private Button inviteButton;
    private Button muteButton;

    private TLRPC.Chat currentGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setTheme(org.telegram.ui.ActionBar.Theme.getCurrentThemeResId());
        setContentView(R.layout.group_profile_layout);

        initViews();
        loadMockGroup();
        bindGroupData();
        setupListeners();
    }

    private void initViews() {
        groupAvatar = findViewById(R.id.groupAvatar);
        groupName = findViewById(R.id.groupName);
        groupDescription = findViewById(R.id.groupDescription);
        groupMemberCount = findViewById(R.id.groupMemberCount);
        groupOnlineCount = findViewById(R.id.groupOnlineCount);
        joinButton = findViewById(R.id.joinButton);
        inviteButton = findViewById(R.id.inviteButton);
        muteButton = findViewById(R.id.muteButton);
    }

    private void loadMockGroup() {
       currentGroup = new TLRPC.TL_chat();
       currentGroup.title = "ABCD";
       currentGroup.participants_count = 1540;
//       currentGroup.online_count = 245;

       // this class is for demo purposes only if any error occurs comment out the Loadmockgroup
   }

    private void bindGroupData() {
        groupName.setText(currentGroup.title);
        // groupDescription.setText(currentGroup.about);
        groupMemberCount.setText("Members: "+ currentGroup.participants_count);
        // groupOnlineCount.setText("Online: "+ currentGroup);

        TLRPC.ChatFull chatFull = org.telegram.messenger.MessagesController.getInstance(org.telegram.messenger.UserConfig.selectedAccount).getChatFull(currentGroup.id);

        if (chatFull != null && chatFull.about != null) {
            groupDescription.setText(chatFull.about);
        }else {
            groupDescription.setText("No description");
        }

        groupOnlineCount.setText("345"); //mock fallback no fuction available for fetching online count in live 

        //groupAvatar.setImageResource()
        // ImageLoader.getInstance().setImage(groupAvatar, avatarUrl, null, null);
    }

    private void setupListeners() {
        joinButton.setOnClickListener(v -> Toast.makeText(this,"Join clicked",Toast.LENGTH_SHORT).show());

        inviteButton.setOnClickListener(v -> Toast.makeText(this,"Invite clicked",Toast.LENGTH_SHORT).show());

        muteButton.setOnClickListener(v -> Toast.makeText(this,"Mute clicked",Toast.LENGTH_SHORT).show());
    }
}