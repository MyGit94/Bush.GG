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
	
	private int elementSize;    /** 페이지에 보여지는 목록 갯수 */
	private int pageSize;       /** 페이지에 보여지는 페이지 갯수 */
	private int requestPage;    /** 사용자 요청 페이지 */
	private int rowCount;       /** 테이블 목록 갯수 */
	private String keyword;       // 검색 키워드
	private String searchType;    // 검색 유형

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
		return this; // 현재 인스턴스를 반환하도록 게터 메서드 정의
	}

	public int getOffset() {
		return (requestPage - 1) * pageSize;
	}
}
