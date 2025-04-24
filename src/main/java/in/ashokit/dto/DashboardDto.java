package in.ashokit.dto;

import lombok.Data;

@Data
public class DashboardDto {
	
	private Integer totalEnqs;
	private Integer openEnqs;
	private Integer enrolledEnqs;
	private Integer lostEnqs;

}
