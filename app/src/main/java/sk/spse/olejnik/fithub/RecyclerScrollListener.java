package sk.spse.olejnik.fithub;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public abstract class RecyclerScrollListener extends RecyclerView.OnScrollListener {
    private int visibleThreshold = 5; // Number of items from the end at which to start loading more data
    private int currentPage = 0;
    private int previousTotalItemCount = 0;
    private boolean loading = true;

    private LinearLayoutManager layoutManager;

    public RecyclerScrollListener(LinearLayoutManager layoutManager) {
        this.layoutManager = layoutManager;
    }


    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
        int totalItemCount = layoutManager.getItemCount();

        if (loading && totalItemCount > previousTotalItemCount) {
            loading = false;
            previousTotalItemCount = totalItemCount;
        }

        if (!loading && (lastVisibleItemPosition + visibleThreshold) >= totalItemCount) {
            currentPage++;
            onLoadMore(currentPage, totalItemCount, recyclerView);
            loading = true;
        }
    }

    public abstract void onLoadMore(int page, int totalItemsCount, RecyclerView view);
}