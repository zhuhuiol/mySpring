package com.homolo.homolo.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * 实现要求：
 * 1、根据代码片段实现一个简单的SOCKET ECHO程序；
 * 2、接受到客户端连接后，服务端返回一个欢迎消息;
 * 3、接受到"bye"消息后， 服务端返回一个结束消息，并结束当前连接;
 * 4、支持通过telnet连接本服务端，并且可正常运行；
 * 5、注意代码注释书写。
 *
 */
public class EchoApplicationTest {

	public static void main(String[] args) throws IOException {
		//启动服务端
//		EchoServer echoServer= new EchoServer();
//		echoServer.startService();
		//启动客户端
//		EchoClient echoClient = new EchoClient();
//		echoClient.startService();


	}
}
class EchoServer {
	public static void main(String[] args) throws IOException {
		EchoServer echoServer= new EchoServer();
		echoServer.startService();
	}
	private static final Logger LOGGER = LoggerFactory.getLogger(EchoServer.class);
	// TODO
	//默认端口
	private int port = 8081;
	//socker服务
	private ServerSocket serverSocket;

	public EchoServer() throws IOException {
		LOGGER.info("服务端构造方法");
		this.serverSocket = new ServerSocket(this.port);
	}
	public EchoServer(int port) throws IOException {
		this.port = port;
		this.serverSocket = new ServerSocket(this.port);
	}

	//输出
	private PrintWriter getWriter(Socket socket) {
		try {
			OutputStream outputStream = socket.getOutputStream();
			return new PrintWriter(outputStream, true);
		} catch (IOException e) {
			LOGGER.error("创建回复客户机异常:{}", e.getMessage(), e);
		}
		return null;
	}

	private BufferedReader getReader(Socket socket) {
		try {
			InputStream inputStream = socket.getInputStream();
			return new BufferedReader(new InputStreamReader(inputStream));
		} catch (IOException e) {
			LOGGER.error("创建读取机异常:{}", e.getMessage(), e);
		}
		return null;
	}

	/**
	 * 服务端启动服务.
	 */
	public void startService() {
		//客户机连接.
		try {
			Socket socket = this.serverSocket.accept();
			LOGGER.info("客户端连接成功，ip:{},port:{}", socket.getInetAddress(), socket.getPort());
			BufferedReader br = getReader(socket);	//读取客户机交流语句
			PrintWriter pw = getWriter(socket);
			//本地控制台输入
			Scanner input = new Scanner(System.in);
			pw.println("欢迎你");
			String message;
			while ((message = br.readLine()) != null) {
				LOGGER.info("收到客户端消息：{}", message);
				if ("bye".equals(message)) {
					//接到再见,返回语句，断开连接
					pw.println("bye");
					break;
				} else {
					message = input.nextLine();
					pw.println(message);
				}
			}
		} catch (IOException e) {
			LOGGER.error("创建服务异常:{}", e.getMessage(), e);
		}
	}
}

class EchoClient {
	public static void main(String[] args) throws IOException {
		EchoClient echoClient = new EchoClient();
		echoClient.startService();
	}
	// TODO
	//默认端口
	private int port = 8081;
	//默认本机
	private String host = "localhost";
	private Socket socket;

	private static final Logger LOGGER = LoggerFactory.getLogger(EchoClient.class);

	public EchoClient() throws IOException {
		this.socket = new Socket(host, port);
	}
	//可以传其他通的服务
	public EchoClient(int port, String host) throws IOException {
		this.port = port;
		this.host = host;
		this.socket = new Socket(host, port);
	}

	//输出
	private PrintWriter getWriter(Socket socket) {
		try {
			OutputStream outputStream = socket.getOutputStream();
			return new PrintWriter(outputStream, true);
		} catch (IOException e) {
			LOGGER.error("创建回复客户机异常:{}", e.getMessage(), e);
		}
		return null;
	}

	private BufferedReader getReader(Socket socket) {
		try {
			InputStream inputStream = socket.getInputStream();
			return new BufferedReader(new InputStreamReader(inputStream));
		} catch (IOException e) {
			LOGGER.error("创建读取机异常:{}", e.getMessage(), e);
		}
		return null;
	}

	/**
	 * 客户端启动服务.
	 */
	public void startService() throws IOException {
		BufferedReader br = getReader(socket);	//读取服务端交流语句
		PrintWriter pw = getWriter(socket);
		String message;
		Scanner input = new Scanner(System.in);
//		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
		LOGGER.info("收到服务端消息：{}", br.readLine());
		//将控制台输入语句读取到message
		while ((message = input.nextLine()) != null) {
			//发送给服务端
			pw.println(message);
			//打印服务端发的信息
			LOGGER.info("收到服务端消息：{}", br.readLine());
			//收到结束语句结束会话
			if ("bye".equals(message)) {
				break;
			}
		}
	}

}