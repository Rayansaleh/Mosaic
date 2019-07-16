//This class contains all that define a possible piece(a zone)
//A index to find it among the others
//A probability of validity
//A orientation (Horizontal)(Vertical)
public class Zone
{
    public int     i;
    public double  proba;
    public boolean side;

    public Zone(int i, double proba, boolean side)
    {
        this.i = i;
        this.proba = proba;
        this.side = side;
    }
}
