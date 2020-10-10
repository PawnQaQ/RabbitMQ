package HaloWorld;

import com.rabbitmq.client.*;
import utils.MqUtils;

import java.io.IOException;

//消费方
public class Customer {

    public static void main(String[] args) throws Exception {
        //创建连接对象
        Connection connection = MqUtils.getFactory();
        //获得通道
        Channel channel = connection.createChannel();
        //与Provider的参数保持一致
        /**
         *  '参数1':用来声明通道对应的队列
         *  '参数2':用来指定是否持久化队列
         *  '参数3':用来指定是否独占队列
         *  '参数4':用来指定是否自动删除队列
         *  '参数5':对队列的额外配置
         */
        channel.queueDeclare("hello", true, false, false, null);


        //消费消息
        //参数1: 消费那个队列的消息 队列名称
        //参数2: 开始消息的自动确认机制
        //参数3: 消费时的回调接口
        channel.basicConsume("hello", true, new DefaultConsumer(channel) {
                    @Override
                    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                        System.out.println(new String(body));
                    }
                }

        );
    }
}
