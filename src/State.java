import java.util.HashSet;

public class State {

    private HashSet<Coordinate> w_pawns;
    private HashSet<Coordinate> b_pawns;

    private boolean w_turn;
    private static int max_X = -1, max_Y = -1;

    /**
     * creates the initial state
     * @param maxX it is the max value of a legal column, as it null base. e.g. with = 4 then maxX is allowed to be 0,1,2,3
     * @param maxY it is the max value of a legal row, as it null base. e.g. height = 3 then maxY is allowed to be 0,1,2
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

    /**
     * Copy constructor
     * @param s the state which is copied
     */
    public State (State s){
        this.w_pawns = new HashSet<>(s.w_pawns);
        this.b_pawns = new HashSet<>(s.b_pawns);
        this.w_turn = s.w_turn;
    }

    /**
     * changes this state to make Action a
     * @param a the action which is performend on state s
     * @param do_ = true => do the action which is performend on state s else undo
     */
    public void doAction(Action a, boolean do_){
        if(do_){
            if(a.is_w_action() != w_turn){
                System.err.println("State turn: "+ w_turn +" not equal action turn: " + a.is_w_action());
            }
        }else {
            if(a.is_w_action() == w_turn){
                System.err.println("State turn: "+ w_turn +" is equal action turn: " + a.is_w_action() + "but it's an undo");
            }
        }

        w_turn = !w_turn;

        HashSet<Coordinate> friendly_pawns; // have the same color;
        HashSet<Coordinate> enemy_pawns; // have the other color
        Coordinate add, remove;

        if(a.is_w_action()){
            friendly_pawns = w_pawns;
            enemy_pawns = b_pawns;
        }else{
            friendly_pawns = b_pawns;
            enemy_pawns = w_pawns;
        }
        Moves move = a.get_move();

        if(do_){
            add = a.get_to();
            remove = a.get_From();
        }else{
            add = a.get_From();
            remove = a.get_to();
        }
        friendly_pawns.remove(remove);
        friendly_pawns.add(add);

        if(move.equals(Moves.TAKE_LEFT) || move.equals(Moves.TAKE_RIGHT)){
            if(do_)
                enemy_pawns.remove(a.get_to());
            else
                enemy_pawns.add(a.get_to());
        }
    }

    @Override
    public String toString() {
        String newline = System.getProperty("line.separator");
        String current_turn;
        StringBuilder field = new StringBuilder();
        if(w_turn)
            current_turn = "white's turn";
        else
            current_turn = "black's turn";
        System.out.println("Amount of white pawns: " + w_pawns.size());
        System.out.println("Amount of black pawns: " + b_pawns.size());

        for(int y = max_Y; y >= 0 ; y--){
            for(int x = 0; x <= max_X; x++){
                if(w_pawns.contains(new Coordinate(x ,y))){
                    field.append("W");
                }else if(b_pawns.contains(new Coordinate(x ,y))){
                    field.append("B");
                }else{
                    field.append("-");
                }
            }
            field.append(newline);
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
     * @param c coordinate
     * @return if free => true, else false
     */
    public boolean coordinate_is_free(Coordinate c){
        return !w_pawns.contains(c) && !b_pawns.contains(c);
    }

    /**
     * only for testing
     * @param x with
     * @param y height
     */
    public static void setMax(int x, int y){
        max_X = x;
        max_Y = y;
    }
    public boolean isW_turn() {
        return w_turn;
    }
}
