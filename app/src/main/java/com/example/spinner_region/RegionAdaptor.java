package com.example.spinner_region;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class RegionAdaptor extends AppCompatActivity {
    private ArrayList<Region> regionArrayList;
    private Context context;

    public int getCount() {
        return this.regionArrayList.size();
    }


    public void addRegion(String code, String name, String rnum) {
        Region Ritem = new Region();

        Ritem.setCode(code);
        Ritem.setName(name);
        Ritem.setRnum(rnum);
        regionArrayList.add(Ritem);
    }


    public RegionAdaptor(Context context,ArrayList<Region> regionArrayList)
    {
        this.context=context;
        this.regionArrayList=regionArrayList;
    }



}
