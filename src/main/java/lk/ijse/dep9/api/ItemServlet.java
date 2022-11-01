package lk.ijse.dep9.api;

import jakarta.annotation.Resource;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import lk.ijse.dep9.api.util.HttpServlet2;
import lk.ijse.dep9.dto.ItemDTO;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet(name = "item-servlet", value = "/items/*")
public class ItemServlet extends HttpServlet2 {

    @Resource(lookup = "java:/comp/env/jdbc/pos")
    private DataSource pool;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().println("<h1>Item Servlet - doGet..</h1>");
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
