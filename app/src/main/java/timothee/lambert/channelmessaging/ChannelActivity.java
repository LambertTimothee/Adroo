package timothee.lambert.channelmessaging;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.gson.Gson;

import java.util.HashMap;

/**
 * Created by lambetim on 30/01/2017.
 */
public class ChannelActivity extends AppCompatActivity implements OnDownloadCompleteListener{
    private ListView lvMessages;
    private Button btnEnvoyer;
    private EditText txtMessage;
    private CallBackMessage msg;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel);
        lvMessages = (ListView) findViewById(R.id.lvMessages);
        btnEnvoyer = (Button)findViewById(R.id.btnEnvoyer);
        txtMessage = (EditText)findViewById(R.id.txtMessage);
        loadMessage();
    }

    public void loadMessage(){
        SharedPreferences settings = getSharedPreferences(LoginActivity.PREFS_NAME, 0);
        String accesstoken = settings.getString("accesstoken", "error");
        HashMap<String, String> connectInfo = new HashMap<>();
        connectInfo.put("accesstoken", accesstoken);
        Log.d("Yolooooo",Integer.toString(getIntent().getIntExtra("id",0)));
        connectInfo.put("channelid", Integer.toString(getIntent().getIntExtra("id",0)));
        Async Async = new Async(getApplicationContext(), connectInfo,"getmessages");
        Async.setOnDownloadCompleteListener(this);
        Async.execute();
    }

    public void onDownloadComplete(String result) {
        Gson gson = new Gson();
        msg = gson.fromJson(result, CallBackMessage.class);
        Log.d("resulttttttttttt","fe"+result);
        lvMessages.setAdapter(new MessageListViewAdapter(getApplicationContext(), R.layout.activity_channel ,R.layout.message_layout, msg.messages));
    }

}
