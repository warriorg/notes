package me.warriorg.dao;

import java.util.List;
import me.warriorg.pojo.Advert;

public interface AdvertMapper {
    int deleteByPrimaryKey(Integer adId);

    int insert(Advert record);

    Advert selectByPrimaryKey(Integer adId);

    List<Advert> selectAll();

    int updateByPrimaryKey(Advert record);
}