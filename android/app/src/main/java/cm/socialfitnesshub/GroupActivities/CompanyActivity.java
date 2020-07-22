package cm.socialfitnesshub.GroupActivities;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import cm.socialfitnesshub.CustomViews.ActivityWithBar;
import cm.socialfitnesshub.R;

public class CompanyActivity extends ActivityWithBar implements Observer {

    private FloatingActionButton fabAddCompany;

    private RecyclerView recyclerView;

    private CompanyAdapter companyAdapter;
    private List<Company> companies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupToolBar(R.layout.ga_company);

        final Context context = this;

        recyclerView = findViewById(R.id.group_activity_company_list);

        companyAdapter = new CompanyAdapter(context, companies);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(companyAdapter);

        updateAdapter();

        CompanyManager.getInstance().addObserver(this);

        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));


        fabAddCompany = findViewById(R.id.fabAddCompany);
        fabAddCompany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CompanyActivity.this, CompanyAddActivity.class);
                startActivity(i);
            }
        });

    }

    private void updateAdapter() {
        companies = CompanyManager.getInstance().getCompanies();
        companyAdapter.updateCompanies(companies);
    }

    @Override
    public void update(Observable o, Object arg) {
        updateAdapter();
    }
}
