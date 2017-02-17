package com.example.common.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.common.R;
import com.example.common.network.models.Content;
import com.example.common.network.models.constants.ContentTypes;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by myotive on 2/12/2017.
 */

public class ContentAdapter extends RecyclerView.Adapter<ContentAdapter.ContentViewHolder> {


    private Context context;
    private List<Content> contents;


    public ContentAdapter(Context context, List<Content> contents){
        this.context = context;
        this.contents = contents;

        sortContent();
    }

    public void swapData(List<Content> contents){
        this.contents = contents;

        sortContent();

        this.notifyDataSetChanged();
    }

    /**
     * Probably not best to do this here, but since I'm using this across modules, I didn't want
     * to have to presort.
     */
    private void sortContent() {
        Collections.sort(this.contents, new Comparator<Content>() {
            @Override
            public int compare(Content lhs, Content rhs) {
                return lhs.getType().compareTo(rhs.getType());
            }
        });
    }

    @Override
    public ContentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_content, parent, false);
        
        return new ContentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ContentViewHolder holder, int position) {
        Content content = contents.get(position);

        holder.name.setText(content.getName());

        if(content.getType().equals(ContentTypes.DIR)){
            holder.type.setImageResource(R.drawable.folder);
        }
        else{
            holder.type.setImageResource(R.drawable.file);
        }

    }

    @Override
    public int getItemCount() {
        return contents != null ? contents.size() : 0;
    }

    class ContentViewHolder extends RecyclerView.ViewHolder  {

        ImageView type;
        TextView name;

        ContentViewHolder(View itemView) {
            super(itemView);
            type = (ImageView)itemView.findViewById(R.id.content_img_type);
            name = (TextView)itemView.findViewById(R.id.content_txt_name);
        }
    }
}
