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

public class Clients_RecyclerView_Adapter extends RecyclerView.Adapter<Clients_RecyclerView_Adapter.ClientsViewHolder>{

    private Context context;
    private RecyclerViewClick recyclerViewClick;

    private ArrayList<String> clients_id, clients_code,clients_contact_name;
    private int selectedRowPosition = -1;
    private int lastSelectedRowPosition = -1;

    public Clients_RecyclerView_Adapter(Context context, RecyclerViewClick recyclerViewClick, ArrayList<String> clients_id, ArrayList<String> clients_code, ArrayList<String> clients_contact_name) {
        this.context = context;
        this.recyclerViewClick = recyclerViewClick;
        this.clients_id = clients_id;
        this.clients_code = clients_code;
        this.clients_contact_name = clients_contact_name;
    }

    @NonNull
    @Override
    public ClientsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.clients_recyclerview_row, parent, false);
        return new Clients_RecyclerView_Adapter.ClientsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClientsViewHolder holder, int position) {
        holder.clients_id.setText(clients_id.get(position));
        holder.clients_code.setText(clients_code.get(position));
        holder.clients_contact_name.setText(clients_contact_name.get(position));

        if(position == selectedRowPosition){
            holder.Background.setBackgroundColor(Color.LTGRAY);
        }
        else{
            holder.Background.setBackgroundColor(Color.WHITE);
        }
    }

    @Override
    public int getItemCount() {
        return clients_id.size();
    }


    public class ClientsViewHolder extends RecyclerView.ViewHolder{

        TextView clients_id, clients_code,clients_contact_name;
        androidx.constraintlayout.widget.ConstraintLayout Background;

        public ClientsViewHolder(@NonNull View itemView) {
            super(itemView);

            clients_id = itemView.findViewById(R.id.id_clients_row);
            clients_code = itemView.findViewById(R.id.code_clients_row);
            clients_contact_name = itemView.findViewById(R.id.contact_name_clients_row);
            Background = itemView.findViewById(R.id.clients_row_background);

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
                            recyclerViewClick.onItemSelected(selectedRowPosition,false);
                        }
                    }
                }
            });
        }
    }
}
