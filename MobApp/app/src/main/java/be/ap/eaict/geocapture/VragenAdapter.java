package be.ap.eaict.geocapture;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import be.ap.eaict.geocapture.Model.Puzzel;



public class VragenAdapter extends ArrayAdapter<Puzzel>{
    public VragenAdapter(Context context, List<Puzzel> puzzels) {
        super(context, -1 , puzzels);
    }

    private HashMap<String, String> textValues = new HashMap<String, String>();

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View view = convertView;
        boolean convertViewWasNull = false;
        if(view == null){
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.vragen_list_item,null);
            convertViewWasNull = true;



        }



        TextView txtVraag = (TextView) view.findViewById(R.id.vraag);
        txtVraag.setText(getItem(position).vraag);
        EditText antwoord = (EditText)  view.findViewById(R.id.antwoord);

        if(convertViewWasNull ){
            //be aware that you shouldn't do this for each call on getView, just once by listItem when convertView is null
            antwoord.addTextChangedListener(new GenericTextWatcher(antwoord));
        }

        antwoord.setTag("theFirstEditTextAtPos:"+position);


        return view;
    }

    private class GenericTextWatcher implements TextWatcher {

        private View view;
        private GenericTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

        public void afterTextChanged(Editable editable) {
            String text = editable.toString();
            //save the value for the given tag :
            VragenAdapter.this.textValues.put((String) view.getTag(), editable.toString());
        }
    }

    public String getValueFromFirstEditText(int position){
        //here you need to recreate the id for the first editText
        String result = textValues.get("theFirstEditTextAtPos:"+position);
        if(result ==null)
            result = "default value";

        return result;
    }
}
