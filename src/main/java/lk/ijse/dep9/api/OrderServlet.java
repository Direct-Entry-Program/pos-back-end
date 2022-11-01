package lk.ijse.dep9.api;

import jakarta.annotation.Resource;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import lk.ijse.dep9.api.util.HttpServlet2;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet(name = "order-servlet", value = "/orders/*")
public class OrderServlet extends HttpServlet2 {
//    @Resource(lookup = "java:/comp/env/jdbc/pos")
//    private DataSource pool;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getPathInfo() == null || request.getPathInfo().equals("/")) {
            String query = request.getParameter("q");
            String size = request.getParameter("size");
            String page = request.getParameter("page");

            if (query != null && size != null && page != null) {
                if (!size.matches("\\d+") || !page.matches("\\d+")) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid page or size");
                }
                else {
//                    searchPaginatedOrders(query, Integer.parseInt(size), Integer.parseInt(page), response);
                    response.getWriter().println("<h1>Search paginated orders</h1>");
                }
            }
        }
        else {
            Matcher matcher = Pattern.compile("^/([A-Fa-f0-9]{8}(-[A-Fa-f0-9]{4}){3}-[A-Fa-f0-9]{12})/?$")
                    .matcher(request.getPathInfo());
            if (matcher.matches()) {
//                    getOrderDetails(matcher.group(1), response);
                response.getWriter().println("<h1>Get order details</h1>");
            }
            else {
                response.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED, "Expected valid UUID");
            }
        }
    }

    private void searchPaginatedOrders(String query, int parseInt, int parseInt1, HttpServletResponse response) {

    }

    private void getOrderDetails(String orderId, HttpServletResponse response) {

    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().println("<h1>Order Servlet - doPost..</h1>");
    }
}
