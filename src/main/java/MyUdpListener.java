import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class MyUdpListener {
    public static void main(String[] args) throws InterruptedException, UnknownHostException {
        new MyUdpListener().run();
    }

    public void run() throws UnknownHostException, InterruptedException {
        NioEventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
//            b.localAddress(new InetSocketAddress("localhost",8787));
            b.group(group).channel(NioDatagramChannel.class)
                    .option(ChannelOption.SO_BROADCAST, true)
                    .handler(new ChannelInitializer<NioDatagramChannel>() {
                        @Override
                        public void initChannel(final NioDatagramChannel ch) throws Exception {

                            ChannelPipeline p = ch.pipeline();
                            p.addLast(new IncommingPacketHandler());
                        }
                    });

            Integer pPort = 8787;
            InetAddress address = InetAddress.getLocalHost();
            System.out.printf("waiting for message %s %s \n", String.format(pPort.toString()), String.format(address.toString()));
//            b.bind(address, 8787).sync().channel().closeFuture().await();
//
            // Bind and start to accept incoming connections.
            ChannelFuture channelFuture = b.bind(address,pPort).sync();
            channelFuture.channel().closeFuture().sync();
        }
        finally {
            group.shutdownGracefully().sync();
        }
    }
}
