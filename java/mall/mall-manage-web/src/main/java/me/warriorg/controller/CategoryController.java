package me.warriorg.controller;

import me.warriorg.pojo.Category;
import me.warriorg.service.CategoryService;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: 高士勇
 * @date: 2019-03-28
 */
@RestController
@RequestMapping("category")
@DubboComponentScan
public class CategoryController {

    @Reference
    private CategoryService categoryService;

    @RequestMapping("findTree")
    public List<Category> findCateGoryTree(){
        return categoryService.findCategoryTree();
    }
}
