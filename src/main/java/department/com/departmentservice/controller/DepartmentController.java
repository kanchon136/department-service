package department.com.departmentservice.controller;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import department.com.departmentservice.dto.ApiResponse;
import department.com.departmentservice.entity.Department;
import department.com.departmentservice.service.DepartmentService;

@RestController
@RequestMapping("/department")
public class DepartmentController {
	@Autowired
	private DepartmentService service;
 
	
	@PostMapping("/saveDepartment")
	public ResponseEntity<?> saveDepartWithHttpResponse( @RequestBody Department department){
		ApiResponse response = new ApiResponse();
		try {
			service.saveDepartment(department);
			response.setStatus(true);
			response.setMessage("Save Successfully");
			return new ResponseEntity<ApiResponse>(response,HttpStatus.CREATED);
		}catch (Exception  e  ) {
			response.setStatus(false);
			response.setMessage("Not Success");
			return new ResponseEntity<ApiResponse>(response,HttpStatus.BAD_REQUEST);	 
		}
		
	}
	
	
	@PutMapping("/updateDepartment")
	public ResponseEntity<?> updateDepartment(@RequestBody Department department
			                                      ,@RequestParam("deptId") Long deptId){
		
		try {
			ApiResponse response = service.saveDepartment(department, deptId);
			response.setStatus(true);
			response.setMessage("Update Successful");
			return ResponseEntity.ok(response);
			
		}catch (Exception e) {
			ApiResponse response = new ApiResponse();
			response.setStatus(false);
			response.setMessage("Not Success");
			return new ResponseEntity<ApiResponse>(response, HttpStatus.NO_CONTENT);
		}	 
	}
	 
	 @DeleteMapping("/deleteDepartmetnById")
	   public ResponseEntity<?> deleteDepartmentById(@RequestParam(name="deptId") Long deptId) {
		  
		   try {
			ApiResponse response=   service.deleteDepartmentById(deptId);
			if(response.isStatus()) {
				return ResponseEntity.ok(response);
			}else {
				return new ResponseEntity<ApiResponse>(response,HttpStatus.NOT_FOUND);
			}
		   }catch( Exception e ){
			   new ResponseEntity<ApiResponse>(new ApiResponse(false,e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
			   
		   }
		return ResponseEntity.internalServerError().build();
		   
	   }
	
	@GetMapping("/findDepartmentById/{id}")
	public Department getDepartmentById(@PathVariable("id")   Long departmentId) {
		return service.findDepartmentById(departmentId);
	}
	
	@GetMapping("/findAllDepartments")
	public ResponseEntity<?> findAllDepartments(){
		return new ResponseEntity<>(service.findAllDepartments(),HttpStatus.OK);
		
	}
	
	
	 @GetMapping("/welcome")
	    public String welcome() {
	    	return "Welcome to department Service..!";
	    }
	
	 
	
	//this the optional
	
   @GetMapping("/multisearch/{departmentId}/{departmentName}/{departmentCode}")
   public Department multiSearch(@PathVariable(required = false) String departmentId,
			@PathVariable(required=false) String departmentName,
			@PathVariable(required= false) String departmentCode) {
    	
    	  if(StringUtils.isNotBlank(departmentId) && StringUtils.isNotBlank(departmentName) && StringUtils.isNotBlank(departmentCode)) {
    		 return  service.findByDepartmentIdAndDepartmentNameAndDepartmentCode(Long.valueOf(departmentId), departmentName, departmentCode);
    	  }else if(StringUtils.isNotEmpty(departmentId)) {
    		 return  service.findDepartmentById(Long.valueOf(departmentId));
    	  }else if (StringUtils.isNotBlank(departmentName)) {
    		  return service.findByDepartmentName(departmentName);
    	  }else {
    		  return service.findByDepartmentCode(departmentCode);
    	  } 
	}
    
    
   @GetMapping("/grouDepartments")
    public ResponseEntity<?> passingGroupbyValue(){
    	Map<String, List<Department>> groupmap = service.findAllDepartments()
    			.stream().filter(f->f.getDepartmentName() != null)
    			.collect(Collectors.groupingBy(d->d.getDepartmentName()));
    	
    	Map<String, List<String>>  groupByTwoArg = service.findAllDepartments()
    			.stream().filter(d-> d.getDepartmentName() != null)
    			.collect(Collectors.groupingBy(Department::getDepartmentName,
    					Collectors.mapping(Department::getDepartmentName, Collectors.toList())));
    	
    	Map<String, Set<Department>> checkDuplicate = service.findAllDepartments()
    			.stream().filter(d-> d.getDepartmentName() != null)
    			.collect(Collectors.groupingBy(Department::getDepartmentName, Collectors.toSet()));
    			
    	Map<String, Set<String>> checkDupDownStream = service.findAllDepartments()
    			.parallelStream().filter(f->f.getDepartmentName() != null)
    			.collect(Collectors.groupingBy(Department::getDepartmentName, Collectors.mapping(
    					Department::getDepartmentName, Collectors.toSet())));
    	
    	TreeMap<String , Set<String>> checkDuAndSort= service.findAllDepartments()
    			.stream().filter(f->f.getDepartmentName() !=null)
    			.collect(Collectors.groupingBy(
    					Department::getDepartmentName, TreeMap::new, Collectors.mapping(
    							Department::getDepartmentName, Collectors.toSet())));
     
    	return new ResponseEntity<>(checkDuAndSort,HttpStatus.OK);
    	
    }
   
	
	
	
}
