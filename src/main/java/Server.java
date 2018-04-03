import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.*;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.handler.codec.serialization.ClassResolvers;
import org.jboss.netty.handler.codec.serialization.ObjectDecoder;
import org.jboss.netty.handler.codec.serialization.ObjectEncoder;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.LinkedList;
import java.util.Vector;
import java.util.concurrent.Executors;

import static java.lang.Thread.sleep;

/**
 * Created by h nh on 16/11/2016.
 */

public class Server
{
    private final Charset           UTF8_CHARSET = Charset.forName("UTF-8");
    static private Vector           met_tab = new Vector(16);
    static private Vector           tab_met = new Vector(16);
    static private String           req;
    static private LinkedList       Client = new LinkedList();
    static private String[] Player = new String[4];
    static private J_rules_games    rules;
    static private int              j = 0;

    public static void main (String[] args)
    {
        int i = 0;

    /*    while (i != 4)
        {
            Player[i] = "";
            i++;
        }*/
        init_server();
        tab_met = init_read_file("C:\\Users\\h nh\\IntelliJIDEAProjects\\methodes.txt", tab_met);
    }

    private static void init_server()
    {
        try
        {
            ServerBootstrap bootstrap = new ServerBootstrap(
                    new NioServerSocketChannelFactory(
                            Executors.newCachedThreadPool(),
                            Executors.newCachedThreadPool()));

            // Set up the pipeline factory.
            bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
                public ChannelPipeline getPipeline() throws Exception {
                    return Channels.pipeline(
                            new ObjectEncoder(),
                            new ObjectDecoder(ClassResolvers.cacheDisabled(getClass().getClassLoader())),
                            new ServerHandler()
                    );
                };
            });
            // Bind and start to accept incoming connections.
            InetAddress IP= InetAddress.getLocalHost();
            System.out.println("IP of my system is := " + IP.getHostAddress());
            bootstrap.bind(new InetSocketAddress("localhost", 8090));
        }
        catch (Exception e)
        {
            e.printStackTrace(System.err);
        }
    }

    static private class ServerHandler extends SimpleChannelHandler
    {
        private final Charset UTF8_CHARSET = Charset.forName("UTF-8");

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
        public void messageReceived(ChannelHandlerContext ctx, MessageEvent e)
        {
            try
            {
                int i = 0;
                ReqRep tab_request = new ReqRep(tab_met);
                byte[] str = (byte[]) e.getMessage();
                System.setProperty("file.encoding", "UTF-8");
                String string = new String(str, UTF8_CHARSET);
                char[] req_tmp = string.toCharArray();
                req = "";
                while (i != 8)
                {
                    req += req_tmp[i];
                    i++;
                }
                System.out.println("I got requete : " + req);
                met_tab = init_read_file("C:\\Users\\h nh\\IntelliJIDEAProjects\\sMethodes.txt", met_tab);
                i = 0;
                while (!req.equals(tab_met.get(i)))
                    i++;
                if (i == 0)
                {
                    InfoClient client = new InfoClient();
                    String name = tab_request.connect(e.getChannel(), string);
                    client._sock = e.getChannel();
                    client.p = new Player(name);
                    Client.add(j, client);
                    j++;
                    if (j == 4)
                    {
                        j = 0;
                        Card_management paquet = new Card_management();
                        while (j != 4)
                        {
                            int count;

                            InfoClient c = (InfoClient) Client.get(j);
                            c.p.Set_player_card(paquet.playable());
                            count = 0;
                            System.out.println("Data de " + c.p.Get_player_name());
                            while (count != 8)
                            {
                                String data = "" + c.p.tab_card[count];
                                tab_request.array_req[4].execute(c, data, rules);
                                count++;
                            }
                            Player[j] = c.p.Get_player_name();
                            j++;
                        }
                        rules = new J_rules_games(Player);
                        InfoClient c = (InfoClient) Client.get(0);
                        tab_request.array_req[8].execute(c, (String) tab_met.get(8), rules);
                        System.out.println((String) tab_met.get(8));
                        j = 5;
                    }
                }
                else
                {
                    tab_request.re_init();
                    System.out.println("Heya !");
                    int k = 0;
                    InfoClient p = (InfoClient) Client.get(0);
                    while (e.getChannel() != p._sock)
                    {
                        p = (InfoClient) Client.get(k);
                        k++;
                    }
                    tab_request.array_req[i].execute(p, string, rules);
                    rules = tab_request.getRules_tmp();
                    if (rules.return_value == -1)
                        tab_request.array_req[8].execute(p, (String) tab_met.get(8), rules);
                    else
                    {
                        System.out.println("Je suis bien placé par là" + k);
                        if (k != 4)
                        {
                            k++;
                            p = (InfoClient) Client.get(k);
                        }
                        else
                            p = (InfoClient) Client.get(0);
                        System.out.println("Je suis bien placé par là" + p.p.player_name);
                        tab_request.array_req[8].execute(p, (String) tab_met.get(8), rules);
                    }
                }
            }
            catch (Exception m)
            {
                System.out.println(m.toString());
            }
        }
        public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) {
            e.getCause().printStackTrace();
            Channel ch = e.getChannel();
            ch.close();
        }
    }

    static private Vector init_read_file(String file, Vector v)
    {
        String fichier = file;
        String chaine = "";
        try
        {
            String ligne;
            InputStream ips=new FileInputStream(fichier);
            InputStreamReader ipsr=new InputStreamReader(ips);
            BufferedReader br=new BufferedReader(ipsr);
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
}
