package in.ashokit.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import in.ashokit.dto.DashboardDto;
import in.ashokit.dto.EnquiryDto;
import in.ashokit.entities.Counsellor;
import in.ashokit.entities.Enquiry;
import in.ashokit.repo.CounsellorRepo;
import in.ashokit.repo.EnquiryRepo;
import in.ashokit.services.EnquiryService;

@Service
public class EnquiryServiceImpl implements EnquiryService {
	
	@Autowired
	private EnquiryRepo enqRepo;
	
	@Autowired
	private CounsellorRepo counsellorRepo;

	@Override
	public DashboardDto getDashboardInfo(Integer counsellorId) {
		
		Counsellor centity = new Counsellor();
		centity.setCounsellorId(counsellorId);
		
		Enquiry enq = new Enquiry();
		enq.setCounsellor(centity);
		
		List<Enquiry> enqsList = enqRepo.findAll(Example.of(enq));
		
		int total = enqsList.size();
		
		int open = enqsList.stream()
						   .filter(e -> e.getEnqStatus().equals("Open"))
				           .collect(Collectors.toList()).size();
		
		int enrolled = enqsList.stream()
				               .filter(e -> e.getEnqStatus().equals("Enrolled"))
				               .collect(Collectors.toList()).size();
		
		int lost = enqsList.stream()
	               .filter(e -> e.getEnqStatus().equals("Lost"))
	               .collect(Collectors.toList()).size();
		
		/*
		 * DashboardDto dto = new DashboardDto(); 
		 * dto.setTotalEnqs(total);
		 * dto.setOpenEnqs(open);
		 * dto.setEnrolledEnqs(enrolled); 
		 * dto.setLostEnqs(lost);
		 */	
		
		return DashboardDto.builder()
							.totalEnqs(total)
							.enrolledEnqs(enrolled)
							.lostEnqs(lost)
							.openEnqs(open)
							.build();

	}

	@Override
	public boolean upsertEnquiry(EnquiryDto enqDto, Integer counsellorId) {
		
		Enquiry entity = new Enquiry();
		
		BeanUtils.copyProperties(enqDto, entity);
		
		Counsellor counsellor = 
				counsellorRepo.findById(counsellorId).orElseThrow();
		
		entity.setCounsellor(counsellor);
		
		Enquiry savedEntity = enqRepo.save(entity);
		
		return savedEntity.getEnqId()!=null;
	}

	@Override
	public List<EnquiryDto> getEnquiries(Integer counsellorId) {
		
		List<EnquiryDto> dtosList = new ArrayList<>();	
		
		List<Enquiry> enqsList = enqRepo.findByCounsellorCounsellorId(counsellorId);
		
		enqsList.forEach(e -> {
			EnquiryDto dto = new EnquiryDto();
			BeanUtils.copyProperties(e, dto);
			dtosList.add(dto);
		});
		
		return dtosList;
	}
	
	@Override
	public List<EnquiryDto> filterEnqs(EnquiryDto filterDto, Integer counsellorId) {
		Enquiry entity = new Enquiry();
		
		if(filterDto.getClassMode()!=null && !filterDto.getClassMode().equals("")) {
			entity.setClassMode(filterDto.getClassMode());
		}
		if(filterDto.getCourseName()!=null && !filterDto.getCourseName().equals("")) {
			entity.setCourseName(filterDto.getCourseName());
		}
		if(filterDto.getEnqStatus()!=null && !filterDto.getEnqStatus().equals("")) {
			entity.setEnqStatus(filterDto.getEnqStatus());
		}
		Counsellor counsellor = counsellorRepo.findById(counsellorId).orElseThrow();
		entity.setCounsellor(counsellor);
		
		List<Enquiry> enqsList = enqRepo.findAll(Example.of(entity));
		
		List<EnquiryDto> dtosList = new ArrayList<>();	
		enqsList.forEach(e -> {
			EnquiryDto dto = new EnquiryDto();
			BeanUtils.copyProperties(e, dto);
			dtosList.add(dto);
		});
		return dtosList;
	}

	@Override
	public EnquiryDto getEnquiry(Integer enqId) {
		Enquiry entity = enqRepo.findById(enqId).orElseThrow();
		EnquiryDto dto = new EnquiryDto();
		BeanUtils.copyProperties(entity, dto);
		return dto;
	}
}







