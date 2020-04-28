package com.trulloy.bfunx.ui.covid19;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.trulloy.bfunx.R;

public class ShowCountListAdapter extends ArrayAdapter {

    private final Activity context;
    private final String[] confirmed;
    private final String[] recovered;
    private final String[] deceased;

    public ShowCountListAdapter(Activity context, String[] nameArrayParam, String[] infoArrayParam, String[] imageIDArrayParam){
        super(context, R.layout.covid19_listview_row , nameArrayParam);

        this.context=context;
        this.confirmed = imageIDArrayParam;
        this.recovered = nameArrayParam;
        this.deceased = infoArrayParam;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.covid19_listview_row, null,true);

        //this code gets references to objects in the listview_row.xml file
        TextView confirmedLvTxtView = (TextView) rowView.findViewById(R.id.confirmedLvTxtView);
        TextView recoveredLvTxtView = (TextView) rowView.findViewById(R.id.recoveredLvTxtView);
        TextView deceasedLvTxtView = (TextView) rowView.findViewById(R.id.deceasedLvTxtView);

        //this code sets the values of the objects to values from the arrays
        confirmedLvTxtView.setText(confirmed[position]);
        recoveredLvTxtView.setText(recovered[position]);
        deceasedLvTxtView.setText(deceased[position]);

        return rowView;
    };
}
