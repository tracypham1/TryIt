package com.example.tryit.ui.generatedrecipes;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tryit.R;
import com.example.tryit.Recipe_Activity;
import com.example.tryit.models.Recipe;
import com.example.tryit.ui.searchResults.SearchResultsFragment;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecyclerViewAdapterSearchResult extends RecyclerView.Adapter<RecyclerViewAdapterSearchResult.MyViewHolder> {

    private Context mContext;
    private List<Recipe> mData;
    private ViewGroup viewGroup;

    public RecyclerViewAdapterSearchResult(Context mContext, List<Recipe> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.cardview_item_search_result, parent, false);
        this.viewGroup = parent;
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.tv_recipe_title.setText(mData.get(position).getName().toString());
        if (mData.get(position).getThumbnail().isEmpty()) {
            holder.img_recipe_thumbnail.setImageResource(R.drawable.nopicture);
        } else{
            Picasso.get().load(mData.get(position).getThumbnail()).into(holder.img_recipe_thumbnail);
        }
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, Recipe_Activity.class);
                intent.putExtra("id", mData.get(position).getSId()); //changed to SId!!
                intent.putExtra("name",mData.get(position).getName());
                intent.putExtra("img",mData.get(position).getThumbnail());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);

//                AppCompatActivity activity = (AppCompatActivity) viewGroup.getContext();
//                activity.getSupportFragmentManager().beginTransaction()
//                        .replace(viewGroup.getId(), new RecipeFragment(
//                                mData.get(position).getSId(),
//                                mData.get(position).getName(),
//                                mData.get(position).getThumbnail()
//                        ))
//                        .addToBackStack(null)
//                        .commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_recipe_title;
        ImageView img_recipe_thumbnail;
        CardView cardView;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_recipe_title = itemView.findViewById(R.id.search_result_recipe_title);
            img_recipe_thumbnail = itemView.findViewById(R.id.search_result_recipe_img);
            cardView = itemView.findViewById(R.id.search_result_cardview);
        }
    }
}
