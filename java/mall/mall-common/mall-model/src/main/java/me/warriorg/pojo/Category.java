package me.warriorg.pojo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Setter
@Getter
public class Category implements Serializable {
    private Integer categoryId;

    private String categoryName;

    private Integer parentId;

    private Integer level;

    private String isDelete;

    private Integer sort;

    private String status;

    private Date createTime;

    private Integer createId;

    private Date updateTime;

    private Integer updateId;

    private String bak1;

    private String bak2;

    private String bak3;
}