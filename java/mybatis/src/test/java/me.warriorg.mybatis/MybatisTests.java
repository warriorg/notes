package me.warriorg.mybatis;

import me.warriorg.mybatis.dao.AuthorMapper;
import me.warriorg.mybatis.domain.Author;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.Set;
import java.util.UUID;


public class MybatisTests {
    private final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    /***
     * 制造测试数据
     */
    @Test
    public void setupData() {
        SqlSession sqlSession = MybatisUtils.getSqlSessionFactory().openSession();
        try {
            AuthorMapper authorMapper = sqlSession.getMapper(AuthorMapper.class);
            Author author = new Author();
            author.setId("1");
            author.setName("云飞");
            author.setEmail("xx@126.com");
            authorMapper.insertAuthor(author);
            sqlSession.commit();

        } finally {
            sqlSession.close();
        }

    }

    @Test
    public void findAllTest() {
        SqlSession sqlSession = MybatisUtils.getSqlSessionFactory().openSession();
        try {
            AuthorMapper authorMapper = sqlSession.getMapper(AuthorMapper.class);
            Set<Author> authors = authorMapper.findAll();
            logger.debug("测试查询 返回 数据列表: {}", authors);
        } finally {
            if (sqlSession != null) {
                sqlSession.close();
            }
        }
    }

    @Test
    public void findAllResultMap() {
        SqlSession sqlSession = MybatisUtils.getSqlSessionFactory().openSession();
        try {
            AuthorMapper authorMapper = sqlSession.getMapper(AuthorMapper.class);
            Set<Author> authors = authorMapper.findAllResultMap();
            logger.debug("测试查询 返回 数据列表: {}", authors);
        } finally {
            if (sqlSession != null) {
                sqlSession.close();
            }
        }
    }

    @Test
    public void findAuthorBlogTest() {
        SqlSession sqlSession = MybatisUtils.getSqlSessionFactory().openSession();
        try {
            AuthorMapper authorMapper = sqlSession.getMapper(AuthorMapper.class);
            Set<Author> authors = authorMapper.findAuthorBlog();
            logger.debug("测试查询 返回 数据列表: {}", authors);
        } finally {
            if (sqlSession != null) {
                sqlSession.close();
            }
        }
    }

    @Test
    public void findByIdTest() {
        SqlSession sqlSession = MybatisUtils.getSqlSessionFactory().openSession();
        try {
            AuthorMapper authorMapper = sqlSession.getMapper(AuthorMapper.class);
            Author author = authorMapper.findById("1");
            logger.debug("测试查询 返回 数据列表: {}", author);
        } finally {
            if (sqlSession != null) {
                sqlSession.close();
            }
        }
    }



    @Test
    public void insertAuthorTest() {
        SqlSession sqlSession = MybatisUtils.getSqlSessionFactory().openSession();
        try {
            AuthorMapper authorMapper = sqlSession.getMapper(AuthorMapper.class);
            Author author = new Author();
            author.setId(UUID.randomUUID().toString());
            author.setName("云飞");
            author.setEmail("xx@126.com");
            int result = authorMapper.insertAuthor(author);
            sqlSession.commit();
            logger.debug("测试插入 返回受影响的条数: {}", result);
        } finally {
            if (sqlSession != null) {
                sqlSession.close();
            }
        }
    }

    @Test
    public void insertAuthorNullIdTest() {
        SqlSession sqlSession = MybatisUtils.getSqlSessionFactory().openSession();
        try {
            AuthorMapper authorMapper = sqlSession.getMapper(AuthorMapper.class);
            Author author = new Author();
            author.setName("云飞");
            author.setEmail("xx@126.com");
            int result = authorMapper.insertAuthorNullId(author);
            sqlSession.commit();
            Assertions.assertNotNull(author.getId());
            logger.debug("测试插入 返回受影响的条数: {}, 插入的对象：{}", result, author);
        } finally {
            if (sqlSession != null) {
                sqlSession.close();
            }
        }
    }

    @Test
    public void updateAuthorById() {
        SqlSession sqlSession = MybatisUtils.getSqlSessionFactory().openSession();
        try {
            AuthorMapper authorMapper = sqlSession.getMapper(AuthorMapper.class);
            Author author = new Author();
            author.setId("1");
            author.setName("张五");
            author.setEmail("xx@126.com");
            int result = authorMapper.updateAuthorById(author);
            sqlSession.commit();
            logger.debug("测试更新 返回受影响的条数: {}", result);
        } finally {
            if (sqlSession != null) {
                sqlSession.close();
            }
        }
    }

    @Test
    public void deleteAuthorById() {
        SqlSession sqlSession = MybatisUtils.getSqlSessionFactory().openSession();
        try {
            AuthorMapper authorMapper = sqlSession.getMapper(AuthorMapper.class);
            int result = authorMapper.deleteAuthorById("1");
            sqlSession.commit();
            logger.debug("测试删除 返回受影响的条数: {}", result);
        } finally {
            if (sqlSession != null) {
                sqlSession.close();
            }
        }
    }

}
