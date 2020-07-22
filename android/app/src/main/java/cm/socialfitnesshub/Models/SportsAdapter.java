package cm.socialfitnesshub.Models;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cm.socialfitnesshub.R;

public class SportsAdapter extends RecyclerView.Adapter<SportsAdapter.SportsViewHolder> {
    private List<Sports> mSports;
    private Context mContext;

    public SportsAdapter(Context mContext, List<Sports> mSports) {

        this.mContext = mContext;
        this.mSports = mSports;
    }

    public void updateSports(List<Sports> sports) {
        this.mSports = sports;
        notifyDataSetChanged();
    }

    @Override
    public SportsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pf_tab2_sports_content_list,

                parent, false);
        return new SportsAdapter.SportsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SportsViewHolder holder, int position) {

        Sports s = mSports.get(position);

        holder.pos_sport.setText(s.toString());
    }

    @Override
    public int getItemCount() {
        return mSports.size();
    }


    class SportsViewHolder extends RecyclerView.ViewHolder {
        TextView pos_sport;

        public SportsViewHolder(View itemView) {

            super(itemView);

            pos_sport = (TextView) itemView.findViewById(R.id.textSport);
        }


    }
}
