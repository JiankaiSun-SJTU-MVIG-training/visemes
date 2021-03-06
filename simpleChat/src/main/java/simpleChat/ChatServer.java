package simpleChat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class ChatServer {
	private int port;
	
	public ChatServer(int port)
	{
		this.port = port;
	}
	
	public void run() throws Exception
	{
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try
		{
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup)
			 .channel(NioServerSocketChannel.class)
			 .childHandler(new ChatServerInitializer())
			 .option(ChannelOption.SO_BACKLOG, 128)
			 .childOption(ChannelOption.SO_KEEPALIVE, true);
			
			System.out.println("ChatServer Startup!");
			// �󶨶˿ڣ���ʼ���ս���������
			ChannelFuture f = b.bind(port).sync();
			// �ȴ�������  socket �ر� ��
            // ����������У��ⲻ�ᷢ��������������ŵعر���ķ�������
			f.channel().closeFuture().sync();
		}
		finally
		{
			workerGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();
			
			System.out.println("ChatServer Shutdown!");
		}
	}
	
	public static  void main(String[] args) throws Exception
	{
		int port;
		if(args.length > 0)
		{
			port = Integer.parseInt(args[0]);
		}
		else
		{
			port = 8080;
		}
		new ChatServer(port).run();
	}

}
