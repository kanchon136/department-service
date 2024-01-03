package department.com.departmentservice;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class DepartmentServiceApplication {

	public static void main(String[] args) {
		//SpringApplication.run(DepartmentServiceApplication.class, args);
		
		SpringApplication application = new SpringApplication(DepartmentServiceApplication.class);
		
		application.setBannerMode(Banner.Mode.OFF);
		application.run(args);
		System.out.println("===========Department Service is Running============");
	}

}
