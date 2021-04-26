package com.xivs.common.Utils;

import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hasher {
    public static final Logger logger = (Logger) LoggerFactory.getLogger(Hasher.class);

    public static byte[] getHash(String in){
        byte[] in_b = in.getBytes(StandardCharsets.UTF_8);
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            return md.digest(in_b);

        }catch (NoSuchAlgorithmException ex){
            logger.error(ex.getMessage());
            return in_b;
        }

    }
}
