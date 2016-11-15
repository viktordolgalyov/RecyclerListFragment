package com.recyclerlistfragment;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.util.ArrayMap;
import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.recyclerlistfragment.LoadMoreScrollListener.OnLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

public abstract class RecyclerListSupportFragment<T> extends Fragment {
    private RecyclerView list;
    private List<T> items;
    private boolean isLoadingData;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(onProvideLayoutId(), container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        list = ((RecyclerView) view.findViewById(onProvideListId()));
        initList();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (this instanceof OnLoadMoreListener) {
            ((OnLoadMoreListener) this).onLoadMore(0, 0);
        }
    }

    @CallSuper
    protected void initList() {
        if (list == null) {
            return;
        }
        list.setHasFixedSize(hasFixedSize());

        RecyclerView.ItemAnimator animator = onProvideItemAnimator();
        if (animator != null) {
            list.setItemAnimator(animator);
        }

        RecyclerView.LayoutManager manager = onProvideLayoutManager();
        if (manager != null) {
            list.setLayoutManager(manager);
        }

        RecyclerView.OnScrollListener scrollListener = onProvideScrollListener();
        if (scrollListener != null) {
            list.addOnScrollListener(scrollListener);
        }

        List<RecyclerView.ItemDecoration> decorations = onProvideItemDecorations();
        if (decorations != null) {
            for (RecyclerView.ItemDecoration itemDecoration : decorations) {
                list.addItemDecoration(itemDecoration);
            }
        }

        RecyclerView.ItemDecoration decoration = onProvideItemDecoration();
        if (decoration != null) {
            list.addItemDecoration(decoration);
        }

        ArrayMap<Integer, RecyclerView.ItemDecoration> indexedDecorations = onProvideIndexedItemDecorations();
        if (indexedDecorations != null) {
            for (Integer index : indexedDecorations.keySet()) {
                list.addItemDecoration(indexedDecorations.get(index), index);
            }
        }

        Pair<Integer, RecyclerView.ItemDecoration> indexedDecoration = onProvideIndexedItemDecoration();
        if (indexedDecoration != null) {
            list.addItemDecoration(indexedDecoration.second, indexedDecoration.first);
        }

        RecyclerView.Adapter adapter = onProvideAdapter();
        if (adapter != null) {
            list.setAdapter(adapter);
        }
    }

    @Nullable
    protected abstract RecyclerView.Adapter onProvideAdapter();

    @Nullable
    protected List<RecyclerView.ItemDecoration> onProvideItemDecorations() {
        return RecyclerListFragmentDefaultProvider.onProvideItemDecorations();
    }

    @Nullable
    protected RecyclerView.ItemDecoration onProvideItemDecoration() {
        return RecyclerListFragmentDefaultProvider.onProvideItemDecoration();
    }

    @Nullable
    protected ArrayMap<Integer, RecyclerView.ItemDecoration> onProvideIndexedItemDecorations() {
        return RecyclerListFragmentDefaultProvider.onProvideIndexedItemDecorations();
    }

    @Nullable
    protected Pair<Integer, RecyclerView.ItemDecoration> onProvideIndexedItemDecoration() {
        return RecyclerListFragmentDefaultProvider.onProvideIndexedItemDecoration();
    }

    @Nullable
    protected RecyclerView.ItemAnimator onProvideItemAnimator() {
        return RecyclerListFragmentDefaultProvider.onProvideItemAnimator();
    }

    protected RecyclerView.LayoutManager onProvideLayoutManager() {
        return RecyclerListFragmentDefaultProvider.onProvideLayoutManager(getActivity());
    }

    @Nullable
    protected RecyclerView.OnScrollListener onProvideScrollListener() {
        OnLoadMoreListener loadMoreListener = null;
        if (this instanceof OnLoadMoreListener) {
            loadMoreListener = (OnLoadMoreListener) this;
        }
        return RecyclerListFragmentDefaultProvider.onProvideScrollListener(loadMoreListener);
    }

    @LayoutRes
    protected int onProvideLayoutId() {
        return RecyclerListFragmentDefaultProvider.onProvideLayoutId();
    }

    @IdRes
    protected int onProvideListId() {
        return RecyclerListFragmentDefaultProvider.onProvideListId();
    }

    protected boolean hasFixedSize() {
        return RecyclerListFragmentDefaultProvider.hasFixedSize();
    }

    @Nullable
    protected RecyclerView getRecyclerView() {
        return list;
    }

    @Nullable
    protected RecyclerView.Adapter getAdapter() {
        return list == null ? null : list.getAdapter();
    }

    protected int getAdapterSize() {
        RecyclerView.Adapter adapter = getAdapter();
        return adapter == null ? 0 : adapter.getItemCount();
    }

    protected void notifyDataSetChanged() {
        if (list == null || list.getAdapter() == null) return;
        list.getAdapter().notifyDataSetChanged();
    }

    protected void addItems(List<T> items) {
        if (items == null) {
            return;
        }
        if (this.items == null) {
            this.items = new ArrayList<>();
        }
        if (getAdapter() == null || getAdapter().getItemCount() == 0) {
            RecyclerView.Adapter adapter = onProvideAdapter();
            RecyclerView view = getRecyclerView();
            if (view != null) {
                view.setAdapter(adapter);
            }
        }
        int previousCount = this.items.size();
        this.items.addAll(items);
        RecyclerView.Adapter adapter = getAdapter();
        if (adapter != null && list != null) {
            adapter.notifyItemRangeInserted(previousCount, items.size());
        }
    }

    protected void insertItem(T item, int index) {
        if (item == null || index < 0) {
            return;
        }
        if (items == null) {
            items = new ArrayList<>();
        }
        items.add(index, item);
        RecyclerView.Adapter adapter = getAdapter();
        if (adapter != null) {
            adapter.notifyItemInserted(index);
        }
    }

    protected void insertItem(T item) {
        insertItem(item, items == null ? 0 : items.size());
    }

    protected void removeItem(int index) {
        if (items == null || index >= items.size() || index < 0) {
            return;
        }
        items.remove(index);
        RecyclerView.Adapter adapter = getAdapter();
        if (adapter != null) {
            adapter.notifyItemRemoved(index);
        }
    }

    protected void moveItem(int oldPosition, int updatedPosition) {
        if (items == null || oldPosition < 0 || oldPosition >= items.size() || updatedPosition < 0 || updatedPosition >= items.size()) {
            return;
        }
        T item = items.get(oldPosition);
        items.remove(oldPosition);
        items.add(updatedPosition, item);
        RecyclerView.Adapter adapter = getAdapter();
        if (adapter != null) {
            adapter.notifyItemMoved(oldPosition, updatedPosition);
        }
    }

    @Nullable
    protected T getItem(int index) {
        if (items == null || index < 0 || index >= items.size()) {
            return null;
        }
        return items.get(index);
    }

    @Nullable
    protected List<T> getItems() {
        return items;
    }

    protected void notifyLoadingDataStart() {
        isLoadingData = true;
    }

    protected void notifyLoadingDataEnd() {
        isLoadingData = false;
    }

    protected boolean isLoadingData() {
        return isLoadingData;
    }
}
