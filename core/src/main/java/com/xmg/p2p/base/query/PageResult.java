package com.xmg.p2p.base.query;

import lombok.Data;

import java.util.Collections;
import java.util.List;
@Data
public class PageResult {
	private Integer currentPage;
	private Integer pageSize;
	private Integer totalCount;
	private List<?> data;
	private Integer totalPage;
	private Integer prevPage;
	private Integer nextPage;

	public PageResult(Integer currentPage, Integer pageSize, Integer totalCount, List<?> data) {
		this.currentPage = currentPage;
		this.pageSize = pageSize;
		this.totalCount = totalCount;
		this.data = data;
		totalPage = totalCount % pageSize == 0 ? totalCount / pageSize : (totalCount / pageSize + 1);
		prevPage = currentPage - 1 > 1 ? currentPage - 1 : 1;
		nextPage = currentPage + 1 > totalPage ? totalPage : currentPage + 1;
	}

	public static PageResult getEmpty(Integer pageSize){

		return new PageResult(1,pageSize,0, Collections.EMPTY_LIST);
	}

	/**
	 * 防止总页数为0(totalCount为0)时，startPage>totalPage,此时boostrap的分页组件会报错
	 * @return
	 */
	public Integer getTotalPage(){
		return this.totalPage == 0 ? 1 : totalPage;
	}

	
	
	

}
