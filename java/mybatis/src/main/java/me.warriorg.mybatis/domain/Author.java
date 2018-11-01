package me.warriorg.mybatis.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author warrior
 */
@Setter @Getter @ToString
public class Author {
    private String id;
    private String name;
    private String email;
}
