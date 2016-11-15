package com.recyclerlistfragmentexample;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.recyclerlistfragment.BaseViewHolder;
import com.recyclerlistfragment.OnItemClickListener;
import com.recyclerlistfragment.SimpleAdapter;

import java.util.List;

public class SimpleAdapterExample extends SimpleAdapter<DataModel, SimpleAdapterExample.DataViewHolder> {

    public SimpleAdapterExample(List<DataModel> items, OnItemClickListener clickListener) {
        super(items, clickListener);
    }

    @Override
    protected int onProvideItemLayout() {
        return R.layout.item_data;
    }

    @NonNull
    @Override
    protected DataViewHolder onProvideHolder(View itemView, @Nullable OnItemClickListener clickListener) {
        return new DataViewHolder(itemView, clickListener);
    }

    @Override
    protected void fillHolder(DataModel item, DataViewHolder holder) {
        holder.icon.setImageResource(item.getIcon());
        holder.description.setText(item.getDescription());
        holder.title.setText(item.getTitle());
    }

    static class DataViewHolder extends BaseViewHolder {
        ImageView icon;
        TextView title;
        TextView description;

        DataViewHolder(View itemView, OnItemClickListener listener) {
            super(itemView, listener);
            icon = (ImageView) itemView.findViewById(R.id.icon);
            title = (TextView) itemView.findViewById(R.id.title);
            description = (TextView) itemView.findViewById(R.id.description);
        }
    }
}
