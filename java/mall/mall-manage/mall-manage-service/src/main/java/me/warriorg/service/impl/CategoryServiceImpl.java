package me.warriorg.service.impl;

import me.warriorg.dao.CategoryMapper;
import me.warriorg.pojo.Category;
import me.warriorg.service.CategoryService;
import org.apache.dubbo.config.annotation.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @date: 2019-03-28
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Resource
    private CategoryMapper categoryMapper;

    @Override
    public List<Category> findCategoryTree() {
        return categoryMapper.selectAll();
    }
}
