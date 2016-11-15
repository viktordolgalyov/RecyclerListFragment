package com.recyclerlistfragment;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class BaseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private final View itemView;
    private OnItemClickListener listener;

    public BaseViewHolder(View itemView) {
        super(itemView);
        this.itemView = itemView;
    }

    public BaseViewHolder(View itemView, OnItemClickListener listener) {
        this(itemView);
        this.listener = listener;
        View clickableView = getClickableView();
        if (clickableView != null) {
            clickableView.setOnClickListener(this);
        }
    }

    @Nullable
    protected View getClickableView() {
        return itemView;
    }

    @Override
    public void onClick(View view) {
        if (this.listener != null && getAdapterPosition() != RecyclerView.NO_POSITION) {
            listener.onItemClick(getAdapterPosition());
        }
    }
}
