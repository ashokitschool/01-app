package in.ashokit.services.impl;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.ashokit.dto.CounsellorDto;
import in.ashokit.entities.Counsellor;
import in.ashokit.repo.CounsellorRepo;
import in.ashokit.services.CounsellorService;

@Service
public class CounsellorServiceImpl implements CounsellorService {

	@Autowired
	private CounsellorRepo counsellorRepo;

	@Override
	public CounsellorDto login(String email, String pwd) {
		Counsellor entity = counsellorRepo.findByEmailAndPwd(email, pwd);
		if (entity != null) {
			CounsellorDto dto = new CounsellorDto();
			BeanUtils.copyProperties(entity, dto);
			return dto;
		}
		return null;
	}

	@Override
	public boolean isEmailUnique(String email) {
		Optional<Counsellor> byEmail = counsellorRepo.findByEmail(email);
		if (byEmail.isPresent()) {
			return false;
		}
		return true;
	}

	@Override
	public boolean register(CounsellorDto counsellorDto) {
		Counsellor entity = new Counsellor();
		BeanUtils.copyProperties(counsellorDto, entity);
		Counsellor savedEntity = counsellorRepo.save(entity);
		return savedEntity.getCounsellorId() != null;
	}
}
