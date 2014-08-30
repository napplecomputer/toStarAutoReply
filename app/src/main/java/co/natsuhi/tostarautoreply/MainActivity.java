package co.natsuhi.tostarautoreply;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import co.natsuhi.tostarautoreply.utils.NotificationListenerServiceUtil;


public class MainActivity extends Activity {

    private TextView mTitleTextView;
    private TextView mMessageTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTitleTextView = (TextView) findViewById(R.id.textTitle);
        mMessageTextView = (TextView) findViewById(R.id.textMessage);
        findViewById(R.id.message_area).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = NotificationListenerServiceUtil.createStartNotificationSettingIntent();
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (NotificationListenerServiceUtil.isEnabled(this)) {
            mTitleTextView.setText(R.string.enabled_title);
            mMessageTextView.setText(getString(R.string.enabled_message,getString(R.string.app_name)));
        } else {
            mTitleTextView.setText(R.string.not_enabled_title);
            mMessageTextView.setText(getString(R.string.not_enabled_message,getString(R.string.app_name)));
        }

    }
}
