package com.example.tiktokexperience.data.mock;

import com.example.tiktokexperience.data.model.ExperienceItem;
import java.util.*;

public class MockDataSource {

    public static List<ExperienceItem> getMockList(int start, int count) {
        List<ExperienceItem> list = new ArrayList<>();
        Random random = new Random();

        for (int i = start; i < start + count; i++) {
            ExperienceItem item = new ExperienceItem();

            item.id = i;
            item.title = "这是体验内容：" + i;
            item.username = "User " + i;
            item.likes = (int) (Math.random() * 3000);
            item.liked = false;

            // ⭐ Mock 网络封面图
            item.coverUrl = "https://picsum.photos/400/500?random=" + item.id;

            // ⭐ Mock 用户头像
            item.userAvatar =
                    "https://api.dicebear.com/7.x/bottts/png?seed=" + item.id;

            // ⭐⭐ 你要加在这里 ↓↓↓
            android.util.Log.d("TEST", "Mock生成头像URL = " + item.userAvatar);

            list.add(item);
        }

        return list;
    }
}
