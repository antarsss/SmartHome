package com.n8plus.smarthome.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.n8plus.smarthome.Model.Module;
import com.n8plus.smarthome.R;

import java.util.ArrayList;

public class ModuleAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Module> arrayList;
    private int layout;

    public ModuleAdapter(Context context, ArrayList<Module> arrayList, int layout) {
        this.context = context;
        this.arrayList = arrayList;
        this.layout = layout;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class ViewHolder {
        TextView txtModuleType, txtModulePin, txtModuleConnect;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);

            viewHolder = new ViewHolder();
            viewHolder.txtModuleConnect = (TextView) convertView.findViewById(R.id.txtModuleConnect);
            viewHolder.txtModuleType = (TextView) convertView.findViewById(R.id.txtModuleType);
            viewHolder.txtModulePin = (TextView) convertView.findViewById(R.id.txtModulePin);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.txtModuleType.setText(arrayList.get(position).getModuleType().name());
        viewHolder.txtModulePin.setText("Pin: " + arrayList.get(position).getPin());
        if (arrayList.get(position).isConnect()) {
            viewHolder.txtModuleConnect.setText("Connected");
            viewHolder.txtModuleConnect.setTextColor(Color.parseColor("#00a0dc"));
        }
        else {
            viewHolder.txtModuleConnect.setText("Disconnected");
            viewHolder.txtModuleConnect.setTextColor(Color.parseColor("#ffff4444"));
        }


        return convertView;
    }
}
