package com.emekamomodu.dronewebservice.model;

/**
 * @author CMOMODU
 * @version 1.0
 * @date 12/31/21 7:38 PM
 */
public class Response {

    private Boolean success = false;

    private String message = "";

    private Object data = "N/A";

    public Response() {
    }

    public Response(String message) {
        this.message = message;
    }

    public Response(Boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public Response(Boolean success, String message, Object data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Response{" +
                "success=" + success +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
