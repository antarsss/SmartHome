package com.n8plus.smarthome.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.n8plus.smarthome.Model.Device;
import com.n8plus.smarthome.Model.Enum.DeviceType;
import com.n8plus.smarthome.R;

import java.util.List;

/**
 * Created by Hiep_Nguyen on 2/2/2018.
 */

public class DeviceAdapter extends BaseAdapter {

    private Context context;
    private List<Device> deviceList;
    private int layout;

    public DeviceAdapter(Context context, List<Device> deviceList, int layout) {
        this.context = context;
        this.deviceList = deviceList;
        this.layout = layout;
    }

    @Override
    public int getCount() {
        return deviceList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    private class ViewHolder{
        TextView txtName, txtState, txtPosition;
        ImageView imgIcon;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

       ViewHolder viewHolder;

        if (view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);

            viewHolder = new ViewHolder();

            viewHolder.txtName = (TextView) view.findViewById(R.id.txtName);
            viewHolder.txtState = (TextView) view.findViewById(R.id.txtState);
            viewHolder.txtPosition = (TextView) view.findViewById(R.id.txtPosition);
            viewHolder.imgIcon = (ImageView) view.findViewById(R.id.imgIcon);

            view.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) view.getTag();
        }

        // Set image
        Device device = deviceList.get(i);
        if (device.getDeviceType()== DeviceType.LIGHT){
            viewHolder.imgIcon.setImageResource(R.drawable.idea);
        }
        else if (device.getDeviceType()== DeviceType.DOOR){
            if (device.getDescription().contains("Door")){
                viewHolder.imgIcon.setImageResource(R.drawable.close_door);
            }
            else {
                viewHolder.imgIcon.setImageResource(R.drawable.window);
            }
        }
        else {
            viewHolder.imgIcon.setImageResource(R.drawable.cctv);
        }

        viewHolder.txtName.setText(device.getDeviceName());

        if (device.isConnect()){
            viewHolder.txtState.setText("Connected");
            viewHolder.txtState.setTextColor(Color.parseColor("#00a0dc"));
        }
        else {
            viewHolder.txtState.setText("Disconnected");
            viewHolder.txtState.setTextColor(Color.parseColor("#ffff4444"));
        }

        viewHolder.txtPosition.setText(device.getPosition().name());

        return view;
    }
}
