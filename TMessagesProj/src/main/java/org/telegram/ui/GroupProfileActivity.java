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
        setTheme(org.telegram.ui.ActionBar.Theme.getCurrentThemeResId());
        setContentView(R.layout.group_profile_layout);

        initViews();
        loadMockGroup();
        bindGroupData();
        setupListeners();
    }

    private void initViews() {
        groupAvatar = findViewById(R.id.groupAvatar);
        groupName = findViewById(R.id.groupName);
        groupDescription = findViewById()
    }
}