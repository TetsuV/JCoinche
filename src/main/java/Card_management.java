/**
 * Created by Adrien on 16/11/2016.
 */
public class Card_management extends Card
{
    int     tab_card[] = new int[32];
    static int     j = 0;
    int coeur = 8; // 0
    int carreau = 16; // 1
    int pique = 24; // 2
    int trefle = 32; // 3

    public Card_management()
    {
        int     count = 0;
        while (count != 32)
        {
            this.tab_card[count] = count;
            count++;
        }
        card_mix();
    }

    protected int[] playable()
    {
        int     card_playable[] = new int[8];
        int     i = 0;

        while (i != 8)
        {
            card_playable[i] = tab_card[j];
            j++;
            i++;
            if (j == 32)
                j = 0;
        }
        return (card_playable);
    }

    protected void  card_mix()
    {
        double rand;
        int rand1;
        int rand2;
        int tmp;
        int count = 0;

        while (count != 32 * 2)
        {
            rand = Math.random();
            rand = rand * (32);
            rand = (double) ((int) (rand));
            rand1 = (int) rand;
            rand = Math.random();
            rand = rand * (31);
            rand = (double) ((int) (rand));
            rand2 = (int) rand;
            tmp = this.tab_card[rand1];
            this.tab_card[rand1] = this.tab_card[rand2];
            this.tab_card[rand2] = tmp;
            count++;
        }
    }
}
