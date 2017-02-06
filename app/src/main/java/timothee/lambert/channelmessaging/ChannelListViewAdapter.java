package timothee.lambert.channelmessaging;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by lambetim on 27/01/2017.
 */

public class ChannelListViewAdapter extends ArrayAdapter<Channel>{

    TextView txtName;
    TextView txtUsersConnected;
    //private final Channel values;
    public ChannelListViewAdapter(Context context, int resource, int textViewResourceId, List<Channel> objects) {
        super(context, resource, textViewResourceId, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.row_layout, parent, false);
        txtName = (TextView) rowView.findViewById(R.id.name);
        txtName.setText(getItem(position).name);
        txtUsersConnected = (TextView) rowView.findViewById(R.id.connectedusers);
        txtUsersConnected.setText("Nombre d'utilisateurs connectés : " + getItem(position).connectedusers);
        return rowView;
    }
}
