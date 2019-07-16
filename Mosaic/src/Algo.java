import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Algo
{
    public static ArrayList<String> allPieces = new ArrayList<>();
    public static char[][] map_base;
    public static int pieces = 0;
    public static int score = 0;
    public static int R;
    public static int C;
    public static int L;
    public static int H;

    public Algo(Read info)
    {
        this.R = info.info_base.get(0);
        this.C = info.info_base.get(1);
        this.L = info.info_base.get(2);
        this.H = info.info_base.get(3);
        this.map_base = info.map_base;
        back_tracking(0, 0, R - 1, C - 1);
    }

    //Creates a new zone each time
    //At first the zone takes the all map
    //Then its divided in 2 so given the orientation chosen(horizontal)(vertical)
    //The method will be called with different parameters
    //Its called 2 times regardless of the orientation because there is 2 new zones each time
    public static void back_tracking(int r1, int c1, int r2, int c2)
    {
        Zone currZone = create_pieces(r1, c1, r2, c2);
        if (currZone == null)
            return ;
        if (currZone.side == true)
        {
            back_tracking(r1, c1, r1 + currZone.i, c2);
            back_tracking(r1 + currZone.i + 1, c1, r2, c2);
        } else {
            back_tracking(r1, c1, r2, c1 + currZone.i);
            back_tracking(r1, c1 + currZone.i + 1, r2, c2);
        }
    }

    //Creates a list of valid pieces
    //If the zone isn't valid it stop
    //If the zone is valid it becomes a pieces
    //If the zone is too big the probability of the cut to create a smaller zone is calculated
    public static Zone create_pieces(int r1, int c1, int r2, int c2)
    {
        ArrayList<Zone> possiblePiece = new ArrayList<>();
        List<Double> filter = new ArrayList<Double>();
        Zone maxProba = null;
        int i = 0;

        if (r1 == r2 && c1 == c2)
            return (null);
        filter = check_validity(r1, c1, r2, c2);
        if (filter.get(1) == 0)
            return (null);
        else if (filter.get(1) == 1)
        {
            allPieces.add(r1 + " " + c1 + " " + r2 + " " + c2);
            score += filter.get(2);
            pieces++;
            return (null);
        }
        possiblePiece.addAll(calcul_proba_horizontal(r1, c1, r2, c2));
        possiblePiece.addAll(calcul_proba_vertical(r1, c1, r2, c2));
        i = 1;
        maxProba = possiblePiece.get(0);
        while (i < possiblePiece.size())
        {
            if (possiblePiece.get(i).proba > maxProba.proba)
                maxProba = possiblePiece.get(i);
            i++;
        }
        return (new Zone(maxProba.i, maxProba.proba, maxProba.side));
    }

    //Return a list of possible pieces with their coordinates and the probability of their validity
    //Only if the piece is horizontal
    public static ArrayList<Zone> calcul_proba_horizontal(int r1, int c1, int r2, int c2)
    {
        ArrayList<Zone> horizontalPossPiece = new ArrayList<>();
        double proba = 0;
        double prob1 = 0;
        double prob2 = 0;
        int maxPos = 0;
        int i = 0;

        maxPos = c2 - c1;
        while (i < maxPos)
        {
            prob1 = check_validity(r1, c1, r2, c1 + i).get(0);
            prob2 = check_validity(r1, c1 + i + 1, r2, c2).get(0);
            proba = prob1 * prob2;
            horizontalPossPiece.add(new Zone(i, proba,false));
            i++;
        }
        return (horizontalPossPiece);
    }

    //Return a list of possible pieces with their coordinates and the probability of their validity
    //Only if the piece is vertical
    public static ArrayList<Zone> calcul_proba_vertical(int r1, int c1, int r2, int c2)
    {
        ArrayList<Zone> verticalPossPiece = new ArrayList<>();
        double proba = 0;
        double prob1 = 0;
        double prob2 = 0;
        int maxPos = 0;
        int i = 0;

        maxPos = r2 - r1;
        while (i < maxPos) {
            prob1 = check_validity(r1, c1, r1 + i, c2).get(0);
            prob2 = check_validity(r1 + i + 1, c1, r2, c2).get(0);
            proba = prob1 * prob2;
            verticalPossPiece.add(new Zone(i, proba, true));
            i++;
        }
        return (verticalPossPiece);
    }

    //Check the validity of a piece and the probability of the zone division
    //The probability is found by using 3 elements:
    //- Is there less, enough or more color than necessary
    //- Is the zone too small, at the max size, or too big
    //- The quantity of a color compare to the other in the zone
    //The higher the probability is the better, it means that all the elements are at an optimal value to create a zone
    public static List<Double> check_validity(int r1, int c1, int r2, int c2)
    {
        int WB = 0;
        int W = 0;
        int B = 0;
        int i = r1 - 1;
        int j = 0;
        double posProba;
        List<Integer> statStock;
        List<Double>  valityFilter = new ArrayList<Double>();

        while (++i <= r2)
        {
            j = c1 - 1;
            while (++j <= c2)
            {
                if (map_base[i][j] == 'W')
                    W++;
                else
                    B++;
            }
        }
        WB = W + B;
        statStock = ft_stat(W, B, WB);
        posProba = (1 / 3.0) * ((1 - (((statStock.get(0) + statStock.get(1)) / (L * 2.0)))) + statStock.get(4) *
                (1 - statStock.get(2) / H) + (statStock.get(3) / (B + W)) * ((Math.min(B, W)) / Math.max(B, W)));
        valityFilter.add(posProba);
        valityFilter.add(Double.valueOf(statStock.get(5)));
        valityFilter.add((double) WB);
        return (valityFilter);
    }

    //Check all the statistics that will help to consider a piece valid or not
    //Here are the values contained in statStock listed by index:
    //0 - Is there enough White for a piece, (0 if true)(number missing otherwise)
    //1 - Is there enough Black for a piece, (0 if true)(number missing otherwise)
    //2 - Is there enough Black and White for a piece, (0 if true)(number missing otherwise)
    //3 - Is there too much Black and White for a piece, (0 if true)(number more otherwise)
    //4 - Is there enough of the both color, (1 if true)(0 otherwise)
    //5 - Is there more color or enough and is the piece too big, (2 if both true)(1 if only first true)(0 otherwise)
    public static List<Integer> ft_stat(int W, int B, int WB)
    {
        List<Integer> statStock = new ArrayList<Integer>(Collections.nCopies(6, 0));

        if (W < L)
            statStock.set(0, L - W);
        if (B < L)
            statStock.set(1, L - B);
        if (H > WB)
            statStock.set(2, H - WB);
        if (H < WB)
            statStock.set(3, WB - H);
        if (statStock.get(0) == 0 && statStock.get(1) == 0)
            statStock.set(4, 1);
        if (W >= L && B >= L)
        {
            if (WB > H)
                statStock.set(5, 2);
            else
                statStock.set(5, 1);
        }
        return (statStock);
    }
}
