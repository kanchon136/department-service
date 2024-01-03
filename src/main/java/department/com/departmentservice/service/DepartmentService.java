package department.com.departmentservice.service;

 

import java.util.List;

import department.com.departmentservice.dto.ApiResponse;
import department.com.departmentservice.entity.Department;


public interface DepartmentService {
	
 Department saveDepartment(Department department);
  Department findDepartmentById(Long departmentId);
 
  Department findByDepartmentName(String departmentName);
 Department findByDepartmentCode(String departmentCode);
 
 Department findByDepartmentIdAndDepartmentNameAndDepartmentCode(Long departMentId,
			String departmentName, String departmentCode);
 
  List<Department> findAllDepartments();
  
  ApiResponse saveDepartment(Department department,Long departmentID);
  
  ApiResponse deleteDepartmentById(Long DeptId);
 
}
