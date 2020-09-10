package com.example.applicationandroiddecommandeenligne;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Articles_RecyclerView_Adapter extends RecyclerView.Adapter<Articles_RecyclerView_Adapter.ClientsViewHolder>{

    private Context context;
    private RecyclerViewClick recyclerViewClick;

    private ArrayList<String> articles_id, articles_code, articles_name;
    private int selectedRowPosition = -1;
    private int lastSelectedRowPosition = -1;

    public Articles_RecyclerView_Adapter(Context context, RecyclerViewClick recyclerViewClick, ArrayList<String> articles_id, ArrayList<String> articles_code, ArrayList<String> articles_name) {
        this.context = context;
        this.recyclerViewClick = recyclerViewClick;
        this.articles_id = articles_id;
        this.articles_code = articles_code;
        this.articles_name = articles_name;
    }

    @NonNull
    @Override
    public ClientsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.articles_recyclerview_row, parent, false);
        return new Articles_RecyclerView_Adapter.ClientsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClientsViewHolder holder, int position) {
        holder.articles_id.setText(articles_id.get(position));
        holder.articles_code.setText(articles_code.get(position));
        holder.articles_name.setText(articles_name.get(position));

        if(position == selectedRowPosition){
            holder.Background.setBackgroundColor(Color.LTGRAY);
        }
        else{
            holder.Background.setBackgroundColor(Color.WHITE);
        }
    }

    @Override
    public int getItemCount() {
        return articles_id.size();
    }


    public class ClientsViewHolder extends RecyclerView.ViewHolder{

        TextView articles_id, articles_code,articles_name;
        androidx.constraintlayout.widget.ConstraintLayout Background;

        public ClientsViewHolder(@NonNull View itemView) {
            super(itemView);

            articles_id = itemView.findViewById(R.id.id_articles_row);
            articles_code = itemView.findViewById(R.id.code_articles_row);
            articles_name = itemView.findViewById(R.id.name_articles_row);
            Background = itemView.findViewById(R.id.articles_row_background);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (selectedRowPosition == -1){
                        selectedRowPosition = getAdapterPosition();
                        notifyDataSetChanged();
                        recyclerViewClick.onItemSelected(selectedRowPosition,false);
                        lastSelectedRowPosition = selectedRowPosition;
                    }
                    else{
                        selectedRowPosition = getAdapterPosition();
                        if (selectedRowPosition != lastSelectedRowPosition){
                            notifyDataSetChanged();
                            recyclerViewClick.onItemSelected(selectedRowPosition, false);
                            lastSelectedRowPosition = selectedRowPosition;
                        }
                        else if (selectedRowPosition == lastSelectedRowPosition){
                            selectedRowPosition = -1;
                            lastSelectedRowPosition = -1;
                            notifyDataSetChanged();
                            recyclerViewClick.onItemSelected(selectedRowPosition, false);
                        }
                    }
                }
            });
        }
    }
}
