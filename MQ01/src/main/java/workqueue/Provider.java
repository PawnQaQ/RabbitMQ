package workqueue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import utils.MqUtils;

public class Provider {
    public static void main(String[] args) throws Exception {
        //获得连接对象
        Connection connection = MqUtils.getFactory();
        //建立通道
        Channel channel = connection.createChannel();
        //通道绑定对应消息队列
        //参数0:队列名称 如果队列不存在自动创建  参数1: 是否持久化  参数2:是否独占队列 参数3:是否自动删除  参数4:其他属性
        channel.queueDeclare("hello",true,false,false,null);

        //发送消息
        //参数1: 交换机名称 参数2:队列名称  参数3:传递消息额外设置 参数4:消息的具体内容
        for(int i=0;i<20;i++){
            channel.basicPublish("", "hello", null,(i+"halo rabbitmq queue work...").getBytes());
        }

        //关闭
        MqUtils.closeConnectionAndChanel(channel,connection);
    }

}
