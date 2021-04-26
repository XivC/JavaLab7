package com.xivs.common.dataTransfer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Класс стандартного ответа.
 * Сообщения передаются в messages
 * Статус запроса присваивается SUCCESS или ERROR
 * body хранит строковые поля
 * attachments хранит любые dataTransference
 */
public class Response implements Serializable {
    public static enum Status {
        OK,
        ERROR
    }

    public final ArrayList<String> messages;
    public final HashMap<String, DataTransference<?>> attachments;
    public final Status status;

    public Response(Status status, ArrayList<String> messages, HashMap<String, DataTransference<?>> attachments) {
        this.messages = messages;
        this.attachments = attachments;
        this.status = status;
    }


}