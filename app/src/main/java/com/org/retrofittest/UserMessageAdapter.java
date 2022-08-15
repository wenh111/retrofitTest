package com.org.retrofittest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.org.retrofittest.RetrofittestPaging.UserBeen;

import java.util.List;

public class UserMessageAdapter extends RecyclerView.Adapter<UserMessageAdapter.LinearViewHolder> {
    private Context context;
    private List<UserBeen.UserMessageDTO> userData;


    public UserMessageAdapter(Context context, List<UserBeen.UserMessageDTO> userData) {
        this.context = context;
        this.userData = userData;
    }

    public List<UserBeen.UserMessageDTO> getUserData() {
        return userData;
    }

    public void setUserData(List<UserBeen.UserMessageDTO> userData) {
        this.userData = userData;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }



    @NonNull
    @Override
    public LinearViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LinearViewHolder(LayoutInflater.from(context).inflate(R.layout.recycler_view_item_user_message, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull LinearViewHolder holder, int position) {
        holder.name.setText(userData.get(position).getName());
        holder.account.setText(userData.get(position).getAccount());
        holder.password.setText(userData.get(position).getPassword());
        holder.telephoneNumber.setText(userData.get(position).getTelephone_number());
    }

    @Override
    public int getItemCount() {
        return userData == null ? 0 : userData.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    class LinearViewHolder extends RecyclerView.ViewHolder {
        private TextView name,account,password,telephoneNumber;
        public LinearViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            account = itemView.findViewById(R.id.account);
            password = itemView.findViewById(R.id.password);
            telephoneNumber = itemView.findViewById(R.id.telephoneNumber);

        }
    }
}
