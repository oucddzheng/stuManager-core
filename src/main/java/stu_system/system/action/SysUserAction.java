package stu_system.system.action;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.faces.annotation.RequestMap;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.util.SystemOutLogger;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import net.sf.json.JSONArray;
import stu_system.core.action.BaseAction;
import stu_system.system.model.SysUserModel;
import stu_system.system.service.SysUserService;
import tools.FormatCalendar;
import tools.FormatEmpty;
import tools.UniqueCode;

@Controller("sysUserAction")
@RequestMapping("/sysUserAction")
public class SysUserAction extends BaseAction {
	
	@Autowired
	private SysUserService<SysUserModel> sysUserService;
	@RequestMapping("/login.do")
	public void login(SysUserModel sysUserModel , HttpServletRequest request , HttpServletResponse response) throws Exception {
				
		SysUserModel sysUserModelParameter = new SysUserModel();
		if ( FormatEmpty.isEmpty(sysUserModel.getUserAccount())) {
			response.getWriter().write("no");
			return;
		}else {
			sysUserModelParameter.setUserAccount(sysUserModel.getUserAccount());
		}
		if(FormatEmpty.isEmpty(sysUserModel.getUserPassword())) {
			response.getWriter().write("no");
			return;
		}else {
			sysUserModelParameter.setUserPassword(sysUserModel.getUserPassword());
		}
		if(FormatEmpty.isEmpty(sysUserModel.getRoleCode())) {
			response.getWriter().write("no");
			return;
		}else {
			sysUserModelParameter.setRoleCode(sysUserModel.getRoleCode());
		}
	    ArrayList<SysUserModel> sysUserModelList = (ArrayList<SysUserModel>) sysUserService.selectUserAndRole(sysUserModelParameter);
	    if(FormatEmpty.isEmpty(sysUserModelList)) {
	    	response.getWriter().write("no");
	    	return;
	    }else {	    	
	    	HttpSession session = request.getSession();
	    	session.setAttribute("currentUser", sysUserModelList.get(0));
	    	response.getWriter().write("yes");
	    }   
	}
	@RequestMapping("/save.do")
	public void save(SysUserModel sysUserModel , HttpServletRequest request , HttpServletResponse response) throws Exception {
		SysUserModel sysUserModelParameter = new SysUserModel();
		sysUserModelParameter.setCode(UniqueCode.getUUID());
		String userAccount = sysUserModel.getUserAccount();
		if(FormatEmpty.isEmpty(userAccount)) {
			System.out.println("�û����˻�������Ϊ��");
			return;
		}
		sysUserModelParameter.setUserAccount(userAccount);
		String userPassword = sysUserModel.getUserPassword();
		if(FormatEmpty.isEmpty(userPassword)) {
			System.out.println("�û������벻��Ϊ��");
			return;
		}
		sysUserModelParameter.setUserPassword(userPassword);
		String userTrueName = sysUserModel.getUserTrueName();
		if(FormatEmpty.isEmpty(userTrueName)) {
			System.out.println("�û�����ʵ���ֲ���Ϊ��");
			return;
		}		
		sysUserModelParameter.setUserTrueName(userTrueName);
		String userTelephone = sysUserModel.getUserTelephone();
		if(FormatEmpty.isEmpty(userTelephone)) {
			System.out.println("�û�����ϵ��ϵ�绰����Ϊ��");
			return;
		}
		sysUserModelParameter.setUserTelephone(userTelephone);
		String roleCode = sysUserModel.getRoleCode();
		if(FormatEmpty.isEmpty(roleCode)) {
			System.out.println("�û��Ľ�ɫ���벻��Ϊ��");
			return;
		}
		sysUserModelParameter.setRoleCode(roleCode);
		String classCode = sysUserModel.getClassCode();
		if(FormatEmpty.isEmpty(classCode)) {
			sysUserModelParameter.setClassCode("000000");
		}else {
			sysUserModelParameter.setClassCode(classCode);
		}
		sysUserModelParameter.setCreateTime(FormatCalendar.getCurrentTime(FormatCalendar.F_YMDHMS));
		sysUserModelParameter.setUpdateTime(FormatCalendar.getCurrentTime(FormatCalendar.F_YMDHMS));
		HttpSession session = request.getSession();
		//��session����ȡ����ǰ�ĵ�½�û��˺�
		String currentUserAccount =((SysUserModel)session.getAttribute("currentUser")).getUserAccount() ;
		if(FormatEmpty.isEmpty(currentUserAccount)) {
			System.out.println("��ǰ�û���û�е�½");
			return;
		}
		sysUserModelParameter.setCreator(currentUserAccount);
		sysUserModelParameter.setUpdater(currentUserAccount);
		sysUserModelParameter.setIsDelete(0);
		sysUserModelParameter.setIsEffect(1);
		sysUserModelParameter.setOrderBy((new Random()).nextDouble());
		String descr = sysUserModel.getDescr();
		if(FormatEmpty.isEmpty(descr)) {
			sysUserModelParameter.setDescr("��");
		}else {
			sysUserModelParameter.setDescr(descr);
		}
		sysUserService.insert(sysUserModelParameter);
		response.getWriter().write("{\"success\":\"true\"}");
	}
	@RequestMapping("/getUserInformation.do")
	public void getUserInformation(String userAccount ,String page , String rows , HttpServletRequest request , HttpServletResponse response) throws Exception {
		SysUserModel sysUserModelParameter = new SysUserModel();
		sysUserModelParameter.setUserAccount(userAccount);
		sysUserModelParameter.setIsDelete(0);
		sysUserModelParameter.setSort("id");
		sysUserModelParameter.setOrder("ASC");
		sysUserModelParameter.setPage(Integer.valueOf(page));
		sysUserModelParameter.setRows(Integer.valueOf(rows));
		ArrayList<SysUserModel> sysUserModelList = (ArrayList<SysUserModel>) sysUserService.selectUserAndRoleAndClass(sysUserModelParameter);
	    if(FormatEmpty.isEmpty(sysUserModelList)) {
	    	response.getWriter().write("[]");
	    }else {
	    	ArrayList<Map<String , String>> listMapRequired = new ArrayList();
	    	for(SysUserModel modelForEach : sysUserModelList) {
	    		Map<String , String> mapModel = new HashMap();
	    		mapModel.put("cb", "");
	    		mapModel.put("id", modelForEach.getId().toString());
	    		mapModel.put("userAccount", modelForEach.getUserAccount());
	    		mapModel.put("userPassword", modelForEach.getUserPassword());
	    		mapModel.put("userTrueName", modelForEach.getUserTrueName());
	    		mapModel.put("userTelephone", modelForEach.getUserTelephone());
	    		//����left join��ѯ��ʱ��sysClassModel ��������п���Ϊ�գ�Ҫ�ж�һ��
	    		if(modelForEach.getSysClassModel()==null) {
	    			mapModel.put("className", "");
	    		}else {
	    			mapModel.put("className", modelForEach.getSysClassModel().getClassName());
	    		}
	    		mapModel.put("roleName", modelForEach.getSysRoleModel().getRoleName());
	    		mapModel.put("descr", modelForEach.getDescr());
	    		listMapRequired.add(mapModel);
	    	}
	    	  JSONArray sysUserModellistJson = JSONArray.fromObject(listMapRequired);	    	  
	    	   response.getWriter().write("{\"total\":"+sysUserModelParameter.getPager().getRowCount()+",\"rows\":" + sysUserModellistJson.toString() +"}");
	    }
	}
	@RequestMapping("/update.do")
	public void update(SysUserModel sysUserModel , Integer id , HttpServletRequest request , HttpServletResponse response) throws Exception {
		SysUserModel sysUserModelParameter = new SysUserModel();
		if(id == null) {
			System.out.println("û��id");
			return;
		}
		sysUserModelParameter.setId(id);
		String userAccount = sysUserModel.getUserAccount();
		if(FormatEmpty.isEmpty(userAccount)) {
			System.out.println("�û����˻�������Ϊ��");
			return;
		}
		sysUserModelParameter.setUserAccount(userAccount);
		String userPassword = sysUserModel.getUserPassword();
		if(FormatEmpty.isEmpty(userPassword)) {
			System.out.println("�û������벻��Ϊ��");
			return;
		}
		sysUserModelParameter.setUserPassword(userPassword);
		String userTrueName = sysUserModel.getUserTrueName();
		if(FormatEmpty.isEmpty(userTrueName)) {
			System.out.println("�û�����ʵ���ֲ���Ϊ��");
			return;
		}		
		sysUserModelParameter.setUserTrueName(userTrueName);
		String userTelephone = sysUserModel.getUserTelephone();
		if(FormatEmpty.isEmpty(userTelephone)) {
			System.out.println("�û�����ϵ��ϵ�绰����Ϊ��");
			return;
		}
		sysUserModelParameter.setUserTelephone(userTelephone);
		String roleCode = sysUserModel.getRoleCode();
		if(FormatEmpty.isEmpty(roleCode)) {
			System.out.println("�û��Ľ�ɫ���벻��Ϊ��");
			return;
		}
		sysUserModelParameter.setRoleCode(roleCode);
		String classCode = sysUserModel.getClassCode();
		if(FormatEmpty.isEmpty(classCode)) {
			sysUserModelParameter.setClassCode("000000");
		}else {
			sysUserModelParameter.setClassCode(classCode);
		}
		sysUserModelParameter.setUpdateTime(FormatCalendar.getCurrentTime(FormatCalendar.F_YMDHMS));
		HttpSession session = request.getSession();
		//��session����ȡ����ǰ�ĵ�½�û��˺�
		String currentUserAccount =((SysUserModel)session.getAttribute("currentUser")).getUserAccount() ;
		if(FormatEmpty.isEmpty(currentUserAccount)) {
			System.out.println("��ǰ�û���û�е�½");
			return;
		}
		sysUserModelParameter.setUpdater(currentUserAccount);
		sysUserModelParameter.setIsDelete(0);
		sysUserModelParameter.setIsEffect(1);
		String descr = sysUserModel.getDescr();
		if(FormatEmpty.isEmpty(descr)) {
			sysUserModelParameter.setDescr("��");
		}else {
			sysUserModelParameter.setDescr(descr);
		}
		sysUserService.update(sysUserModelParameter);	
		response.getWriter().write("{\"success\":\"true\"}");
	}
	@RequestMapping("/deleteByUpdate.do")
	@ResponseBody
	public String deleteByUpdate(String ids , HttpServletRequest request , HttpServletResponse response) throws Exception {
		if(FormatEmpty.isEmpty(ids)) {
			System.out.println("û��ѡ��Ҫɾ�����û�");
			return "{\"success\":\"false\"}";
		}
		String userIdArray[] = ids.split(",");
		HttpSession session = request.getSession();
		//��session����ȡ����ǰ�ĵ�½�û��˺�
		String currentUserAccount =((SysUserModel)session.getAttribute("currentUser")).getUserAccount() ;
		for(String userId : userIdArray) {
			SysUserModel sysUserModelParameter = new SysUserModel();
			sysUserModelParameter.setUpdateTime(FormatCalendar.getCurrentTime(FormatCalendar.F_YMDHMS));
			sysUserModelParameter.setUpdater(currentUserAccount);
			sysUserModelParameter.setIsDelete(1);
			sysUserModelParameter.setId(Integer.valueOf(userId));
			sysUserService.updateActive(sysUserModelParameter);
		}
		return "{\"success\":\"true\"}";
	}
	@RequestMapping("/getUserInformationForAttendence.do")
	public void getUserInformationForAttendence(SysUserModel sysUserModel , HttpServletRequest request , HttpServletResponse response) throws Exception {
		SysUserModel sysUserModelParameter = new SysUserModel();
		
		HttpSession session = request.getSession();
		SysUserModel currentUser = (SysUserModel) session.getAttribute("currentUser");
		if(currentUser== null) {
			System.out.println("�û�û�е�½");
			return;
		}		
		sysUserModelParameter.setClassCode(currentUser.getClassCode());
		sysUserModelParameter.setRoleCode("1");
		sysUserModelParameter.setSort("id");
		sysUserModelParameter.setOrder("ASC");
		ArrayList<SysUserModel> sysUserModelList = (ArrayList<SysUserModel>) sysUserService.selectAll(sysUserModelParameter);
		if(FormatEmpty.isEmpty(sysUserModelList)) {
	    	response.getWriter().write("[]");
	    }else {
	    	ArrayList<Map<String , String>> listMapRequired = new ArrayList();
	    	for(SysUserModel modelForEach : sysUserModelList) {
	    		Map<String , String> mapModel = new HashMap();
	    		mapModel.put("cb", "");
	    		mapModel.put("id", modelForEach.getId().toString());
	    		mapModel.put("code", modelForEach.getCode());
	    		
	    		mapModel.put("userTrueName", modelForEach.getUserTrueName());
	    		listMapRequired.add(mapModel);
	    	}
	    	JSONArray sysUserModellistJson = JSONArray.fromObject(listMapRequired);
	    	response.getWriter().write(sysUserModellistJson.toString());
	}
		
  }
	//���������û���Ϣ
	 @RequestMapping(value = "/batchImportUserInf.do", method = RequestMethod.POST)
	    public void batchImportUserInf(@RequestParam(value = "filename") MultipartFile file, HttpServletRequest request,
	            HttpServletResponse response ) throws Exception {
	        // �ж��ļ��Ƿ�Ϊ��
	        if (file == null)
	            return ;
	        // ��ȡ�ļ���
	        String name = file.getOriginalFilename();
	        // ��һ���ж��ļ��Ƿ�Ϊ�գ����ж����С�Ƿ�Ϊ0���������Ƿ�Ϊnull��
	        long size = file.getSize();
	        if (name == null || ("").equals(name) && size == 0)
	            return ;

	        // �������롣�������ļ������ļ���
	        System.out.println(name);
	        boolean b = batchImport(name, file , request);
	        if (b) {
	        	System.out.println("�û���Ϣ�Ѿ��ɹ�����");
	        	response.getWriter().write("{\"success\":\"true\"}");
	           /* String Msg = "��������EXCEL�ɹ���";
	            request.getSession().setAttribute("msg", Msg);*/
	        } else {
	        	System.out.println("�û���Ϣ����ʧ��");
	            /*String Msg = "��������EXCELʧ�ܣ�";
	            request.getSession().setAttribute("msg", Msg);*/
	        }
	       
	        return ;
	    }

	    // ��������ͻ�
	    private boolean batchImport(String name, MultipartFile file  , HttpServletRequest request ) throws Exception {
	        boolean b = false;
	        try {
	            // Ҳ������request��ȡ�ϴ��ļ�
	            // MultipartFile fileFile = request.getFile("file"); //������ҳ���name����
	            // ת����������
	            InputStream is = file.getInputStream();	            
	            // �õ�excel
	            XSSFWorkbook workbook = new XSSFWorkbook(is);	            
	            // �õ�sheet
	            XSSFSheet sheet = workbook.getSheetAt(0);	           
	            // �õ�����
	            int firstRow = sheet.getFirstRowNum();                 
	            int lastRow = sheet.getLastRowNum();
	           /* System.out.println("aaaaaaaaaaaaaaaaaaaa"+firstRow);
	            System.out.println("bbbbbbbbbbbbbbbbbbbb"+lastRow);*/
	            ArrayList<String> cellValueList = new ArrayList();
	            SysUserModel sysUserModel = new SysUserModel();
	            for (int i = firstRow; i < lastRow+1 ; i++) {

	                XSSFRow row = sheet.getRow(i);
	                int firstCell = row.getFirstCellNum();
	                int lastCell = row.getLastCellNum();
	               
	                for (int j = firstCell; j < lastCell; j++) {
	                    XSSFCell cell = row.getCell(j);
	                    if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
	                        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	                    }
	                    String cellValue = cell.getStringCellValue();

	                    cellValueList.add(cellValue);	                                       
	                }
	                sysUserModel.setUserAccount(cellValueList.get(0));
	                sysUserModel.setUserPassword(cellValueList.get(1));
	                sysUserModel.setUserTrueName(cellValueList.get(2));
	                sysUserModel.setUserTelephone(cellValueList.get(3));
	                sysUserModel.setRoleCode(cellValueList.get(4));
	                sysUserModel.setClassCode(cellValueList.get(5));
	                sysUserModel.setDescr(cellValueList.get(6));	
	                cellValueList.clear();  //���������
	                saveUserBatchImport(sysUserModel , request );//������������ĺ���
	            }
	            // ������Ҫ�Ĳ���
	            b = true;
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        return b;
	    }
	    private void saveUserBatchImport(SysUserModel sysUserModel , HttpServletRequest request) throws Exception {
	    	SysUserModel sysUserModelParameter = new SysUserModel();
			sysUserModelParameter.setCode(UniqueCode.getUUID());
			String userAccount = sysUserModel.getUserAccount();
			if(FormatEmpty.isEmpty(userAccount)) {
				System.out.println("�û����˻�������Ϊ��");
				return;
			}
			sysUserModelParameter.setUserAccount(userAccount);
			String userPassword = sysUserModel.getUserPassword();
			if(FormatEmpty.isEmpty(userPassword)) {
				System.out.println("�û������벻��Ϊ��");
				return;
			}
			sysUserModelParameter.setUserPassword(userPassword);
			String userTrueName = sysUserModel.getUserTrueName();
			if(FormatEmpty.isEmpty(userTrueName)) {
				System.out.println("�û�����ʵ���ֲ���Ϊ��");
				return;
			}		
			sysUserModelParameter.setUserTrueName(userTrueName);
			String userTelephone = sysUserModel.getUserTelephone();
			if(FormatEmpty.isEmpty(userTelephone)) {
				System.out.println("�û�����ϵ��ϵ�绰����Ϊ��");
				return;
			}
			sysUserModelParameter.setUserTelephone(userTelephone);
			String roleCode = sysUserModel.getRoleCode();
			if(FormatEmpty.isEmpty(roleCode)) {
				System.out.println("�û��Ľ�ɫ���벻��Ϊ��");
				return;
			}
			sysUserModelParameter.setRoleCode(roleCode);
			String classCode = sysUserModel.getClassCode();
			if(FormatEmpty.isEmpty(classCode)) {
				sysUserModelParameter.setClassCode("000000");
			}else {
				sysUserModelParameter.setClassCode(classCode);
			}
			sysUserModelParameter.setCreateTime(FormatCalendar.getCurrentTime(FormatCalendar.F_YMDHMS));
			sysUserModelParameter.setUpdateTime(FormatCalendar.getCurrentTime(FormatCalendar.F_YMDHMS));
			HttpSession session = request.getSession();
			//��session����ȡ����ǰ�ĵ�½�û��˺�
			String currentUserAccount =((SysUserModel)session.getAttribute("currentUser")).getUserAccount() ;
			if(FormatEmpty.isEmpty(currentUserAccount)) {
				System.out.println("��ǰ�û���û�е�½");
				return;
			}
			sysUserModelParameter.setCreator(currentUserAccount);
			sysUserModelParameter.setUpdater(currentUserAccount);
			sysUserModelParameter.setIsDelete(0);
			sysUserModelParameter.setIsEffect(1);
			sysUserModelParameter.setOrderBy((new Random()).nextDouble());
			String descr = sysUserModel.getDescr();
			if(FormatEmpty.isEmpty(descr)) {
				sysUserModelParameter.setDescr("��");
			}else {
				sysUserModelParameter.setDescr(descr);
			}
			sysUserService.insert(sysUserModelParameter);			
	    }
	   //���������û���Ϣ
	    @RequestMapping("/export.do")
	    public @ResponseBody String export(String ids , HttpServletRequest request ,HttpServletResponse response) {
	        response.setContentType("application/binary;charset=UTF-8");
	       
	        if(FormatEmpty.isEmpty(ids)) {
				System.out.println("û��ѡ��Ҫ�������û�");
				return "������Ϣʧ��";
			}
	        
	        try {
	            ServletOutputStream out = response.getOutputStream();
	            String fileName = new String(
	                    ("UserInfo " + new SimpleDateFormat("yyyy-MM-dd").format(new Date())).getBytes(), "UTF-8");
	            response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xls");
	            export(out , ids);
	            return "success";
	        } catch (Exception e) {
	            e.printStackTrace();
	            return "������Ϣʧ��";
	        }
	    }

	    private void export(ServletOutputStream out , String ids) throws Exception {
	        try {
	            // ��һ��������һ��workbook����Ӧһ��Excel�ļ�
	            HSSFWorkbook workbook = new HSSFWorkbook();
	            // �ڶ�������webbook�����һ��sheet,��ӦExcel�ļ��е�sheet
	            HSSFSheet hssfSheet = workbook.createSheet("sheet1");
	            // ����������sheet����ӱ�ͷ��0��,ע���ϰ汾poi��Excel����������������short
	            HSSFRow hssfRow = hssfSheet.createRow(0);
	            // ���Ĳ���������Ԫ�񣬲�����ֵ��ͷ ���ñ�ͷ����
	            HSSFCellStyle hssfCellStyle = workbook.createCellStyle();
	            // ������ʽ
	            hssfCellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);

	            HSSFCell hssfCell = null;
	            String[] titles = { "�û����", "�û�����", "�û�����", "��ʵ����" , "��ϵ�绰"};
	            for (int i = 0; i < titles.length; i++) {
	                hssfCell = hssfRow.createCell(i);// ��������0��ʼ
	                hssfCell.setCellValue(titles[i]);// ����1
	                hssfCell.setCellStyle(hssfCellStyle);// �о�����ʾ
	            }

	            // ���岽��д��ʵ������
	            String userIdArray[] = ids.split(",");
	            
	            List<SysUserModel> userList = new ArrayList<>();
	            SysUserModel sysUserModelParameter = new SysUserModel();
	            for (String id : userIdArray ) {
	            	sysUserModelParameter.setId(Integer.valueOf(id));
//	            	SysUserModel sysUserModelresult = (SysUserModel) sysUserService.selectId(sysUserModelParameter);
	            	SysUserModel sysUserModelresult =  sysUserService.selectId(sysUserModelParameter);
	            	userList.add(sysUserModelresult);
	            
	            }
	            
	           /* UserModel um1 = new UserModel(1, "aa1", 21, "aa2");
	            UserModel um2 = new UserModel(2, "bb1", 22, "bb2");
	            UserModel um3 = new UserModel(3, "cc1", 23, "cc2");
	            UserModel um4 = new UserModel(4, "dd1", 24, "dd2");
	            users.add(um1);
	            users.add(um2);
	            users.add(um3);
	            users.add(um4);*/

	            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	            // if (users != null && !users.isEmpty()) {
	            for (int i = 0; i < userList.size(); i++) {
	                hssfRow = hssfSheet.createRow(i + 1);
	                SysUserModel user = userList.get(i);

	                // ��������������Ԫ�񣬲�����ֵ
	                int userid = 0;
	                if (user.getId() != 0) {
	                    userid = user.getId();
	                }
	                hssfRow.createCell(0).setCellValue(userid);
	                String userAccount = "";
	                if (user.getUserAccount() != null) {
	                	userAccount = user.getUserAccount();
	                }
	                hssfRow.createCell(1).setCellValue(userAccount);
	                String userPassword = "";
	                if (user.getUserPassword() != null) {
	                	userPassword = user.getUserPassword();
	                }
	                hssfRow.createCell(2).setCellValue(userPassword);
	                
	                String userTrueName = "";
	                if (user.getUserTrueName() != null) {
	                	userTrueName = user.getUserTrueName();
	                }
	                hssfRow.createCell(3).setCellValue(userTrueName);
	                
	                String userTelephone = "";
	                if (user.getUserTelephone() != null) {
	                	userTelephone = user.getUserTelephone();
	                }
	                hssfRow.createCell(4).setCellValue(userTelephone);	          
	            }
	           

	            // ���߲������ļ�������ͻ��������
	            try {
	                workbook.write(out);
	                out.flush();
	                out.close();
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	            throw new Exception("������Ϣʧ�ܣ�");
	        }
	    }
	    
	    
	    
		
}
