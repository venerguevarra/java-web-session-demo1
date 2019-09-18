package com.demo.session;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/order-taker")
public class OrderTakerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Cookie userIdCookie = null;
        final Cookie[] myCookies = request.getCookies();
        if (myCookies != null) {
            for (Cookie cookie : myCookies) {
                if (CookieManager.USER_ID_COOKIE.equals(cookie.getName())) {
                    userIdCookie = cookie;
                    break;
                }

            }
        }
        if (userIdCookie == null) {
            userIdCookie = CookieManager.createUserIdCookie("guest", request);
            response.addCookie(userIdCookie);
        }

        final HttpSession session = request.getSession(true);
        List<String> orderItems = (List<String>)session.getAttribute("orderItems");
        if (orderItems == null) {
            orderItems = new ArrayList<String>();
        }

        final String selectOrderItem = request.getParameter("orderItem");
        orderItems.add(selectOrderItem);
        session.setAttribute("orderItems", orderItems);

        final RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
        dispatcher.forward(request, response);
    }

}
