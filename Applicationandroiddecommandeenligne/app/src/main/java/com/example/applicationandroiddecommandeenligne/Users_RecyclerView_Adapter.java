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

public class Users_RecyclerView_Adapter extends RecyclerView.Adapter<Users_RecyclerView_Adapter.UsersViewHolder> {

    private Context context;
    private RecyclerViewClick recyclerViewClick;

    private ArrayList<String> users_id,users_login,users_name;
    private int selectedRowPosition = -1;
    private int lastSelectedRowPosition = -1;

    public Users_RecyclerView_Adapter(Context context, ArrayList<String> users_id, ArrayList<String> users_login, ArrayList<String> users_name, RecyclerViewClick recyclerViewClick){
        this.context = context;
        this.users_id = users_id;
        this.users_login = users_login;
        this.users_name = users_name;
        this.recyclerViewClick = recyclerViewClick;
    }
    @NonNull
    @Override
    public UsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.users_recyclerview_row, parent, false);
        return new UsersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UsersViewHolder holder, int position) {
        holder.users_id.setText(users_id.get(position));
        holder.users_login.setText(users_login.get(position));
        holder.users_name.setText(users_name.get(position));

        if(position == selectedRowPosition){
            holder.Background.setBackgroundColor(Color.LTGRAY);
        }
        else{
            holder.Background.setBackgroundColor(Color.WHITE);
        }
    }

    @Override
    public int getItemCount() {
        return users_id.size();
    }

    public class UsersViewHolder extends RecyclerView.ViewHolder {

        TextView users_id,users_login,users_name;
        androidx.constraintlayout.widget.ConstraintLayout Background;

        public UsersViewHolder(@NonNull View itemView) {
            super(itemView);
            users_id = itemView.findViewById(R.id.id_users_row);
            users_name = itemView.findViewById(R.id.name_users_row);
            users_login = itemView.findViewById(R.id.login_users_row);
            Background = itemView.findViewById(R.id.users_row_background);

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
                            recyclerViewClick.onItemSelected(selectedRowPosition,false);
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
