package data;

import org.apache.activemq.ActiveMQConnectionFactory;
import javax.jms.*;

class ActiveMQ {

    void submit(String message) throws Exception {
        ActiveMQConnectionFactory jmsConnectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        Connection connection = jmsConnectionFactory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue photoQueue = session.createQueue("Letters");
        MessageProducer producer = session.createProducer(photoQueue);
        TextMessage textMessage = session.createTextMessage(message);
        producer.send(textMessage);
        connection.stop();
    }

    void usingListener()
    {
        ActiveMQConnectionFactory jmsConnectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        Connection connection = null;

        try {
            connection = jmsConnectionFactory.createConnection();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination destination=session.createQueue("Letters");
            MessageConsumer consumer = session.createConsumer(destination);
            System.out.println("Checking logged messages in activeMQ");

            consumer.setMessageListener(new MessageListener() {
                public void onMessage(Message msg) {
                    try {
                        if (! (msg instanceof TextMessage))
                            throw new RuntimeException("no text message");
                        TextMessage tm = (TextMessage) msg;

                        ReadCSV readCSV=new ReadCSV();
                        readCSV.processedCSV(tm.getText());

                    }
                    catch (JMSException e) {
                        System.err.println("Error reading message");
                    }
                }
            });
            connection.start();
            try {
                Thread.sleep(1000);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }

    }


}
