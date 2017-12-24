package com.bps.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bps.persistence.tables.User;
import com.bps.service.core.ProcessContext;
import com.bps.service.core.ProcessContextPool;
import com.bps.service.core.UserManager;
import com.bps.service.exceptions.BaseException;
import com.bps.util.CommonConstants;
import com.bps.util.CommonUtility;

@WebServlet(urlPatterns = "/user", name = "UserController")
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public UserController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ProcessContext context = ProcessContextPool.get();
		if (context == null) {
			ProcessContext processContext = new ProcessContext();
			ProcessContextPool.set(processContext);
		}
		HttpSession session = request.getSession();
		String email = (String) session.getAttribute(CommonConstants.EMAIL);
		UserManager userManager = new UserManager();
		User user = null;
		try {
			user = userManager.getUser(email);
			if (user != null) {
				if(ProcessContextPool.get().getUser() == null) {
					ProcessContextPool.get().setUser(user);
				}
				String action = request.getParameter(CommonConstants.ACTION);
				if ("changePassword".equalsIgnoreCase(action)) {
					String newPassword1 = request.getParameter("newPassword1");
					String newPassword2 = request.getParameter("newPassword2");
					if (newPassword1 != null && !newPassword1.isEmpty() && newPassword2 != null && !newPassword2.isEmpty() && newPassword1.equals(newPassword2)) {
						user.setPassword(newPassword1);
						userManager.updateUser(user);
						request.setAttribute(CommonConstants.IS_PASSWORD_CHANGE_SUCCESSFUL, true);
						CommonUtility.navigateToPage("/views/adminHome.jsp", request, response);
					} else {
						request.setAttribute(CommonConstants.IS_PASSWORD_CHANGE_SUCCESSFUL, false);
						CommonUtility.navigateToPage("/views/adminHome.jsp", request, response);
					}
				} else if ("changeName".equalsIgnoreCase(action)) {
					String name = request.getParameter("name");
					if (name != null && !name.isEmpty()) {
						user.setName(name);
						userManager.updateUser(user);
						request.setAttribute(CommonConstants.IS_NAME_CHANGE_SUCCESSFUL, true);
						session.setAttribute("name", name);
						CommonUtility.navigateToPage("/views/adminHome.jsp", request, response);
					} else {
						request.setAttribute(CommonConstants.IS_NAME_CHANGE_SUCCESSFUL, false);
						CommonUtility.navigateToPage("/views/adminHome.jsp", request, response);
					}
				} else if ("deleteAccount".equalsIgnoreCase(action)) {

				}
			} else {
				// TODO: Handle error case
			}
		} catch (BaseException e1) {
			e1.printStackTrace();
		}
		// }
	}
}
