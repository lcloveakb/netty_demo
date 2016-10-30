package org.meyi.demo.nettyDemo4;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class EchoServer {
	private final int port;
	
	public EchoServer(int port) {
		this.port = port;
	}
	
	public void start() throws Exception {
		EventLoopGroup group = new NioEventLoopGroup();
		try {
			ServerBootstrap b = new ServerBootstrap();
			b.group(group)
				.channel(NioServerSocketChannel.class)
				.localAddress(port)
				.childHandler(new ChannelInitializer() {

					@Override
					protected void initChannel(Channel arg0) throws Exception {
						arg0.pipeline().addLast(new EchoServerHandler());
					}
				});
			
			ChannelFuture f = b.bind().sync();
			System.out.println(EchoServer.class.getName() + "开始监听: " + f.channel().localAddress());
			f.channel().closeFuture().sync();
		} finally {
			group.shutdownGracefully().sync();
		}
	}
	
	public static void main(String[] args) throws Exception {
		new EchoServer(8080).start();
	}
}