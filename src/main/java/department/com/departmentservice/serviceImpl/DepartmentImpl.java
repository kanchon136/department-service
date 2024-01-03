package department.com.departmentservice.serviceImpl;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
 
import org.springframework.stereotype.Service;

import department.com.departmentservice.dto.ApiResponse;
import department.com.departmentservice.entity.Department;
import department.com.departmentservice.repository.DepartmentRepository;
import department.com.departmentservice.service.DepartmentService;

@Service
public class DepartmentImpl  implements DepartmentService{
	
	@Autowired
    private DepartmentRepository repo;
	
	@Override
	public Department saveDepartment(Department department) {
		 
		return repo.save(department);
	}

	@Override
	public Department findDepartmentById(Long departmentId) {
	 
		return repo.findById(departmentId).get();
	}

	@Override
	public Department findByDepartmentName(String departmentName) {
		
		return repo.findByDepartmentName(departmentName);
	}

	@Override
	public Department findByDepartmentCode(String departmentCode) {
		 
		return repo.findByDepartmentCode(departmentCode);
	}

	@Override
	public Department findByDepartmentIdAndDepartmentNameAndDepartmentCode(Long departMentId, String departmentName,
			String departmentCode) {
		 
		return null;
	}

	@Override
	public List<Department> findAllDepartments() {
		 
		return repo.findAll().parallelStream()
			//	.filter(d-> StringUtils.isNotBlank(d.getDepartmentName()) && StringUtils.isNotEmpty(d.getDepartmentCode()))
				.sorted(Comparator.comparing(Department::getDepartmentName, Comparator.nullsLast(String.CASE_INSENSITIVE_ORDER))
						.thenComparing(Department::getDepartmentCode,Comparator.nullsLast(String.CASE_INSENSITIVE_ORDER)))
				.collect(Collectors.toList());
	}

	@Override
	public ApiResponse saveDepartment(Department department, Long departmentID) {
	         Department department1 = repo.findByDepartmentId(departmentID);
	         ApiResponse apiResponse = new ApiResponse();
	         if(department1 != null) {
	        	 department1.setDepartmentId(departmentID); 
	        	 department1.setDepartmentAddress(department.getDepartmentAddress());
	        	 department1.setDepartmentCode(department.getDepartmentCode());
	        	 department1.setDepartmentName(department.getDepartmentName());
	        	 repo.save(department1);
	         }
		
		
		return apiResponse;
	}

	@Override
	public ApiResponse deleteDepartmentById(Long deptId) {
		 
		Optional<Department> deOptional = repo.findById(deptId);
		if(deOptional.isPresent()) {
			repo.delete(deOptional.get());
			return new ApiResponse(true, "Delete SuccessFully");
		}else {
			return new ApiResponse(false, "Department Not Found:"+deptId);
		}
		
		 
		
	}

}
