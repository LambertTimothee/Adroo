package timothee.lambert.channelmessaging;


import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, OnDownloadCompleteListener {

    private Button btValider;
    private TextView txtId;
    private TextView txtMdp;
    private EditText edId;
    private EditText edMdp;
    public static final String PREFS_NAME = "MyPrefsFile";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btValider = (Button) findViewById(R.id.btnValidate);
        btValider.setOnClickListener(this);
        edId = (EditText) findViewById(R.id.edId);
        edMdp = (EditText) findViewById(R.id.edMdp);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == btValider.getId())
        {
            if(ConnexionInternet.isConnectedInternet(this))
            {
                HashMap<String, String> connectInfo = new HashMap<>();
                connectInfo.put("username", edId.getText().toString());
                connectInfo.put("password", edMdp.getText().toString());
                Async Async = new Async(getApplicationContext(), connectInfo,"connect");
                Async.setOnDownloadCompleteListener(this);
                Async.execute();
            }
            else
            {
                Toast.makeText(this, "Vous n'êtes pas connecté à internet", Toast.LENGTH_SHORT).show();
            }
        }

    }

    @Override
    public void onDownloadComplete(String result) {

        Gson gson = new Gson();


        CallbackConnect r = gson.fromJson(result, CallbackConnect.class);
        if(r.code==200){
            Toast.makeText(this, "Vous êtes connecté ! ", Toast.LENGTH_SHORT).show();
            SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.putString("accesstoken", r.accesstoken);
            // Commit the edits!
            editor.commit();

            Intent myIntent = new Intent(getApplicationContext(),ChannelListActivity.class);
            startActivity(myIntent);


        }
        else{
            Toast.makeText(this, "Erreur de connexion", Toast.LENGTH_SHORT).show();
        }

    }



}

