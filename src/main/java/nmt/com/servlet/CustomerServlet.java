package nmt.com.servlet;

import com.mongodb.MongoClient;
import nmt.com.dao.MongoDBCustomerDAO;
import nmt.com.model.Customer;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/customer")
public class CustomerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer offset = Integer.valueOf(req.getParameter("offset"));
        Integer limit = Integer.valueOf(req.getParameter("limit"));

        MongoClient mongo = (MongoClient) req.getServletContext()
                .getAttribute("MONGO_CLIENT");
        MongoDBCustomerDAO customerDAO = new MongoDBCustomerDAO(mongo);
        List<Customer> customers = customerDAO.readAllCustomer(offset, limit);
        String context = "";
        context = getString(context, customers);
        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();
        out.print("[" + context + "]");
        out.flush();
    }

    static String getString(String context, List<Customer> customers) {
        for (Customer cus : customers) {
            String data = "{" +
                    "\"id\": \"" + cus.getId() + "\"," +
                    "\"code\": \"" + cus.getCode() + "\"," +
                    "\"name\": \"" + cus.getName() + "\"," +
                    "\"address\": \"" + cus.getAddress() + "\"," +
                    "\"dateBirth\": \"" + cus.getDateBirth() + "\"," +
                    "\"createdAt\": \"" + cus.getCreatedAt() + "\"," +
                    "}";
            context += data + ",";
        }
        return context;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String address = req.getParameter("address");
        String dateBirth = req.getParameter("address");

        if ((name == null || name.equals("")) || (address == null || address.equals(""))) {
            System.out.println("Error");
        } else {
            Customer cus = new Customer();
            cus.setName(name);
            cus.setAddress(address);
//            cus.setDateBirth(dateBirth);
            MongoClient mongo = (MongoClient) req.getServletContext().getAttribute("MONGO_CLIENT");
            MongoDBCustomerDAO customerDAO = new MongoDBCustomerDAO(mongo);
            customerDAO.createCustomer(cus);
            resp.setContentType("application/json");
            PrintWriter out = resp.getWriter();
            String context = "{" +
                    "\"id\": \"" + cus.getId() + "\"," +
                    "\"name\": \"" + cus.getName() + "\"," +
                    "\"country\": \"" + cus.getAddress() + "\"," +
                    "}";
            out.print(context);
            out.flush();
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        String name = req.getParameter("name");
        String country = req.getParameter("country");
        if (id == null || "".equals(id)) {
            throw new ServletException("id missing for edit operation");
        }
        if ((name == null || name.equals("")) || (country == null || country.equals(""))) {
            throw new ServletException("name and country missing for edit operation");
        } else {
            MongoClient mongo = (MongoClient) req.getServletContext().getAttribute("MONGO_CLIENT");
            MongoDBCustomerDAO customerDAO = new MongoDBCustomerDAO(mongo);
            Customer cus = new Customer();
            cus.setId(id);
            cus.setName(name);
            cus.setAddress(country);
            customerDAO.updateCustomer(cus);
            resp.setContentType("application/json");
            PrintWriter out = resp.getWriter();
            String context = "{" +
                    "\"id\": \"" + cus.getId() + "\"," +
                    "\"name\": \"" + cus.getName() + "\"," +
                    "\"country\": \"" + cus.getAddress() + "\"," +
                    "}";
            out.print(context);
            out.flush();
        }

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        if (id == null || "".equals(id)) {
            throw new ServletException("id missing for delete operation");
        }
        MongoClient mongo = (MongoClient) req.getServletContext().getAttribute("MONGO_CLIENT");
        MongoDBCustomerDAO customerDAO = new MongoDBCustomerDAO(mongo);
        Customer cus = new Customer();
        cus.setId(id);
        customerDAO.deleteCustomer(cus);
        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();
        String context = "{\"message\": \"Delete successfully\"}";
        out.print(context);
        out.flush();
    }
}
