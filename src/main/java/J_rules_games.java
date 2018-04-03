import java.util.LinkedList;

/**
 * Created by Adrien on 16/11/2016.
 */
public class            J_rules_games extends Card_management
{

    LinkedList          player_liste = new LinkedList();
    int                 atout_color;
    int                 count = 0;
    int                 end_game = 0;
    int                 tab_actu[] = new int[4];
    int                 return_value = 0;

    public              J_rules_games(String[] player_name)
    {
        int             count = 0;

        if (player_name.length != 4)
            System.out.println("Error, all player name are not set");
        while (count != 4)
        {
            Player player = new Player(player_name[count]);
            player_liste.add(count, player);
            count++;
        }
    }

    public void finalize()
    {
        int i = 0;

        while (i != player_liste.size())
        {
            player_liste.removeFirst();
            i++;
        }
    }

    protected void Set_player_liste(LinkedList player_liste)
    {
        this.player_liste = player_liste;
    }

    protected LinkedList Get_player_liste()
    {
        return this.player_liste;
    }

    protected int play(Player player, int card)
    {
        if (card == -1)
            return 0;
        if (check_card_color(card, player.card_to_play_this_round) == -1)
        {
            if (check_player_card_color(Get_card_color(card), player) == 0)
                return 0;
            else
                return -1;
        }
        else
            return 0;
    }

    protected int check_player_card_color(int color, Player player)
    {
        int             count = 0;
        int             value = 0;

        while (count != player.nb_card)
        {
            if (check_card_color(color, player.tab_card[count]) == 0)
                value++;
        }
        return value;
    }

    protected int check_player_card_atout(Player player)
    {
        String  card_color;
        int     color;
        int     count = 0;
        int     value = 0;

        while (count != player.nb_card)
        {
            card_color = card_color_to_string(player.tab_card[count]);
            color = card_color_to_int(card_color);
            if (color == atout_color)
                value++;
            count++;
        }
        return value;
    }
}
