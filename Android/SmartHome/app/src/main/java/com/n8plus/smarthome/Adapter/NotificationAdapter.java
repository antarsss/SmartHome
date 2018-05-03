package com.n8plus.smarthome.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.n8plus.smarthome.Model.Enum.NotificationLevel;
import com.n8plus.smarthome.Model.Enum.NotificationType;
import com.n8plus.smarthome.Model.Notification;
import com.n8plus.smarthome.Presenter.NotificationPresenter.NotificationPresenter;
import com.n8plus.smarthome.R;
import com.n8plus.smarthome.View.HomePage.CameraPage.CameraDetails.CameraDetailsView;
import com.n8plus.smarthome.View.HomePage.LightPage.LightListView;
import com.n8plus.smarthome.View.HomePage.DoorPage.DoorList.DoorListView;

import java.text.SimpleDateFormat;
import java.util.List;

interface ItemTouchHelperAdapter {

    void removeItem(int position);

    boolean containItem(int position);

    void restoreItem(Notification item, int position);
}

/**
 * Created by Hiep_Nguyen on 2/5/2018.
 */

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> implements ItemTouchHelperAdapter {

    List<Notification> list;
    NotificationAdapter.ViewHolder viewHolder;
    NotificationPresenter presenter;
    private Context context;

    public NotificationAdapter(Context context, List<Notification> list, NotificationPresenter presenter) {
        this.context = context;
        this.list = list;
        this.presenter = presenter;
    }

    @Override
    public NotificationAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.row_notification, parent, false);
        return new NotificationAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(NotificationAdapter.ViewHolder holder, int position) {
        this.viewHolder = holder;
        final Notification notification = list.get(position);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm - dd/MM/yy");
        viewHolder.imgNotification.setImageResource(notification.getLevel() == NotificationLevel.NORMAL ? R.drawable.alarm : R.drawable.anonymous);
        viewHolder.txtMessage.setText(notification.getTitle());
        viewHolder.imgMiniIcon.setImageResource(getImageResource(notification));
        viewHolder.txtTime.setText(sdf.format(notification.getCreateAt()));
        viewHolder.layout.setBackgroundResource(notification.isState() ? R.drawable.border_box : R.drawable.background_unread);
        viewHolder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToDeviceView(notification);
                presenter.updateNotification(notification);
            }
        });
    }

    private void goToDeviceView(Notification notification) {
        switch (notification.getType()) {
            case DOOR_ON:
            case DOOR_OFF: {
                Intent intent = new Intent(context, DoorListView.class);
                context.startActivity(intent);
                break;
            }
            case LIGHT_ON:
            case LIGHT_OFF: {
                Intent intent = new Intent(context, LightListView.class);
                context.startActivity(intent);
                break;
            }
            case CAMERA_CAPTURE:
            case CAMERA_WARNING: {
                Intent intent = new Intent(context, CameraDetailsView.class);
                context.startActivity(intent);
                break;
            }
        }

    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private int getImageResource(Notification notification) {
        NotificationType notificationType = notification.getType();
        int image = 0;
        switch (notificationType) {
            case CAMERA_CAPTURE:
                image = R.drawable.photo_camera;
                break;
            case CAMERA_WARNING:
                image = R.drawable.security_camera;
                break;
            case LIGHT_ON:
                image = R.drawable.light_on;
                break;
            case LIGHT_OFF:
                image = R.drawable.light_off;
                break;
            case DOOR_ON:
                image = R.drawable.door;
                break;
            case DOOR_OFF:
                image = R.drawable.close_door;
                break;
        }
        return image;
    }

    @Override
    public void removeItem(int position) {
        list.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public boolean containItem(int position) {
        return list.get(position) != null;
    }

    @Override
    public void restoreItem(Notification item, int position) {
        list.add(position, item);
        notifyItemInserted(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgNotification;
        ImageView imgMiniIcon;
        TextView txtMessage;
        TextView txtTime;
        LinearLayout layout;


        public ViewHolder(View itemView) {
            super(itemView);
            imgNotification = (ImageView) itemView.findViewById(R.id.imgNotification);
            imgMiniIcon = (ImageView) itemView.findViewById(R.id.imgMiniIcon);
            txtMessage = (TextView) itemView.findViewById(R.id.txtMessage);
            txtTime = (TextView) itemView.findViewById(R.id.txtTime);
            layout = (LinearLayout) itemView.findViewById(R.id.layoutNoti);
        }
    }
}