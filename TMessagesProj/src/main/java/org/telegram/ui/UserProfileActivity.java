package org.telegram.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import org.telegram.messenger.NotificationCenter;
import org.telegram.ui.ActionBar.Theme;

public class UserProfileActivity extends Activity{
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        Button toggleThemeButton = findViewById(R.id.toggleThemeButton);
        toggleThemeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isDay = Theme.isCurrentThemeDay();
                NotificationCenter.getGlobalInstance().postNotificationName(
                    NotificationCenter.needSetDayNightTheme,
                    Theme.getActiveTheme(),
                    !isDay, 
                    null, -1
                );
            }
        });
    }
}
