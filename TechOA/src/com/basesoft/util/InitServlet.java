package com.basesoft.util;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class InitServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public InitServlet() {
	}

	public void init() throws ServletException {
		try {
			WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
			DBUpgrade dbUpgrade = (DBUpgrade) context.getBean("dbUpgrade");

			getServletContext().setAttribute("RootPath", getServletContext().getRealPath(""));
			getServletContext().setAttribute("springContext",
					WebApplicationContextUtils.getWebApplicationContext(getServletContext()));
			startDB();

			dbUpgrade.upgrade(getServletContext().getAttribute("RootPath") + "/WEB-INF/dbupgrade/");
		} catch (Exception ex) {
			throw new ServletException(ex.getMessage());
		}
	}

	private void startDB() {
	}
}
