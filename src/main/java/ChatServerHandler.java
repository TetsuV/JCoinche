import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.*;
import org.jboss.netty.handler.ssl.SslHandler;
import sun.nio.cs.StandardCharsets;
import java.nio.charset.Charset;

import java.lang.reflect.Array;
import java.net.SocketAddress;
import java.nio.charset.Charset;

public class ChatServerHandler extends SimpleChannelHandler
{
 /*   private final Charset UTF8_CHARSET = Charset.forName("UTF-8");

    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e)
    {
        SocketAddress remoteAddress = ctx.getChannel().getRemoteAddress();
        System.out.println(remoteAddress);
        byte[] str = (byte[]) e.getMessage();
        System.setProperty( "file.encoding", "UTF-8" );
        String string = new String(str, UTF8_CHARSET);
        char[] req = string.toCharArray();
        //System.out.flush();
    }

    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) {
        e.getCause().printStackTrace();
        Channel ch = e.getChannel();
        ch.close();
    }*/
}
