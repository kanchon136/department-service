package department.com.departmentservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import department.com.departmentservice.entity.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
	
	Department findByDepartmentId(Long departMentId);
	Department findByDepartmentName(String departmentName);
	Department findByDepartmentCode(String departmentCode);
	
	Department findByDepartmentIdAndDepartmentNameAndDepartmentCode(Long departMentId,
			String departmentName, String departmentCode);
	
	boolean existsByDepartmentId(Long departMentId);
	boolean existsByDepartmentName(String departmentName);
	boolean existsByDepartmentCode(String departmentCode);
	
	@Query("select d from Department d where d.departmentName=:departmentName or d.departmentCode=:departmentCode")
	Department findbyDepartmentNameOrDepartmentCode(@Param("departmentName") String departmentName,@Param("departmentCode") String departmentCode);
	
 
}
