package pl.yameo.recruitmenttask.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class FileStatisticsDetails {
	
	private LocalDateTime firstPostDate;
	private LocalDateTime lastPostDate;
	private long totalPosts;
	private BigDecimal avgScore;
	
	public FileStatisticsDetails() {		
	}
	
	public FileStatisticsDetails(LocalDateTime firstPostDate, LocalDateTime lastPostDate, long totalPosts, BigDecimal avgScore) {
		this.firstPostDate = firstPostDate;
		this.lastPostDate = lastPostDate;
		this.totalPosts = totalPosts;
		this.avgScore = avgScore;
	}
	
	public LocalDateTime getFirstPostDate() {
		return firstPostDate;
	}
	
	public LocalDateTime getLastPostDate() {
		return lastPostDate;
	}
	
	public long getTotalPosts() {
		return totalPosts;
	}
	
	public BigDecimal getAvgScore() {
		return avgScore;
	}

}
