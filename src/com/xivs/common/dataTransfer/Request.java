package com.xivs.common.dataTransfer;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Класс стандартного ответа.
 * body хранит строковые поля
 * attachments хранит любые dataTransference
 */
public class Request implements Serializable {

    public final String method;
    public final HashMap<String, DataTransference<?>> attachments;
    public Auth auth;
    public Request(String method, HashMap<String, DataTransference<?>> attachments) {
        this.auth = new Auth("", "");
        this.method = method;
        this.attachments = attachments;
    }
    public void setAuth(Auth auth) {
        this.auth = auth;
    }


}
