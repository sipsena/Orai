package com.example.orai;

import android.app.Activity;
import android.text.Layout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;

//CustumWeatherList yra adapteris. Sujung duomenis is DB (DAO) su vaizdu (xml)
public class CustomWeatherList  extends BaseAdapter {

    private Activity context;
    private ArrayList<Weather> weathers;
    private PopupWindow popup;
    private WeatherDAO weatherDAO;

    public CustomWeatherList(){}

    public CustomWeatherList(Activity context, ArrayList<Weather> weathers, WeatherDAO weatherDAO) {
        this.context = context;
        this.weathers = weathers;
        this.weatherDAO = weatherDAO;
    }
public void setWeathers(ArrayList<Weather> weathers){
        this.weathers=weathers;
}

    //savo viduje turi visus GUI elementus (visas iraso vaizdas)
    public static class ViewHolder{
        TextView textViewId;
        TextView textViewCountry;
        TextView textViewDegrees;
        Button editButton;
        Button deleteButton;
    }

    @Override
    public int getCount() {
        return weathers.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    //Sio metodo viduje susiesime viewHolder klases kintamuosius su GUI esanciais elementais ir
    // uzpildysime duomenimis is DB
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row=convertView;
        LayoutInflater inflater=context.getLayoutInflater();
        ViewHolder holder;

        if(convertView==null){
            holder = new ViewHolder();
            row = inflater.inflate(R.layout.row_item,null,true);

            //susiejame koda su vaizdu
            holder.textViewId=row.findViewById(R.id.id);
            holder.textViewCountry=row.findViewById(R.id.country);
            holder.textViewDegrees=row.findViewById(R.id.degrees);
            holder.editButton=row.findViewById(R.id.edit_button);
            holder.deleteButton=row.findViewById(R.id.delete_button);

            //isaugo holder kartu su vaizdais
            row.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }

        //uzpildymas vaizdo irasais is DB
        holder.textViewId.setText("" + weathers.get(position).getId());
        holder.textViewCountry.setText(weathers.get(position).getCountryName());
        holder.textViewDegrees.setText("" + weathers.get(position).getDegrees());

        final int positionPopup=position;

        //aprasome Edit Button logika
        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editPopup(positionPopup);
            }
        });
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                weatherDAO.delete(weathers.get(positionPopup));
                //Kai istriname irasa, reikia atnaujinti sarasa
                //turime nurodyti (castinti) kokio listo norime
                weathers= (ArrayList<Weather>) weatherDAO.readAll();
                notifyDataSetChanged();
            }
        });


        return row;
    }

    private void editPopup(final int positionPopup){
        //popup window
    LayoutInflater layoutInflater=context.getLayoutInflater();
    View layout = layoutInflater.inflate(R.layout.edit_popup,
            (ViewGroup) context.findViewById(R.id.popup));
    popup = new PopupWindow(layout,600, 670 ,true);
    popup.showAtLocation(layout, Gravity.CENTER, 0, 0);

    //susiejamas kodas su vaizdu
        final EditText country=layout.findViewById(R.id.edit_country);
        final EditText degrees=layout.findViewById(R.id.edit_degrees);
        Button save = layout.findViewById(R.id.save_popup);
        country.setText(weathers.get(positionPopup).getCountryName());
        degrees.setText(""+ weathers.get(positionPopup).getDegrees());

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getcountry=country.getText().toString();
                String getdegrees=degrees.getText().toString();
                Weather weather=weathers.get(positionPopup);
                weather.setCountryName(getcountry);
                weather.setDegrees(Double.parseDouble(getdegrees));

                weatherDAO.update(weather);
                //atnaujiname vizda po redagavimo
                weathers=(ArrayList<Weather>) weatherDAO.readAll();
                notifyDataSetChanged();
                popup.dismiss();
            }
        });

    }

}
