package com.mybatis.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mybatis.entity.Employee;

public interface EmployeeDynamicSQLMapper {

	/**
	 * ��̬SQL��ѯ��ʹ��if��ǩ
	 * @param employee
	 * @return
	 */
	List<Employee> listEmpsByConditionIf(Employee employee);
	
	/**
	 * ��̬SQL��ѯ��ʹ��trim��ǩ
	 * @param employee
	 * @return
	 */
	List<Employee> listEmpsByConditionTrim(Employee employee);
	
	/**
	 * ��̬SQL��ѯ��ʹ��choose��ǩ
	 * @param employee
	 * @return
	 */
	List<Employee> listEmpsByConditionChoose(Employee employee);
	
	/**
	 * ��̬SQL�޸ģ�ʹ��set��ǩ
	 * @param employee
	 */
	void updateEmp(Employee employee);
	
	/**
	 * ��̬SQL��ѯ��ʹ��foreach��ǩ
	 * @param ids
	 * @return
	 */
	List<Employee> listEmpsByConditionForeach(@Param("ids") List<Integer> ids);
	
	/**
	 * ������ӣ�ʹ��foreach��ǩ
	 * @param emps
	 */
	void insertEmps(@Param("emps") List<Employee> emps);
	
	/**
	 * ��̬SQL��ѯ��ʹ��mybatis�ṩ�����ò���
	 * @param employee
	 * @return
	 */
	List<Employee> listEmpsByConditionInnerParameter(Employee employee);
}
