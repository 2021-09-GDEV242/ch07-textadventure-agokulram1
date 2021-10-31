import java.util.HashMap;

/**
 * 8.28 - The Player for the game
 *
 * @author Gokul Ram
 * @version 2021.10.30
 */
public class Player
{
    // Defining the Player
    private String Name;
    // 8.30 The Hashmap below allows player to carry any number of items 
    private HashMap<String, Item> playitems;
    private int maxweight;
    private int carryweight; 
    private Room currentRoom;

    /**
     * Constructor for objects of class Player
     * @param Player Name
     * @param Maximum weight the player can carry
     * 8.31 Restricts the max weight a player can carry
     */
    public Player(String pname, int pmaxweight, Room pCurrentRoom)
    {
        Name = pname;
        maxweight = pmaxweight;
        carryweight = 0; 
        currentRoom = pCurrentRoom;
        playitems = new HashMap<>();
    }

    /**
     * 8.29 The player picks up an item from the room
     *
     * @param  Item - The item the player picks up
     * @return Boolean - True or False - if the player picks it up successfully then return true else false
     */
     public void take(Item item)
    {
        if (carryweight + item.getItemWeight() > maxweight)
            System.out.println("Too heavy for you, you can carry only upto "+maxweight+", you are already carrying "+carryweight);
            //return false;
        else {
            playitems.put(item.getItemDesc(), item);
            carryweight = carryweight + item.getItemWeight();
            System.out.println("You have pickedup "+item.getItemDesc());
        }
            //return true;
    }
    
    /**
     * Accessor method for Current Room
     * @param Room - room to set as the current room
     */
    public void setCurrentRoom(Room croom){
        currentRoom = croom; 
    }
    
    
    /** 
     * 8.29 The player drops an item into the room
     * @param  Item Name - The name of item the player drop
     * @return Boolean - if player successfuly drops, then return true else false
     */
    public void drop(String dropitemname){
        Item ditem = playitems.remove(dropitemname);
        if (ditem != null) 
            //return false;
          {
            carryweight = carryweight - ditem.getItemWeight();
            System.out.println("You have dropped "+dropitemname);
            //return true;
            }
        else 
            System.out.println("You are not carrying "+dropitemname);
    }
    
    /**
     * 8.32 Prints all the items that the user has picked up
     */
    public void printAllPlayerItems() {
        if (playitems.isEmpty()) {
            System.out.println("You are not carrying any items");
        }
        else {
            System.out.print("You are carrying ");
            for (String playitem : playitems.keySet())  
                System.out.print(playitem+" ");
            System.out.println();
        }
    }
    
    /**
     * 8.33 Eat the magic cookie increases the max carry weight by 50
     * @return - Returns the maximum weight the player can carry
     */
    public int eatCookie() {
        maxweight = maxweight+50; 
        return maxweight;
    }
    
    public void enterRoom(Room room){
        currentRoom = room;
    }
    
}
