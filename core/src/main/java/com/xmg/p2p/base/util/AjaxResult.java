package com.xmg.p2p.base.util;

import lombok.Getter;
import lombok.Setter;

@Setter@Getter
public class AjaxResult {
	private boolean success = true;
	private String msg;
	public AjaxResult() {
		super();
	}
	public AjaxResult(String msg) {
		super();
		this.success = false;
		this.msg = msg;
	}
	public AjaxResult(boolean success,String msg){
		this.success = success;
		this.msg = msg;
	}
}
