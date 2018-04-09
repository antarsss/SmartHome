package com.n8plus.smarthome.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.n8plus.smarthome.View.LoadDoor.DetectionDoor;
import com.n8plus.smarthome.Activity.NotificationDetail;
import com.n8plus.smarthome.Interface.CountMarkedAsRead;
import com.n8plus.smarthome.Model.Notification;
import com.n8plus.smarthome.Model.Enum.NotificationType;
import com.n8plus.smarthome.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hiep_Nguyen on 2/5/2018.
 */

public class NotificationAdapter extends BaseAdapter {

    private Context context;
    List<Notification> list;
    private int layout;
    boolean seen;
    ViewHolder viewHolder;
    CountMarkedAsRead countMarkedAsRead;

    public NotificationAdapter(Context context, List<Notification> list, int layout) {
        this.context = context;
        this.list = list;
        this.layout = layout;
    }

    public NotificationAdapter(Context context, CountMarkedAsRead markedAsRead,  List<Notification> list, int layout) {
        this.context = context;
        this.list = list;
        this.layout = layout;
        countMarkedAsRead = markedAsRead;
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    private class ViewHolder {
        ImageView imgNotification, imgMore;
        TextView txtMessage, txtTime;
        LinearLayout layoutNoti;
    }


    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        final int pos = i;

        if (view == null) {

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);

            viewHolder = new ViewHolder();

            viewHolder.txtMessage = view.findViewById(R.id.txtMessage);
            viewHolder.imgNotification = view.findViewById(R.id.imgNotification);
            viewHolder.imgMore = view.findViewById(R.id.imgMore);
            viewHolder.txtTime = view.findViewById(R.id.txtTime);
            viewHolder.layoutNoti = view.findViewById(R.id.layoutNoti);

            view.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) view.getTag();
        }

        final Notification notification = list.get(i);

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm - dd/MM/yy");
        viewHolder.imgNotification.setImageResource(notification.getType()==NotificationType.DOOR ? R.drawable.door:R.drawable.anonymous);
        viewHolder.txtMessage.setText(notification.getMessage());
        viewHolder.txtTime.setText(sdf.format(notification.getTime()));

        seen = notification.isState();
        setBgSeen();

        viewHolder.imgMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
                popupMenu.getMenuInflater().inflate(R.menu.more_menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()){
                            case R.id.mnuDetails:
                                if (notification.getType()== NotificationType.DOOR){
                                    view.getContext().startActivity(new Intent(view.getContext(), DetectionDoor.class));
                                }
                                else {
                                    Intent intent = new Intent(view.getContext(), NotificationDetail.class);
                                    intent.putExtra("unknowAlert", notification);
                                    view.getContext().startActivity(intent);
                                }
                                break;
                            case R.id.mnuMark:
                                list.get(i).setState(false);
                                notifyDataSetChanged();
                                countMarkedAsRead.updateList((ArrayList<Notification>) list);
                                break;
                            case R.id.mnuDelNoti:
                                list.remove(list.remove(i));
                                notifyDataSetChanged();
                                countMarkedAsRead.updateList((ArrayList<Notification>) list);
                                break;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });

        return view;
    }

    private void setBgSeen(){
        if (seen){
            viewHolder.txtTime.setTextColor(Color.parseColor("#898989"));
            viewHolder.layoutNoti.setBackgroundColor(Color.parseColor("#d1f3ff"));
        }else{
            viewHolder.txtTime.setTextColor(Color.parseColor("#898989"));
            viewHolder.layoutNoti.setBackgroundColor(Color.parseColor("#f9f9f9"));
        }
    }
}
