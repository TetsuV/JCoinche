/**
 * Created by Adrien on 21/11/2016.
 */
public class    Card
{
    String      tab_card[] = {"A","10","R","D","V","9","8","7","A","10","R","D","V","9","8","7","A","10","R","D","V","9","8","7","A","10","R","D","V","9","8","7"};
    String      tab_card_atout[] = {"V","9","A","10","R","D","8","7","V","9","A","10","R","D","8","7","V","9","A","10","R","D","8","7","V","9","A","10","R","D","8","7"};
    int         tab_card_value[] = {11,10,4,3,2,0,0,0,11,10,4,3,2,0,0,0,11,10,4,3,2,0,0,0,11,10,4,3,2,0,0,0};
    int         tab_card_value_atout[] = {20,18,11,10,4,3,0,0};
    int         tab_color_card[] = {0,1,2,3};

    protected int Get_card_value(int card_value)
    {
        return this.tab_card_value[card_value];
    }

    protected int Get_card_value_atout(int card_value)
    {
        return (this.tab_card_value_atout[card_value]);
    }

    protected String Get_card(int value_card)
    {
        return (this.tab_card[value_card]);
    }

    protected String Get_card_atout(int value_card)
    {
        return(this.tab_card_atout[value_card]);
    }

    protected String card_color_to_string(int card_value)
    {
        if (card_value < 8 && card_value > -1)
            return ("p");
        else if (card_value > 7 && card_value < 16)
            return ("co");
        else if (card_value > 15 && card_value < 24)
            return ("ca");
        else if (card_value > 23 && card_value < 32)
            return ("t");
        else
            return "fals";
    }

    protected int card_color_to_int(String card_color)
    {
        if (card_color == "p")
            return 0;
        else if (card_color == "co")
            return 1;
        else if (card_color == "ca")
            return 2;
        else if (card_color == "t")
            return 3;
        else
            return -1;
    }

    protected int Get_card_color(int card)
    {
        String card_color;

        card_color = card_color_to_string(card);
        return card_color_to_int(card_color);
    }

    protected int check_card_value(int card1, int card2)
    {
        String  cards1;
        String  cards2;
        int     count1 = 0;
        int     count2 = 0;

        cards1 = Get_card(card1);
        cards2 = Get_card(card2);
        while (tab_card[count1] != cards1)
            count1++;
        while (tab_card[count2] != cards2)
            count2++;
        if (count1 > count2)
            return 2;
        else if (count2 > count1)
            return 1;
        else
            return 0;
    }

    protected int check_card_color(int card1, int card2)
    {
        String  color1;
        String  color2;

        color1 = card_color_to_string(card1);
        color2 = card_color_to_string(card2);
        if (color1 == color2)
            return (0);
        else
            return -1;
    }
}
