
package timothee.lambert.channelmessaging.Fragments;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.HashMap;

import timothee.lambert.channelmessaging.Async;
import timothee.lambert.channelmessaging.CallBackMessage;
import timothee.lambert.channelmessaging.LoginActivity;
import timothee.lambert.channelmessaging.Message;
import timothee.lambert.channelmessaging.MessageListViewAdapter;
import timothee.lambert.channelmessaging.OnDownloadCompleteListener;
import timothee.lambert.channelmessaging.R;

/**
 * Created by lambetim on 17/03/2017.
**/
abstract class MessageFragment extends Fragment implements OnDownloadCompleteListener, View.OnClickListener {

    private ListView lvMessages;
    private Button btnEnvoyer;
    private EditText txtMessage;
    private CallBackMessage msg;
    private Handler handler;
    public int id;
    public static final String PREFS_NAME = "MyPrefsFile";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.channel_message_fragment, container);
        lvMessages = (ListView) v.findViewById(R.id.lvMessages);
        btnEnvoyer = (Button) v.findViewById(R.id.btnEnvoyer);
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        btnEnvoyer.setOnClickListener(this);

        Runnable r = new Runnable() {
            public void run() {

                HashMap<String, String> connectInfo = new HashMap<>();

                SharedPreferences settings = getActivity().getSharedPreferences(PREFS_NAME, 0);
                connectInfo.put("accesstoken", settings.getString("accesstoken",""));
                connectInfo.put("channelid", String.valueOf(id));

                Async async = new Async(getActivity(), connectInfo,"http://www.raphaelbischof.fr/messaging/?function=getmessages");
                async.setOnDownloadCompleteListener(MessageFragment.this);
                async.execute();
                handler.postDelayed(this, 1000);
            }
        };

        handler.postDelayed(r, 1000);

    }


    public void onDownloadComplete(String result, int requestCode) {
        if (getActivity() != null) {
            Gson gson = new Gson();
            if (requestCode == 2) {
                Message messages = gson.fromJson(result, Message.class);
                lvMessages.setAdapter(new MessageListViewAdapter(getActivity(), R.layout.activity_channel, R.layout.message_layout, msg.messages));
            }
        }
    }

    @Override
    public void onClick(View v) {

        if(v.getId() == btnEnvoyer.getId())
        {
            postMsg();
        }
    }
    public void postMsg(){
        HashMap<String, String> connectInfo = new HashMap<>();
        SharedPreferences settings = getActivity().getSharedPreferences(LoginActivity.PREFS_NAME,0);
        String accessToken = settings.getString("accesstoken", "error");
        connectInfo.put("accesstoken",accessToken);
        connectInfo.put("channelid",Integer.toString(getActivity().getIntent().getIntExtra("id",0)));
        connectInfo.put("message",txtMessage.getText().toString());
        Async Async = new Async(getActivity(),connectInfo,"sendmessage");
        Async.execute();
    }

}