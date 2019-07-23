package com.datang.hrb.controller;

import java.awt.image.RenderedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.datang.hrb.service.ClassService;
import com.datang.hrb.service.LoginService;
import com.datang.hrb.service.RegisterService;
import com.datang.hrb.service.StudentService;
import com.datang.hrb.service.impl.ClassServiceImpl;
import com.datang.hrb.service.impl.LoginServiceImpl;
import com.datang.hrb.service.impl.RegisterServiceImpl;
import com.datang.hrb.service.impl.StudentServiceImpl;
import com.datang.hrb.util.ImgCodeUtil;
import com.datang.hrb.vo.Class;
import com.datang.hrb.vo.Student;
import com.datang.hrb.vo.User;


public class GlobalController extends HttpServlet {

	private Map<String,String> userMap = new HashMap<String,String>();
	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("run in doget");
		String uri = req.getRequestURI();
		String action = uri.substring(uri.lastIndexOf("/")+1, uri.indexOf("."));
		if(action.equals("getImgCode")) {
			// 调用工具类生成的验证码和验证码图片
	        Map<String, Object> codeMap = ImgCodeUtil.generateCodeAndPic();
	        //code-->1234
	        //codePic-->picbyte

	        // 将四位数字的验证码保存到Session中。
	        HttpSession session = req.getSession();
	        session.setAttribute("code", codeMap.get("code").toString());

	        // 禁止图像缓存。
	        resp.setHeader("Pragma", "no-cache");
	        resp.setHeader("Cache-Control", "no-cache");
	        resp.setDateHeader("Expires", -1);

	        resp.setContentType("image/jpeg");

	        // 将图像输出到Servlet输出流中。
	        ServletOutputStream sos;
	        try {
	            sos = resp.getOutputStream();
	            ImageIO.write((RenderedImage) codeMap.get("codePic"), "jpeg", sos);
	            sos.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		}else {
			doPost(req,resp);
		}
		
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		System.out.println("uri===="+req.getRequestURI());
		String uri = req.getRequestURI();
		String action = uri.substring(uri.lastIndexOf("/")+1, uri.indexOf("."));
		 HttpSession session = req.getSession();
		 
		if(action.equals("register")) {//注册
			String username = req.getParameter("username");
			String password = req.getParameter("password");
			String phone = req.getParameter("phone");
			RegisterService registerService = new RegisterServiceImpl();
			User user = new User();
			user.setUsername(username);
			user.setPassword(password);
			user.setPhone(phone);
			int i = registerService.addUser(user);
			if(i==1) {
				resp.sendRedirect("register_success.jsp");
			}else {
				resp.sendRedirect("register_fail.jsp");
			}
			
		}else if(action.equals("login")) {//
			String username = req.getParameter("username");
			String password = req.getParameter("password");
			String code = req.getParameter("code");
			String sessionCode = (String)session.getAttribute("code");
			if(code!=null&&code.equalsIgnoreCase(sessionCode)) {
				LoginService loginService = new LoginServiceImpl();
				StudentService StudentService = new StudentServiceImpl();
				ClassService ClassService = new ClassServiceImpl();
				User user = new User();
				user.setUsername(username);
				user.setPassword(password);
				User dbuser = loginService.login(user);
				if(dbuser==null) {
					//登录失败
					resp.sendRedirect("login_fail.jsp");
				}else {
					
					session.setAttribute("user", dbuser);
					
					List<User> userList = loginService.getUserList();
					List<Student> stu_list = StudentService.studentList();
					List<Class> class_list = ClassService.classList();
					session.setAttribute("userList", userList);
					session.setAttribute("studentList", stu_list);
					session.setAttribute("classList", class_list);
					//登录成功
					resp.sendRedirect("login_success.jsp");
				}
			}else {
				session.setAttribute("message", "验证码错误");
				resp.sendRedirect("error.jsp");
			}
		}else if(action.equals("addClass")){//新增班级
			//整合班级数据
			Class clazz = new Class();
			clazz.setName(req.getParameter("name"));
			clazz.setProfession(req.getParameter("profession"));
			clazz.setSchool(req.getParameter("school"));
			//调用service方法，保存数据
			ClassService classService = new ClassServiceImpl();
			int i = classService.addClass(clazz);
			if(i==1) {
				//跳转到班级列表页----
				resp.sendRedirect("classList.do");
			}else {
				//跳转到错误页面
				session.setAttribute("message", "新增班级错误");
				resp.sendRedirect("error.jsp");
			}
		}else if(action.equals("classList")){//查询所有班级列表
			
			//调用service方法，保存数据
			ClassService classService = new ClassServiceImpl();
			List<Class> classList = classService.classList();
			
			session.setAttribute("classList",classList);
			resp.sendRedirect("class_list.jsp");
		}else if(action.equals("addStu")){//新增学生
			//整合学生数据
			Student student = new Student();
			student.setCode(req.getParameter("code"));
			student.setName(req.getParameter("name"));
			student.setClassId(Integer.parseInt(req.getParameter("classid")));
			student.setGender(req.getParameter("gender"));
			student.setEmail(req.getParameter("email"));
			student.setPhone(req.getParameter("phone"));
			//调用service方法，保存数据
			StudentService studentService = new StudentServiceImpl();
			int i = studentService.addStudent(student);
			if(i==1) {
				//跳转到学生列表页----
				resp.sendRedirect("studentList.do");
			}else {
				//跳转到错误页面
				session.setAttribute("message", "新增学生错误");
				resp.sendRedirect("error.jsp");
			}
		}else if(action.equals("studentList")){//新增学生
			//调用service方法，保存数据
			StudentService studentService = new StudentServiceImpl();
			List<Student> studentList = studentService.studentList();
			
			session.setAttribute("studentList",studentList);
			resp.sendRedirect("student_list.jsp");
			
		}
		
	}

}
