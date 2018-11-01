package me.warriorg.mybatis.dao;

import me.warriorg.mybatis.domain.Author;

import java.util.Set;

/**
 * @author warrior
 */
public interface AuthorMapper {

    /***
     *
     * @return
     */
    Set<Author> findAll();

    /***
     *
     * @param id
     * @return
     */
    Author findById(String id);

    /**
     *
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
