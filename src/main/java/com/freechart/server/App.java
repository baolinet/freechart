package com.freechart.server;

import javax.jms.*;

import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * Hello world!
 * 
 */
public class App implements MessageListener {
	public static String brokerURL = "tcp://localhost:61616";

	private ConnectionFactory factory;
	private Connection connection;
	private Session session;
	private MessageConsumer consumer;

//	public static void main(String[] args) {
//		App app = new App();
//		app.run();
//	}

	public void run() {
		try {
			ConnectionFactory factory = new ActiveMQConnectionFactory(brokerURL);
			connection = factory.createConnection();
			connection.start();
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Destination destination = session.createQueue("test");
			consumer = session.createConsumer(destination);
			consumer.setMessageListener(this);
		} catch (Exception e) {
			System.out.println("Caught:" + e);
			e.printStackTrace();
		}
	}

	public void onMessage(Message message) {
		try {
			if (message instanceof TextMessage) {
				TextMessage txtMessage = (TextMessage) message;
				System.out.println("Message received: " + txtMessage.getText());
			} else {
				System.out.println("Invalid message received.");
			}
		} catch (JMSException e) {
			System.out.println("Caught:" + e);
			e.printStackTrace();
		}
	}
}