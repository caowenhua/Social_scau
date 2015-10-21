package org.social.response;

import java.io.Serializable;

/**
 * Created by caowenhua on 2015/10/17.
 */
public class BaseResponse implements Serializable{

    /**
     * status : success
     * message : msg
     */

    private String status;
    private String message;

    public void setStatus(String status) {
        this.status = status;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
