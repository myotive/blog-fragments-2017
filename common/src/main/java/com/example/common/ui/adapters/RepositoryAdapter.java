package com.example.common.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.common.R;
import com.example.common.network.models.Repository;

import java.util.List;

/**
 * Created by myotive on 2/12/2017.
 */

public class RepositoryAdapter extends RecyclerView.Adapter<RepositoryAdapter.RepositoryViewHolder> {

    public interface RepositoryItemClick{
        void OnRepositoryItemClick(View view, Repository item);
    }

    android.text.format.DateFormat df = new android.text.format.DateFormat();

    private Context context;
    private List<Repository> repositories;
    private RepositoryItemClick itemClickCallback;

    public RepositoryAdapter(Context context, List<Repository> repositories, RepositoryItemClick itemClickCallback){
        this.context = context;
        this.repositories = repositories;
        this.itemClickCallback = itemClickCallback;
    }

    public void swapData(List<Repository> repositories){
        this.repositories = repositories;
        this.notifyDataSetChanged();
    }

    public void setRepositoryItemClick(RepositoryItemClick repositoryItemClick){
        this.itemClickCallback = repositoryItemClick;
    }

    public List<Repository> getRepositories() {
        return repositories;
    }

    @Override
    public RepositoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_repository, parent, false);
        
        return new RepositoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RepositoryViewHolder holder, int position) {
        Repository repository = repositories.get(position);

        holder.name.setText(repository.getName());
        holder.desc.setText(repository.getDescription());
        holder.created_at.setText(df.format("MM/dd/yyyy hh:mm a", repository.getCreated_at()));
    }

    @Override
    public int getItemCount() {
        return repositories != null ? repositories.size() : 0;
    }

    class RepositoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        LinearLayout container;
        TextView name;
        TextView desc;
        TextView created_at;

        RepositoryViewHolder(View itemView) {
            super(itemView);
            name = (TextView)itemView.findViewById(R.id.text_name);
            desc = (TextView)itemView.findViewById(R.id.text_desc);
            created_at = (TextView)itemView.findViewById(R.id.text_created_at);
            container = (LinearLayout)itemView.findViewById(R.id.item_container);
            container.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(itemClickCallback != null){
                itemClickCallback.OnRepositoryItemClick(view,
                        repositories.get(getAdapterPosition()));
            }
        }
    }
}
