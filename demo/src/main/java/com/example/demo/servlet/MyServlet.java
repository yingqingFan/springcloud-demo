package com.example.demo.servlet;

import com.example.demo.common.IpAdressHelper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MyServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("-----------doGet----------------");
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("-----------doPost----------------");
        String ip = IpAdressHelper.getIpAddr(req);
        resp.getWriter().print("<h1>Hello MyServlet Response return you this</h1><br/>ipAdress"+ip);
    }

    @Override
    public void init() throws ServletException {
        super.init();
    }

}
