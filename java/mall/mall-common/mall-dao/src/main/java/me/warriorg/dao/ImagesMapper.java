package me.warriorg.dao;

import java.util.List;
import me.warriorg.pojo.Images;

public interface ImagesMapper {
    int deleteByPrimaryKey(Integer imgId);

    int insert(Images record);

    Images selectByPrimaryKey(Integer imgId);

    List<Images> selectAll();

    int updateByPrimaryKey(Images record);
}