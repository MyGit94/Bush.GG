package com.pinkward.bushgg.domain.common.web;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 페이징 처리 계산
 */
@Getter
@Setter
@ToString
public class Pagination {

	private PageParams params;

	private int totalPages;
	private int listNo;
	private int startPage;
	private int endPage;
	private int previousStartPage;
	private int nextStartPage;

	public Pagination(PageParams params) {
		this.params = params;
		compute();
	}

	/**
	 * 페이징 계산 메소드
	 */
	public void compute(){

		totalPages = (int)Math.ceil((double)params.getRowCount() / params.getElementSize());

		listNo = (params.getRequestPage() - 1) / params.getPageSize();

		startPage = (listNo * params.getPageSize()) + 1;
		endPage = (listNo * params.getPageSize()) + params.getPageSize();
		if (endPage > totalPages){
			endPage = totalPages;
		}

		previousStartPage = startPage - params.getPageSize();
		if (previousStartPage < 0)  previousStartPage = 1;

		nextStartPage = startPage + params.getPageSize();
	}
	
}
