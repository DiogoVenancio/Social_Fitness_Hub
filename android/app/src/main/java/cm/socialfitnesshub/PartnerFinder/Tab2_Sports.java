package cm.socialfitnesshub.PartnerFinder;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import cm.socialfitnesshub.Models.Sports;
import cm.socialfitnesshub.Models.SportsAdapter;
import cm.socialfitnesshub.Profile.ProfileManager;
import cm.socialfitnesshub.R;

public class Tab2_Sports extends Fragment implements Observer {
    private RecyclerView recyclerView;

    private Button add_sport;
    private SportsAdapter mSportAdapter;

    private List<Sports> sports = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.pf_tab2_sports, container, false);

        recyclerView = rootView.findViewById(R.id.recycler_view);

        add_sport = rootView.findViewById(R.id.add_sport);
        add_sport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SportAddDialog addDialog = new SportAddDialog(rootView.getContext());
                addDialog.show();
            }
        });

        ProfileManager.getInstance().addObserver(this);

        mSportAdapter = new SportsAdapter(rootView.getContext(), sports);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        recyclerView.setAdapter(mSportAdapter);
        updateAdapter();
        recyclerView.addItemDecoration(new DividerItemDecoration(rootView.getContext(), LinearLayoutManager.VERTICAL));

        return rootView;
    }

    @Override
    public void update(Observable o, Object arg) {
        updateAdapter();
    }

    private void updateAdapter() {
        sports = ProfileManager.getInstance().getSportsPracticed();
        mSportAdapter.updateSports(sports);
    }
}
