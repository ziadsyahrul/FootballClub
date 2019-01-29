package com.ziadsyahrul.tugasfootballapi.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ziadsyahrul.tugasfootballapi.DetailActivity;
import com.ziadsyahrul.tugasfootballapi.R;
import com.ziadsyahrul.tugasfootballapi.model.Constant;
import com.ziadsyahrul.tugasfootballapi.model.TeamData;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterApi extends RecyclerView.Adapter<AdapterApi.ViewHolder> {

    private Bundle bundle;
    private Context context;
    private final List<TeamData> teamDataList;

    public AdapterApi(Context context, List<TeamData> teamDataList) {
        this.context = context;
        this.teamDataList = teamDataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.list_football_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final TeamData dataResponse = teamDataList.get(i);
        viewHolder.txtNamaTeam.setText(dataResponse.getStrTeam());
        Glide.with(context).load(dataResponse.getStrTeamBadge()).into(viewHolder.imgAvatar);
        RequestOptions options = new RequestOptions().error(R.drawable.ic_broken_image_black_24dp);
        Glide.with(context).load(dataResponse.getStrTeamBadge()).apply(options).into(viewHolder.imgAvatar);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bundle = new Bundle();
                bundle.putString(Constant.id, dataResponse.getIdTeam());
                context.startActivity(new Intent(context, DetailActivity.class).putExtras(bundle));
            }
        });

    }

    @Override
    public int getItemCount() {
        return teamDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imgAvatar)
        ImageView imgAvatar;
        @BindView(R.id.txtNamaTeam)
        TextView txtNamaTeam;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
