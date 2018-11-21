package me.warriorg.mybatis.basic.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Setter @Getter @ToString
public class Blog implements Serializable {

    private int id;
    private String title;
    private String content;
    private Date createDt;
    private String authorId;

    private Author author;
}
