/**
 * Created by Adrien on 16/11/2016.
 */
public class Player
{
    int         nb_card;
    int         num_player;
    int         tab_card[] = new int[8];
    String      player_name;
    int         player_team;
    String      annonce_color;
    int         annonce_value;
    int         card_to_play_this_round;
    boolean     have_to_play = false;
    int         i_have_card = 0;

    public Player(String player_name)
    {
        this.player_name = player_name;
    }

    protected int Get_player_team()
    {
        return this.player_team;
    }

    protected void Set_player_team(int team)
    {
        this.player_team = team;
    }

    protected String Get_player_name()
    {
        return (this.player_name);
    }

    protected void Set_player_name(String name)
    {
        this.player_name = name;
    }

    protected int[] Get_player_card()
    {
        return (this.tab_card);
    }

    protected void Add_player_card(int value, int count)
    {
        this.tab_card[count] = value;
    }
    protected void Set_player_card(int[] tab)
    {
        this.tab_card = tab;
    }

    protected void Set_card_to_play_this_round(int card)
    {
        this.card_to_play_this_round = card;
    }

    protected int Get_card_to_play_this_round()
    {
        return (this.card_to_play_this_round);
    }
}
