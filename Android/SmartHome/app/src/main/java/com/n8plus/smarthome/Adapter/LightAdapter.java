package com.n8plus.smarthome.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.kyleduo.switchbutton.SwitchButton;
import com.n8plus.smarthome.Activity.HomeActivity;
import com.n8plus.smarthome.Model.Device;
import com.n8plus.smarthome.R;

import java.util.ArrayList;

/**
 * Created by Hiep_Nguyen on 3/1/2018.
 */

public class LightAdapter extends RecyclerView.Adapter<LightAdapter.ViewHolder> {

    ArrayList<Device> devices;

    Context context;

    public LightAdapter(ArrayList<Device> devices, Context context) {
        this.devices = devices;
        this.context = context;
    }

    @Override
    public LightAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.row_light, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final LightAdapter.ViewHolder holder, final int position) {
        final Device device = devices.get(position);
        System.out.println(device.get_id());
        if (device.isState()) {
            holder.imgLight.setImageResource(R.drawable.light_on);
        } else {
            holder.imgLight.setImageResource(R.drawable.light_off);
        }
        holder.txtNameLight.setText(device.getDeviceName());
        holder.swtState.setChecked(device.isState());

        holder.swtState.setTintColor(Color.parseColor("#00a0dc"));
        holder.swtState.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                device.setState(b);
                holder.imgLight.setImageResource(b ? R.drawable.light_on : R.drawable.light_off);
                HomeActivity.mSocket.emit("c2s-change", HomeActivity.deviceConvert.object2Json(device));
            }
        });

    }

    @Override
    public int getItemCount() {
        return devices.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgLight;
        TextView txtNameLight;
        SwitchButton swtState;

        public ViewHolder(View itemView) {
            super(itemView);
            imgLight = (ImageView) itemView.findViewById(R.id.imgLight);
            txtNameLight = (TextView) itemView.findViewById(R.id.txtNameLight);
            swtState = (SwitchButton) itemView.findViewById(R.id.swtState);
        }
    }
}
