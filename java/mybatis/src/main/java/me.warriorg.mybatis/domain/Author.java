package me.warriorg.mybatis.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Set;

/**
 * @author warrior
 */
@Setter @Getter @ToString
public class Author implements Serializable {

    private String id;
    private String name;
    private String email;

    private Set<Blog> blogs;
}
