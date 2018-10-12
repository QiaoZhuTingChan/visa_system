package com.jyd.message;

import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {

	public static void main(String[] args) {
		ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext(
				new String[] { "config/webSocket.xml" });
		ISendMessage sendMessage = (ISendMessage) classPathXmlApplicationContext.getBean("SendMessage");

		ServerMessage serverMessage = new ServerMessage();
		serverMessage.setType("NewMessage");
		serverMessage.setContent("Hello world");
		serverMessage.setMessageId(10);

		sendMessage.sendMessageToAllClient(serverMessage);

		// 发送消息给所有用户
		 List<Integer> list = sendMessage.getAllClientUser();

		 String users = new String();

		 for (Integer userId : list) {
		 users += userId + ",";
		 }

		 serverMessage = new ServerMessage();
		 serverMessage.setType("newMessage");
		 serverMessage.setContent("All UserId:" + users);
		 serverMessage.setMessageId(10);

		 sendMessage.sendMessageToAllClient(serverMessage);

		 ServerMessage serverMessageTotal = new ServerMessage();
		 serverMessageTotal.setType("totalMessage");
		 serverMessageTotal.setContent("10");
		 serverMessageTotal.setMessageId(10);

		 sendMessage.sendMessageToClient(serverMessageTotal, 51);
	}
}
