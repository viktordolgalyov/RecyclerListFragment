package com.recyclerlistfragment;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public abstract class SimpleAdapter<I, H extends BaseViewHolder> extends RecyclerView.Adapter<H> {
    private List<I> items;
    private OnItemClickListener clickListener;

    protected SimpleAdapter(List<I> items) {
        if (items == null) {
            this.items = new ArrayList<>();
        } else {
            this.items = items;
        }
    }

    protected SimpleAdapter(List<I> items, OnItemClickListener clickListener) {
        this(items);
        this.clickListener = clickListener;
    }

    @Override
    public H onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View item = inflater.inflate(onProvideItemLayout(), parent, false);
        return onProvideHolder(item, clickListener);
    }

    @Override
    public void onBindViewHolder(H holder, int position) {
        I item = items.get(position);
        fillHolder(item, holder);
    }

    void setItems(List<I> items) {
        this.items = items;
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }

    @Nullable
    protected OnItemClickListener getClickListener() {
        return clickListener;
    }

    @LayoutRes
    protected abstract int onProvideItemLayout();

    @NonNull
    protected abstract H onProvideHolder(View itemView, @Nullable OnItemClickListener clickListener);

    protected abstract void fillHolder(I item, H holder);
}
