package com.lemon.library.recyclerview;


import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.lemon.library.R;
import com.lemon.library.widgets.XSwipeRefreshLayout;

public class XRecyclerView extends LinearLayout {

    private RecyclerView mRecyclerView;
    private XSwipeRefreshLayout mSwipeRefreshLayout;
    private PullLoadMoreListener mPullLoadMoreListener;

    private boolean isLoadMoreEnabled = true;
    private boolean isRefresh = false;
    private boolean isLoadMore = false;

    private LinearLayout mFooterView;
    private Context mContext;


    public XRecyclerView(Context context) {
        super(context);
        initView(context);
    }

    public XRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {
        mContext = context;
        View view = LayoutInflater.from(context).inflate(R.layout.pull_loadmore_layout, null);
        mSwipeRefreshLayout = (XSwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        mFooterView = (LinearLayout) view.findViewById(R.id.footer_linearlayout);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (!isRefresh()) {
                    setIsRefresh(true);
                    refresh();
                }
            }
        });

        mRecyclerView.setHasFixedSize(true);
//        setLinearLayout();
        // ����Item���ӡ��Ƴ�����
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        //��ӷָ���
        //mRecyclerView.addItemDecoration(new DividerItemDecoration(
        //getActivity(), DividerItemDecoration.HORIZONTAL_LIST));
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int lastVisibleItem = 0;
                int firstVisibleItem = 0;
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                int totalItemCount = layoutManager.getItemCount();
                if (layoutManager instanceof LinearLayoutManager) {
                    LinearLayoutManager linearLayoutManager = ((LinearLayoutManager) layoutManager);
                    lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                    firstVisibleItem = linearLayoutManager.findFirstCompletelyVisibleItemPosition();
                } else if (layoutManager instanceof GridLayoutManager) {
                    GridLayoutManager gridLayoutManager = ((GridLayoutManager) layoutManager);
                    //ͨ��LayoutManager�ҵ���ǰ��ʾ������item��position
                    lastVisibleItem = gridLayoutManager.findLastVisibleItemPosition();
                    firstVisibleItem = gridLayoutManager.findFirstCompletelyVisibleItemPosition();
                } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                    StaggeredGridLayoutManager staggeredGridLayoutManager = ((StaggeredGridLayoutManager) layoutManager);
                    //��ΪStaggeredGridLayoutManager�������Կ��ܵ��������ʾ��item���ڶ������������ȡ������һ������
                    //�õ�����������ȡ��������positionֵ�����Ǹ����������ʾ��positionֵ��
                    int[] lastPositions = new int[((StaggeredGridLayoutManager) layoutManager).getSpanCount()];
                    staggeredGridLayoutManager.findLastVisibleItemPositions(lastPositions);
                    lastVisibleItem = findMax(lastPositions);
                    firstVisibleItem = staggeredGridLayoutManager.findFirstVisibleItemPositions(lastPositions)[0];
                }
                if (firstVisibleItem == 0) {
                    if (!isLoadMore()) {
                        setPullRefreshEnable(true);
                    }
                } else {
                    setPullRefreshEnable(false);
                }

                /**
                 * ����ˮƽ���Ǵ�ֱ
                 */
                if (!isRefresh() && isLoadMoreEnabled() && (lastVisibleItem >= totalItemCount - 1)
                        && !isLoadMore() && (dx > 0 || dy > 0)) {
                    setIsLoadMore(true);
                    loadMore();
                }
            }
        });
    }

    /**
     * ���Բ��ֹ�����
     */
    public void setLinearLayoutManager() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
    }

    /**
     * ���񲼾ֹ�����
     */
    public void setGridLayoutManager() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 2);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        // ���ò��ֹ�����
        mRecyclerView.setLayoutManager(gridLayoutManager);
    }

    /**
     * �������񲼾ֹ�����
     */
    public void setStaggeredGridLayoutManager() {
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
        // ���ò��ֹ�����
        mRecyclerView.setLayoutManager(staggeredGridLayoutManager);
    }

    //�ҵ������е����ֵ
    private int findMax(int[] lastPositions) {

        int max = lastPositions[0];
        for (int value : lastPositions) {
            //       int max    = Math.max(lastPositions,value);
            if (value > max) {
                max = value;
            }
        }
        return max;
    }

    public void refresh() {
        mRecyclerView.setVisibility(View.VISIBLE);
        if (mPullLoadMoreListener != null) {
            mPullLoadMoreListener.onRefresh();
        }
    }

    public void loadMore() {
        if (mPullLoadMoreListener != null && isLoadMoreEnabled) {
            mFooterView.setVisibility(View.VISIBLE);
            mPullLoadMoreListener.onLoadMore();
        }
    }

    public void setPullRefreshEnable(boolean enable) {
        mSwipeRefreshLayout.setEnabled(enable);
    }

    public boolean isPullRefreshEnable() {
        return mSwipeRefreshLayout.isEnabled();
    }

    public void setRefreshing(final boolean isRefreshing) {
        mSwipeRefreshLayout.post(new Runnable() {

            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(isRefreshing);
            }
        });
    }

    /**
     * ���ظ������,Ϊ��ֹƵ����������,isLoadMoreΪfalse�ſ��ٴ������������
     */
    public void setCompleted() {
        isRefresh = false;
        mSwipeRefreshLayout.setRefreshing(false);

        isLoadMore = false;
        mFooterView.setVisibility(View.GONE);
    }


    public void scrollToTop() {
        mRecyclerView.scrollToPosition(0);
    }

    public void setAdapter(RecyclerView.Adapter adapter) {
        if (adapter != null) {
            mRecyclerView.setAdapter(adapter);
        }
    }

    public void setLoadMoreEnabled(boolean enabled) {
        this.isLoadMoreEnabled = enabled;
    }

    public boolean isLoadMoreEnabled() {
        return isLoadMoreEnabled;
    }

    public boolean isLoadMore() {
        return isLoadMore;
    }

    public void setIsLoadMore(boolean isLoadMore) {
        this.isLoadMore = isLoadMore;
    }

    public boolean isRefresh() {
        return isRefresh;
    }

    public void setIsRefresh(boolean isRefresh) {
        this.isRefresh = isRefresh;
    }

    public void setPullLoadMoreListener(PullLoadMoreListener listener) {
        mPullLoadMoreListener = listener;
    }

    public interface PullLoadMoreListener {
        public void onRefresh();

        public void onLoadMore();
    }

}
