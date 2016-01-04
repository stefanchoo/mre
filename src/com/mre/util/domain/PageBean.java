package com.mre.util.domain;

import java.util.List;

/**
 * 用来处理分页的对象，主要用来封装要在分页操作时用到的数据
 * 
 * @author Administrator
 * 
 */
@SuppressWarnings("rawtypes")
public class PageBean {

	// 需要页面指定或者传递的参数
	private int currentPage;        // 当前页
	private int pageSize;           // 每页显示的数量 默认为10
                               
	// 需要查询数据库的得到的  
	private List recordList;        // 本页的数据列表
	private int recordCount;   
                               
	// 需要计算得到的数据      
	private int pageCount;          // 总页数
	private int beginPageIndex;     // 页码列表的开始索引 （包含）
	private int endPageIndex;       // 页码列表的结束索引 （包含）

	public PageBean(int currentPage, int pageSize, List recordList,
			int recordCount) {
		super();
		this.currentPage = currentPage;
		this.pageSize = pageSize;
		this.recordList = recordList;
		this.recordCount = recordCount;

		// 计算总页数
		this.pageCount = (recordCount + pageSize - 1) / pageSize;

		// beginPageIndex endPageIndex;
		// 总页数不多于3页，则全部显示
		if (pageCount <= 3) {
			beginPageIndex = 1;
			endPageIndex = pageCount;
		} else {
			// 总页数多于3页，（前1个 + 当前页 + 后1个）
			beginPageIndex = currentPage - 1;
			endPageIndex = currentPage + 1;
			// 当前面的页码不足1个时，则显示前3个页码
			if(beginPageIndex < 1){
				beginPageIndex = 1;
				endPageIndex = 3;
			}
			// 当后面的页码不足1个时，则显示后3个页码
			if(endPageIndex > pageCount){
				beginPageIndex = pageCount - 3 + 1;
				endPageIndex = pageCount;
			}
		}
	/*	System.out.println("pageCount=" + pageCount);*/
	}

	public List getRecordList() {
		return recordList;
	}

	public void setRecordList(List recordList) {
		this.recordList = recordList;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCuount) {
		this.pageCount = pageCuount;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getRecordCount() {
		return recordCount;
	}

	public void setRecordCount(int recordCount) {
		this.recordCount = recordCount;
	}

	public int getBeginPageIndex() {
		return beginPageIndex;
	}

	public void setBeginPageIndex(int beginPageIndex) {
		this.beginPageIndex = beginPageIndex;
	}

	public int getEndPageIndex() {
		return endPageIndex;
	}

	public void setEndPageIndex(int endPageIndex) {
		this.endPageIndex = endPageIndex;
	}
}
