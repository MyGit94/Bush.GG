package com.pinkward.bushgg.domain.common.web;


import lombok.*;
import org.apache.ibatis.type.Alias;

/**
 * 페이지 계산에 필요한 정보들 포장
 */
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Alias("PageParams")
public class PageParams {
	
	private int elementSize;
	private int pageSize;
	private int requestPage;
	private int rowCount;
	private String keyword;
	private String searchType;

	public PageParams() {
		this.elementSize =10;
		this.pageSize = 5;
		this.requestPage = 1;
		this.rowCount = 0;
	}
	public PageParams(int elementSize, int pageSize, int requestPage, int rowCount) {
		this.elementSize = elementSize;
		this.pageSize = pageSize;
		this.requestPage = requestPage;
		this.rowCount = rowCount;
	}

	public PageParams getPageParams() {
		return this;
	}

	public int getOffset() {
		return (requestPage - 1) * pageSize;
	}
}
