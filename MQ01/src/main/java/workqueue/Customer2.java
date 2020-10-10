package workqueue;

import com.rabbitmq.client.*;
import utils.MqUtils;

import java.io.IOException;

public class Customer2 {
    public static void main(String[] args) throws Exception {
        //获取连接对象
        Connection connection = MqUtils.getFactory();
        //获取通道
        final Channel channel = connection.createChannel();

        //1.一次只接受一条未确认的消息
        channel.basicQos(1);

        //通道绑定对象 与Provider保持一致
        channel.queueDeclare("hello",true,false,false,null);
        //消费消息
        // 2.参数2:关闭自动确认消息
        channel.basicConsume("hello",false,new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("消费者2 "+new String(body));
                //3.手动确认消息
                // 参数1:确认队列中那个具体消息 参数2:是否开启多个消息同时确实
                channel.basicAck(envelope.getDeliveryTag(),false);
            }
        });


    }
}
