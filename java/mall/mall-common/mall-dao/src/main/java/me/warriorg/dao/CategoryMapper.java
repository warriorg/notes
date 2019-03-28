package me.warriorg.dao;

import java.util.List;
import me.warriorg.pojo.Category;

public interface CategoryMapper {
    int deleteByPrimaryKey(Integer categoryId);

    int insert(Category record);

    Category selectByPrimaryKey(Integer categoryId);

    List<Category> selectAll();

    int updateByPrimaryKey(Category record);
}