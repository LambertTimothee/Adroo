package timothee.lambert.channelmessaging;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.HashMap;

/**
 * Created by lambetim on 27/01/2017.
 */
public class ChannelListActivity extends AppCompatActivity implements OnDownloadCompleteListener, AdapterView.OnItemClickListener {
    private ListView lvChannel;
    public static final String PREFS_NAME = "MyPrefsFile";
    private CallBackChan ch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_channellist);

        SharedPreferences settings = getSharedPreferences(LoginActivity.PREFS_NAME, 0);
        String accesstoken = settings.getString("accesstoken", "error");
        if(ConnexionInternet.isConnectedInternet(this)) {
            HashMap<String, String> connectInfo = new HashMap<>();
            lvChannel = (ListView) findViewById(R.id.lvChannel);
            connectInfo.put("accesstoken", accesstoken);
            Async Async = new Async(getApplicationContext(), connectInfo, "getchannels");
            Async.setOnDownloadCompleteListener(this);
            Async.execute();
        }
        else
        {
            Toast.makeText(this, "Vous n'êtes pas connecté à internet", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onDownloadComplete(String result) {
        Gson gson = new Gson();
        ch = gson.fromJson(result, CallBackChan.class);
        lvChannel.setAdapter(new ChannelListViewAdapter(getApplicationContext(), R.layout.activity_channellist ,R.layout.row_layout, ch.channels));
        lvChannel.setOnItemClickListener(this);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent myIntent = new Intent(getApplicationContext(),ChannelActivity.class);
        myIntent.putExtra("id", ch.channels.get(position).channelID);
        startActivity(myIntent);
    }
}
