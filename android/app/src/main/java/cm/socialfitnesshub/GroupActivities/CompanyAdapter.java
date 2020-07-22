package cm.socialfitnesshub.GroupActivities;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cm.socialfitnesshub.Profile.ProfileActivity;
import cm.socialfitnesshub.R;

public class CompanyAdapter extends RecyclerView.Adapter<CompanyAdapter.CompanyViewHolder> {

    private List<Company> companies;
    private Context context;


    public CompanyAdapter(Context context, List<Company> companies) {
        this.companies = companies;
        this.context = context;
    }

    public void updateCompanies(List<Company> companies) {
        this.companies = companies;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CompanyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ga_company_list_content,
                parent, false);
        return new CompanyAdapter.CompanyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CompanyViewHolder holder, final int position) {


        final Company c = companies.get(position);

        holder.companyName.setText(c.getName());
        holder.companyWebsite.setText(c.getWebsite());
        holder.companyAddress.setText(c.getAddress());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //CompanyManager.getInstance().removeCompany(c);
                Intent i = new Intent(context, CompanyDetailActivity.class);
                i.putExtra("company_position", position);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return companies.size();
    }

    public class CompanyViewHolder extends RecyclerView.ViewHolder {

        TextView companyName, companyWebsite, companyAddress;

        public CompanyViewHolder(View itemView) {
            super(itemView);
            companyName = itemView.findViewById(R.id.company_list_name);
            companyWebsite = itemView.findViewById(R.id.company_list_website);
            companyAddress = itemView.findViewById(R.id.company_list_address);

        }
    }
}
