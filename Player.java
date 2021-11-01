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
    // rudimentary health system
    private int healthmeter;
    /**
     * Constructor for objects of class Player
     * @param pname Player Name
     * @param pmaxweight Maximum weight the player can carry
     * @param pCurrentRoom Current room of the player
     * 8.31 Restricts the max weight a player can carry
     *  
     */ 
    public Player(String pname, int pmaxweight, Room pCurrentRoom)
    {
        Name = pname;
        maxweight = pmaxweight;
        carryweight = 0; 
        currentRoom = pCurrentRoom;
        healthmeter = 5;
        playitems = new HashMap<>();
    }
     
    /**
     * 8.29 The player picks up an item from the room
     *
     * @param  item - The item the player picks up
     * Credit A rudimentary health system - taking reduce the health meter by one
     */
     public void take(Item item)
    {
        if (healthmeter <= 2) {
        System.out.println("Warning!!! Eat something, you are weak, your health level is " + healthmeter);
        }
 
        if (carryweight + item.getItemWeight() > maxweight)
            System.out.println("Too heavy for you, you can carry only upto "+maxweight+", you are already carrying "+carryweight+
            ". The item "+item.getItemDesc()+" is weighing "+item.getItemWeight());
        else {
            playitems.put(item.getItemDesc(), item);
            carryweight = carryweight + item.getItemWeight();
            healthmeter = healthmeter - 1;
            System.out.println("You have pickedup "+item.getItemDesc()+
            ", your currently carrying "+carryweight+", your health is "+healthmeter);
            
        }
    }
    /**
     * accessor method for health meter
     * @return healthmeter
     */
    public int getHealth(){
    return healthmeter;
    }
    
    /**
     * accessor method for maximum carrying weight
     * @return maxweight - integer
     */
    public int getMaxWeight(){
        return maxweight;
    }
    
    /**
     * Accessor method for Current Room
     * @param croom - room to set as the current room
     */
    public void setCurrentRoom(Room croom){
        currentRoom = croom; 
    }
    
    
    /** 
     * 8.29 The player drops an item into the room
     * @param  dropitemname - The name of item the player drop
   
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
            System.out.println("Your total carrying weight is "+carryweight+
            ", your max carrying capacity is "+maxweight+
            ", your health is at "+healthmeter);
        }
    }
    
    /**
     * 8.33 Eat the magic cookie increases the max carry weight by 50
     * increase health meter by 2.
     * Credit A Eat actually changes game state (increases health, allows player to lift heavier item, etc.)
     * @return - Returns the maximum weight the player can carry
     */
    public boolean eatCookie() {
        if (healthmeter >= 10) {
           System.out.println("You ate too much, and you cannot eat more"); 
           return false;
        } else{
        healthmeter = healthmeter + 2;
        maxweight = maxweight+50; 
        return true;
    }
    }
    
    public void enterRoom(Room room){
        currentRoom = room;
    }
    
}
