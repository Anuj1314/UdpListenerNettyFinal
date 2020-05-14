import com.pojo.Messages;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.CharsetUtil;

import java.io.ByteArrayInputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;


public class IncommingPacketHandler extends SimpleChannelInboundHandler<DatagramPacket> {

    @Override
    protected void messageReceived(ChannelHandlerContext channelHandlerContext, DatagramPacket packet) throws Exception {
        System.out.println("\nPacket Content  ::>>  "+packet.content().toString(CharsetUtil.UTF_8));

//        Deserialising object
        ByteArrayInputStream bis = new ByteArrayInputStream(packet.content().array());
        ObjectInput in = null;
        Messages o = null;
        try{
            in = new ObjectInputStream(bis);
            o = (Messages) in.readObject();
            System.out.println(o);
        }finally {
            try{
                if(in!=null){
                    in.close();
                }
            }catch (Exception e) {
                System.out.println(e);
            }
        }





        try{

            MongoDataSetupAndSend mongoDataSetupAndSend = new MongoDataSetupAndSend();
            mongoDataSetupAndSend.run(o);

        }
        catch (Exception e){
            System.out.println(e);
        }
//        final InetAddress srcAddr = packet.sender().getAddress();
//        final ByteBuf buf = packet.content();
//        System.out.println("printing bytebuf"+buf);
//        final int rcvPktLength = buf.readableBytes();
//        final byte[] rcvPktBuf = new byte[rcvPktLength];
//        buf.readBytes(rcvPktBuf);
//        System.out.println("Inside incomming packet handler");

        }
}
