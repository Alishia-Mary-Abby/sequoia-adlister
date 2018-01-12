package com.codeup.adlister.controllers;


import com.codeup.adlister.dao.DaoFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ViewAdsInCategoriesServlet", urlPatterns = "/ads/category")
public class ViewCategoryPages extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long categoryId = Long.parseLong(request.getParameter("categoryId"));
        request.setAttribute("ads", DaoFactory.getAdsDao().findAdsByCategory(categoryId));
        request.getRequestDispatcher("/WEB-INF/ads/index.jsp").forward(request, response);
    }
}