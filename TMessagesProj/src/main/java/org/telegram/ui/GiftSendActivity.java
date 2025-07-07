package org.telegram.ui;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import org.telegram.messenger.R;
    public class GiftSendActivity extends Activity {
        @Override
        protected void onCreate(Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            setContentView(R.layout.gift_send_layout);

            Toast.makeText(this, "Gift send Screen Loaded", Toast.LENGTH_SHORT).show();
        }
    }