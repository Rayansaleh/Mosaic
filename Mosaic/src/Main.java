public class Main {
    //Collect the information necessary to run the algorithm
    //Run the algorithm and get out of it :
    //- The coordinates of piece
    //- The number of pieces
    //- The Score(optional)
    //Then print results
    public static void main(String [] args) {
        Read info = new Read(args[0]);
        Algo algo = new Algo(info);
        //System.out.println(algo.score);
        System.out.println(algo.pieces);
        for (int i = 0; i < algo.allPieces.size(); i++)
            System.out.println(algo.allPieces.get(i));
    }
}