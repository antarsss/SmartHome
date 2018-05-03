package com.n8plus.smarthome.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.n8plus.smarthome.Model.Device;
import com.n8plus.smarthome.Model.Enum.ModuleType;
import com.n8plus.smarthome.R;
import com.n8plus.smarthome.View.HomePage.CameraPage.CameraDetails.CameraDetailsView;
import com.n8plus.smarthome.View.HomePage.CameraPage.CameraList.CameraListView;

import java.util.ArrayList;

public class CameraListAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Device> deviceList;
    private int layout;

    public CameraListAdapter(Context context, ArrayList<Device> deviceList, int layout) {
        this.context = context;
        this.deviceList = deviceList;
        this.layout = layout;
    }

    @Override
    public int getCount() {
        return deviceList.size();
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
        TextView txtCameraPos, txtCameraState;
        ImageView imgSetting;
        LinearLayout lnrState, lnr_play;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);

            viewHolder = new ViewHolder();
            viewHolder.txtCameraPos = (TextView) convertView.findViewById(R.id.txtCameraPos);
            viewHolder.txtCameraState = (TextView) convertView.findViewById(R.id.txtCameraState);
            viewHolder.imgSetting = (ImageView) convertView.findViewById(R.id.imgSetting);
            viewHolder.lnrState = (LinearLayout) convertView.findViewById(R.id.lnrState);
            viewHolder.lnr_play = (LinearLayout) convertView.findViewById(R.id.lnr_play);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.txtCameraPos.setText(deviceList.get(position).getPosition().name());
        viewHolder.txtCameraState.setText(deviceList.get(position).getConnectByType(ModuleType.CAMERA) == true ? "Connected" : "Disconnected");
        if (deviceList.get(position).getConnectByType(ModuleType.CAMERA)) {
            viewHolder.lnrState.setBackgroundResource(R.drawable.linear_custom_blue);
        } else {
            viewHolder.lnrState.setBackgroundResource(R.drawable.linear_custom_red);
        }

        viewHolder.lnr_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (deviceList.get(position).getConnectByType(ModuleType.CAMERA)) {
                    Intent intent = new Intent((CameraListView) context, CameraDetailsView.class);
                    intent.putExtra("Camera", deviceList.get(position));
                    ((CameraListView) context).startActivity(intent);
                } else {
                    final AlertDialog.Builder alert = new AlertDialog.Builder(((CameraListView) context));
                    alert.setTitle("Question");
                    alert.setMessage("Please connect this camera to control it !");
                    alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            deviceList.get(position).setConnect(true);
                            notifyDataSetChanged();
                            Intent intent = new Intent((CameraListView) context, CameraDetailsView.class);
                            intent.putExtra("Camera", deviceList.get(position));
                            ((CameraListView) context).startActivity(intent);
                        }
                    });
                    alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    alert.create().show();
                }
            }
        });

        return convertView;
    }
}
