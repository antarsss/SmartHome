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
import com.n8plus.smarthome.Model.Device;
import com.n8plus.smarthome.Model.Enum.ModuleType;
import com.n8plus.smarthome.Presenter.HomePresenter.LightPresenter.LightList.LightListPresenter;
import com.n8plus.smarthome.R;
import com.n8plus.smarthome.View.HomePage.LightPage.LightListView;

import java.util.ArrayList;

/**
 * Created by Hiep_Nguyen on 3/1/2018.
 */

public class LightListAdapter extends RecyclerView.Adapter<LightListAdapter.ViewHolder> {

    ArrayList<Device> devices;
    Context context;
    LightListAdapter.ViewHolder holder;
    LightListPresenter lightListPresenter;

    public LightListAdapter(ArrayList<Device> devices, Context context, LightListPresenter lightListPresenter) {
        this.devices = devices;
        this.context = context;
        this.lightListPresenter = lightListPresenter;
    }

    @Override
    public LightListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.row_light, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final LightListAdapter.ViewHolder holder, final int position) {
        this.holder = holder;
        final Device device = devices.get(position);
        holder.imgLight.setImageResource(device.getStateByType(ModuleType.LIGHT) ? R.drawable.light_on : R.drawable.light_off);
        holder.txtNameLight.setText(device.getDeviceName());
        holder.swtState.setChecked(device.getStateByType(ModuleType.LIGHT));
        holder.swtState.setTintColor(Color.parseColor("#00a0dc"));

        final boolean current = holder.swtState.isChecked();
        holder.swtState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                device.setState(holder.swtState.isChecked());
                holder.swtState.setChecked(current);
                lightListPresenter.emitEmitterDevice(device);
            }
        });
        holder.swtState.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    ((LightListView) context).count++;
                } else {
                    if (((LightListView) context).count > 0) {
                        ((LightListView) context).count--;
                    }
                }
                ((LightListView) context).setCheckAll();
            }
        });
    }

    @Override
    public int getItemCount() {
        return devices.size();
    }

    public void emitAll() {
        for (Device device : devices) {
            lightListPresenter.emitEmitterDevice(device);
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
