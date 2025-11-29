package com.example.tiktokexperience.data.repository;

import com.example.tiktokexperience.data.mock.MockDataSource;
import com.example.tiktokexperience.data.model.ExperienceItem;

import java.util.List;

public class ExperienceRepository {

    public List<ExperienceItem> loadData(int page) {
        return MockDataSource.getMockList(page * 10, 10);
    }
}
