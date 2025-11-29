package com.example.tiktokexperience.ui.experience;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.tiktokexperience.data.model.ExperienceItem;

import java.util.ArrayList;
import java.util.List;

public class ExperienceViewModel extends ViewModel {

    public MutableLiveData<List<ExperienceItem>> dataList =
            new MutableLiveData<>(new ArrayList<>());

    private int page = 0;

    // ⭐ 单条 Mock 数据
    private ExperienceItem generateMockItem(int index) {
        ExperienceItem item = new ExperienceItem();

        // 随机封面（可显示）
        item.coverUrl = "https://picsum.photos/seed/cover" + index + "/400/300";

        // ⭐⭐ 正确头像（必须带 img=id 和时间戳避免缓存）
        item.userAvatar = "https://i.pravatar.cc/150?img=" + (index % 70)
                + "&t=" + System.currentTimeMillis();

        item.title = "这是体验内容：" + index;
        item.username = "User " + index;

        item.likes = (int) (200 + Math.random() * 3000);
        item.liked = false;

        return item;
    }

    // 一页 10 条
    private List<ExperienceItem> generateMockPage(int page) {
        List<ExperienceItem> list = new ArrayList<>();
        int start = page * 10;

        for (int i = 0; i < 10; i++) {
            list.add(generateMockItem(start + i));
        }
        return list;
    }

    // 加载更多
    public void loadMore() {
        List<ExperienceItem> old = dataList.getValue();
        old.addAll(generateMockPage(page));
        dataList.setValue(old);
        page++;
    }

    // 下拉刷新
    public void refresh() {
        page = 0;
        dataList.setValue(generateMockPage(page));
        page++;
    }
    public void preloadNextPage() {
        generateMockPage(page + 1);   // 仅生成、不显示
    }

}
