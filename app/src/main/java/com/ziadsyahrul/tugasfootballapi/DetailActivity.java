package com.ziadsyahrul.tugasfootballapi;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
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

public class DetailActivity extends AppCompatActivity {

    @BindView(R.id.imgLogoClub)
    ImageView imgLogoClub;
    @BindView(R.id.txtNamaClub)
    TextView txtNamaClub;
    @BindView(R.id.txtDetail)
    TextView txtDetail;

    private Bundle bundle;
    private int id;
    private ApiInterface apiInterface;
    private List<TeamData> teamDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        bundle = getIntent().getExtras();

        if (bundle != null){
            id = Integer.valueOf(bundle.getString(Constant.id));
        }

        teamDataList = new ArrayList<>();


        getData();

    }

    private void getData() {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<TeamResponse> call = apiInterface.getDetailResponse(id);
        call.enqueue(new Callback<TeamResponse>() {
            @Override
            public void onResponse(Call<TeamResponse> call, Response<TeamResponse> response) {
                TeamResponse teamResponse = response.body();
                teamDataList = teamResponse.getTeams();


                txtNamaClub.setText(teamDataList.get(0).getStrTeam());
                Glide.with(DetailActivity.this).load(teamDataList.get(0).getStrTeamBadge()).into(imgLogoClub);
                txtDetail.setText(teamDataList.get(0).getStrDescriptionEN());
            }

            @Override
            public void onFailure(Call<TeamResponse> call, Throwable t) {
                Toast.makeText(DetailActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}
