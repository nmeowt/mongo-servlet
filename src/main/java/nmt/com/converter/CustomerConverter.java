package nmt.com.converter;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import nmt.com.model.Customer;
import org.bson.types.ObjectId;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;


public class CustomerConverter {
    // convert Person Object to MongoDB DBObject
    public static DBObject toDBObject(Customer cus) {
        BasicDBObjectBuilder builder = BasicDBObjectBuilder.start()
                .append("code", cus.getCode())
                .append("name", cus.getName())
                .append("address", cus.getAddress())
                .append("dateBirth", cus.getDateBirth())
                .append("createdAt", cus.getCreatedAt());
        return builder.get();
    }

    // convert DBObject Object to Person
    public static Customer toCustomer(DBObject doc) {
        Customer cus = new Customer();
        cus.setCode((Integer) doc.get("code"));
        cus.setName((String) doc.get("name"));
        cus.setAddress((String) doc.get("address"));
        cus.setDateBirth((Date) doc.get("dateBirth"));
        Date date = (Date) doc.get("createdAt");
        Timestamp createdAt = new Timestamp(date.getTime());
        cus.setCreatedAt(createdAt);
        ObjectId id = (ObjectId) doc.get("_id");
        cus.setId(id.toString());
        return cus;
    }
}
