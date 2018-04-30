package com.n8plus.smarthome.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.nkzawa.emitter.Emitter;
import com.google.gson.Gson;
import com.kyleduo.switchbutton.SwitchButton;
import com.n8plus.smarthome.Model.Device;
import com.n8plus.smarthome.Model.Enum.Type;
import com.n8plus.smarthome.Presenter.ControlLight.LightAdapterPresenter;
import com.n8plus.smarthome.R;
import com.n8plus.smarthome.View.ControlLight.ControlLightView;
import com.n8plus.smarthome.View.HomePage.HomeActivity;
import com.n8plus.smarthome.View.LoadScreen.StartViewActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Hiep_Nguyen on 3/1/2018.
 */

public class LightAdapter extends RecyclerView.Adapter<LightAdapter.ViewHolder> implements LightAdapterImpl {

    ArrayList<Device> devices;
    Context context;
    LightAdapter.ViewHolder holder;
    LightAdapterPresenter lightAdapterPresenter;

    public LightAdapter(ArrayList<Device> devices, Context context) {
        this.devices = devices;
        this.context = context;
        lightAdapterPresenter = new LightAdapterPresenter(this);
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
            public void onClick(final View view) {
                device.setState(holder.swtState.isChecked());
                StartViewActivity.mSocket.emit("c2s-change", HomeActivity.deviceConvert.object2Json(device));
                StartViewActivity.mSocket.once("s2c-change", new Emitter.Listener() {
                    @Override
                    public void call(final Object... args) {
                        ((AppCompatActivity) context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Log.v("Light", args[0].toString());
                                try {
                                    JSONObject jsonObject = new JSONObject(args[0].toString());
                                    boolean success = jsonObject.getBoolean("success");
                                    if (success) {
                                        JSONObject device1 = jsonObject.getJSONObject("device");
                                        Gson gson = new Gson();
                                        Device device2 = gson.fromJson(device1.toString(), Device.class);
                                        if (device2 != null) {
                                            if (device.equals(device2)) {
                                                ((SwitchButton) view).setChecked(device2.getStateByType(Type.LIGHT));
                                            }
                                        } else {
                                            ((SwitchButton) view).setChecked(current);
                                        }
                                    } else {
                                        ((SwitchButton) view).setChecked(current);
                                    }
                                } catch (JSONException e) {
                                }
                            }
                        });
                    }
                });
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
        return ((ControlLightView) context).count == ((ControlLightView) context).getCountAllLight();
    }

    public void emitAll() {
        for (final Device device : devices) {
            StartViewActivity.mSocket.emit("c2s-change", HomeActivity.deviceConvert.object2Json(device));
            StartViewActivity.mSocket.once("s2c-change", new Emitter.Listener() {
                @Override
                public void call(final Object... args) {
                    ((AppCompatActivity) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.v("Light", args[0].toString());
                            try {
                                JSONObject jsonObject = new JSONObject(args[0].toString());
                                boolean success = jsonObject.getBoolean("success");
                                if (success) {
                                    JSONObject device1 = jsonObject.getJSONObject("device");
                                    Gson gson = new Gson();
                                    Device device2 = gson.fromJson(device1.toString(), Device.class);
                                    if (device2 != null) {
                                        if (device.equals(device2)) {
                                            holder.swtState.setChecked(device2.getStateByType(Type.LIGHT));
                                        }
                                    }
                                } else {
                                 }
                            } catch (JSONException e) {
                            }
                        }
                    });
                }
            });
        }
    }

    @Override
    public void setSwitchButton(Device device, boolean checked) {
        for (int i = 0; i < devices.size(); i++) {
            String id = (String) holder.swtState.getTag(i);
            if (id.equals(device.get_id())) {
                holder.swtState.setChecked(checked);
                holder.imgLight.setImageResource(checked ? R.drawable.light_on : R.drawable.light_off);
            }
        }

    }

    @Override
    public void setSwitchButtonFailure() {
        Toast.makeText(context, "Turn on/off light failure!", Toast.LENGTH_LONG).show();
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
