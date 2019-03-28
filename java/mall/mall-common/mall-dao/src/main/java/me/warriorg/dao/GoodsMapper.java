package me.warriorg.dao;

import java.util.List;
import me.warriorg.pojo.Goods;

public interface GoodsMapper {
    int deleteByPrimaryKey(Integer goodsId);

    int insert(Goods record);

    Goods selectByPrimaryKey(Integer goodsId);

    List<Goods> selectAll();

    int updateByPrimaryKey(Goods record);
}