import java.util.Set;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  For each existing exit, the room 
 * stores a reference to the neighboring room.
 * 
 * @author  Michael Kölling, Gokul Ram and David J. Barnes
 * @version 2021.10.29
 */

public class Room 
{
    private String description;
    private HashMap<String, Room> exits;        // stores exits of this room.
    private Item roomItem;     // stores the objects in a room
    // 8.21 - 1. created item object in this class as a class variable
    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Room(String description) 
    {
        this.description = description;
        exits = new HashMap<>();
    }
    
     /**
     * 8.20 method to store the item
     * @param itemDesc the item's description.
     * @param itemWgt the item's weight.
     */
    public void storeItem(String itemDesc, int itemWgt) 
    {
        roomItem = new Item(itemDesc,itemWgt);
        
    }
    /**
     * Define an exit from this room.
     * @param direction The direction of the exit.
     * @param neighbor  The room to which the exit leads.
     */
    public void setExit(String direction, Room neighbor) 
    {
        exits.put(direction, neighbor);
    }

    /**
     * @return The short description of the room
     * (the one that was defined in the constructor).
     */
    public String getShortDescription()
    {
        return description;
    }

    /**
     * Return a description of the room in the form:
     *     You are in the kitchen.
     *     Exits: north west
     * @return A long description of this room
     * 
     * 8.20 added item to the long description
     * You are in a computing lab, it has a Computer.
     * Exits: east north
     * 8.21 3. getLongDescription in room class is the best way to print the item description.
     * 8.21 4. because we want to print in along with room details.
     */
    public String getLongDescription()
    {
        return "You are " + description + ", it has a "+ roomItem.getItemDesc() + ".\n" + getExitString();
    }

    /**
     * Return a string describing the room's exits, for example
     * "Exits: north west".
     * @return Details of the room's exits.
     */
    private String getExitString()
    {
        String returnString = "Exits:";
        Set<String> keys = exits.keySet();
        for(String exit : keys) {
            returnString += " " + exit;
        }
        return returnString;
    }

    /**
     * Accessor method that returns the room item
     * @return roomItem 
     */
    public Item getRoomItem(){
        return roomItem;
    }
    
    /**
     * Return the room that is reached if we go from this room in direction
     * "direction". If there is no room in that direction, return null.
     * @param direction The exit's direction.
     * @return The room in the given direction.
     */
    public Room getExit(String direction) 
    {
        return exits.get(direction);
    }
    
    
   
}

