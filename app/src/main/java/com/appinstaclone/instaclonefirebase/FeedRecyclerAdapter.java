package com.appinstaclone.instaclonefirebase;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FeedRecyclerAdapter extends RecyclerView.Adapter<FeedRecyclerAdapter.PostHolder> {

    private ArrayList<String> emails, expressions, images;

    public FeedRecyclerAdapter(ArrayList<String> emails, ArrayList<String> expressions, ArrayList<String> images) {
        this.emails = emails;
        this.expressions = expressions;
        this.images = images;
    }

    @NonNull
    @Override
    public PostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.rec_row, parent, false);

        return new PostHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostHolder holder, int position) {
        holder.emailT.setText(emails.get(position));
        holder.expressionT.setText(expressions.get(position));
        Picasso.get().load(images.get(position)).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return emails.size();
    }

    class PostHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView emailT;
        TextView expressionT;

        public PostHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageList);
            emailT = itemView.findViewById(R.id.emailList);
            expressionT = itemView.findViewById(R.id.expressionList);
        }
    }

}
