package cm.socialfitnesshub.PartnerFinder;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import cm.socialfitnesshub.Models.Activity;
import cm.socialfitnesshub.Models.ActivityAdapter;
import cm.socialfitnesshub.Models.PartnerFinder;

import cm.socialfitnesshub.Profile.ProfileManager;
import cm.socialfitnesshub.R;

public class Tab1_Activity extends Fragment implements Observer {

    private Activity activity = Activity.getInstance();
    private RecyclerView recyclerView;
    private ActivityAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.pf_tab1_my_activity, container, false);


        recyclerView = rootView.findViewById(R.id.recycler_view);
        activity.addObserver(this);


        List<PartnerFinder> activities = new ArrayList<>();

        for (PartnerFinder pf : activity.getActivities()) {
            if (pf.getUserId().equals(ProfileManager.getInstance().getProfile().getProfileId())) {
                activities.add(pf);
            }
        }

        mAdapter = new ActivityAdapter(rootView.getContext(), activities);

        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        recyclerView.setAdapter(mAdapter);

        recyclerView.addItemDecoration(new DividerItemDecoration(rootView.getContext(), LinearLayoutManager.VERTICAL));

        return rootView;
    }


    @Override
    public void update(Observable o, Object arg) {
        mAdapter.notifyDataSetChanged();
    }
}
