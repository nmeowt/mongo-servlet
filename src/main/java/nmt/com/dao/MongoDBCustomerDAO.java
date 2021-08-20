package nmt.com.dao;

import com.mongodb.*;
import nmt.com.converter.CustomerConverter;
import nmt.com.model.Customer;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

public class MongoDBCustomerDAO {
    //DAO class for different MongoDB CRUD operations
    DBCollection col;

    public MongoDBCustomerDAO(MongoClient mongo) {
        this.col = mongo.getDB("demo").getCollection("customer");
    }

    public Customer createCustomer(Customer cus) {
        DBObject doc = CustomerConverter.toDBObject(cus);
        this.col.insert(doc);
        ObjectId id = (ObjectId) doc.get("_id");
        cus.setId(id.toString());
        return cus;
    }

    public void updateCustomer(Customer cus) {
        DBObject query = BasicDBObjectBuilder.start().append("_id", new ObjectId(cus.getId())).get();
        this.col.update(query, CustomerConverter.toDBObject(cus));
    }

    public List<Customer> readAllCustomer(Integer offset, Integer limit) {
        List<Customer> data = new ArrayList<Customer>();
        DBCursor cursor = col.find().skip(offset).limit(limit);
        while (cursor.hasNext()) {
            DBObject doc = cursor.next();
            Customer cus = CustomerConverter.toCustomer(doc);
            data.add(cus);
        }
        return data;
    }

    public void deleteCustomer(Customer cus) {
        DBObject query = BasicDBObjectBuilder.start().append("_id", new ObjectId(cus.getId())).get();
        this.col.remove(query);
    }

    // update later
    public Customer readCustomer(Customer cus) {
        DBObject query = BasicDBObjectBuilder.start().append("_id", new ObjectId(cus.getId())).get();
        DBObject data = this.col.findOne(query);
        return CustomerConverter.toCustomer(data);
    }
}
