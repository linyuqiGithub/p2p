package com.xmg.p2p.base.query;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter@Getter@ToString
public class QueryObject {
	public static Integer NO_PAGE = 0;
	private Integer currentPage = 1;
	private Integer pageSize = 10;
    
    public Integer getStart(){
		//防止页面传递过来的currentPage为null，出现空指针异常
		if(currentPage == null){
			currentPage = 1;
		}
    	return (currentPage - 1) * pageSize;		
    }
}
