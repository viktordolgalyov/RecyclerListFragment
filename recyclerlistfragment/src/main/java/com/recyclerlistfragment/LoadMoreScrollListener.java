package com.recyclerlistfragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

public class LoadMoreScrollListener extends RecyclerView.OnScrollListener {
    private LoadMoreScrollListener.OnLoadMoreListener listener;

    LoadMoreScrollListener(LoadMoreScrollListener.OnLoadMoreListener listener) {
        this.listener = listener;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        int visibleItems = getLastVisibleItemPosition(recyclerView == null ? null : recyclerView.getLayoutManager());
        int offset = recyclerView == null ? 0 : recyclerView.getAdapter() == null ? 0 : recyclerView.getAdapter().getItemCount();
        if (listener != null && visibleItems >= 0) {
            listener.onLoadMore(visibleItems, offset);
        }
    }

    private int getLastVisibleItemPosition(RecyclerView.LayoutManager manager) {
        if (manager instanceof LinearLayoutManager) {
            return ((LinearLayoutManager) manager).findLastVisibleItemPosition();
        } else if (manager instanceof StaggeredGridLayoutManager) {
            int[] positionTarget = new int[((StaggeredGridLayoutManager) manager).getSpanCount()];
            ((StaggeredGridLayoutManager) manager).findLastCompletelyVisibleItemPositions(positionTarget);
            return positionTarget[positionTarget.length - 1];
        }
        return -1;
    }

    public interface OnLoadMoreListener {
        void onLoadMore(int lastVisibleItem, int offset);
    }
}
