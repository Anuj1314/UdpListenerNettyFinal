import java.io.IOException;
import java.net.*;

public class UDPClient {
    public static void main(String args[]) throws SocketException, UnknownHostException {

        byte[] buf = new byte[256];
        DatagramSocket socket = new DatagramSocket();;
        InetAddress address =  InetAddress.getLocalHost();;
        buf= "hello".getBytes();
        DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 8787);
        try {
            socket.send(packet);
            System.out.println(buf);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}