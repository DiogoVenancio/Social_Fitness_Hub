package cm.socialfitnesshub.Models;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import cm.socialfitnesshub.PartnerFinder.EditActivity;
import cm.socialfitnesshub.Profile.ProfileManager;
import cm.socialfitnesshub.R;

public class ActivityAdapter extends RecyclerView.Adapter<ActivityAdapter.ActivityViewHolder> {
    private List<PartnerFinder> mActivity;
    private Context mContext;

    LayoutInflater inflater;
    private ArrayList<PartnerFinder> newArray;

    class ActivityViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView pos_event;
        TextView pos_nickname;
        TextView pos_location;
        TextView pos_date;
        TextView pos_sport;
        TextView pos_desc;

        public ActivityViewHolder(View itemView) {
            super(itemView);

            pos_event = (TextView) itemView.findViewById(R.id.textViewEventName);
            pos_nickname = (TextView) itemView.findViewById(R.id.textViewNickName);
            pos_location = (TextView) itemView.findViewById(R.id.textViewLocation);
            pos_date = (TextView) itemView.findViewById(R.id.textViewDate);
            pos_sport = (TextView) itemView.findViewById(R.id.textViewSport);
            pos_desc = (TextView) itemView.findViewById(R.id.textViewDesc);

            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {

            int position = getLayoutPosition();
            PartnerFinder activity = mActivity.get(position);

            if (activity.getUserId().equals(ProfileManager.getInstance().getProfile().getProfileId())) {
                openEditActivity(activity);
            }
        }

        private void openEditActivity(PartnerFinder activity) {
            Intent intent = new Intent(mContext, EditActivity.class);
            intent.putExtra("EditActivity", activity);

            mContext.startActivity(intent);
        }
    }

    public ActivityAdapter(Context mContext, List<PartnerFinder> mActivity) {

        this.mContext = mContext;
        this.mActivity = mActivity;
        inflater = LayoutInflater.from(mContext);
        this.newArray = new ArrayList<PartnerFinder>();
        this.newArray.addAll(mActivity);
    }

    @Override
    public ActivityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pf_tab1_my_activity_content,
                parent, false);
        return new ActivityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ActivityViewHolder holder, int position) {
        PartnerFinder pf = mActivity.get(position);

        holder.pos_event.setText(pf.getEventsName());
        holder.pos_nickname.setText(pf.getNickName());
        holder.pos_location.setText(pf.getLocation().getAddress());
        holder.pos_date.setText(pf.getDate().toString());
        holder.pos_sport.setText(pf.getSport().toString());
        holder.pos_desc.setText(pf.getDescription());

    }

    @Override
    public int getItemCount() {
        return mActivity.size();
    }

    public void filter(String charText)
    {
        charText = charText.toLowerCase(Locale.getDefault());

        mActivity.clear();
        if (charText.length() == 0)
        {
            mActivity.addAll(newArray);
        } else {
            for (PartnerFinder wp : newArray) {
                if (wp.getEventsName().toLowerCase(Locale.getDefault()).contains(charText)) {
                    mActivity.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }

}




