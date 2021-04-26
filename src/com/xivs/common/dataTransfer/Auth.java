package com.xivs.common.dataTransfer;

import com.xivs.common.Utils.Hasher;

import java.io.Serializable;

public class Auth implements Serializable {
    public String login;
    public byte[] password;
    public Auth(String login, String password){
        this.login = login;
        this.password = Hasher.getHash(password);
    }
}
