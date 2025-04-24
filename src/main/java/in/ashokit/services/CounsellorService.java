package in.ashokit.services;

import in.ashokit.dto.CounsellorDto;

public interface CounsellorService {

	public CounsellorDto login(String email, String pwd);

	public boolean isEmailUnique(String email);

	public boolean register(CounsellorDto counsellorDto);

}