package be.ap.eaict.geocapture;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import be.ap.eaict.geocapture.Model.Locatie;
import be.ap.eaict.geocapture.Model.Regio;

public class HostConfigLocatiesAdapter extends ArrayAdapter<Locatie> {
    public HostConfigLocatiesAdapter(Context context, List<Locatie> locaties) {
        super(context, -1 , locaties);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.markers_list_item,null);

        TextView txtMarker = (TextView) view.findViewById(R.id.marker_text);
        txtMarker.setText(getItem(position).getLocatienaam());

        LinearLayout background = (LinearLayout) view.findViewById(R.id.marker_background);
        if (getItem(position).used)
            background.setBackgroundColor(Color.WHITE);
        else
            background.setBackgroundColor(Color.rgb(255, 200,200));

        return view;
    }
}