package com.org.retrofittest.RetrofittestPaging.PayEventModel;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.org.retrofittest.R;

import java.util.List;

public class PayEventPagedListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public Context context;
    public List<PayEventBean.AllPayListDTO> data;
    private final int TYPE_ITEM = 0;//正常的Item
    private final int TYPE_FOOT = 1;//尾部刷新
    private boolean hasMore = true;

    public PayEventPagedListAdapter(Context context, List<PayEventBean.AllPayListDTO> data) {

        this.data = data;
        this.context = context;
    }

    public void setData(List<PayEventBean.AllPayListDTO> data) {
        this.data = data;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            return new PayEventViewHolder(LayoutInflater.from(context).inflate(R.layout.paging_layout, parent, false));
        } else {
            return new PayEventFootHolder(LayoutInflater.from(context).inflate(R.layout.item_foot, parent, false));
        }
        /*if (viewType == TYPE_FOOT) {
            return new PayEventFootHolder(LayoutInflater.from(context).inflate(R.layout.item_foot, parent, false));
        } else{
            return new PayEventViewHolder(LayoutInflater.from(context).inflate(R.layout.paging_layout, parent, false));
        }*/
        //return new PayEventViewHolder(LayoutInflater.from(context).inflate(R.layout.paging_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof PayEventViewHolder) {
            PayEventBean.AllPayListDTO payListDTO = data.get(position);
            ((PayEventViewHolder) holder).cost.setText(String.valueOf(payListDTO.getCost()));
            ((PayEventViewHolder) holder).date.setText(payListDTO.getDate());
        } else {
            if (position == 0) {
                ((PayEventFootHolder) holder).linearLayout.setVisibility(View.INVISIBLE);
            } else {
                ((PayEventFootHolder) holder).linearLayout.setVisibility(View.VISIBLE);
            }
            if (isHasMore()) {
                ((PayEventFootHolder) holder).textView.setText("上拉加载更多");
            } else {
                ((PayEventFootHolder) holder).textView.setText("没有更多数据了");
            }
        }

//        Log.e("true",user.name);

    }

    public boolean isHasMore() {
        return hasMore;
    }

    public void setHasMore(boolean hasMore) {
        this.hasMore = hasMore;
    }

    /*@Override
        public void onBindViewHolder(@NonNull PayEventViewHolder holder, int position) {
            PayEventBean.AllPayListDTO payListDTO = data.get(position);
    //        Log.e("true",user.name);
            holder.cost.setText(String.valueOf(payListDTO.getCost()));
            holder.date.setText(payListDTO.getDate());

        }*/
    public void addData(List<PayEventBean.AllPayListDTO> addData) {
        data.addAll(addData);
        notifyItemRangeChanged(getItemCount() - 1, addData.size());
    }

    @Override
    public int getItemCount() {
        int itemCount = data == null ? 0 : data.size() + 1;
        Log.i("ItemCount", "itemCount ====================> " + itemCount);
        return itemCount;
    }

    @Override
    public int getItemViewType(int position) {
        Log.i("ItemCount", "position ====================> " + position);
        Log.i("ItemCount", "getItemCount() - 1 ====================> " + (getItemCount() - 1));
        if (position == getItemCount() - 1) {
            return TYPE_FOOT;
        }
        return TYPE_ITEM;
    }

    static class PayEventViewHolder extends RecyclerView.ViewHolder {
        private final TextView cost;
        private final TextView date;

        //private final View view;
        public PayEventViewHolder(@NonNull View itemView) {
            super(itemView);
            //view = itemView.findViewById(R.id.view);
            cost = itemView.findViewById(R.id.cost);
            date = itemView.findViewById(R.id.date);

        }
    }

    static class PayEventFootHolder extends RecyclerView.ViewHolder {
        private final TextView textView;
        private LinearLayout linearLayout;

        public PayEventFootHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.text_view);
            linearLayout = itemView.findViewById(R.id.linearLayout);
        }
    }

}
