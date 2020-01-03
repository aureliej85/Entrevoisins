package com.openclassrooms.entrevoisins.ui.favorites_list;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;
import com.openclassrooms.entrevoisins.ui.detailsNeighbour.NeighbourDetailActivity;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MyFavoritesRecyclerViewAdapter extends RecyclerView.Adapter<MyFavoritesRecyclerViewAdapter.ViewHolder> {

    private final List<Neighbour> mFavorites;
    private NeighbourApiService mNeighbourApiService;
    private Context context;
    private static final String TAG = "Favorite neighbour";

    public MyFavoritesRecyclerViewAdapter(List<Neighbour> items ) {
        mFavorites = items;
    }


    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_favorites, parent, false);
        context = parent.getContext();
        return new MyFavoritesRecyclerViewAdapter.ViewHolder(view);
    }


    public void onBindViewHolder(final MyFavoritesRecyclerViewAdapter.ViewHolder holder, int position) {
        mNeighbourApiService = DI.getNeighbourApiService();

        Neighbour favorites = mFavorites.get(position);
        holder.mFavoriteName.setText(favorites.getName());
        Glide.with(holder.mFavoriteAvatar.getContext())
                .load(favorites.getAvatarUrl())
                .apply(RequestOptions.circleCropTransform())
                .into(holder.mFavoriteAvatar);

        holder.mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage(context.getString(R.string.deleteMessage) + " " + favorites.getName() + " "+ context.getString(R.string.de_vos_favoris));
                builder.setCancelable(false);
                builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mNeighbourApiService.deleteFavorite(favorites);
                        notifyDataSetChanged();
                        dialog.dismiss();
                        Toast.makeText(context, favorites.getName() + " " + context.getString(R.string.confirm_favorite_delete), Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "was deleted ");
                    }
                });
                builder.setNegativeButton("Non", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), NeighbourDetailActivity.class);
                intent.putExtra("neighbour", favorites);
                view.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mFavorites.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_list_avatar2)
        public ImageView mFavoriteAvatar;
        @BindView(R.id.item_list_name2)
        public TextView mFavoriteName;
        @BindView(R.id.item_list_delete_button2)
        public ImageButton mDeleteButton;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}


