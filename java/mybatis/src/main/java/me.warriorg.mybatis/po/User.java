package me.warriorg.mybatis.po;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class User {
    private int id;
    private String name;
    private int gender;
    private String address;
}