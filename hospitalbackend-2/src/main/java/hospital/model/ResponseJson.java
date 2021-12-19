package hospital.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class ResponseJson implements Serializable {
    private int status;
    private String message;
    private String data;

    public ResponseJson() {
    }

    public ResponseJson(int status, String message, String data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }
}
