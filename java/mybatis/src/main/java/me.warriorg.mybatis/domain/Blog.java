package me.warriorg.mybatis.domain;

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
    private Date date;

    private Author author;
}
