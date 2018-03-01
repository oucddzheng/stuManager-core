package stu_system.core.model;

public class BaseModel extends PagerModel {
	private Integer id = null;          //�˻�id
	private String  code;               // ����
	
	private String createTime = null;   //���˺ŵĴ���ʱ�� 
	private String updateTime = null;   //���˺ŵĸ���ʱ��
	private String creator = null;      //���˺ŵĴ�����  �����Ի���������������  private Integer createBy;  // �����ˣ�һ��Ϊ�û���������
	private String updater = null;      //���˺���������� �����Ի���������������   private Integer updateBy;         // �����ˣ�һ��Ϊ�û���������
	private Integer isDelete = null; 	//�Ƿ��Ѿ���ɾ��
	private Integer isEffect = null;    //�Ƿ���Ч
	
	private Double  orderBy;            // ������ţ�С�����ͣ� �����ݿ������������õ�	
	private Integer type;    	        // ���ͣ�һ��Ϊ�ֵ��������
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
