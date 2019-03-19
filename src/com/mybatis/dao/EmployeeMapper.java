package com.mybatis.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import com.mybatis.entity.Employee;

public interface EmployeeMapper {
	
	
	Employee getEmpById(Integer id);
	
	Employee getEmpByIdAndlastName(@Param("id") Integer id,@Param("lastName") String lastName);
	
	List<Employee> listEmps();
	
	//����һ����¼��map��key����������ֵ���Ƕ�Ӧ��ֵ
	Map<String,Object> getEmpByIdReturnMap(Integer id);
	
	/*
	 * ������¼��װһ��map������������¼��������ֵ�Ǽ�¼��װ���javaBean
	 * @MapKey������mybatis��װ���map��ʱ��ʹ���ĸ�������Ϊ����
	 */
	@MapKey("id")
	Map<Integer,Employee> listEmpsReturnMap();
	
	int insertEmp(Employee employee);
	
	boolean deleteEmpById(Integer id);
	
	void updateEmpById(Employee empoyee);
	

}
