package OOP.ec22473.MP;

class Room_ec22473 extends Room{
    boolean main_light_on = true;
    boolean boogey_man = true;
    
    public Direction visit(Visitor visitor, Direction location)
    {
         if (location == Direction.FROM_NORTH) {
            visitor.tell("You are located in the North side of the building");
        } else if (location == Direction.FROM_EAST) {
            visitor.tell("You are located in the East side of the building");
        } else if (location == Direction.FROM_SOUTH) {
            visitor.tell("You are located in the South side of the building");
        } else if (location == Direction.FROM_WEST) {
            visitor.tell("You are located in the West side of the building");
        }
        
         if (main_light_on) {
            visitor.tell("The room is well lit");
        } else {
            visitor.tell("I can hardly make out anything!");
        }
        
        if (boogey_man){
            visitor.tell("The boogey man is near, be WARY!");
        }
        else{
            visitor.tell("You are safe for now.");
        }
        
        char[] choices = {'1', '2'};

        int choice = visitor.getChoice("1. Search the room 2. open cabinet ", choices);
        if(choice == '1')
        {
            visitor.tell("You have stumbled across a pot of 5 gold coins. Take them");
            visitor.giveGold(5);
        }
        else if(choice == '2')
        {
            visitor.tell("Oh no, the boogey man has taken all your coins!");
            visitor.takeGold(5);
        }
        return location;
    }

}