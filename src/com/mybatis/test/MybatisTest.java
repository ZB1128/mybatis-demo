package com.mybatis.test;


import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.javassist.bytecode.stackmap.TypeData.ClassName;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.Test;

import com.mybatis.dao.DepartmentMapper;
import com.mybatis.dao.EmployeeAnnotationMapper;
import com.mybatis.dao.EmployeeMapper;
import com.mybatis.dao.EmployeeMapperPlus;
import com.mybatis.entity.Department;
import com.mybatis.entity.Employee;

/**
 * 
 * 1���ӿ�ʽ���
 * 		ԭ����Dao	->		DaoImpl
 * 		mybatis��Mapper	->	xxxMapper.xml
 * 2��SqlSession��������ݿ��һ�λỰ���������ر�
 *  SqlSession��connectionһ�������Ƿ��̰߳�ȫ��ÿ��ʹ�ö�
 *  Ӧ��ȥ��ȡ�µĶ���
 * 3��Mapper�ӿ�û��ʵ���࣬����mybatis��Ϊ����ӿ�����һ��������󣨽��ӿں�xml���а󶨣�
 * 		EmployeeMapper employeeMapper = sqlSession.getMapper(EmployeeMapper.class);
 * 4��������Ҫ�������ļ���
 * 		mybatis��ȫ�������ļ����������ݿ����ӳ���Ϣ�������������Ϣ�ȣ�
 * 		sqlӳ���ļ���������ÿһ��sql����ӳ����Ϣ
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
	 * 1������xml�����ļ���ȫ�������ļ�������һ��SqlSessionFactory����������Դ�����л�������Ϣ��
	 * 2��sqlӳ���ļ���������ÿһ��sql�Լ�sql�ķ�װ����ȣ�
	 * 3����sqlӳ���ļ�ע���������ļ��У�
	 * 4��д���룺
	 * 		1)������ȫ�������ļ��õ�SqlSessionFactory��
	 * 		2)��ʹ��sqlSession��������ȡ��sqlSession����ʹ������ִ����ɾ�Ĳ�
	 * 			һ��sqlSession���Ǵ�������ݿ��һ�λỰ������رգ�
	 * 		3)��ʹ��sql��Ψһ��ʶ������MyBatisִ���ĸ�sql��sql���Ǳ�����sqlӳ���ļ��еģ�
	 * 
	 * @throws IOException
	 */
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

	
	@Test
	public void test02() throws IOException {
		//��ȡsqlSessionFactory����
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		//��ȡsqlSession����
		SqlSession openSession = sqlSessionFactory.openSession();
		try {
			//��ȡ��ڵ�ʵ�������
			//��Ϊ����Զ��Ĵ���һ��������󣬴������ȥִ����ɾ�Ĳ�ķ���
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
	
	/**
	 * 
	  *  ������ɾ�Ĳ�
	 * 1��mybatis������ɾ��ֱ�Ӷ����������ͷ���ֵ
	 * 		Integer��Long��Boolean��void
	 * 2����Ҫ�����ֶ��ύ����
	 * 		sqlSession.openSession(); -> �ֶ��ύ
	 * 		sqlSession.openSession(true); -> �Զ��ύ
	 * 
	 * @throws IOException
	 */
	@Test
	public void test04() throws IOException {
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		//1����ȡ�޲ι����SqlSession�ǲ����Զ��ύ�ģ���Ҫ�ֶ��ύ
		SqlSession openSession = sqlSessionFactory.openSession();
		try {
			EmployeeMapper employeeMapper = openSession.getMapper(EmployeeMapper.class);

			//��������
			Employee employee = new Employee(null, "lisi", 1, "lisi@qq.com");
			int insertEmp = employeeMapper.insertEmp(employee);
			System.out.println(insertEmp);
			System.out.println(employee);
			
			//����ɾ��
			/*boolean deleteEmpById = employeeMapper.deleteEmpById(1);
			System.out.println(deleteEmpById);*/
			
			//�����޸�
			//employeeMapper.updateEmpById(new Employee(2, "zhangsan", 1, "zhangsan@qq.com"));
			
			
			
			//2���ֶ��ύ
			openSession.commit();
		}finally {
			openSession.close();
		}
	}
	
	@Test
	public void test05() throws IOException {
		//��ȡsqlSessionFactory����
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		//��ȡsqlSession����
		SqlSession openSession = sqlSessionFactory.openSession();
		try {
			//��ȡ��ڵ�ʵ�������
			//��Ϊ����Զ��Ĵ���һ��������󣬴������ȥִ����ɾ�Ĳ�ķ���
			EmployeeMapper employeeMapper = openSession.getMapper(EmployeeMapper.class);
			Employee employee = employeeMapper.getEmpByIdAndlastName(2, "zhangsan");
			System.out.println(employee);
		} finally {
			openSession.close();
		}
	}
	
	/**
	 * ���Է���List
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	@Test
	public void test06() throws ClassNotFoundException, IOException {
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		SqlSession openSession = sqlSessionFactory.openSession();
		try {
			EmployeeMapper employeeMapper = openSession.getMapper(EmployeeMapper.class);
			List<Employee> listEmps = employeeMapper.listEmps();
			listEmps.forEach(System.out::println);
		}finally {
			openSession.close();
		}
	}
	
	/**
	 * ���Է���map
	 * @throws IOException
	 */
	@Test
	public void test07() throws IOException {
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		SqlSession openSession = sqlSessionFactory.openSession();
		try {
			EmployeeMapper employeeMapper = openSession.getMapper(EmployeeMapper.class);
			//Map<String, Object> empByIdReturnMap = employeeMapper.getEmpByIdReturnMap(2);
			Map<Integer, Employee> listEmpsReturnMap = employeeMapper.listEmpsReturnMap();
			for(Entry<Integer, Employee> item:listEmpsReturnMap.entrySet()) {
				System.out.println("key:"+item.getKey()+",value:"+item.getValue());
			}
		}finally {
			openSession.close();
		}
	}
	
	/**
	 * ���Է���resultMap
	 * @throws IOException
	 */
	@Test
	public void test08() throws IOException {
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		SqlSession openSession = sqlSessionFactory.openSession();
		try {
			EmployeeMapperPlus employeeMapperPlus = openSession.getMapper(EmployeeMapperPlus.class);
			Employee employee = employeeMapperPlus.getEmpById(2);
			System.out.println(employee);
		}finally {
			openSession.close();
		}
	}
	
	/**
	 * ����association
	 * @throws IOException
	 */
	@Test
	public void test09() throws IOException {
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		SqlSession openSession = sqlSessionFactory.openSession();
		try {
			EmployeeMapperPlus employeeMapperPlus = openSession.getMapper(EmployeeMapperPlus.class);
			Employee employee = employeeMapperPlus.getEmpByIdStep(3);
			System.out.println(employee.getLastName());
			System.out.println(employee.getDept());
		}finally {
			openSession.close();
		}
	}
	
	/**
	 * ����collection
	 * @throws IOException
	 */
	@Test
	public void test10() throws IOException {
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		SqlSession openSession = sqlSessionFactory.openSession();
		try {
			DepartmentMapper departmentMapper = openSession.getMapper(DepartmentMapper.class);
			//Department department = departmentMapper.getDeptById(1);
			Department department = departmentMapper.getDeptByIdStep(1);
			System.out.println(department.getDeptName());
			department.getEmps()
				.forEach(System.out::println);
		}finally {
			openSession.close();
		}
	}
	
}
