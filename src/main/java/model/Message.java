package model;

import org.json.JSONObject;

public class Message implements JSONTransform{
    private String title, message, type, field;

    public Message(String title, String message, String type, String field) {
        this.title = title;
        this.message = message;
        this.type = type;
        this.field = field;
    }
    
    public Message(){}
    
    public Message(String json){
        JSONObject j = new JSONObject(json);
        
        this.title = j.getString("title");
        this.message = j.getString("message");
        this.type = j.getString("type");
        this.field = j.getString("field");
    }
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }
    
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        
        json.put("title", title);
        json.put("message", message);
        json.put("type", type);
        json.put("field", field);
        
        return json;
    }
    
}
