/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 *  
 *  8.20 adding one item per room
 * @author  Michael Kölling,Gokul Ram and David J. Barnes
 * @version 2021.10.29
 */

public class Game 
{
    private Parser parser;
    private Room currentRoom;
    // 8.23 to remember the previous room new variable previousRoom is added
    private Room previousRoom;
    private Player gamePlayer;
    /** this is the main method for the game class
     * @param args not needed
     */
    public static void main(String[] args) {
        Game game1 = new Game();
        game1.play();
    }
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRooms();
        parser = new Parser();
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room outside, theater, pub, lab, office;
         
        // create the rooms
        outside = new Room("outside the main entrance of the university");
        theater = new Room("in a lecture theater");
        pub = new Room("in the campus pub");
        lab = new Room("in a computing lab");
        office = new Room("in the computing admin office");
        
        // initialise room exits
        outside.setExit("east", theater);
      
        outside.setExit("south", lab);
        outside.setExit("west", pub);
        
        theater.setExit("west", outside);

        pub.setExit("east", outside);

        lab.setExit("north", outside);
        lab.setExit("east", office);

        office.setExit("west", lab);

        // 8.20 storing one item in each room.
        // 8.21 2. The item description is created here but passed as a param to the storeItem method which is in room class
        outside.storeItem("Bench",100);
        theater.storeItem("Screen",200);
        pub.storeItem("Food",5);
        lab.storeItem("Computer",60);
        office.storeItem("Chair",25);
        
        currentRoom = outside;  // start game outside
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
        gamePlayer = new Player("Gokul", 100, currentRoom);        
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Zuulu!");
        System.out.println("World of Zuulu is a new, incredibly boring adventure game.");
        System.out.println("Type '" + CommandWord.HELP + "' if you need help.");
        System.out.println();
        System.out.println(currentRoom.getLongDescription());
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     * 8.16 added case for look and eat.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        CommandWord commandWord = command.getCommandWord();

        switch (commandWord) {
            case UNKNOWN:
                System.out.println("I don't know what you mean...");
                break;

            case HELP:
                printHelp();
                break;

            case GO:
                goRoom(command);
                break;
            case LOOK:
                look();
                break;
            case EAT:
                 eat();
                 break;
            case QUIT:
                wantToQuit = quit(command);
                break;
            case BACK:
                back();
                break;
            case DROP:
                drop(command);
                break;
            case TAKE:
                take();
                break;
                // 8.32 Implement an items command that prints out all items currently carried and their total weight.
            case ITEMS:
                items();
        }
        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
    }

    /** 
     * Try to go in one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
     
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            // 8.23 for back command we are storing previous room
            previousRoom = currentRoom;
            currentRoom = nextRoom;
            gamePlayer.setCurrentRoom(currentRoom);
            System.out.println(currentRoom.getLongDescription());
        }
    }
    
    /**
     * Implementation for 8.32, prints all the items carried by the player
     */
    private void items(){
        gamePlayer.printAllPlayerItems();
    }
    /**
     * Implementation for 8.29. 2 word command, the player drops the item and his carry weight is reduced
     * @param Command - Pass the command as the parameter
     */
    private void drop(Command command)
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Drop What?");
            return;
        }

        String dropitemname = command.getSecondWord();
        gamePlayer.drop(dropitemname);
    }
    
    /**
     * Implementation for 8.29. The player picks up the item in the current room. He can only pickup what item is there in the room, so no parameter is necessary
     */
    private void take() 
    {
        gamePlayer.take(currentRoom.getRoomItem());
    }
    
    /**
     * this method prints the description of the room
     */
    private void look()
    {
    System.out.println(currentRoom.getLongDescription());
    }
    /**
     * prints eat message on screen
     */
    private void eat()
    {
     int mweight = gamePlayer.eatCookie();
     System.out.println("You have eaten now and are not hungry anymore, you can carry a maximum of "+mweight);
    }
    /**
     * 8.23 the back command takes the player into the previous room he/she was in
     *
     */
    private void back()
    {
     Room tempRoom = currentRoom;
     currentRoom = previousRoom;
     previousRoom = tempRoom;
     gamePlayer.setCurrentRoom(currentRoom);
     System.out.println(currentRoom.getLongDescription());
    }
    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
}
