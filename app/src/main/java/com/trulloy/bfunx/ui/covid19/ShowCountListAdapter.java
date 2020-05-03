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

import java.util.List;

public class ShowCountListAdapter extends ArrayAdapter {

    private final Activity context;
    private final String[] cities;
    private final String[] confirmed;
    private final String[] active;
    private final String[] recovered;
    private final String[] deceased;

    public ShowCountListAdapter(Activity context, String[] citiesArrayParam, String[] confirmed, String[] active, String[] recovered, String[] dead){
        super(context, R.layout.covid19_listview_row , citiesArrayParam);

        this.context=context;
        this.cities = citiesArrayParam;
        this.confirmed = confirmed;
        this.active = active;
        this.recovered = recovered;
        this.deceased = dead;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.covid19_listview_row, null,true);

        //this code gets references to objects in the listview_row.xml file
        TextView cityNameTxtView = (TextView) rowView.findViewById(R.id.cityNameTxtView);
        TextView confirmedLvTxtView = (TextView) rowView.findViewById(R.id.confirmedLvTxtView);
        TextView activeLvTxtView = (TextView) rowView.findViewById(R.id.activeLvTxtView);
        TextView recoveredLvTxtView = (TextView) rowView.findViewById(R.id.recoveredLvTxtView);
        TextView deceasedLvTxtView = (TextView) rowView.findViewById(R.id.deceasedLvTxtView);

        //this code sets the values of the objects to values from the arrays
        cityNameTxtView.setText(cities[position]);
        confirmedLvTxtView.setText(confirmed[position]);
        activeLvTxtView.setText(active[position]);
        recoveredLvTxtView.setText(recovered[position]);
        deceasedLvTxtView.setText(deceased[position]);

        return rowView;
    };
}
