package org.meyi.demo.socketDemo;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;

import io.netty.channel.AddressedEnvelope;

/**
 * 
 * @Description:
 * @User: l_c
 * @Date: 2017年7月31日 下午3:32:53
 */
public class Client {

	public static String IP_ADDR = "10.0.0.250";

	public static final int PORT = 2323;

	public static void main(String[] args) {
		System.out.println("客户端启动.."+(System.getProperty("user.dir")));
		System.out.println("当收到服务器端字符为\"OK\"的时候, 客户端讲终止\n");
		while (true) {
			Socket socket = null;
			try {
				socket = new Socket(IP_ADDR, PORT);

				DataInputStream input = new DataInputStream(socket.getInputStream());

				DataOutputStream output = new DataOutputStream(socket.getOutputStream());
				System.out.println("请输入：\t");
				String string = new BufferedReader(new InputStreamReader(System.in)).readLine();
				output.write(string.getBytes());

				String ret = input.readLine();
				System.err.println("服务器返回过来的是：" + ret);
				if("OK".equals(ret)) {
					System.err.println("客户端即将关闭");
					Thread.sleep(500);
					break;
				}
				
				output.close();
				input.close();

			} catch (Exception e) {

			} finally {
				if (socket != null) {
					try {
						socket.close();
					} catch (IOException e) {
						socket = null;
						System.err.println("客户端fianlly 异常"+ e.getMessage());
					}
				}
			}
		}
	}

}
