/**
 * Created by Adrien on 23/11/2016.
 */
public class Display
{
    protected void display_card(String card1_color, int card1_value)
    {
        String l_card = "" + card1_value;
        System.out.println("+-------+");
        System.out.print("*");
        System.out.print(card1_value + " " + card1_color);
        if (l_card.length() == 2 && card1_color.length() == 1)
            System.out.println("   *");
        else if (card1_color.length() == 2 && l_card.length() == 1)
            System.out.println("   *");
        else if (l_card.length() == 2 && card1_color.length() == 2)
            System.out.println("  *");
        else
            System.out.println("    *");
        System.out.println("*       *");
        System.out.println("*       *");
        System.out.println("*       *");
        System.out.println("*       *");
        System.out.println("+-------+");
    }
}
