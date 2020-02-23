package com.example.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.List;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.UserListItem> {

    Context context;
    List<String> mUserList;
    ItemClickListener listener;

    public UserListAdapter(Context context, List<String> mUserList, ItemClickListener listener) {
        this.context = context;
        this.mUserList = mUserList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public UserListItem onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_item, parent, false);
        final UserListItem holder = new UserListItem(view);



        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull UserListItem holder, int position) {
        holder.txtUserName.setText(mUserList.get(position));

        //Defining ClickListener for the View or users
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClicked(view,holder.getAdapterPosition());
            }
        });

    }

    @Override
    public int getItemCount() {
        return mUserList.size();
    }

    public class UserListItem extends RecyclerView.ViewHolder {
        TextView txtUserName;

        public UserListItem(@NonNull View itemView) {
            super(itemView);
            txtUserName = itemView.findViewById(R.id.txt_userName);
        }
    }
    public interface ItemClickListener{
        void onItemClicked(View v, int position);
    }
}
