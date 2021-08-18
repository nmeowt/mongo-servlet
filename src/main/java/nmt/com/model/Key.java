package nmt.com.model;

public enum Key {
    CUSTOMER_ALL("customer:all"),
    CUSTOMER_DATA("customer:%s:data"),
    CUSTOMER_IDS("customer:ids");

    private String key;
    Key(String key){
        this.key = key;
    }

    public String key(){
        return key;
    }

    public String formated(String... value){
        return String.format(key, value);
    }
}
