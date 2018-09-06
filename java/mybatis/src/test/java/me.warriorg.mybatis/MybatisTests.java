package me.warriorg.mybatis;

import me.warriorg.mybatis.po.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.InputStream;


public class MybatisTests {
	
	private static SqlSessionFactory sqlSessionFactory;

	/**
	 * @Before注解的方法会在@Test注解的方法之前执行
	 * 
	 * @throws Exception
	 */
	@BeforeAll
	static void init() throws Exception {
		//指定全局配置文件路径
		String resource = "SqlMapperConfig.xml";
		//加载资源文件（全局配置文件和映射文件）
		InputStream inputStream = Resources.getResourceAsStream(resource);
		//还有构建者模式，去创建SqlSessionFactory对象
		sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream );
		//设计模式分三类23种：创建型（5）、结构型（7）、行为型（11）
	}

	@Test
	public void testFindUserById() {
		//由SqlSessionFactory工厂去创建SqlSession（会话）
		SqlSession sqlSession = sqlSessionFactory.openSession();
		
		//调用sqlsession接口，去实现数据库的增删改查操作
		//参数1：statement的id值（可以不加namespace）：namespace+"."+statementID
		//参数2：唯一入参
		User user = sqlSession.selectOne("test.findUserById", 1);
		
		System.out.println(user);
		//释放资源
		sqlSession.close();
	}

}
