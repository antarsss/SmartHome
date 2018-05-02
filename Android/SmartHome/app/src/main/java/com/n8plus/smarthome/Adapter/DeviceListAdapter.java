package com.n8plus.smarthome.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.n8plus.smarthome.Model.DeviceViewType;
import com.n8plus.smarthome.R;

import java.util.List;

/**
 * Created by Hiep_Nguyen on 3/3/2018.
 */

public class DeviceListAdapter extends BaseAdapter {
    private Context context;
    private List<DeviceViewType> deviceViewTypeList;
    private int layout;

    public DeviceListAdapter(Context context, List<DeviceViewType> deviceViewTypeList, int layout) {
        this.context = context;
        this.deviceViewTypeList = deviceViewTypeList;
        this.layout = layout;
    }


    @Override
    public int getCount() {
        return deviceViewTypeList.size();
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
        TextView txtNameType, txtQuantity;
        ImageView imgDeviceType;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;

        if (view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);

            viewHolder = new ViewHolder();

            viewHolder.txtNameType = (TextView) view.findViewById(R.id.txtNameType);
            viewHolder.txtQuantity = (TextView) view.findViewById(R.id.txtQuantity);
            viewHolder.imgDeviceType = (ImageView) view.findViewById(R.id.imgDeviceType);

            view.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) view.getTag();
        }

        DeviceViewType deviceViewType = deviceViewTypeList.get(i);

        viewHolder.imgDeviceType.setImageResource(deviceViewType.getImage());
        viewHolder.txtNameType.setText(deviceViewType.getNameType());
        viewHolder.txtQuantity.setText(""+ deviceViewType.getDeviceList().size());

        return view;
    }
}
