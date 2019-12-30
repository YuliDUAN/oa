package com.dk.oa.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dk.oa.global.FaceSpot;
import org.json.JSONObject;

/**
 * Servlet implementation class FaceLoginServlet
 */
public class FaceLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public FaceLoginServlet() {
        super();
    }


	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.getHeader("textml; charset=UTF-8");
		//实例化PrintWriter对象
		PrintWriter out = response.getWriter();
		String img = request.getParameter("img");
		JSONObject js = FaceSpot.searchFace(img, "yuriDuan", "1000");
		System.out.println(js.toString(2));
		out.print(js);
	}

}
