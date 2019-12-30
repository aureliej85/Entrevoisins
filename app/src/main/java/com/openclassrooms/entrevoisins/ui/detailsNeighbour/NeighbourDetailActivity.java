package com.openclassrooms.entrevoisins.ui.detailsNeighbour;

import android.os.Bundle;
import android.support.annotation.Nullable;
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


public class NeighbourDetailActivity extends AppCompatActivity {

    private TextView mUsername;
    private ImageView mAvatar;
    private ImageView mStar;
    private TextView mAdresse;
    private TextView mTel;
    private TextView mLink;
    private TextView mDescr;
    private TextView mBigUsername;


    private NeighbourApiService mApiService;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_neighbour_detail);

        mApiService = DI.getNeighbourApiService();

        mUsername = findViewById(R.id.usernameText);
        mAvatar = findViewById(R.id.avatarPic);
        mStar = findViewById(R.id.favButton);
        mAdresse = findViewById(R.id.adressText);
        mTel = findViewById(R.id.telText);
        mLink = findViewById(R.id.urlText);
        mDescr = findViewById(R.id.descText);
        mBigUsername = findViewById(R.id.bigUsernameText);

        Toolbar myToolbar = findViewById(R.id.toolbar2);


        /************ TOOLBAR **************/
        setSupportActionBar(myToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true); // Enable the Up button
        ab.setDisplayShowTitleEnabled(false); // Disable the title

        /************ GET INTENT ***********/
        String bigUsername = getIntent().getStringExtra("username");
        mBigUsername.setText(bigUsername);

        String name = getIntent().getStringExtra("username");
        mUsername.setText(name);

        String avatar = getIntent().getStringExtra("avatar");
        Glide.with(this)
                .asBitmap()
                .load(avatar)
                .centerCrop()
                .into(mAvatar);

        String adresse = getIntent().getStringExtra("adresse");
        mAdresse.setText(adresse);

        String tel = getIntent().getStringExtra("tel");
        mTel.setText(tel);

        String link = getIntent().getStringExtra("link");
        mLink.setText(link);

        String descr = getIntent().getStringExtra("description");
        mDescr.setText(descr);

        mStar.setImageResource(R.drawable.ic_star_white_24dp);


        yellowStar();

    }


    public void editFavorite(View view) {
        int ID = getIntent().getIntExtra("ID", 0);

        /******** Ajout du voisin dans la liste des favoris ******/

        if((mApiService.getFavorites().contains(new Neighbour(ID)))){
            Toast.makeText(this, "Ce voisin est déjà dans vos favoris.", Toast.LENGTH_SHORT).show();
        } else {
            for (Neighbour favorite : mApiService.getNeighbours()) {
                if ((ID == favorite.getId()) && (!mApiService.getFavorites().contains(new Neighbour(ID)))){
                mApiService.getFavorites().add(favorite);
                Toast.makeText(this, favorite.getName() + " est maintenant dans vos favoris.", Toast.LENGTH_SHORT).show();
                mStar.setImageResource(R.drawable.ic_star_yellow);
                }
            }
        }
    }


   private void yellowStar(){
        int ID = getIntent().getIntExtra("ID", 0);

        for (Neighbour favorite : mApiService.getFavorites()) {
            if (ID == favorite.getId()) {
                mStar.setImageResource(R.drawable.ic_star_yellow);
            }
        }

    }





}
