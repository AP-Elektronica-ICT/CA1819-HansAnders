package be.ap.eaict.geocapture;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import java.util.List;

public class MarkerAdapter extends ArrayAdapter<String> {
    public MarkerAdapter(Context context, List<String> markers) {super(context,-1, markers);}

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.markers_list_item,null);

        TextView txtMarker = (TextView) view.findViewById(R.id.marker_text);
        txtMarker.setText(getItem(position));

        return view;
    }
}
