package stu_system.core.model;

import java.io.Serializable;

import stu_system.core.pager.Pager;



public class PagerModel implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer page  = 1;   //这个属性是用来第几页 ,这个是默认值默认是第一页
    private Integer rows  = 10;  //这个属性是每页要显示几条记录 默认显示10条
    private String  sort;        //设置排序所用的字段
    private String  order;        //这个字段是用来设置排序方向  是升序还是降序
    private Pager   pager = new Pager();// 分页导航
    //这个方法在往外取pager属性的时候会执行
    public Pager getPager() {
      /*pager.setPageId(page);
        pager.setPageSize(rows);
        if (sort != null && sort.trim().length() != 0) {
            if (order != null && order.trim().length() != 0) {
                pager.setOrderField(sort + " " + order);
            } else {
                pager.setOrderField(sort);
            }
        }*/
        return pager;
    }   
    public void setPager(Pager pager) {
    	/*pager.setPageId(page);
        pager.setPageSize(rows);
        if (sort != null && sort.trim().length() != 0) {
            if (order != null && order.trim().length() != 0) {
                pager.setOrderField(sort + " " + order);
            } else {
                pager.setOrderField(sort);
            }
        }   	*/
        this.pager = pager;
    }
    /**
     * 这个方法是在老师代码基础上更改，将原来的代码拆分成两个方法，分别对排序和分页功能所需要的字段进行设置
     * 在BaseService中的selectModel方法中有调用
     */
    public void setPagerPageIdPageSizeAndPagerOrderField() {
    	pager.setPageId(page);
        pager.setPageSize(rows);
        setPagerOrderField();
    }
    /**
     * 设置排序功能所需要的字段在BaseService中的selectModel方法中有调用
     */
    public void setPagerOrderField() {
    	if (sort != null && sort.trim().length() != 0) {
            if (order != null && order.trim().length() != 0) {
                pager.setOrderField(sort + " " + order);
            } else {
                pager.setOrderField(sort);
            }
        }   	
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

}
