package com.mybatis.test;


import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.Test;

import com.mybatis.dao.EmployeeAnnotationMapper;
import com.mybatis.dao.EmployeeMapper;
import com.mybatis.entity.Employee;

/**
 * 
 * 1、接口式编程
 * 		原生：Dao	->		DaoImpl
 * 		mybatis：Mapper	->	xxxMapper.xml
 * 2、SqlSession代表和数据库的一次会话，用完必须关闭
 *  SqlSession和connection一样它都是非线程安全，每次使用都
 *  应该去获取新的对象；
 * 3、Mapper接口没有实现类，但是mybatis会为这个接口生成一个代理对象（将接口和xml进行绑定）
 * 		EmployeeMapper employeeMapper = sqlSession.getMapper(EmployeeMapper.class);
 * 4、两个重要的配置文件：
 * 		mybatis的全局配置文件：包含数据库连接池信息，事务管理器信息等；
 * 		sql映射文件：保存了每一个sql语句的映射信息
 * 
 * @author Administrator
 *
 */
class MybatisTest {
	
	public SqlSessionFactory getSqlSessionFactory() throws IOException {
		String resource = "mybatis-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		return sqlSessionFactory;
	}

	/**
	 * 
	 * 1、根据xml配置文件（全局配置文件）创建一个SqlSessionFactory对象，有数据源等运行环境的信息；
	 * 2、sql映射文件，配置了每一个sql以及sql的封装规则等；
	 * 3、将sql映射文件注册在配置文件中；
	 * 4、写代码：
	 * 		1)、根据全局配置文件得到SqlSessionFactory；
	 * 		2)、使用sqlSession工厂，获取到sqlSession对象使用它来执行增删改查
	 * 			一个sqlSession就是代表和数据库的一次会话，用完关闭；
	 * 		3)、使用sql的唯一标识来告诉MyBatis执行哪个sql，sql都是保存在sql映射文件中的；
	 * 
	 * @throws IOException
	 */
	@Test
	public void test01() throws IOException {
		//1、获取SqlSessionFactory
		String resource = "mybatis-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		
		//2、获取sqlSession实例，能直接执行已经映射的sql语句
		SqlSession openSession = sqlSessionFactory.openSession();
		//sql的唯一标识，执行sql要用的参数
		Employee employee = openSession.selectOne("com.mybatis.EmployeeMapper.selectEmp", 1);
		System.out.println(employee);
		
		openSession.close();
	}

	
	@Test
	public void test02() throws IOException {
		//获取sqlSessionFactory对象
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		//获取sqlSession对象
		SqlSession openSession = sqlSessionFactory.openSession();
		try {
			//获取借口的实现类对象
			//会为借口自动的创建一个代理对象，代理对象去执行增删改查的方法
			EmployeeMapper employeeMapper = openSession.getMapper(EmployeeMapper.class);
			Employee employee = employeeMapper.getEmpById(1);
			System.out.println(employee);
		} finally {
			openSession.close();
		}
	}
	
	@Test
	public void test03() throws IOException {
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		SqlSession openSession = sqlSessionFactory.openSession();
		try {
			EmployeeAnnotationMapper mapper = openSession.getMapper(EmployeeAnnotationMapper.class);
			Employee empById = mapper.getEmpById(1);
			System.out.println(empById);
		}finally {
			openSession.close();
		}
	}
	
}
