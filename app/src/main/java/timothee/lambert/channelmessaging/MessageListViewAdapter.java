package timothee.lambert.channelmessaging;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by lambetim on 06/02/2017.
 */
public class MessageListViewAdapter extends ArrayAdapter<Message> {

    TextView txtMsg;
    TextView txtDate;
    //private final Channel values;
    public MessageListViewAdapter(Context context, int resource, int textViewResourceId, List<Message> objects) {
        super(context, resource, textViewResourceId, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View msgView = inflater.inflate(R.layout.message_layout, parent, false);
        txtMsg = (TextView) msgView.findViewById(R.id.txtMsg);
        txtMsg.setText(getItem(position).username+" : "+getItem(position).message);
        txtDate = (TextView) msgView.findViewById(R.id.txtDate);
        txtDate.setText(getItem(position).date.toString());
        return msgView;
    }

}
