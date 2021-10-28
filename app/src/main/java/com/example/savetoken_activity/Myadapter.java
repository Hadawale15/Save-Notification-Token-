package com.example.savetoken_activity;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class Myadapter extends BaseAdapter {
    Activity activity;
    List<Model> list;
    LayoutInflater inflater;

    public Myadapter(Activity activity, List<Model> list) {
        this.activity = activity;
        this.list = list;
        inflater=activity.getLayoutInflater();

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        view=inflater.inflate(R.layout.newlayout,null,false);
        TextView T1=view.findViewById(R.id.email_id);
        TextView T2=view.findViewById(R.id.token_id);

        Model model=list.get(position);
        T1.setText(model.getEmail());
        T2.setText(model.getToken());

        return view;
    }
}
