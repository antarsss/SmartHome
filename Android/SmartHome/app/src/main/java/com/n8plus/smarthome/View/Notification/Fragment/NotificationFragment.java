package com.n8plus.smarthome.View.Notification.Fragment;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.n8plus.smarthome.Activity.NotificationDetail;
import com.n8plus.smarthome.Adapter.NotificationAdapter;
import com.n8plus.smarthome.Model.Enum.LevelNotification;
import com.n8plus.smarthome.Model.Notification;
import com.n8plus.smarthome.Presenter.Notification.NotificationPresenter;
import com.n8plus.smarthome.R;
import com.n8plus.smarthome.View.LoadDoor.DetectionDoor;

import java.util.ArrayList;

/**
 * Created by Hiep_Nguyen on 2/1/2018.
 */

@SuppressLint("ValidFragment")
public class NotificationFragment extends Fragment implements NotificationFragmentImpl {
    View view;
    ListView lvNotification;
    ArrayList<Notification> arrayList;
    NotificationAdapter adapter;
    NotificationPresenter notificationPresenter;

    public NotificationFragment(NotificationPresenter notificationPresenter) {
        this.notificationPresenter = notificationPresenter;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_notification, container, false);
        lvNotification = (ListView) view.findViewById(R.id.lvNotification);
        return view;
    }

    @Override
    public void loadNotificationSuccess(ArrayList<Notification> list) {
        adapter = new NotificationAdapter(view.getContext(), list, R.layout.row_notification);
        lvNotification.setAdapter(adapter);
        lvNotification.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Notification notification = arrayList.get(i);
                if (notification.getType() == LevelNotification.DOOR) {
                    view.getContext().startActivity(new Intent(view.getContext(), DetectionDoor.class));
                    notification.setState(false);
                    countMarkedAsRead.updateList(arrayList);
                    adapter.notifyDataSetChanged();
                } else {
                    Intent intent = new Intent(view.getContext(), NotificationDetail.class);
                    intent.putExtra("unknowAlert", notification);
                    view.getContext().startActivity(intent);
                    notification.setState(false);
                    countMarkedAsRead.updateList(arrayList);
                    adapter.notifyDataSetChanged();
                }
            }
        });

    }

    @Override
    public void loadNotificationFailure(String message) {

    }
}
