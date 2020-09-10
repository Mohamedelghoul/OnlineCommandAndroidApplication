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

public class Ligne_Cde_RecyclerView_Adapter extends RecyclerView.Adapter<Ligne_Cde_RecyclerView_Adapter.ClientsViewHolder>{

    private Context context;
    private RecyclerViewClick recyclerViewClick;

    private ArrayList<String> Ligne_id, Ligne_Commande_id,Ligne_TTC;
    private int selectedRowPosition = -1;
    private int lastSelectedRowPosition = -1;

    public Ligne_Cde_RecyclerView_Adapter(Context context, RecyclerViewClick recyclerViewClick, ArrayList<String> ligne_id, ArrayList<String> ligne_Commande_id, ArrayList<String> ligne_TTC) {
        this.context = context;
        this.recyclerViewClick = recyclerViewClick;
        Ligne_id = ligne_id;
        Ligne_Commande_id = ligne_Commande_id;
        Ligne_TTC = ligne_TTC;
    }

    @NonNull
    @Override
    public ClientsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.clients_recyclerview_row, parent, false);
        return new Ligne_Cde_RecyclerView_Adapter.ClientsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClientsViewHolder holder, int position) {
        holder.Ligne_id.setText(Ligne_id.get(position));
        holder.Ligne_Commande_id.setText(Ligne_Commande_id.get(position));
        holder.Ligne_TTC.setText(Ligne_TTC.get(position));

        if(position == selectedRowPosition){
            holder.Background.setBackgroundColor(Color.LTGRAY);
        }
        else{
            holder.Background.setBackgroundColor(Color.WHITE);
        }
    }

    @Override
    public int getItemCount() {
        return Ligne_id.size();
    }

    public class ClientsViewHolder extends RecyclerView.ViewHolder{

        TextView Ligne_id, Ligne_Commande_id, Ligne_TTC;
        androidx.constraintlayout.widget.ConstraintLayout Background;

        public ClientsViewHolder(@NonNull View itemView) {
            super(itemView);

            Ligne_id = itemView.findViewById(R.id.id_clients_row);
            Ligne_Commande_id = itemView.findViewById(R.id.code_clients_row);
            Ligne_TTC = itemView.findViewById(R.id.contact_name_clients_row);
            Background = itemView.findViewById(R.id.clients_row_background);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (selectedRowPosition == -2){
                        selectedRowPosition = getAdapterPosition();
                        notifyDataSetChanged();
                        recyclerViewClick.onItemSelected(selectedRowPosition,true);
                        lastSelectedRowPosition = selectedRowPosition;
                    }
                    else{
                        selectedRowPosition = getAdapterPosition();
                        if (selectedRowPosition != lastSelectedRowPosition){
                            notifyDataSetChanged();
                            recyclerViewClick.onItemSelected(selectedRowPosition,true);
                            lastSelectedRowPosition = selectedRowPosition;
                        }
                        else if (selectedRowPosition == lastSelectedRowPosition){
                            selectedRowPosition = -1;
                            lastSelectedRowPosition = -1;
                            notifyDataSetChanged();
                            recyclerViewClick.onItemSelected(selectedRowPosition,true);
                        }
                    }
                }
            });
        }
    }
}


