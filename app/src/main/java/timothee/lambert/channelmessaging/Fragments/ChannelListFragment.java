package timothee.lambert.channelmessaging.Fragments;



import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.HashMap;

import timothee.lambert.channelmessaging.Async;
import timothee.lambert.channelmessaging.CallBackChan;
import timothee.lambert.channelmessaging.ChannelActivity;
import timothee.lambert.channelmessaging.ChannelListViewAdapter;
import timothee.lambert.channelmessaging.ConnexionInternet;
import timothee.lambert.channelmessaging.LoginActivity;
import timothee.lambert.channelmessaging.OnDownloadCompleteListener;
import timothee.lambert.channelmessaging.R;

/**
 * Created by lambetim on 27/01/2017.
 */
public class ChannelListFragment extends Fragment implements OnDownloadCompleteListener, AdapterView.OnItemClickListener {
    private ListView lvChannel;
    public static final String PREFS_NAME = "MyPrefsFile";
    private CallBackChan ch;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstaceState) {
        View v = inflater.inflate(R.layout.channel_list_fragment, container);
        lvChannel = (ListView)v.findViewById((R.id.lvChannel));
        return v;
    }
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        lvChannel.setOnItemClickListener(this);
        if(ConnexionInternet.isConnectedInternet(getActivity()))
        {
        HashMap<String, String> connectInfo = new HashMap<>();
        SharedPreferences settings = getActivity().getSharedPreferences(LoginActivity.PREFS_NAME,0);
        String accesstoken = settings.getString("accesstoken", "error");

        connectInfo.put("accesstoken",accesstoken);
        Async Async = new Async(getActivity(),connectInfo,"getchannels");
        Async.setOnDownloadCompleteListener(this);


            Async.execute();
        }
        else
        {
            Toast.makeText(getActivity().getApplicationContext(), "Erreur de connexion", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onDownloadComplete(String result) {
        Gson gson = new Gson();
        ch = gson.fromJson(result, CallBackChan.class);
        lvChannel.setAdapter(new ChannelListViewAdapter(getActivity(), R.layout.activity_channellist ,R.layout.row_layout, ch.channels));
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent myIntent = new Intent(getActivity(),ChannelActivity.class);
        myIntent.putExtra("id", ch.channels.get(position).channelID);
        startActivity(myIntent);
    }
}
