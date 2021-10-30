
/**
 * Write a description of class Item here.
 * for 8.20
 *
 * @author Gokul Ram
 * @version 2021.10.29
 */
public class Item

{
    // instance variables 
    private String itemDescription;
    private int itemWeight;

    /**
     * Constructor for objects of class Item
     * @param itemDesc the item's description.
     * @param itemWgt the item's weight.
     */
    
    public Item(String itemDesc, int itemWgt)
    {
        // initialise instance variables
        setItemDesc(itemDesc);
        setItemWeight(itemWgt);
    }

    /**
     * get method for item description and returns string
     * @return itemDescription returns item description as a string type
     */
    public String getItemDesc(){
           return itemDescription;
    }
    /**
     * set method for item description takes in String parameter
     * @param newItemDesc the item's description.
     */
    public void setItemDesc(String newItemDesc) {
        this.itemDescription = newItemDesc;
    }
    /**
     * get method for item weight and returns int
     * @return itemWeight returns item weight as an int
     */
    public int getItemWeight(){
    return itemWeight;
    }
    /**
     * set method for item weight takes in int parameter
     * @param newItemweight, an int
     */
    public void setItemWeight(int newItemweight){
        this.itemWeight = newItemweight;
    }
}
