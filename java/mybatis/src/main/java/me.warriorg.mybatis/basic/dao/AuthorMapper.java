package me.warriorg.mybatis.basic.dao;


import me.warriorg.mybatis.basic.domain.Author;

import java.util.Set;

/**
 * @author warrior
 */
public interface AuthorMapper {

    void createTable();

    /***
     *
     * @return
     */
    Set<Author> findAll();

    /***
     *
     * @return
     */
    Set<Author> findAllResultMap();

    /***
     * 级连查询
     * @return
     */
    Set<Author> findAuthorBlog();

    /***
     *
     * @param id
     * @return
     */
    Author findById(String id);

    /**
     * @param author
     * @return
     */
    int insertAuthor(Author author);


    /***
     * 测试不设置主键的方式
     * @param author
     * @return
     */
    int insertAuthorNullId(Author author);

    /***
     *
     * @param author
     * @return
     */
    int updateAuthorById(Author author);

    /***
     *
     * @param id
     * @return
     */
    int deleteAuthorById(String id);


}
