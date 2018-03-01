package stu_system.system.model;

import stu_system.core.model.BaseModel;

public class SysWorkAttendanceModel extends BaseModel {
	//在数据库中对应的字段
	/*id, code, user_code time, one_check, two_check, three_check, four_check, five_check, six_check, create_time, update_time, creator, updater,
	is_delete, is_effect, orderby, descr*/
	private String time;
	private String userCode;
	private Integer oneCheck;
	private Integer twoCheck;
	private Integer threeCheck;
	private Integer fourCheck;
	private Integer fiveCheck;
	private Integer sixCheck;
	//下面的属性在数据库中没有是自己加上去的
	private String userTrueName;
	
    private String oneCheckName;
    private Integer userId;
    private String twoCheckName;
    private String threeCheckName;
    private String fourCheckName;
    private String fiveCheckName;
    private String sixCheckName;
	
    private SysUserModel sysUserModel = new SysUserModel();
    
    
    
	public SysUserModel getSysUserModel() {
		return sysUserModel;
	}
	public void setSysUserModel(SysUserModel sysUserModel) {
		this.sysUserModel = sysUserModel;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getUserTrueName() {
		return userTrueName;
	}
	public void setUserTrueName(String userTrueName) {
		this.userTrueName = userTrueName;
	}
	public String getOneCheckName() {
		return oneCheckName;
	}
	public void setOneCheckName(String oneCheckName) {
		this.oneCheckName = oneCheckName;
	}
	public String getTwoCheckName() {
		return twoCheckName;
	}
	public void setTwoCheckName(String twoCheckName) {
		this.twoCheckName = twoCheckName;
	}
	public String getThreeCheckName() {
		return threeCheckName;
	}
	public void setThreeCheckName(String threeCheckName) {
		this.threeCheckName = threeCheckName;
	}
	public String getFourCheckName() {
		return fourCheckName;
	}
	public void setFourCheckName(String fourCheckName) {
		this.fourCheckName = fourCheckName;
	}
	public String getFiveCheckName() {
		return fiveCheckName;
	}
	public void setFiveCheckName(String fiveCheckName) {
		this.fiveCheckName = fiveCheckName;
	}
	public String getSixCheckName() {
		return sixCheckName;
	}
	public void setSixCheckName(String sixCheckName) {
		this.sixCheckName = sixCheckName;
	}
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public Integer getOneCheck() {
		return oneCheck;
	}
	public void setOneCheck(Integer oneCheck) {
		this.oneCheck = oneCheck;
	}
	public Integer getTwoCheck() {
		return twoCheck;
	}
	public void setTwoCheck(Integer twoCheck) {
		this.twoCheck = twoCheck;
	}
	public Integer getThreeCheck() {
		return threeCheck;
	}
	public void setThreeCheck(Integer threeCheck) {
		this.threeCheck = threeCheck;
	}
	public Integer getFourCheck() {
		return fourCheck;
	}
	public void setFourCheck(Integer fourCheck) {
		this.fourCheck = fourCheck;
	}
	public Integer getFiveCheck() {
		return fiveCheck;
	}
	public void setFiveCheck(Integer fiveCheck) {
		this.fiveCheck = fiveCheck;
	}
	public Integer getSixCheck() {
		return sixCheck;
	}
	public void setSixCheck(Integer sixCheck) {
		this.sixCheck = sixCheck;
	}
	
}
