package com.bittech.java.client.service;
import com.bittech.java.util.CommUtils;

import java.io.IOException;
import java.io.OutputStream;

import java.io.InputStream;
import java.net.Socket;
import java.util.Properties;

/**
 * @Date: 2019-08-13 10:37
 * @Description:
 */
public class Connect2Server {
    private static final String IP;
    private static final int PORT;
    static {
        Properties pros = CommUtils.loadProperties("socket.properties");
        IP = pros.getProperty("address");
        PORT = Integer.parseInt(pros.getProperty("port"));
    }
    private Socket client;
    private InputStream in;
    private OutputStream out;

    public Connect2Server() {
        try {
            client = new Socket(IP,PORT);
            in = client.getInputStream();
            out = client.getOutputStream();
        } catch (IOException e) {
            System.err.println("与服务器建立连接失败");
            e.printStackTrace();
        }
    }

    /*
    拿输入流获取服务器端发来的消息
    拿输出流给服务器端发送消息
     */
    public InputStream getIn() {
        return in;
    }

    public OutputStream getOut() {
        return out;
    }
}
