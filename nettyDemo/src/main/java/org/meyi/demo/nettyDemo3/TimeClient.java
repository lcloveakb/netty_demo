package org.meyi.demo.nettyDemo3;

import org.meyi.demo.nettyDemo.EchoServer;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * 服务器和客户端在网络之间最大的和唯一的区别  :
 * 	  使用不同的Bootstrap和Channel
 * @author meyi
 *
 */
public class TimeClient {
	
	private int port; 
	private String host;
	
	public TimeClient(String host, int port) {
		super();
		this.port = port;
		this.host = host;
	}

    public static void main(String[] args) throws Exception {
    	
    	int port=8989;
//    	String host = "localhost";
    	String host = "10.0.2.2";
    	if(args.length>0){
    		host=args[0];
    	}
    	if(args.length>0&&args.length>=1){
    		port=Integer.parseInt(args[1]);
    	}
    	
    	EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            Bootstrap b = new Bootstrap(); // 类似ServerBootstrap
            b.group(workerGroup); // 只指定一个eventloopgroup,将作为老板和工人
            b.channel(NioSocketChannel.class); // 创建在客户端的通道
            b.option(ChannelOption.SO_KEEPALIVE, true); // 客户端不做childOption()
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new TimeClientHandler());
                }
            });

            // 启动客户端
            ChannelFuture f = b.connect(host, port).sync();

            // 等待链接关闭
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }
}