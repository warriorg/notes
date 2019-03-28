package me.warriorg.service;

import me.warriorg.pojo.Category;

import java.util.List;

/**
 * @author: 高士勇
 * @date: 2019-03-28
 */
public interface CategoryService {

    List<Category> findCategoryTree();
}
