package com.n8plus.smarthome.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.n8plus.smarthome.Activity.NotificationDetail;
import com.n8plus.smarthome.Model.Enum.LevelNotification;
import com.n8plus.smarthome.Model.Notification;
import com.n8plus.smarthome.R;
import com.n8plus.smarthome.View.LoadDoor.DetectionDoor;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by Hiep_Nguyen on 2/5/2018.
 */

public class NotificationAdapter extends BaseAdapter {

    List<Notification> list;
    boolean seen;
    ViewHolder viewHolder;
    private Context context;
    private int layout;

    public NotificationAdapter(Context context, List<Notification> list, int layout) {
        this.context = context;
        this.list = list;
        this.layout = layout;
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

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        final int pos = i;

        if (view == null) {

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);

            viewHolder = new ViewHolder();

            viewHolder.txtMessage = view.findViewById(R.id.txtTitle);
            viewHolder.imgNotification = view.findViewById(R.id.imgNotification);
            viewHolder.imgMore = view.findViewById(R.id.imgMore);
            viewHolder.txtTime = view.findViewById(R.id.txtTime);
            viewHolder.layoutNoti = view.findViewById(R.id.layoutNoti);

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        final Notification notification = list.get(i);

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm - dd/MM/yy");
        viewHolder.imgNotification.setImageResource(notification.getType() == LevelNotification.DOOR ? R.drawable.door : R.drawable.anonymous);
        viewHolder.txtMessage.setText(notification.getMessage());
        viewHolder.txtTime.setText(sdf.format(notification.getTime()));

        seen = notification.isState();

        viewHolder.imgMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
                popupMenu.getMenuInflater().inflate(R.menu.more_menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.mnuDetails:
                                if (notification.getType() == LevelNotification.DOOR) {
                                    view.getContext().startActivity(new Intent(view.getContext(), DetectionDoor.class));
                                } else {
                                    Intent intent = new Intent(view.getContext(), NotificationDetail.class);
                                    intent.putExtra("unknowAlert", notification);
                                    view.getContext().startActivity(intent);
                                }
                                break;
                            case R.id.mnuMark:
                                list.get(i).setState(false);
                                notifyDataSetChanged();
                                break;
                            case R.id.mnuDelNoti:
                                list.remove(list.remove(i));
                                notifyDataSetChanged();
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

    private class ViewHolder {
        ImageView imgNotification, imgMore;
        TextView txtMessage, txtTime;
        LinearLayout layoutNoti;
    }
}
