package HaloWorld;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import utils.MqUtils;

import java.io.IOException;

public class Provider {
    public static void main(String[] args) throws IOException {
        //获得连接对象
        Connection connection = MqUtils.getFactory();
        //创建通道
        Channel channel = connection.createChannel();
        //参数1: 是否持久化  参数2:是否独占队列 参数3:是否自动删除  参数4:其他属性

        //通道绑定对应消息队列
        //参数1:  队列名称 如果队列不存在自动创建
        //参数2:  用来定义队列特性是否要持久化 true 持久化队列   false 不持久化
        //参数3:  exclusive 是否独占队列  true 独占队列   false  不独占
        //参数4:  autoDelete: 是否在消费完成后自动删除队列  true 自动删除  false 不自动删除
        //参数5:  额外附加参数
        channel.queueDeclare("hello", true, false, false, null);

        //发布消息
        //参数1: 交换机名称 参数2:队列名称  参数3:传递消息额外设置  参数4:消息的具体内容
        channel.basicPublish("", "hello", null, "halo rabbitmq".getBytes());
        MqUtils.closeConnectionAndChanel(channel, connection);
    }
}
