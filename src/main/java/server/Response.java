package server;

import java.io.Serializable;

public class Response implements Serializable {
    private String message, detail;

    public Response() {
    }

    public Response(String message, String detail) {
        this.message = message;
        this.detail = detail;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    @Override
    public String toString() {
        return "Response{" +
                "message='" + message + '\'' +
                ", detail='" + detail + '\'' +
                '}';
    }
}
