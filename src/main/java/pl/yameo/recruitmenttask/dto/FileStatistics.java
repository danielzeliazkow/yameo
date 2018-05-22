package pl.yameo.recruitmenttask.dto;

import java.time.LocalDateTime;

public class FileStatistics {	

	private LocalDateTime date = LocalDateTime.now();
	private FileStatisticsDetails details;
	
	public FileStatistics() {		
	}

	public FileStatistics(FileStatisticsDetails details) {
		this.details = details;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public FileStatisticsDetails getDetails() {
		return details;
	}
}


