package com.example.tiktokexperience.ui.experience;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.*;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.graphics.Rect;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageButton;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.tiktokexperience.R;

public class ExperienceActivity extends AppCompatActivity {

    private ExperienceViewModel viewModel;
    private ExperienceAdapter adapter;
    private SwipeRefreshLayout swipe;

    private StaggeredGridLayoutManager layoutManager;
    private int span = 2; // ⭐ 默认 2 列

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_experience);

        RecyclerView recycler = findViewById(R.id.recyclerExperience);
        swipe = findViewById(R.id.swipeRefresh);
        ImageButton btnSwitch = findViewById(R.id.btnSwitch); // ⭐ 切换按钮

        // ⭐ 避免底色发白
        recycler.setBackgroundColor(0xFF111111);

        // ViewModel
        viewModel = new ViewModelProvider(this).get(ExperienceViewModel.class);

        // ⭐ 初始两列瀑布流
        layoutManager = new StaggeredGridLayoutManager(span, StaggeredGridLayoutManager.VERTICAL);
        layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        recycler.setLayoutManager(layoutManager);

        // Adapter
        adapter = new ExperienceAdapter(viewModel.dataList.getValue(), this);
        recycler.setAdapter(adapter);

        // LiveData
        viewModel.dataList.observe(this, list -> adapter.notifyDataSetChanged());

        // 加载第一页
        viewModel.loadMore();

        // 下拉刷新
        swipe.setOnRefreshListener(() -> {
            viewModel.refresh();
            swipe.setRefreshing(false);
        });

        // ⭐⭐⭐ 预加载 + 加载更多逻辑（附加功能 1）
        recycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView rv, int dx, int dy) {
                super.onScrolled(rv, dx, dy);

                // ⭐ 滚到底部→加载更多
                if (!rv.canScrollVertically(1)) {
                    viewModel.loadMore();
                }

                // ⭐⭐ 图片预加载：当前最后一个 +3 位置
                int[] lastPos = layoutManager.findLastVisibleItemPositions(null);
                int last = Math.max(lastPos[0], lastPos[1]);

                if (last + 3 < viewModel.dataList.getValue().size()) {
                    String preloadUrl = viewModel.dataList.getValue().get(last + 3).coverUrl;

                    Glide.with(ExperienceActivity.this)
                            .load(preloadUrl)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .preload();  // ⭐⭐⭐ 预加载
                }
            }
        });

        // ⭐ 统一控制 item 间距
        recycler.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view,
                                       RecyclerView parent,
                                       RecyclerView.State state) {

                int spaceH = dp(4);   // 左右 4dp
                int spaceV = dp(4);   // 上下 4dp

                outRect.left = spaceH;
                outRect.right = spaceH;
                outRect.top = spaceV;
                outRect.bottom = spaceV;
            }
        });

        // ⭐⭐⭐ 单列 <-> 双列切换（附加功能 3 预留）
        btnSwitch.setOnClickListener(v -> {

            span = (span == 2) ? 1 : 2;

            layoutManager = new StaggeredGridLayoutManager(
                    span,
                    StaggeredGridLayoutManager.VERTICAL
            );
            layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);

            recycler.setLayoutManager(layoutManager);
        });
    }

    // dp 转 px
    private int dp(int dp) {
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp,
                getResources().getDisplayMetrics()
        );
    }
}

