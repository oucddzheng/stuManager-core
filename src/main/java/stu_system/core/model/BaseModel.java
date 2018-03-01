package stu_system.core.model;

public class BaseModel extends PagerModel {
	private Integer id = null;          //账户id
	private String  code;               // 编码
	
	private String createTime = null;   //该账号的创建时间 
	private String updateTime = null;   //该账号的更新时间
	private String creator = null;      //该账号的创建者  该属性还可以这样来创建  private Integer createBy;  // 创建人（一般为用户表主键）
	private String updater = null;      //该账号最近更新者 该属性还可以这样来创建   private Integer updateBy;         // 更新人（一般为用户表主键）
	private Integer isDelete = null; 	//是否已经被删除
	private Integer isEffect = null;    //是否还有效
	
	private Double  orderBy;            // 排序序号（小数类型） 在数据库中用来排序用的	
	private Integer type;    	        // 类型（一般为字典表主键）
	private String descr = null;
	public Double getOrderBy() {
		return orderBy;
	}
	public void setOrderBy(Double orderBy) {
		this.orderBy = orderBy;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	
	
	public Integer getId(){
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}	
	
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public String getUpdater() {
		return updater;
	}
	public void setUpdater(String updater) {
		this.updater = updater;
	}
	public Integer getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}
	public Integer getIsEffect() {
		return isEffect;
	}
	public void setIsEffect(Integer isEffect) {
		this.isEffect = isEffect;
	}
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	

}
