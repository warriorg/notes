package me.warriorg.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Advert implements Serializable {
    private Integer adId;

    private String title;

    private String url;

    private String pic;

    private String sort;

    private String valid;

    private Date ontime;

    private Date offtime;

    private Integer categoryId;

    private Integer createId;

    private Date createTime;

    private Integer updateId;

    private Date updateTime;

    private String isDelete;

    private String status;

    private String bak1;

    private String bak2;

    private String bak3;


}