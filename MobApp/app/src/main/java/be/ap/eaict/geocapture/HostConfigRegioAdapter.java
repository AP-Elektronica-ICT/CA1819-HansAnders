package be.ap.eaict.geocapture;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import be.ap.eaict.geocapture.Model.Regio;

public class HostConfigRegioAdapter extends ArrayAdapter<Regio> {
    public HostConfigRegioAdapter(Context context, List<Regio> regios) {
        super(context, -1 , regios);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.markers_list_item,null);

        TextView txtMarker = (TextView) view.findViewById(R.id.marker_text);
        txtMarker.setText(getItem(position).getNaam());
        return view;
    }
}
