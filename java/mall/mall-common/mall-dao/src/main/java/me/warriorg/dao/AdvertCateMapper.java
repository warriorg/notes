package me.warriorg.dao;

import java.util.List;
import me.warriorg.pojo.AdvertCate;

public interface AdvertCateMapper {
    int deleteByPrimaryKey(Integer adCategoryId);

    int insert(AdvertCate record);

    AdvertCate selectByPrimaryKey(Integer adCategoryId);

    List<AdvertCate> selectAll();

    int updateByPrimaryKey(AdvertCate record);
}