package com.github.pedruino.pushnotificationdemo;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.pedruino.pushnotificationdemo.api.ApiClient;
import com.github.pedruino.pushnotificationdemo.api.responses.FirebasePushResponse;
import com.github.pedruino.pushnotificationdemo.api.requests.MessageRequest;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TEST_TOPIC_ID = "pedruino-news";
    private TextView myTokenTextView;
    private EditText toTokenEditText;
    private EditText titleEditText;
    private EditText messageEditText;
    private Button shareMyTokenButton;
    private Button sendNotificationButton;
    private ApiClient apiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.myTokenTextView = findViewById(R.id.activity_main_my_token_text_view);
        this.toTokenEditText = findViewById(R.id.activity_main_to_token_edit_text);
        this.titleEditText = findViewById(R.id.activity_main_title_edit_text);
        this.messageEditText = findViewById(R.id.activity_main_message_edit_text);
        this.sendNotificationButton = findViewById(R.id.activity_main_send_button);
        this.shareMyTokenButton = findViewById(R.id.activity_main_share_token_button);

        this.shareMyTokenButton.setOnClickListener(this);
        this.sendNotificationButton.setOnClickListener(this);

        this.apiClient = new ApiClient();

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            return;
                        }

                        String token = task.getResult().getToken();
                        myTokenTextView.setText(token);
                        Log.d("FCM_TOKEN", token);

                    }
                });

        FirebaseMessaging.getInstance().subscribeToTopic(TEST_TOPIC_ID)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = getString(R.string.msg_fmt_subscribed, TEST_TOPIC_ID);
                        if (!task.isSuccessful()) {
                            msg = getString(R.string.msg_subscribe_failed);
                        }
                        Log.d("Subscription attempt", msg);
                        //Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onClick(View v) {
        if (v == this.shareMyTokenButton) {
            shareToken();
        } else if (v == this.sendNotificationButton) {
            sendNotification();
        }
    }

    private void sendNotification() {
        String to = this.toTokenEditText.getText().toString();
        String title = this.titleEditText.getText().toString();
        String message = this.messageEditText.getText().toString();

        apiClient.getFirebaseCallService().sendPush(new MessageRequest(to, title, message))
                .enqueue(new Callback<FirebasePushResponse>() {
                    @Override
                    public void onResponse(Call<FirebasePushResponse> call, Response<FirebasePushResponse> response) {
                        if (response.code() == 200) {
                            if (response.body().getError() == null) {
                                Toast.makeText(MainActivity.this, getString(R.string.msg_message_successful), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(MainActivity.this, getString(R.string.msg_message_send_failure), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<FirebasePushResponse> call, Throwable t) {
                        Toast.makeText(MainActivity.this, getString(R.string.msg_message_not_sent), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void shareToken() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, this.myTokenTextView.getText().toString());
        startActivity(Intent.createChooser(shareIntent, getString(R.string.share_with)));
    }
}
