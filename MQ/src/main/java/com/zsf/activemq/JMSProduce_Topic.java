package com.zsf.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

/**
 * @author zsf
 * @create 2019-10-24 20:41
 */
public class JMSProduce_Topic {
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
        //5 创建生产者
        MessageProducer producer = session.createProducer(topic);
        for (int i = 1; i <= 3; i++) {
            TextMessage textMessage = session.createTextMessage("topicMsg--" + i);
            producer.send(textMessage);
        }

        producer.close();
        session.close();
        connection.close();

        System.out.println("********主题消息发送到MQ完成");

    }
}
