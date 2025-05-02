package in.ashokit.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import in.ashokit.dto.EnquiryDto;
import in.ashokit.services.EnquiryService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class EnquiryController {

	@Autowired
	private EnquiryService enqService;

	@GetMapping("/enquiry")
	public String addEnquiry(Model model) {

		EnquiryDto enquiryDto = new EnquiryDto();
		model.addAttribute("enq", enquiryDto);

		return "addEnq";
	}

	@PostMapping("/enquiry")
	public String handleAddEnq(@ModelAttribute("enq") EnquiryDto enq, HttpServletRequest req, Model model) {

		HttpSession session = req.getSession(false);
		Integer cid = (Integer) session.getAttribute("COUNSELLOR_ID");

		boolean upsertEnquiry = enqService.upsertEnquiry(enq, cid);

		if (upsertEnquiry) {
			model.addAttribute("smsg", "Enquriy Added");
		} else {
			model.addAttribute("emsg", "Failed to add Enquiry");
		}

		return "addEnq";
	}

	@GetMapping("/view-enqs")
	public String getEnquiries(HttpServletRequest req, Model model) {

		HttpSession session = req.getSession(false);
		Integer cid = (Integer) session.getAttribute("COUNSELLOR_ID");

		List<EnquiryDto> enqList = enqService.getEnquiries(cid);
		model.addAttribute("enquiries", enqList);

		EnquiryDto searchFormDto = new EnquiryDto();
		model.addAttribute("filterDto", searchFormDto);

		return "view-enqs";
	}

	@PostMapping("/filter-enqs")
	public String handleEnqsFilter(@ModelAttribute("filterDto") EnquiryDto filterDto, 
			HttpServletRequest req,
			Model model) {

		HttpSession session = req.getSession(false);
		Integer cid = (Integer) session.getAttribute("COUNSELLOR_ID");

		List<EnquiryDto> enqList = enqService.filterEnqs(filterDto, cid);
		model.addAttribute("enquiries", enqList);
		
		return "view-enqs";
	}
}









