package lk.ijse.dep9.api;

import jakarta.annotation.Resource;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import lk.ijse.dep9.api.util.HttpServlet2;
import lk.ijse.dep9.dto.CustomerDTO;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

@WebServlet(name = "customer-servlet", value = "/customers/*")
public class CustomerServlet extends HttpServlet2 {

    @Resource(lookup = "java:/comp/env/jdbc/pos")
    private DataSource pool;


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws  IOException {
//            loadAllCustomers(response);

        String query = req.getParameter("q");
        String size = req.getParameter("size");
        String page = req.getParameter("page");

        searchPaginatedCustomers(query,Integer.parseInt(size),Integer.parseInt(page),resp);

    }

    @Override
    protected void doPatch(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().println("<h1>doPatch..</h1>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().println("<h1>doPost..</h1>");
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().println("<h1>doDelete..</h1>");
    }

    public void loadAllCustomers(HttpServletResponse response) throws IOException{

        try {

            Connection connection = pool.getConnection();

            Statement stm = connection.createStatement();
            ResultSet rst = stm.executeQuery("SELECT * FROM Customer");



            ArrayList<CustomerDTO> customers = new ArrayList<>();

            while (rst.next()){
                String id = rst.getString("id");
                String name = rst.getString("name");
                String address = rst.getString("address");


                CustomerDTO dto = new CustomerDTO(id,name,address);
                customers.add(dto);
            }


            connection.close();

            Jsonb jsonb = JsonbBuilder.create();
            response.setContentType("application/json");
            jsonb.toJson(customers,response.getWriter());




        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,"Failed to load customers");
        }

    }

    private void searchPaginatedCustomers (String query , int size , int page , HttpServletResponse response) throws IOException {
        try(Connection connection = pool.getConnection()){
            PreparedStatement stm1 = connection.prepareStatement("SELECT COUNT(id) as count FROM Customer WHERE id LIKE ? OR name LIKE  ? OR address LIKE ?");
            PreparedStatement stm2 = connection.prepareStatement("SELECT * FROM Customer WHERE id LIKE ? OR name LIKE ? OR address LIKE ? LIMIT ? OFFSET ?");

            query = "%"+query+"%";
            for (int i = 1; i < 4; i++) {
                stm1.setString(i,query);
                stm2.setString(i,query);
                
            }
            stm2.setInt(4,size);
            stm2.setInt(5,(page-1)*size);

            ResultSet rst = stm1.executeQuery();
            rst.next();
            int totalItems = rst.getInt("count");
            response.setIntHeader("X-Total-count",totalItems);

            rst = stm2.executeQuery();

            ArrayList<CustomerDTO> customers = new ArrayList<>();
            while (rst.next()){
                String id = rst.getString("id");
                String name = rst.getString("name");
                String address = rst.getString("address");
            customers.add(new CustomerDTO(id,name,address));

            }

            response.setContentType("application/json");
            JsonbBuilder.create().toJson(customers,response.getWriter());


        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,"Can not load the db please try again");
        }
    }


}
