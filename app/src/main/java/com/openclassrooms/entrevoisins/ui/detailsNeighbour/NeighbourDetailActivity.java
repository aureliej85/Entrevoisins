package com.openclassrooms.entrevoisins.ui.detailsNeighbour;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NeighbourDetailActivity extends AppCompatActivity {

    @BindView(R.id.usernameText) public TextView mUsername;
    @BindView(R.id.avatarPic) public ImageView mAvatar;
    @BindView(R.id.favButton) public ImageView mStar;
    @BindView(R.id.adressText) public TextView mAdresse;
    @BindView(R.id.telText) public TextView mTel;
    @BindView(R.id.urlText) public TextView mLink;
    @BindView(R.id.descText) public TextView mDescr;
    @BindView(R.id.bigUsernameText) public TextView mBigUsername;

    private NeighbourApiService mApiService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_neighbour_detail);

        mApiService = DI.getNeighbourApiService();

        ButterKnife.bind(this);

        Toolbar myToolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(myToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true); // Enable the Up button
        ab.setDisplayShowTitleEnabled(false); // Disable the title

        mStar.setImageResource(R.drawable.ic_star_white_24dp);
        yellowStar();

        infosNeighbour();
    }


    public void editFavorite(View view) {
        Intent intent = getIntent();
        Neighbour neighbour = intent.getParcelableExtra("neighbour");

        Drawable drawable1 = mStar.getDrawable();
        Drawable drawable2 = ContextCompat.getDrawable(getApplicationContext(),R.drawable.ic_star_yellow);

        if((mApiService.getFavorites().contains(new Neighbour(neighbour.getId())))){
            Toast.makeText(this, neighbour.getName() +" "+ getString(R.string.déjà_dans_favoris), Toast.LENGTH_SHORT).show();
        } else if(!drawable1.equals(drawable2)){
            mApiService.getFavorites().add(neighbour);
            Toast.makeText(this, neighbour.getName() + " " + getString(R.string.est_maintenant_dans_favoris), Toast.LENGTH_SHORT).show();
            mStar.setImageResource(R.drawable.ic_star_yellow);
        }

    }


    private void yellowStar(){
       Intent intent = getIntent();
       Neighbour neighbour = intent.getParcelableExtra("neighbour");

        for (Neighbour favorite : mApiService.getFavorites()) {
            if (neighbour.getId() == favorite.getId()) {
                mStar.setImageResource(R.drawable.ic_star_yellow);
            }
        }
    }


    private void infosNeighbour(){
        Intent intent = getIntent();
        Neighbour neighbour = intent.getParcelableExtra("neighbour");

        mBigUsername.setText(neighbour.getName());
        mUsername.setText(neighbour.getName());
        Glide.with(this)
                .asBitmap()
                .load(neighbour.getAvatarUrl())
                .centerCrop()
                .into(mAvatar);
        mAdresse.setText(neighbour.getAdress());
        mTel.setText(neighbour.getTel());
        mLink.setText(neighbour.getUrl());
        mDescr.setText(neighbour.getDescription());

    }

}
