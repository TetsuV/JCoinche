import org.jboss.netty.channel.Channel;

/**
 * Created by h nh on 23/11/2016.
 */
public abstract class Functor
{
    public abstract void execute(Player p, String data, Channel channel);
}
