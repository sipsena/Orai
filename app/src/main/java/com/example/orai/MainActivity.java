package com.example.orai;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Weather> weathers;
    private WeatherDAO weatherDAO;
    Button add;
    PopupWindow popup;
    ListView listView;
    CustomWeatherList customWeatherList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        weatherDAO = new WeatherDAO(getApplicationContext());
        listView = findViewById(R.id.list);
        add = findViewById(R.id.create_button);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPopup();
            }
        });

        weathers = (ArrayList<Weather>) weatherDAO.readAll();
        //sukuriamas adapteris
        customWeatherList = new CustomWeatherList(MainActivity.this,weathers,weatherDAO);
        listView.setAdapter(customWeatherList);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, "You selected: "
                +weathers.get(position).getCountryName()+" as country.",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void addPopup() {
        LayoutInflater layoutInflater = MainActivity.this.getLayoutInflater();
        View layout = layoutInflater.inflate(R.layout.edit_popup,
                (ViewGroup) MainActivity.this.findViewById(R.id.popup));
        popup = new PopupWindow(layout, 600, 670, true);
        popup.showAtLocation(layout, Gravity.CENTER, 0, 0);

        final EditText country = layout.findViewById(R.id.edit_country);
        final EditText degrees = layout.findViewById(R.id.edit_degrees);
        Button save = layout.findViewById(R.id.save_popup);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getcountry=country.getText().toString();
                String getdegrees=degrees.getText().toString();
                Weather weather= new Weather(getcountry, Double.parseDouble(getdegrees));
                weatherDAO.create(weather);

                if(customWeatherList==null){
                    customWeatherList=new CustomWeatherList((Activity) getApplicationContext(),
                            weathers, weatherDAO);
                    listView.setAdapter(customWeatherList);
                }
                ArrayList<Weather> weathers1 = (ArrayList<Weather>) weatherDAO.readAll();
                customWeatherList.setWeathers(weathers1);

                ((BaseAdapter)listView.getAdapter()).notifyDataSetChanged();
                popup.dismiss();
            }
        });
    }
}