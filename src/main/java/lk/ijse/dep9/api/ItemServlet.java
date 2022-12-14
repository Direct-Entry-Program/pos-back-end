package lk.ijse.dep9.api;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.annotation.Resource;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import lk.ijse.dep9.api.util.HttpServlet2;
import lk.ijse.dep9.dto.ItemDTO;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet(name = "item-servlet", value = "/items/*")
public class ItemServlet extends HttpServlet2 {



    @Resource(lookup = "java:/comp/env/jdbc/pos")
    private DataSource pool;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//            loadAllItems(resp);

        if (req.getPathInfo() == null || req.getPathInfo().equals("/")){
            // [/members, /members/]

            String query = req.getParameter("q");
            String size = req.getParameter("size");
            String page = req.getParameter("page");

            if (query != null && size != null && page != null){
                resp.getWriter().println("<h1>search Members by Page</h1>");
            }
            else if (query != null) {
                resp.getWriter().println("<h1>search Members</h1>");
            } else if (page != null && size != null) {
                resp.getWriter().println("<h1>load All Members by Page</h1>");
            } else {
                loadAllItems(resp);
//                response.getWriter().println("<h1>load All Members</h1>");
            }
        }else {
//                resp.getWriter().printf("<h1>get Member Details of: %s</h1>");

//        resp.getWriter().println("<h1>Item Servlet - doGet..</h1>");

            Matcher matcher = Pattern.compile("^/([A-Fa-f0-9]{8}(-[A-Fa-f0-9]{4}){3}-[A-Fa-f0-9]{12})/?$").matcher(req.getPathInfo());
            if (matcher.matches()){
                getItemDetails(matcher.group(1),resp);

            }else {

                resp.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED,"Expected valid UUID");
            }
        }

//        resp.getWriter().println("<h1>Item Servlet - doGet..</h1>");
    }

    private void loadAllItems(HttpServletResponse response) throws IOException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/pos_db", "root", "mysql")) {
                Statement stm = connection.createStatement();
                ResultSet rst = stm.executeQuery("SELECT * FROM Item");

                ArrayList<ItemDTO> members = new ArrayList<>();

                while (rst.next()){
                    String code = rst.getString("code");
                    int stock = rst.getInt("stock");
                    double unit_price = rst.getDouble("unit_price");
                    String description = rst.getString("description");
//                    ItemDTO dto = new ItemDTO(code, stock, unit_price, description);
                    ItemDTO dto = new ItemDTO(code, stock, unit_price, description);
                    members.add(dto);
                }


                Jsonb jsonb = JsonbBuilder.create();
                response.setContentType("application/json");
                jsonb.toJson(members, response.getWriter());


            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to fetch items");
        }

//        response.getWriter().printf("WS: Load All items");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().println("<h1>Item Servlet - doPost..</h1>");
    }

    @Override
    protected void doPatch(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().println("<h1>Item Servlet - doPatch..</h1>");
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().println("<h1>Item Servlet - doDelete..</h1>");
    }

    private void getItemDetails(String itemCode, HttpServletResponse response){
        try(Connection connection = pool.getConnection();){
            PreparedStatement stm = connection.prepareStatement("SELECT * FROM item WHERE code=?");
            stm.setString(1, itemCode);

            ResultSet rst = stm.executeQuery();
            if (rst.next()){
                String code = rst.getString("code");
                int stock = rst.getInt("stock");
                double unitPrice = rst.getDouble("unit_price");
                String description = rst.getString("description");

                response.setContentType("application/json");
                JsonbBuilder.create().toJson(new ItemDTO(code,stock,unitPrice,description),response.getWriter());

            }else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND,"Not a registered item");
            }

        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
