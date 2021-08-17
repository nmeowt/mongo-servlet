package nmt.com.converter;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import nmt.com.model.Customer;
import org.bson.types.ObjectId;

public class CustomerConverter {
    // convert Person Object to MongoDB DBObject
    public static DBObject toDBObject(Customer cus) {
        BasicDBObjectBuilder builder = BasicDBObjectBuilder.start().append("name", cus.getName()).append("country", cus.getCountry());
        if (cus.getId() == null){
            builder = builder.append("_id", new ObjectId(cus.getId()));
        }
        return builder.get();
    }

    // convert DBObject Object to Person
    public static Customer toCustomer(DBObject doc){
        Customer cus = new Customer();
        cus.setName((String) doc.get("name"));
        cus.setCountry((String) doc.get("_id"));
        ObjectId id = (ObjectId)  doc.get("_id");
        cus.setId(id.toString());
        return cus;
    }
}
