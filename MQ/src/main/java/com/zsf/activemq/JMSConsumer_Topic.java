package com.zsf.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

/**
 * @author zsf
 * @create 2019-10-24 20:48
 */
public class JMSConsumer_Topic {
    public static final String DEFAULT_BROKER_BIND_URL = "tcp://192.168.184.126:61616";
    public static final String TOPIC_NAME = "Topic";

    public static void main(String[] args) throws JMSException {
        //1 创建连接工场,使用默认用户名密码，编码不再体现
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(DEFAULT_BROKER_BIND_URL);
        //2 获得连接并启动
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();
        //3 创建会话,此步骤有两个参数，第一个是否以事务的方式提交，第二个默认的签收方式
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //4 创建主题
        Topic topic = session.createTopic(TOPIC_NAME);
        //5 创建消费者
        MessageConsumer consumer = session.createConsumer(topic);

        /*同步阻塞方式receive() ，订阅者或接收者调用MessageConsumer的receive()方法来接收消息，
        receive()将一直阻塞
        receive(long timeout) 按照给定的时间阻塞，到时间自动退出*/
        //TextMessage textMessage = null;
        //while (true) {
        //    textMessage = (TextMessage) consumer.receive();
        //    if (null != textMessage) {
        //        System.out.println("接收到的消息：" + textMessage.getText());
        //    } else {
        //        break;
        //    }
        //}
        //
        ////8 关闭资源
        //consumer.close();
        //session.close();
        //connection.close();


        /*异步非阻塞方式(监听器onMessage())
        订阅者或接收者通过MessageConsumer的setMessageListener(MessageListener listener)注册一个消息监听器，
        当消息到达之后，系统自动调用监听器MessageListener的onMessage(Message message)方法。*/
        consumer.setMessageListener(message -> {
            if (message != null && message instanceof TextMessage) {
                TextMessage textMessage = (TextMessage) message;
                try {
                    System.out.println("***收到topic：" + textMessage.getText());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
