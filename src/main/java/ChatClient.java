import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.*;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.jboss.netty.handler.codec.serialization.ClassResolvers;
import org.jboss.netty.handler.codec.serialization.ObjectDecoder;
import org.jboss.netty.handler.codec.serialization.ObjectEncoder;
import sun.misc.IOUtils;

import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.charset.Charset;
import java.util.Scanner;
import java.util.Vector;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by h nh on 16/11/2016.
 */
public class ChatClient
{
    static private ChannelFuture    cf;
    static private Vector           tab_method = new Vector(15);
    static private final Charset    UTF8_CHARSET = Charset.forName("UTF-8");
    static private Vector           met_tab = new Vector(15);
    static private ReqRep           tab_request = new ReqRep();
    static private Player           p;
    static private Channel          channel;


    public static void main(String args[]) throws IOException
    {
        BufferedReader br = null;

        try
        {
//            init_client(Integer.parseInt(args[1]), Integer.parseInt(args[2]));
            init_client(1234, 8090);
            tab_method = init_read_file("C:\\Users\\h nh\\IntelliJIDEAProjects\\methodes.txt", tab_method);
            if (args[0].length() != 0)
            {
                p = new Player(args[0]);
                connect_to_serv(args[0]);
            }
            while (p.i_have_card == 0)
            {
                Thread.sleep(1000);
                System.out.println("The Game Will Begun ...");
            }
            Card_management card = new Card_management();
            Display disp = new Display();
            int i = 0;
            while (i != 8)
            {
                System.out.println("p.tab_card is :" + p.tab_card[i]);
                disp.display_card(card.card_color_to_string(p.tab_card[i]), p.tab_card[i]);
                i++;
            }
            System.out.println("Waiting for other players ...");
            while (!p.have_to_play)
            {
            }
            br = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Which card ? ");
            String  choice = br.readLine();
            int nbr = Integer.parseInt(choice);
            choice = Integer.toBinaryString(nbr);
            while (choice.length() != 8)
                choice = '0' + choice;
            String data = "" + tab_method.get(15) + "00000001" + choice;
            tab_request.array_req[15].execute(p, data, channel);
        }
        catch (Exception e)
        {
            e.printStackTrace(System.err);
        }
    }

    static private void init_client(int ip, int port)
    {
        try
        {
            Executor bossPool = Executors.newCachedThreadPool();
            Executor workerPool = Executors.newCachedThreadPool();
            final ChannelFactory channelFactory = new NioClientSocketChannelFactory(bossPool, workerPool);
            ChannelPipelineFactory pipelineFactory = new ChannelPipelineFactory()
            {
                public ChannelPipeline getPipeline() throws Exception
                {
                    return Channels.pipeline(
                            new ObjectEncoder(),
                            new ObjectDecoder(ClassResolvers.cacheDisabled(getClass().getClassLoader())),
                            new ClientChatHandler()
                    );
                }
            };
            ClientBootstrap bootstrap = new ClientBootstrap(channelFactory);
            bootstrap.setPipelineFactory(pipelineFactory);
            InetSocketAddress addressToConnectTo = new InetSocketAddress("localhost", 8090);
            cf = bootstrap.connect(addressToConnectTo);
        }
        catch (Exception e)
        {
            e.printStackTrace(System.err);
        }
    }

    static private Vector init_read_file(String file, Vector v)
    {
        String fichier = file;
        String chaine = "";
        try{
            InputStream ips=new FileInputStream(fichier);
            InputStreamReader ipsr=new InputStreamReader(ips);
            BufferedReader br=new BufferedReader(ipsr);
            String ligne;
            while ((ligne=br.readLine())!=null){
                v.addElement(ligne);
            }
            br.close();
        }
        catch (Exception e){
            System.out.println(e.toString());
        }
        return (v);
    }

    static private void connect_to_serv(String ip)
    {
        try
        {
            byte[] array = new byte[1042];
            InetAddress IP = InetAddress.getLocalHost();
            String temp = IP.getHostAddress();
            array = header(array, 0);
            array = length(array, 1);
            array = data(array, ip);
            channel = cf.getChannel();
            channel.write(array);
        }
        catch (Exception e)
        {
            e.printStackTrace(System.err);
        }
    }

    static private byte[] header(byte[] array, int methode)
    {
        String met = (String) tab_method.get(methode);
        System.out.println(met);
        byte[] temp = met.getBytes();
        int i = 0;

        while (i != 8)
        {
            array[i] = temp[i];
            i++;
        }
        return (array);
    }

    static private byte[] length(byte[] array, int length)
    {
        int i = 8;
        int j = 0;
        String test = Integer.toBinaryString(length);

        while (test.length() != 8)
            test = '0' + test;
        byte[] temp = test.getBytes();
        while (i != 16)
        {
            array[i] = temp[i - 8];
            i++;
        }
        return (array);
    }

    static private byte[] data(byte[] array, String data)
    {
        byte[] temp = data.getBytes();
        int i = 16;
        int j = 0;

        while (j != data.length())
        {
            array[i] += temp[j];
            i++;
            j++;
        }
        System.out.println(data);
        return (array);
    }

    static private class ClientChatHandler extends SimpleChannelHandler
    {
        @Override
        public void messageReceived(ChannelHandlerContext ctx, MessageEvent e)
        {
            int i = 0;
            String           req;

            byte[] str = (byte[]) e.getMessage();
            System.setProperty( "file.encoding", "UTF-8" );
            String string = new String(str, UTF8_CHARSET);
            char[] req_tmp = string.toCharArray();
            req = "";
            while (i != 8)
            {
                req += req_tmp[i];
                i++;
            }
            System.out.println("New req :" + req);
            met_tab = init_read_file("C:\\Users\\h nh\\IntelliJIDEAProjects\\sMethodes.txt", met_tab);
            i = 0;
            while (req.equals(tab_method.get(i)) != true)
                i++;
            tab_request.array_req[i].execute(p, string, e.getChannel());
            p = tab_request.getPlayer();
            System.out.println("-------------------------");
            System.out.println("p havetoplay " + p.have_to_play);
            System.out.println("p card:" + p.tab_card[0]);
            System.out.println("p card:" + p.tab_card[1]);
            System.out.println("p card:" + p.tab_card[2]);
            System.out.println("p card:" + p.tab_card[3]);
            System.out.println("p card:" + p.tab_card[4]);
            System.out.println("p card:" + p.tab_card[5]);
            System.out.println("p card:" + p.tab_card[6]);
            System.out.println("p card:" + p.tab_card[7]);
            System.out.println("-------------------------");
        }

        @Override
        public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e){
            Channel ch = e.getChannel();
            System.out.println("channelConnected");
        }

        @Override
        public void channelDisconnected(ChannelHandlerContext ctx, ChannelStateEvent e){
            Channel ch = e.getChannel();
            System.out.println("channelDisconnected");
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) {
            e.getCause().printStackTrace();
            Channel ch = e.getChannel();
            ch.close();
        }
    }

}
