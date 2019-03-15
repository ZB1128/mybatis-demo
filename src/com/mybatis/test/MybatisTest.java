package com.mybatis.test;


import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.Test;

import com.mybatis.entity.Employee;

class MybatisTest {

	@Test
	public void test01() throws IOException {
		//1����ȡSqlSessionFactory
		String resource = "mybatis-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		
		//2����ȡsqlSessionʵ������ֱ��ִ���Ѿ�ӳ���sql���
		SqlSession openSession = sqlSessionFactory.openSession();
		//sql��Ψһ��ʶ��ִ��sqlҪ�õĲ���
		Employee employee = openSession.selectOne("com.mybatis.EmployeeMapper.selectEmp", 1);
		System.out.println(employee);
		
		openSession.close();
		
	}

}
