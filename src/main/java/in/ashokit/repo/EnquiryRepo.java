package in.ashokit.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import in.ashokit.entities.Enquiry;

public interface EnquiryRepo extends JpaRepository<Enquiry, Integer> {

	// select * from enq_tbl where counsellor_id=:counsellorId
	public List<Enquiry> findByCounsellorCounsellorId(Integer counsellorId);
}
