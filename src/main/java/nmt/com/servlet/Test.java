package nmt.com.servlet;

import com.mongodb.MongoClient;
import nmt.com.dao.MongoDBCustomerDAO;
import nmt.com.model.Customer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/test")
public class Test extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int offset = 0;
        int limit = 100000;
        MongoClient mongo = (MongoClient) req.getServletContext()
                .getAttribute("MONGO_CLIENT");
        MongoDBCustomerDAO customerDAO = new MongoDBCustomerDAO(mongo);
        String context = "";
        List<List<Customer>> list = new ArrayList<>();

        while (offset < 2000000) {
            System.out.println(offset);
            List<Customer> customers = customerDAO.readAllCustomer(offset, limit);
            list.add(customers);
            offset = offset + limit;
        }

        List<Customer> head = list.get(0).subList(0, 5);
        List<Customer> tail = list.get(list.size() - 1).subList(Math.max(list.get(list.size() - 1).size() - 5, 0), list.get(list.size() - 1).size());

        context = CustomerServlet.getString(context,head);
        context = CustomerServlet.getString(context,tail);

        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();
        out.print("[" + context + "]");
        out.flush();
    }


}
