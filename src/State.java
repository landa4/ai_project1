import java.util.HashSet;

public class State {

    private HashSet<Coordinate> w_pawns;
    private HashSet<Coordinate> b_pawns;
    private boolean w_turn;
    private static int max_X = -1, max_Y = -1;

    /**
     * creates the initial state
     * @param maxX it is the max value of a legal column, as it null base. e.g. with = 4 then maxX is allowed to be 0,1,2,3
     * @param maxY it is the max value of a legal row, as it null base. e.g. height = 3 then maxX is allowed to be 0,1,2
     */
    public State (int maxX, int maxY){
        max_X = maxX;
        max_Y = maxY;
        w_pawns = new HashSet<>();
        b_pawns = new HashSet<>();
        w_turn = true;

        //init white pawns;
        for(int i = 0; i < 2; i++){
            for(int k = 0; k <= max_X; k++){
                w_pawns.add(new Coordinate(k,i));
            }
        }
        //init black pawns;
        for(int i = max_Y - 1; i <= max_Y; i++){
            for(int k = 0; k <= max_X; k++){
                b_pawns.add(new Coordinate(k,i));
            }
        }
    }

    /**
     *
     * @param w_pawns positions of white pawns
     * @param b_pawns positions of black pawns
     * @param w_turn true => white's turn, false => black's turn
     */
    public State (HashSet<Coordinate> w_pawns , HashSet<Coordinate>  b_pawns, boolean w_turn){
        this.w_pawns = w_pawns;
        this.b_pawns = b_pawns;
        this.w_turn = w_turn;
    }

    @Override
    public String toString() {
        String newline = System.getProperty("line.separator");
        String current_turn;
        String field = "";
        if(w_turn)
            current_turn = "white's turn";
        else
            current_turn = "black's turn";
        System.out.println("Amount of white pawns: " + w_pawns.size());
        System.out.println("Amount of black pawns: " + b_pawns.size());

        for(int y = max_Y; y >= 0 ; y--){
            for(int x = 0; x <= max_X; x++){
                if(w_pawns.contains(new Coordinate(x ,y))){
                    field += "W";
                }else if(b_pawns.contains(new Coordinate(x ,y))){
                    field += "B";
                }else{
                    field += "-";
                }
            }
            field += newline;
        }
        return current_turn + newline + field;
    }


    public HashSet<Coordinate> get_W_pawns() {
        return w_pawns;
    }

    public HashSet<Coordinate> get_B_pawns() {
        return b_pawns;
    }

    public boolean is_W_turn() {
        return w_turn;
    }

    /**
     * check if coordinate c is free
     * @param c
     * @return
     */
    public boolean coordinate_is_free(Coordinate c){
        return !w_pawns.contains(c) && !b_pawns.contains(c);
    }

    /**
     * only for testing
     * @param x
     * @param y
     */
    public static void setMax(int x, int y){
        max_X = x;
        max_Y = y;
    }
}
