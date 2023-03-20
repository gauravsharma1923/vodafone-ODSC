/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ods;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Himanshu Sharma 
 *
 */
public class SubmitData extends HttpServlet {
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		String strUser = "";
		String strFlag = "";
		String strPass = "";
		String strDate = "";
		String strSID = "";

		try {
			PrintWriter out = response.getWriter();
			strFlag = request.getParameter("flag");
			strFlag = strFlag.trim();
			strUser = request.getParameter("user");
			strUser = strUser.trim();
			strPass = request.getParameter("password");
			strPass = strPass.trim();
			strSID = request.getParameter("sid");
			strSID = strSID.trim();
			strDate = request.getParameter("date");
			strDate = strDate.trim();

			if (strFlag.equals("new")) {

				GetPassword objGetPassword = new GetPassword();
				int res = -1;
				int res1 = -1;int res2 = -1;
				res1 = objGetPassword.insertDB(strUser, strPass, strSID, strDate);
				res2 = objGetPassword.insertDB2(strUser, strPass, strSID, strDate);
				if(res1 == 0 || res2 == 0) {
					res=0;
				}
				if(res1 == 1 || res2 == 1) {
					res=1;
				}
								
				//out.println(res);
				System.out.println("ussdha="+res1+"|dmussd="+res2);				
				out.println("ussdha="+res1+"|dmussd="+res2);

			} else if (strFlag.equals("update")) {
				String curStrSid = request.getParameter("cursid");
				curStrSid = curStrSid.trim();
				GetPassword objGetPassword = new GetPassword();
				int upd = -1;
				int upd1 = -1, upd2 = -1;

				upd1 = objGetPassword.updateDB(strUser, strPass, strSID, strDate, curStrSid);
				upd2 = objGetPassword.updateDB2(strUser, strPass, strSID, strDate, curStrSid);
									
				System.out.println("ussdha="+upd1+"|dmussd="+upd2);			
				out.println("ussdha="+upd1+"|dmussd="+upd2);
			}else if(strFlag.equals("delete")) {
				String curStrSid = request.getParameter("cursid");
				curStrSid = curStrSid.trim();
				GetPassword objGetPassword = new GetPassword();
				int upd = -1;
				int upd1 = -1, upd2 = -1;

				upd1 = objGetPassword.deleteRAC1DB(strUser, strPass, strSID);
				upd2 = objGetPassword.deleteRAC2DB(strUser, strPass, strSID);
				System.out.println("upd1--->"+upd1+",upd2--->"+upd2);
							
				System.out.println("ussdha="+upd1+"|dmussd="+upd2);			
				out.println("ussdha="+upd1+"|dmussd="+upd2);
			}

		} catch (Exception e) {
			e.getMessage();
		}
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	@Override
	public String getServletInfo() {
		return "Short description";
	}
	
}
