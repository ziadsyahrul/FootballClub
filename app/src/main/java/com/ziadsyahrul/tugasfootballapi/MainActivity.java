package com.ziadsyahrul.tugasfootballapi;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.ziadsyahrul.tugasfootballapi.adapter.AdapterApi;
import com.ziadsyahrul.tugasfootballapi.api.ApiClient;
import com.ziadsyahrul.tugasfootballapi.api.ApiInterface;
import com.ziadsyahrul.tugasfootballapi.model.Constant;
import com.ziadsyahrul.tugasfootballapi.model.TeamData;
import com.ziadsyahrul.tugasfootballapi.model.TeamResponse;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.rv_teams)
    RecyclerView rvTeams;
    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout swiperefresh;

    private List<TeamData> dataResponseList;
    private ApiInterface apiInterface;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        dataResponseList = new ArrayList<>();

        swiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
            }
        });

        showProgress();

        getData();
    }

    private void showProgress() {

        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Loading ...");
        progressDialog.show();
    }


    private void getData() {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<TeamResponse> call = apiInterface.getTeamResponse(Constant.s, Constant.c);
        call.enqueue(new Callback<TeamResponse>() {
            @Override
            public void onResponse(Call<TeamResponse> call, Response<TeamResponse> response) {
                progressDialog.dismiss();
                swiperefresh.setRefreshing(false);
                TeamResponse teamResponse = response.body();
                dataResponseList = teamResponse.getTeams();

                rvTeams.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                rvTeams.setAdapter(new AdapterApi(MainActivity.this, dataResponseList));
            }

            @Override
            public void onFailure(Call<TeamResponse> call, Throwable t) {
                progressDialog.dismiss();
                swiperefresh.setRefreshing(false);
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
