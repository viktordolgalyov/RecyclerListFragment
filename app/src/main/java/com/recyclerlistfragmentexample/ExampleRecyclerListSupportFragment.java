package com.recyclerlistfragmentexample;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.recyclerlistfragment.LoadMoreScrollListener.OnLoadMoreListener;
import com.recyclerlistfragment.OnItemClickListener;
import com.recyclerlistfragment.RecyclerListSupportFragment;

import java.util.List;

public class ExampleRecyclerListSupportFragment extends RecyclerListSupportFragment<DataModel>
        implements OnItemClickListener, OnLoadMoreListener {
    private static final int VISIBLE_OFFSET = 15;

    public static Fragment create() {
        return new ExampleRecyclerListSupportFragment();
    }

    @Nullable
    @Override
    protected RecyclerView.Adapter onProvideAdapter() {
        return new SimpleAdapterExample(getItems(), this);
    }

    @Override
    public void onItemClick(int itemPosition) {
        DataModel model = getItem(itemPosition);
        if (model == null) return;
        Toast.makeText(
                getActivity(),
                String.format("%s %s", model.getTitle(), model.getDescription()),
                Toast.LENGTH_SHORT
        ).show();
    }

    @Override
    public void onLoadMore(int lastVisibleItem, int offset) {
        if (!isLoadingData() && offset > getAdapterSize() - VISIBLE_OFFSET) {
            notifyLoadingDataStart();
            List<DataModel> data = StubDataProvider.createStubItems(getAdapterSize());
            notifyLoadingDataEnd();
            addItems(data);
        }
    }
}
