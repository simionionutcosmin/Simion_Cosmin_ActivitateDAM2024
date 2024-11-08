package com.example.seminar4;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.seminar4.Insula;

import java.util.List;

public class AdapterInsula extends BaseAdapter {
    private List<Insula> imagini = null;
    private Context ctx;
    private int resurseLayout;

    public AdapterInsula(List<Insula> insule, Context ctx, int resurseLayout) {
        this.imagini = insule;
        this.ctx = ctx;
        this.resurseLayout = resurseLayout;
    }

    @Override
    public int getCount() {
        return imagini.size();
    }

    @Override
    public Object getItem(int i) {
        return imagini.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(ctx);
        View v = inflater.inflate(resurseLayout, viewGroup, false);

        TextView numeTV = v.findViewById(R.id.numeTV);
        TextView suprafataTV = v.findViewById(R.id.suprafataTV);
        TextView localizareTV = v.findViewById(R.id.localizareTV);
        CheckBox locuitaCB = v.findViewById(R.id.locuitaCB);

        Insula insula = (Insula) getItem(i);

        numeTV.setText(insula.getNume());
        suprafataTV.setText(String.valueOf(insula.getSuprafata()));
        localizareTV.setText(insula.getLocalizare());
        locuitaCB.setChecked(insula.isLocuita());

        return v;
    }
}