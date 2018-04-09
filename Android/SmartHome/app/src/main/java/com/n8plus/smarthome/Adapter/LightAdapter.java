package com.n8plus.smarthome.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.nkzawa.emitter.Emitter;
import com.kyleduo.switchbutton.SwitchButton;
import com.n8plus.smarthome.Activity.HomeActivity;
import com.n8plus.smarthome.Model.Device;
import com.n8plus.smarthome.R;
import com.n8plus.smarthome.Utils.common.SocketSingeton;
import com.n8plus.smarthome.View.ControlLight.ControlLightView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.logging.Handler;

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

    LightAdapter.ViewHolder holder;

    @Override
    public void onBindViewHolder(final LightAdapter.ViewHolder holder, final int position) {
        this.holder = holder;
        final Device device = devices.get(position);

        if (device.isState()) {
            holder.imgLight.setImageResource(R.drawable.light_on);
        } else {
            holder.imgLight.setImageResource(R.drawable.light_off);
        }
        holder.txtNameLight.setText(device.getDeviceName());
        holder.swtState.setChecked(device.isState());

        holder.swtState.setTintColor(Color.parseColor("#00a0dc"));
        final boolean current = device.isState();
        holder.swtState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                device.setState(holder.swtState.isChecked());
                HomeActivity.mSocket.emit("c2s-change", HomeActivity.deviceConvert.object2Json(device));
                Log.i("EMIT", HomeActivity.deviceConvert.object2Json(device).toString());
                holder.imgLight.setImageResource(holder.swtState.isChecked() ? R.drawable.light_on : R.drawable.light_off);
                SwitchButton swtAll = ((ControlLightView) context).findViewById(R.id.swbAllLight);
                swtAll.setChecked(isAllItemSelected());
            }
        });

    }

    public boolean isAllItemSelected() {
        int count = 0;
        for (Device device : devices) {
            if (device.isState()) {
                count++;
            }
        }
        return count == getItemCount();
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

    public void setCheckedAll() {
        for (Device device : devices) {
            HomeActivity.mSocket.emit("c2s-change", HomeActivity.deviceConvert.object2Json(device));
        }
    }

}
