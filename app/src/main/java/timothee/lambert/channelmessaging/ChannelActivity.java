package timothee.lambert.channelmessaging;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.HashMap;

/**
 * Created by lambetim on 30/01/2017.
 */
public class ChannelActivity extends AppCompatActivity implements View.OnClickListener, OnDownloadCompleteListener{
    private ListView lvMessages;
    private Button btnEnvoyer;
    private EditText txtMessage;
    private CallBackMessage msg;
    private Handler handler;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel);
        lvMessages = (ListView) findViewById(R.id.lvMessages);
        btnEnvoyer = (Button)findViewById(R.id.btnEnvoyer);
        txtMessage = (EditText)findViewById(R.id.txtMessage);
        btnEnvoyer.setOnClickListener(this);

        handler = new Handler();
        final Runnable run = new Runnable() {
            @Override
            public void run() {

                loadMessage();
                handler.postDelayed(this,1000);
            }

        };
        handler.postDelayed(run,1000);
    }

    public void loadMessage(){
        SharedPreferences settings = getSharedPreferences(LoginActivity.PREFS_NAME, 0);
        String accesstoken = settings.getString("accesstoken", "error");
        HashMap<String, String> connectInfo = new HashMap<>();
        connectInfo.put("accesstoken", accesstoken);

        connectInfo.put("channelid", Integer.toString(getIntent().getIntExtra("id",0)));
        Async Async = new Async(getApplicationContext(), connectInfo,"getmessages");
        Async.setOnDownloadCompleteListener(this);//ça bug quelque part par ici
        Async.execute();
    }

    public void onDownloadComplete(String result) {
        Gson gson = new Gson();
        msg = gson.fromJson(result, CallBackMessage.class);
        lvMessages.setAdapter(new MessageListViewAdapter(getApplicationContext(), R.layout.activity_channel ,R.layout.message_layout, msg.messages));
    }
    public void postMsg(){
        if(ConnexionInternet.isConnectedInternet(this)) {
            HashMap<String, String> connectInfo = new HashMap<>();
            SharedPreferences settings = getSharedPreferences(LoginActivity.PREFS_NAME, 0);
            String accessToken = settings.getString("accesstoken", "error");
            connectInfo.put("accesstoken", accessToken);
            connectInfo.put("channelid", Integer.toString(getIntent().getIntExtra("id", 0)));
            connectInfo.put("message", txtMessage.getText().toString());
            Async Async = new Async(getApplicationContext(), connectInfo, "sendmessage");
            Async.execute();
        }
        else
        {
            Toast.makeText(this, "Vous n'êtes pas connecté à internet", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {

        if(v.getId() == btnEnvoyer.getId())
        {
            postMsg();
        }
    }
}
