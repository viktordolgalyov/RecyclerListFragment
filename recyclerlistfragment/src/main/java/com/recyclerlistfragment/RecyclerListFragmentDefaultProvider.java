package com.recyclerlistfragment;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.v4.util.ArrayMap;
import android.support.v4.util.Pair;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.recyclerlistfragment.LoadMoreScrollListener.OnLoadMoreListener;

import java.util.List;

final class RecyclerListFragmentDefaultProvider {

    static RecyclerView.OnScrollListener onProvideScrollListener(OnLoadMoreListener listener) {
        return new LoadMoreScrollListener(listener);
    }

    @LayoutRes
    static int onProvideLayoutId() {
        return R.layout.fragment_recycler_list;
    }

    @IdRes
    static int onProvideListId() {
        return R.id.list;
    }

    static boolean hasFixedSize() {
        return true;
    }

    static RecyclerView.LayoutManager onProvideLayoutManager(Context context) {
        if (context != null) {
            return new LinearLayoutManager(context);
        }
        return null;
    }

    static RecyclerView.ItemAnimator onProvideItemAnimator() {
        return new DefaultItemAnimator();
    }

    static List<RecyclerView.ItemDecoration> onProvideItemDecorations() {
        return null;
    }

    static RecyclerView.ItemDecoration onProvideItemDecoration() {
        return null;
    }

    static ArrayMap<Integer, RecyclerView.ItemDecoration> onProvideIndexedItemDecorations() {
        return null;
    }

    static Pair<Integer, RecyclerView.ItemDecoration> onProvideIndexedItemDecoration() {
        return null;
    }
}
