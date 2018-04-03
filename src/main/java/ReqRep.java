import org.jboss.netty.channel.Channel;

/**
 * Created by h nh on 23/11/2016.
 */

public class ReqRep
{
    protected Functor[] array_req = new Functor[16];
    protected Player    p_tmp = new Player("empty");
    static public int          count = 0;

    ReqRep()
    {
        array_req[0] = new connection();
        array_req[1] = new send_name();
        array_req[2] = new send_partner();
        array_req[3] = new annonce();
        array_req[4] = new get_card();
        array_req[5] = new send_table();
        array_req[6] = new send_atout();
        array_req[7] = new new_round();
        array_req[8] = new have_to_play();
        array_req[9] = new send_point();
        array_req[10] = new win();
        array_req[11] = new loose();
        array_req[12] = new raz();
        array_req[13] = new lost_connection();
        array_req[14] = new new_game();
        array_req[15] = new send_card();
    }

    protected String connect(Channel channel, String data)
    {
        data = data.substring(16);
        return (data);
    }

    private class connection extends Functor
    {
        public void execute(Player p, String data, Channel channel)
        {

            System.out.println("Connection d'un client : ");
        }
    }

    private class send_name extends Functor
    {
        public void execute(Player p, String data, Channel channel)
        {

        }
    }
    private class send_partner extends Functor
    {
        public void execute(Player p, String data, Channel channel)
        {

        }
    }
    private class annonce extends Functor
    {
        public void execute(Player p, String data, Channel channel)
        {

        }
    }
    private class get_card extends Functor
    {
        public void execute(Player p, String data, Channel channel)
        {
            if (data.length() > 16)
                data = data.substring(16);
            char[] c = data.toCharArray();
            int charCode = Integer.parseInt(data, 2);
            p_tmp.Add_player_card(charCode, count);
            count++;
            System.out.println("Data is : " + charCode);
            if (count == 8)
            {
                p_tmp.i_have_card = 1;
                count = 0;
            }
            else
                p_tmp.i_have_card = 0;

        }
    }
    private class send_table extends Functor
    {
        public void execute(Player p, String data, Channel channel)
        {

        }
    }
    private class send_atout extends Functor
    {
        public void execute(Player p, String data, Channel channel)
        {

        }
    }
    private class new_round extends Functor
    {
        public void execute(Player p, String data, Channel channel)
        {

        }
    }
    private class have_to_play extends Functor
    {
        public void execute(Player p, String data, Channel channel)
        {
            p_tmp = p;
            p_tmp.have_to_play = true;
        }
    }
    private class send_point extends Functor
    {
        public void execute(Player p, String data, Channel channel)
        {

        }
    }
    private class win extends Functor
    {
        public void execute(Player p, String data, Channel channel)
        {

        }
    }
    private class loose extends Functor
    {
        public void execute(Player p, String data, Channel channel)
        {

        }
    }
    private class raz extends Functor
    {
        public void execute(Player p, String data, Channel channel)
        {

        }
    }
    private class lost_connection extends Functor
    {
        public void execute(Player p, String data, Channel channel)
        {

        }
    }
    private class new_game extends Functor
    {
        public void execute(Player p, String data, Channel channel)
        {

        }
    }
    private class send_card extends Functor
    {
        public void execute(Player p, String data, Channel channel)
        {
            byte[] array = data.getBytes();
            System.out.println("J envoie ma carte " + data);
            channel.write(array);
        }
    }

    protected Player getPlayer()
    {
        return (p_tmp);
    }
}
