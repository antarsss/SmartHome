package com.n8plus.smarthome.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kyleduo.switchbutton.SwitchButton;
import com.n8plus.smarthome.Model.Device;
import com.n8plus.smarthome.Model.Enum.Type;
import com.n8plus.smarthome.R;
import com.n8plus.smarthome.View.ControlLight.ControlLightView;
import com.n8plus.smarthome.View.HomePage.HomeActivity;
import com.n8plus.smarthome.View.Login.LoginActivity;

import java.util.ArrayList;

/**
 * Created by Hiep_Nguyen on 3/1/2018.
 */

public class LightAdapter extends RecyclerView.Adapter<LightAdapter.ViewHolder> {

    ArrayList<Device> devices;
    Context context;
    LightAdapter.ViewHolder holder;

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
        this.holder = holder;
        final Device device = devices.get(position);

        if (device.getStateByType(Type.LIGHT)) {
            holder.imgLight.setImageResource(R.drawable.light_on);
        } else {
            holder.imgLight.setImageResource(R.drawable.light_off);
        }
        holder.txtNameLight.setText(device.getDeviceName());
        holder.swtState.setChecked(device.getStateByType(Type.LIGHT));

        holder.swtState.setTintColor(Color.parseColor("#00a0dc"));
        final boolean current = device.getStateByType(Type.LIGHT);
        holder.swtState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                device.setState(holder.swtState.isChecked());
                LoginActivity.mSocket.emit("c2s-change", HomeActivity.deviceConvert.object2Json(device));
                Log.i("EMIT", HomeActivity.deviceConvert.object2Json(device).toString());
                holder.imgLight.setImageResource(holder.swtState.isChecked() ? R.drawable.light_on : R.drawable.light_off);

                ((ControlLightView) context).setCheckAll(isAllItemSelected(device));
            }
        });

    }

    @Override
    public int getItemCount() {
        return devices.size();
    }


    public boolean isAllItemSelected(Device device) {
        if (device.getStateByType(Type.LIGHT)) {
            ((ControlLightView) context).count++;

        } else {
            if (((ControlLightView) context).count > 0) {
                ((ControlLightView) context).count--;
            }
        }
        System.out.println("count: " + ((ControlLightView) context).count + " | " + ((ControlLightView) context).getCountAllLight());
        return ((ControlLightView) context).count == ((ControlLightView) context).getCountAllLight();
    }

    public void emitAll() {
        for (Device device : devices) {
            LoginActivity.mSocket.emit("c2s-change", HomeActivity.deviceConvert.object2Json(device));
        }
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
