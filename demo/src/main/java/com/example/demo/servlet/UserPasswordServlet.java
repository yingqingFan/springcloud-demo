package com.example.demo.servlet;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@SuppressWarnings("unchecked")
public class UserPasswordServlet extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = -6536083390104403961L;

	private static final int BUFSIZE = 4096;

    
    @Override
   	public void init(ServletConfig config) throws ServletException {
    	SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
              config.getServletContext());  
   		super.init(config);
    }

	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String password = request.getParameter("password");
		String userId = request.getParameter("id");
		int passwordStrengthLevel = 1;
		if (userId != null && userId.equals("")){
			passwordStrengthLevel = -2;
		}
		if(StringUtils.isEmpty(password)){
			passwordStrengthLevel = -1;
		}
		response.setContentType("text/xml;charset=UTF-8"); 
//		System.out.println(passwordStrengthLevel);
//		String data = null;
//		if(passwordStrengthLevel==0){
//			data="weak";
//		}if(passwordStrengthLevel==1){
//			data="meduim";
//		}if(passwordStrengthLevel==2){
//			data="strong";
//		}
		response.getWriter().print(passwordStrengthLevel);
    }

	@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

    }


}
