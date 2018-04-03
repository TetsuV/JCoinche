/**
 * Created by Adrien on 16/11/2016.
 */

import java.util.Scanner;

public class Game extends Player
{

    Scanner sc = new Scanner(System.in);

    public Game(String player_name)
    {
        super(player_name);
    }

    protected void play_card()
    {
        int i = 0;
        int card = 0;
        while (i != 1)
        {
            System.out.println("Choos your card (between 1 and 8)");
            String str = sc.nextLine();
            card = Integer.parseInt(str);
            if (card > 0 && card < 9)
                i = 1;
            else
                System.out.println("bad value");
        }
        card_to_play_this_round = tab_card[card - 1];
        tab_card[card - 1] = -1;
        nb_card--;
    }
}
