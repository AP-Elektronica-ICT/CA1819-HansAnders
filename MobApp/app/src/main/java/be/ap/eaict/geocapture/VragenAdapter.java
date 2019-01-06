package be.ap.eaict.geocapture;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;
import be.ap.eaict.geocapture.Model.Puzzel;



public class VragenAdapter extends ArrayAdapter<Puzzel>{
    public VragenAdapter(Context context, List<Puzzel> puzzels) {
        super(context, -1 , puzzels);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.vragen_list_item,null);

        TextView txtVraag = (TextView) view.findViewById(R.id.vraag);
        txtVraag.setText(getItem(position).vraag);

        return view;
    }
}
