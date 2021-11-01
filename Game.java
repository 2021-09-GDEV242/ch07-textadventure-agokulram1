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
    // for A credit 
    private long starttime;
    private long endtime;
    private int playTime;
    /** this is the main method for the game class
     * @param args not needed
     */
    public static void main(String[] args) {
        Game game1 = new Game();
        game1.play(Integer.parseInt(args[0]));
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
         // adding more rooms
        Room library,dorm,bathRoom, pool, gymRoom, ladiesRoom, mensRoom, bookStore,groupStudyRoom,quietStudyRoom,
        reflectionRoom, lockerRoom,restaurant,cafeteria,furnitureStore; 
        // create the rooms
        outside = new Room("outside the main entrance of the university");
        theater = new Room("in a lecture theater");
        pub = new Room("in the campus pub");
        lab = new Room("in a computing lab");
        office = new Room("in the computing admin office");
        
        library = new Room("in a library");
        dorm = new Room("in the dorm");
        bathRoom = new Room("in the bathroom");
        pool = new Room("in the poolroom");
        gymRoom = new Room("in the gymroom");
        ladiesRoom = new Room("in the ladiesroom");
        mensRoom = new Room("in the mensroom");
        bookStore = new Room("in the bookstore"); 
        groupStudyRoom = new Room("in the group study room");
        quietStudyRoom = new Room("in the quiet study room");
        reflectionRoom = new Room("in the reflection room");
        lockerRoom = new Room ("in the locker room");
        restaurant = new Room("in the restaurant");
        cafeteria = new Room("in the cafeteria");
        furnitureStore = new Room ("in the furniture store");
        // initialise room exits
        outside.setExit("east", theater);
        theater.setExit("west", outside);
        outside.setExit("south", lab);
        lab.setExit("north", outside);
        
        outside.setExit("west", pub);
        pub.setExit("east", outside);
        
        theater.setExit("east", library);
        library.setExit("west", theater);
        
        theater.setExit("south", restaurant);
        restaurant.setExit("north", theater);
        
        pub.setExit("north",pool);
        pool.setExit("south",pub);
        
        pub.setExit("north",furnitureStore);
        furnitureStore.setExit("south",pub);
        
        pool.setExit("east",gymRoom);
        gymRoom.setExit("west",pool);
        
        gymRoom.setExit("south",cafeteria);
        cafeteria.setExit("north",cafeteria);
        bathRoom.setExit("west",lockerRoom);
        lockerRoom.setExit("east",bathRoom);
        
        office.setExit("west", lab);
        lab.setExit("east", office);
        lab.setExit("south",pool);
        pool.setExit("north",lab);
        lab.setExit("west",groupStudyRoom);
        groupStudyRoom.setExit("east",lab);
        
        lab.setExit("south",reflectionRoom);
        reflectionRoom.setExit("north",lab);
       
        office.setExit("south",bathRoom);
        bathRoom.setExit("north",office);
        
        library.setExit("north",dorm);
        dorm.setExit("south",library);
        
        library.setExit("south",bookStore);
        bookStore.setExit("north",library);
        
        dorm.setExit("east",ladiesRoom);
        ladiesRoom.setExit("west",dorm);
        
        mensRoom.setExit("south",dorm);
        dorm.setExit("north",mensRoom);
        // 8.20 storing one item in each room.
        // 8.21 2. The item description is created here but passed as a param to the storeItem method which is in room class
        outside.storeItem("Bench",100);
        theater.storeItem("Screen",200);
        pub.storeItem("Food",5);
        lab.storeItem("Computer",60);
        library.storeItem("book",3);
        dorm.storeItem("Bed",250);
        bathRoom.storeItem("towel",2);
        pool.storeItem("pool",250);
        gymRoom.storeItem("treadmill",2500);
        ladiesRoom.storeItem("shower",25);
        mensRoom.storeItem("shower",25);
        bookStore.storeItem("book",6);
        groupStudyRoom.storeItem("table",90);
        quietStudyRoom.storeItem("journal",15);
        reflectionRoom.storeItem("yoga mat",2);
        office.storeItem("laptop",25);
        lockerRoom.storeItem("bag",15);
        restaurant.storeItem("food",5);
        cafeteria.storeItem("food",5);
        furnitureStore.storeItem("sofa",250);
        currentRoom = outside;  // start game outside
    }

    /**
     *  Main play routine.  Loops until end of play.
     *  Enter how long you want to play in minutes in the Play Time field below.
     *  @param playtime Time limit in playing the game in minutes
     */
    public void play(int playtime) 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
        // For A credit setTimeLimit() and gameElapsed() is added
        setTimeLimit(playtime);
        gamePlayer = new Player("Gokul", 100, currentRoom);        
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = gameElapsed();
            if (!finished) 
                finished = checkHealth();
            if (!finished)
                finished = processCommand(command);
           
        }
        System.out.println("Thank you for playing.  Good bye.");
    }
    
    /**
     * Checking player health to see if can continue in the game
     * @return Healthy - if healthy return true else false
     */
    private boolean checkHealth(){
        boolean unhealthy = (gamePlayer.getHealth() == 0);
        if (unhealthy) 
           System.out.println("You're health is 0, Game Over!!!");    
        return unhealthy;  
        }
    
                 
                 
    /**
     * For A credit
     * Set time limit for how long to play
     * @param playtime - How long to play in minutes
     */
    private void setTimeLimit(int playtime){
        playTime = playtime;
        starttime = System.currentTimeMillis();
        endtime = starttime+(playtime*60000);
    }
    /**
     * For A credit
     * Checks if the time limit is reached, if reached returns true else false
     * @return - Returns true when the time limit is reached else false
     */
    private boolean gameElapsed(){
        if (System.currentTimeMillis() >= endtime) {
           System.out.println("Time Limit reached, you played for "+playTime+" minutes. I hope you had fun!");
           return true;
            }
        else
          return false;
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
                break;
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
     boolean ate = gamePlayer.eatCookie();
     if (ate)
     System.out.println("You have eaten now and are not hungry anymore, you can carry a maximum of "
     +gamePlayer.getMaxWeight()+
     ", your health is at "+gamePlayer.getHealth());
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
