package department.com.departmentservice.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="baseEntity")
public class BaseEntity {
	@Id
private  int id;
	private String baseName;
	private String baseId;
}
