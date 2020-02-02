public class Action {

    private Coordinate from;
    private Coordinate to;
    private boolean w_action;

    public Action(Coordinate from, Coordinate to, boolean w_action){
        this.from = from;
        this.to = to;
        this.w_action = w_action;
    }

    public Action(Coordinate from, Moves moves, boolean w_action){
        this.w_action = w_action;
        this.from = from;

        int direction;
        if(w_action)
            direction = 1;
        else
            direction = -1;

        switch (moves){
            case STEP:
                to = new Coordinate(from.getX(), from.getY() + direction);
                break;
            case TAKE_LEFT:
                to = new Coordinate(from.getX() - 1, from.getY() + direction);
                break;
            case TAKE_RIGHT:
                to = new Coordinate(from.getX() + 1, from.getY() + direction);
                break;
        }
    }
    public Moves get_move(){
        int direction;
        if(w_action)
            direction = 1;
        else
            direction = -1;

        if(from.getX() == to.getX() && from.getY() + direction == to.getY()){
            return Moves.STEP;
        }else if(from.getX() == to.getX() + 1 && from.getY() + direction == to.getY()){
            return Moves.TAKE_RIGHT;
        }else if(from.getX() == to.getX() - 1 && from.getY() + direction == to.getY()){
            return Moves.TAKE_LEFT;
        }else{
            System.err.println("this action " + this + " is not a legal move!");
            return null;
        }
    }

    public Coordinate get_From() {
        return from;
    }
    public Coordinate get_to() {
        return to;
    }
    public boolean is_w_action() {
        return w_action;
    }

    public String toString(){
        String color;
        if(w_action){
            color = "white";
        }else{
            color = "black";
        }
        return color +" from: "+ from +" to: "+ to;
    }
}
