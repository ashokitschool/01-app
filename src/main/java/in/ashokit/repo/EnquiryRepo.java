package in.ashokit.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import in.ashokit.entities.Enquiry;

public interface EnquiryRepo 
			extends JpaRepository<Enquiry, Integer>{

}
