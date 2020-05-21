package com.java.randhirRetail.dto;

/**
 * 
 * @author randhirkumar
 *
 */

public class Employee {

	private final String empName;
	private final String empId;
	private final String empSalary;


	public Employee(String empName, String empId,String empSalary) {
		this.empName = empName;
		this.empId = empId;
		this.empSalary = empSalary;
	}


	public String getEmpName() {
		return empName;
	}


	public String getEmpId() {
		return empId;
	}


	public String getEmpSalary() {
		return empSalary;
	}

	
	
}
