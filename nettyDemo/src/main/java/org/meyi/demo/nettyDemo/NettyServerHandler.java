package org.meyi.demo.nettyDemo;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;


public class NettyServerHandler extends ChannelInboundHandlerAdapter {
	
	/**
	 * 重写channelRead()
	 * 用于接收从客户端发来的新消息时被调用
	 */
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		ByteBuf in = (ByteBuf) msg;
		try{
			//丢弃协议，无法得到任何回应DiscardServer
			System.out.println(in.toString(CharsetUtil.US_ASCII));
			//DiscardServer
//			while(in.isReadable()){
//				System.out.println((char)in.readByte());
//				System.out.flush();
//			}
			
			//有响应EchoServer
			ctx.write(msg);
			ctx.flush();
		}catch(Exception e){
			System.out.println(e.getMessage());
		}finally{
			ReferenceCountUtil.release(msg);
		}
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause){
		//引发异常时关闭连接
		System.out.println("关闭连接");
		cause.printStackTrace();
		ctx.close();
	}
	

}
