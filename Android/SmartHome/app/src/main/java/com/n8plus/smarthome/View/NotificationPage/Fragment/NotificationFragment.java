package com.n8plus.smarthome.View.NotificationPage.Fragment;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.n8plus.smarthome.Adapter.NotificationAdapter;
import com.n8plus.smarthome.Adapter.RecyclerItemTouchHelper;
import com.n8plus.smarthome.Model.Notification;
import com.n8plus.smarthome.Presenter.NotificationPresenter.NotificationPresenter;
import com.n8plus.smarthome.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Hiep_Nguyen on 2/1/2018.
 */

@SuppressLint("ValidFragment")
public class NotificationFragment extends Fragment implements NotificationFragmentImpl, RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {
    RecyclerView recyclerViewNew, recyclerViewOld;
    NotificationAdapter adapterNew, adapterOlder;
    NotificationPresenter notificationPresenter;
    ArrayList<Notification> listNew, listOld;

    @SuppressLint("ResourceAsColor")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification, container, false);
        recyclerViewNew = (RecyclerView) view.findViewById(R.id.lvNewNotification);
        recyclerViewOld = (RecyclerView) view.findViewById(R.id.lvOldNotification);
        notificationPresenter = new NotificationPresenter(this, getActivity());
        HashMap<String, String> map = new HashMap<>();
        notificationPresenter.loadNotificationLastest();
        notificationPresenter.loadNotificationOlder();
        return view;
    }

    @Override
    public void loadNotificationLastest(ArrayList<Notification> list) {
        this.listNew = list;
        adapterNew = new NotificationAdapter(getActivity(), list, notificationPresenter);
        recyclerViewNew.setAdapter(adapterNew);
        initView(recyclerViewNew);
        adapterNew.notifyDataSetChanged();
    }

    @Override
    public void loadNotificationOlder(ArrayList<Notification> list) {
        this.listOld = list;
        adapterOlder = new NotificationAdapter(getActivity(), list, notificationPresenter);
        recyclerViewOld.setAdapter(adapterOlder);
        initView(recyclerViewOld);
        adapterOlder.notifyDataSetChanged();
    }

    @Override
    public void loadNotificationFailure(String message) {

    }

    @Override
    public void updateNotification() {
        if (adapterNew != null) adapterNew.notifyDataSetChanged();
        if (adapterOlder != null) adapterOlder.notifyDataSetChanged();
    }

    @SuppressLint("ResourceAsColor")
    public void initView(RecyclerView recyclerView) {
        ((LinearLayout) recyclerView.getParent()).setVisibility(View.VISIBLE);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.RIGHT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (adapterNew.containItem(viewHolder.getAdapterPosition())) {
            notificationPresenter.removeNotification(listNew.get(position));
            adapterNew.removeItem(viewHolder.getAdapterPosition());
        } else if (adapterOlder.containItem(viewHolder.getAdapterPosition())) {
            notificationPresenter.removeNotification(listOld.get(position));
            adapterOlder.removeItem(viewHolder.getAdapterPosition());
        }
    }
}
