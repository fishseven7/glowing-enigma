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

import com.datang.hrb.service.LoginService;
import com.datang.hrb.service.RegisterService;
import com.datang.hrb.service.impl.LoginServiceImpl;
import com.datang.hrb.service.impl.RegisterServiceImpl;
import com.datang.hrb.util.ImgCodeUtil;
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
			
		}
		
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		String phone = req.getParameter("phone");

		System.out.println("uri===="+req.getRequestURI());
		String uri = req.getRequestURI();
		String action = uri.substring(uri.lastIndexOf("/")+1, uri.indexOf("."));
		 HttpSession session = req.getSession();
		if(action.equals("register")) {//注册
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
			String code = req.getParameter("code");
			String sessionCode = (String)session.getAttribute("code");
			if(code!=null&&code.equalsIgnoreCase(sessionCode)) {
				LoginService loginService = new LoginServiceImpl();
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
					session.setAttribute("userList", userList);
					//登录成功
					resp.sendRedirect("login_success.jsp");
				}
			}else {
				session.setAttribute("message", "验证码错误");
				resp.sendRedirect("error.jsp");
			}
		}else {//
			
		}
		
	}

}
