package com.kdk.springrest.dao;

import java.util.List;

import javax.sql.DataSource;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
 
import com.kdk.springrest.dto.Employee;
 
@Repository("employeeDao")
public class EmployeeDaoImpl implements EmployeeDao {
 
 private JdbcTemplate jdbcTemplate;
 
 @Autowired
 public void setDataSource(DataSource dataSource) {
  this.jdbcTemplate = new JdbcTemplate(dataSource);
 }
 
 public List<Employee> getEmployees() {
  List<Employee> employees = null ;
  
  try {
   employees = jdbcTemplate.query("SELECT * FROM employee",new BeanPropertyRowMapper<Employee>(Employee.class));   
  } catch (DataAccessException e) {
   e.printStackTrace();
  }
  return employees;
 }
 
 public Employee getEmployee(Long employeeId) {
  Employee employee = null;
  try {
   employee = jdbcTemplate.queryForObject("SELECT * FROM employee WHERE employee_id = ?",
     new Object[] { employeeId }, new BeanPropertyRowMapper<Employee>(Employee.class));
  } catch (DataAccessException e) {
   e.printStackTrace();
  }
  return employee;
 
 }
 
 public int deleteEmployee(Long employeeId) {
  int count = jdbcTemplate.update("DELETE from employee WHERE employee_id = ?", new Object[] { employeeId });
  return count;
 }
 
 public int updateEmployee(Employee employee) {
  int count = jdbcTemplate.update(
    "UPDATE employee set first_name = ? , last_name = ? , age = ? where employee_id = ?", new Object[] {
      employee.getFirstName(), employee.getLastName(), employee.getAge(), employee.getEmployeeId() });
  return count;
 }
 
 public int createEmployee(Employee employee) {
  int count = jdbcTemplate.update(
    "INSERT INTO employee(employee_id,first_name, last_name, age)VALUES(?,?,?,?)", new Object[] {
      employee.getEmployeeId(), employee.getFirstName(), employee.getLastName(), employee.getAge() });
  return count;
 }
 
}