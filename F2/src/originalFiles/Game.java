package originalFiles;

import java.io.File;
import java.util.*;
import sp2017g1.*;
import language.*;
//import sp2017g1f2.*;

/**
 * @author  Michael Kolling and David J. Barnes
 * @version 2006.03.30
 */
public class Game 
{
    private WriteToStory c;// = new FXMLDocumentController();
    private Parser parser;
    private Room currentRoom;
    private static HashMap<ItemEnum, Item> inventory;
    private static int progress;
    private int effScore;
    private int timeScore;
    private int steps;
    public String scoreString;
    private ArrayList<String> pickedItems = new ArrayList<String>();
    
    public Room home, garden, bridge, river, waterfall, shed, mountainside, forest, mountain, neighbourHouse;
    
    Door door, ladderDoor;
    
    private Score score = new Score(this);
    private SaveAndLoad saveAndLoad = new SaveAndLoad(this);
    
    // Changed the access modifier of the Item variable to static so the hashmap key (not the actual item called key) can be accessed in TreeStump.java. No idea why it cannot be accessed from TreeStump.java without it.
    public static Item axe, key, hammer, nails, shovel, lumber, block, ladder, test, test1, test2, test3, test4, wood;
    
    Person neighbour;
    TreeStump treeStump;
    public Animal pet;
    EvilNPC evilNPC;
    sp2017g1.Timer time = new sp2017g1.Timer();
    
    Person testNPC;
    
    /**
     *
     * @param i
     */
    public static void setProgress(int i){
        if(progress < i)
            progress = i;
    }
    
    /**
     *
     * @param item
     */
    public void evilNPCEncounter(String item){
        inventory.remove(ItemEnum.valueOf(item));
        //evilInventory.put(ItemEnum.valueOf(item), Item.getItem(ItemEnum.valueOf(item)));
        evilNPC.setStolenItem(item);
        c.toStoryField("EvilNPC stole your " + evilNPC.getStolenItem() + " and ran away\n");
        //evilNPC.setCurrentRoom(evilNPC.getLastRoom());
    }
    
    /**
     *
     * @param _c
     */
    public Game(WriteToStory _c) 
    {
        createInventory();
        createRooms();
        createItems();
        createNPC();
        parser = new Parser();
        progress = 0;
        c = _c;
    }
    
    private void createRooms() {
        //Creates and defines the rooms used in the game.
        
        //Each room has a unique name and description.
        home = new Room("home", "home");
        garden = new Room("in your garden outside your home","garden");
        bridge   = new Room("on the bridge that crosses the local river", "bridge");
        river = new Room("by the river with a great waterfall", "river");
        waterfall = new Room("at the waterfall", "waterfall");
        shed = new Room("at your shed", "shed");
        mountainside = new Room("at the side of the mountain", "mountainside");
        forest = new Room("at a giant oak tree", "forest");
        mountain = new Room("on a mountain cliff", "mountain");
        neighbourHouse = new Room("at your neighbours house", "neighbourHouse");
        
        door = new Door("The door to your house", ItemEnum.key, "home");
        ladderDoor = new Door("ladder to the top of the mountain", ItemEnum.ladder, "mountain");
   
        
        //Defines the exits of each room and where they lead.

        garden.setExit("east", shed);
        garden.setExit("south", door);
        garden.setExit("west", bridge);

        
        bridge.setExit("north", river);
        bridge.setExit("east", garden);
        bridge.setExit("west", neighbourHouse);
        
        river.setExit("north", waterfall);
        river.setExit("south", bridge);

        waterfall.setExit("south", river);

        shed.setExit("north", mountainside);
        shed.setExit("west", garden);


        mountainside.setExit("north", ladderDoor);
        mountainside.setExit("east", forest);
        mountainside.setExit("south", shed);


        mountain.setExit("south", mountainside);

        forest.setExit("west", mountainside);


        neighbourHouse.setExit("east", bridge);
        
        door.setExit("south", home);
        
        ladderDoor.setExit("north", mountain);
        currentRoom = garden;
    }
    
    private void createItems() {
        // itemName = new Item(ItemEnum.itemName, itemLocation, itemScore, timeBonus);
        
        // Found items, worth 15 points.
        axe = new Item(ItemEnum.axe, shed, 15);
        shovel = new Item(ItemEnum.shovel, mountain, 25);
        nails = new Item(ItemEnum.nails, bridge, 15);
        wood = new Item(ItemEnum.wood, waterfall, 15);
        
//        block = new Item(ItemEnum.block, shed);

        // Test items.
        test = new Item(ItemEnum.test, garden);
        test1 = new Item(ItemEnum.test1, garden);
        test2 = new Item(ItemEnum.test2, garden);
        test3 = new Item(ItemEnum.test3, garden);
        test4 = new Item(ItemEnum.test4, garden);
        
        // Made items and quest rewards, worth 40 points.
        key = new Item(ItemEnum.key, 40);
        hammer = new Item(ItemEnum.hammer, 40);
        lumber = new Item(ItemEnum.lumber, 40);
        ladder = new Item(ItemEnum.ladder, 40);
    }
    
    private static void createInventory() {
        inventory = new HashMap<ItemEnum, Item>();
    }

    /**
     *
     * @return
     */
    public static HashMap<ItemEnum, Item> getInventory() {
        return inventory;
    }
    
    private void createNPC() {
        neighbour = new Person(neighbourHouse);
        treeStump = new TreeStump(shed);
        pet = new Animal(Species.DOG, forest);
        evilNPC = new EvilNPC(waterfall);
        
        testNPC = new Person(garden);
    }

    /**
     *
     */
    public void play() 
    {            
        printWelcome();
        time.start();
        boolean finished = false;
        String test;
        /*while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }*/
    }
    
    
    /**
     *
     */
    private void printWelcome()
    {
        c.toStoryField(WordList.WELCOME);
        c.toStoryField(WordList.DESCRIPTION);
        c.toStoryField(WordList.GET_HELP);
        c.toStoryField("");
//        c.toStoryField(currentRoom.getLongDescription());
//        if(!currentRoom.getRoomItems().isEmpty()) {
//            c.toStoryField(WordList.ITEMS_IN_ROOM);
//        } else {
//            
//        }
////        currentRoom.getRoomItemsList();
////        c.toStoryField(currentRoom.getRoomItemsList());
//        getRoomItemList(currentRoom);
    }

    /**
     *
     * @param command
     * @return 
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        CommandWord commandWord = command.getCommandWord();

        if(commandWord == CommandWord.UNKNOWN) {
//            System.out.println(WordList.DONT_KNOW_WHAT_YOU_MEAN);
            c.toStoryField(WordList.DONT_KNOW_WHAT_YOU_MEAN);
            return false;
        }

        if (commandWord == CommandWord.HELP) {
            printHelp();
        }
        else if (commandWord == CommandWord.GO) {
            goRoom(command);
        }
        else if (commandWord == CommandWord.QUIT) {
            wantToQuit = quit(command);
        }
        else if (commandWord == CommandWord.PICK) {
            pickItem(command);
        }
        else if (commandWord == CommandWord.USE) {
            dropItem(command);
        }
        else if (commandWord == CommandWord.DROP) {
            dropItem(command);
        }
        else if (commandWord == CommandWord.INVENTORY) {
            printInventory(command);
        }
//        else if (commandWord == CommandWord.UNLOCK) {
//            unlockRoom(command);
//        }
        /* The combine command has been replaced by the Tree Stump interaction.
        else if (commandWord == CommandWord.COMBINE) {
        combineItems(command);
        }*/
        else if (commandWord == CommandWord.INTERACT) {
            interact(command);
        }
        else if (commandWord == CommandWord.SAVE){
            saveAndLoad.save();
        }
        else if (commandWord == CommandWord.LOAD){
            saveAndLoad.load();
        }
        return wantToQuit;
    }

    private void printHelp() 
    {
//        System.out.println(WordList.PRINT_HELP);
        c.toStoryField(WordList.PRINT_HELP);
        parser.showCommands();
    }
    
    /**
     *
     * @param direction
     */
    public void goRoom(String direction){
        CommandWord commandWord = CommandWord.GO;
        Command command = new Command(commandWord,direction);
        goRoom(command);
    }
    
    /**
     *
     * @param command
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
//            System.out.println(WordList.GO_WHERE);
            c.toStoryField(WordList.GO_WHERE);
            return;
        }
        
        String direction = command.getSecondWord();

        Room nextRoom = currentRoom.getExit(direction);
        Door nextRoom1 = currentRoom.getExitDoor(direction);
        
//        if(nextRoom != null && nextRoom1 != null && nextRoom1.getLock() == true){
//           unlockRoom(command); //Fejl
//        }
        
        if(steps < 50) {
            steps++;
        }
        
        evilNPC.move();
        
        if (nextRoom == null && nextRoom1 == null) {
//            System.out.println("There is no door!");
            c.toStoryField("There is no door!\n");
        }
        else if (nextRoom == null && nextRoom1.getLock() == true){
//            System.out.println("The door is locked");
            if(nextRoom1.getName().equals("mountain")) {
                if (inventory.containsKey(ItemEnum.ladder)) {
                    nextRoom1.setLock(false);
                    currentRoom = nextRoom1.getExit(direction);
                    pet.goPet(nextRoom1.getExit(direction));
                    inventory.remove(ItemEnum.ladder);
                } else {
                    c.toStoryField(WordList.MOUNTAIN_DOOR + "\n");
                }
            } else if (nextRoom1.getName().equals("home")) {
                if (inventory.containsKey(ItemEnum.key) && pet.isFollow()) {
                    nextRoom1.setLock(false);
                    currentRoom = nextRoom1.getExit(direction);
                    pet.goPet(nextRoom1.getExit(direction));
                    inventory.remove(ItemEnum.key);
                } else if (inventory.containsKey(ItemEnum.key)) {
                    c.toStoryField(WordList.STILL_NEED_PET);
                } else {
                    c.toStoryField(WordList.HOME_DOOR + "\n");
                }
            }
            if (nextRoom1.getExit(direction).getRoomName() == "home"){
                if(progress == 0){
//                    System.out.println("I should get my pet before going home");
                    c.toStoryField("I should get my pet before going home");
                } else{
                    setProgress(2);
                }
            }
        }
        else if (nextRoom == null && nextRoom1.getDoor() == true){
            currentRoom = nextRoom1.getExit(direction);
            pet.goPet(nextRoom1.getExit(direction));
            if (currentRoom == home) {
                c.toStoryField(WordList.END_DESCRIPTION);
                //System.exit(0);
            }
            c.toStoryField("going through door");
//            c.toStoryField(currentRoom.getLongDescription());
            /*if (neighbour.getCurrentRoom() == currentRoom) {
                neighbour.interact(command);
            }*/
//            System.out.println(WordList.ITEMS_IN_ROOM);
//            c.toStoryField(WordList.ITEMS_IN_ROOM);
//            currentRoom.getRoomItemsList();
//            c.toStoryField(currentRoom.getRoomItemsList());
//            getRoomItemList(currentRoom);
        } else {
            currentRoom = nextRoom;
            pet.goPet(nextRoom);
            
//            System.out.println(currentRoom.getLongDescription());
//            c.toStoryField(currentRoom.getLongDescription());
            if (currentRoom == neighbour.getCurrentRoom()) {
//                System.out.println("You see your neighbour in the room.");
                c.toStoryField("You see your neightbour in the room.");
                command.setSecondWord("neighbour");
                interact(command);
            } else if(currentRoom == treeStump.getCurrentRoom()) {
//                System.out.println("You see a treestump in the room.");
                c.toStoryField("you see a treestump in the room.");
            } else if (currentRoom == pet.getCurrentRoom() && !pet.isFollow()) {
//                System.out.println("You see a pet. It will now follow you.");
                c.toStoryField(WordList.FOUND_PET);
                pet.startFollow();
                setProgress(1);
            }
            if (currentRoom == evilNPC.getCurrentRoom()) {
                if (getPlayerInventory().isEmpty()){
                }
                else{
                String stringInventory = getPlayerInventory().replace("[", "").replace("]", "").replace(";", "").replace(" ", "");
                String[] keys = stringInventory.split(",");
                

                if (!inventory.isEmpty()  && evilNPC.getStolenItem()== null) {

                    switch (inventory.size()) {
                        case 1: 
                            evilNPCEncounter(keys[0]);
                            break;
                        case 2: {
                            double j = Math.random() * 10;
                            if (j <= 5) {
                                evilNPCEncounter(keys[0]);
                            } else {
                                evilNPCEncounter(keys[1]);
                            }
                            break;
                        }
                        case 3: {
                            c.toStoryField(stringInventory);
                            double j = Math.random() * 10;
                            if (j < 3.33) {
                                evilNPCEncounter(keys[0]);
                            } else if (j >= 3.33 && j < 6.66) {
                                evilNPCEncounter(keys[1]);
                            } else if (j >= 6.66 && j <= 10) {
                                evilNPCEncounter(keys[2]);
                            }
                            break;
                        }
                        default:
                            break;
                    }

                }
                else if (inventory.isEmpty() && evilNPC.getStolenItem()== null){
                    
                }
             else if (evilNPC.getStolenItem()!= null) {//evilInventory.size()>0
                //ItemEnum stolenItem = ItemEnum.valueOf(evilNPC.getStolenItem());                   
                    if (inventory.size() < 3) {
                        c.toStoryField("EvilNPC returns your " + evilNPC.getStolenItem());
                        //evilInventory.remove(ItemEnum.valueOf(evilNPC.getStolenItem()));
                        inventory.put(ItemEnum.valueOf(evilNPC.getStolenItem()), Item.getItem(ItemEnum.valueOf(evilNPC.getStolenItem())));
                        evilNPC.setStolenItem(null);
                    } else {
                        c.toStoryField("EvilNPc wants to return your " + evilNPC.getStolenItem() + ", but your inventory is full.");
                    }
            } else  {
            }
                
                }
            }
            
            if (currentRoom == testNPC.getCurrentRoom()){
                System.out.println("TestNPC is in the room");
            }
            
//            if (!currentRoom.getRoomItems().isEmpty()) {
//                System.out.println(WordList.ITEMS_IN_ROOM);
//                c.toStoryField(WordList.ITEMS_IN_ROOM);
//                currentRoom.getRoomItemsList();
//                c.toStoryField(currentRoom.getRoomItemsList());
//                getRoomItemList(currentRoom);
//            } else {
//                System.out.println("No items in room.");
//                c.toStoryField("No items in room.");
//            }
        }
        

        if (currentRoom == home) {
//            System.out.println("You win!");
            c.toStoryField("You win!");
            //System.exit(0);
        }

    }
    
//    private void unlockRoom(Command command) {
//        if(!command.hasSecondWord()) {
////            System.out.println("Unlock what?");
//            c.toStoryField("unlock what?");
//            return;
//        }
//        
//        String direction = command.getSecondWord();
//
//        Room nextRoom = currentRoom.getExit(direction);
//        Door nextRoom1 = currentRoom.getExitDoor(direction);
//        
//        if (nextRoom1.getName().equals("home") && pet.isFollow()) {
//            nextRoom1.setLock(false);
//            goRoom(command);
//        }
//        
//        if (nextRoom1.getName() != "home"){
//            nextRoom1.setLock(false);
//            /*System.out.println("Room is now unlocked");*/
//            c.toStoryField("Room is now unlocked");
//            inventory.remove(ItemEnum.valueOf(nextRoom1.getKey()));
//            goRoom(command);
//        } else {
//            if (pet.isFollow()) {
//                nextRoom1.setLock(false);
//                /*System.out.println("Room is now unlocked");*/
//                c.toStoryField("Room is now unlocked");
//                inventory.remove(ItemEnum.valueOf(nextRoom1.getKey()));
//                goRoom(command);
//            } else {
//                c.toStoryField(WordList.NO_PET);
//            }
//        }
//    }

    /**
     *
     */
    public void quit() {
        CommandWord commandWord = CommandWord.QUIT;
        Command command = new Command(commandWord, null);
        quit(command);
    }
    
    /**
     *
     * @param direction
     * @return 
     */
    private boolean quit(Command command) {
        if(command.hasSecondWord()) {
            /*System.out.println("Quit what?");*/
            c.toStoryField("Quit what?");
            return false;
        }
        else {
        c.toStoryField("Thank you for playing.  Good bye.");
//        System.out.println("Thank you for playing.  Good bye.");
        System.exit(0);
            return true;
        }
    }
    
    /**
     *
     * @param item
     */
    public void pickItem(String item){
        CommandWord commandWord = CommandWord.GO;
        Command command = new Command(commandWord, item);
        pickItem(command);
    }
    
    /**
     *
     * @param command
     */
    private void pickItem(Command command) {
        try {
            ItemEnum inputItem = ItemEnum.valueOf(command.getSecondWord().toLowerCase());
            
            if (!pickedItems.contains(inputItem.toString())) {
                pickedItems.add(inputItem.toString());
                time.addTime();
            }
            
        if(currentRoom.getRoomItems().containsKey(inputItem) && inventory.size() < 3) {
            inventory.put(inputItem, currentRoom.getRoomItems().get(inputItem));
            currentRoom.getRoomItems().remove(inputItem);
            Item.getAllItems().get(inputItem).setRoom(null);
            if(inventory.get(ItemEnum.valueOf(command.getSecondWord())).getItemName() == "shovel"){
                setProgress(5);
            }
            /*System.out.println(inventory.get(ItemEnum.valueOf(command.getSecondWord())).getItemName() + " is added to the inventory");*/
            c.toStoryField(inventory.get(ItemEnum.valueOf(command.getSecondWord())).getItemName() + " is added to the inventory");

        } else if(!currentRoom.getRoomItems().containsKey(inputItem)) {
            /*System.out.println("That item is not in the room!");*/
            c.toStoryField("That item is not in the room!");
        } else {
            /*System.out.println("Inventory is full");*/
            c.toStoryField("Inventory is full");
        }
        } catch (NullPointerException e) {
            /*System.out.println("Pick what item?");*/
            c.toStoryField("Pick what item?");
        } catch (IllegalArgumentException e) {
            /*System.out.println("That is not an item!");*/
            c.toStoryField("That is not an item!");
        }

    }
    
    /**
     *
     * @param item
     */
    public void dropItem(String item) {
        CommandWord commandWord = CommandWord.DROP;
        Command command = new Command(commandWord, item);
        dropItem(command);
    }
    
    /**
     *
     * @param command
     */
    private void dropItem(Command command) {
        try {
            ItemEnum inputItem = ItemEnum.valueOf(command.getSecondWord().toLowerCase());
        
        if(inventory.containsKey(inputItem)) {
            currentRoom.getRoomItems().put(inputItem, inventory.get(inputItem));
            inventory.get(inputItem).setRoom(currentRoom);
            inventory.remove(inputItem);
            
            /*System.out.println(currentRoom.getRoomItems().get(inputItem).getItemName() + " is removed from the inventory");*/
            c.toStoryField(currentRoom.getRoomItems().get(inputItem).getItemName() + " is removed from the inventory");
        }
        } catch (NullPointerException e) {
            /*System.out.println("Drop what item?");*/
            c.toStoryField("Drop what item?");
        } catch (IllegalArgumentException e) {
            /*System.out.println("That is not an item");*/
            c.toStoryField("That is not an item");
        }
    }
    
    /**
     *
     * @param command
     */
    private void printInventory(Command command) {
        if(!inventory.isEmpty()) {
            System.out.println("In your inventory is: ");
            for (ItemEnum item : inventory.keySet()) {
                System.out.printf("%s  ", inventory.get(item).getItemName());
            } System.out.println();
        } else {
            System.out.println("No items in the inventory");
        }
    }

    // Move this method to TreeStump.java.
    /*    private void combineItems(Command command) {
    if(inventory.containsKey(ItemEnum.nails)) {
    inventory.put(ItemEnum.ladder, ladder);
    inventory.remove(ItemEnum.nails);
    System.out.println(nails.getItemName() + " and x " + "has been combined to " + ladder.getItemName());
    }
    
    else {
    System.out.println("None of the required items are in your inventory.");
    }
    }*/

    /**
     *
     * @param npc
     */
    public void interact(String npc) {
        CommandWord commandWord = CommandWord.INTERACT;
        Command command = new Command(commandWord, npc);
        interact(command);
    }
    
    /**
     *
     * @param command
     */
    private void interact(Command command) {
        try {
            String inputCommand = command.getSecondWord().toLowerCase();
            
            if (command.getSecondWord().isEmpty()) {
                System.out.println("SecondWord!");
            }
            
            if (inputCommand.equals("pet")){
                if (pet.getCurrentRoom() == currentRoom){
                    c.toStoryField(pet.interact(progress));
                    setProgress(1);
                } else {
                System.out.println(WordList.NO_PET);
                c.toStoryField(WordList.NO_PET);
                }
            } else if (inputCommand.equals("neighbour")){
                if (currentRoom == neighbour.getCurrentRoom()) {
                //neighbour.interactExtended(command, key, hammer, inventory);
                
                    c.toStoryField(neighbour.interactExtended(command, key, hammer, inventory));
                
                setProgress(3);
                if(inventory.containsKey(ItemEnum.hammer)){
                    //setProgress();
                  } else if(inventory.containsKey(ItemEnum.key)){
                  setProgress(6);
                  }
                }
            } else if (inputCommand.equals("stump")){
                if (currentRoom == treeStump.getCurrentRoom()) {
                c.toStoryField(treeStump.interactExtendedStump(command, nails, hammer, wood, lumber, ladder, inventory));
                    if(inventory.containsKey(ItemEnum.ladder)){
                    setProgress(4);
                    }
                }
            } else {
                System.out.println(WordList.WRONG_INTERACT);
            }
                
            /*
            if (currentRoom == neighbour.getCurrentRoom()) {
                neighbour.interactExtended(command, key, hammer, inventory);
            } else if (currentRoom == treeStump.getCurrentRoom()) {
                treeStump.interactExtendedStump(command, nails, hammer, wood, lumber, ladder, inventory);
            }

            */
            /*if (currentRoom == treeStump.getCurrentRoom()) {
                treeStump.interactExtendedStump(command, nails, hammer, wood, lumber, ladder, inventory);
            } else {
            System.out.println("There is no Tree Stump in this room.");
            }*/
                    
        } catch (NullPointerException e) {
            System.out.println("What interaction?");
        }
    }
    
    

    /**
     *
     * @param room
     */
    private void getRoomItemList (Room room) {
         for (ItemEnum item : room.getRoomItems().keySet()) {
            //System.out.printf("%s ", room.getRoomItems().get(item).getItemName());
            c.toStoryFieldnln(room.getRoomItems().get(item).getItemName() + " ");
        }
         c.toStoryField(" ");
    }
    
    /**
     * Calculates the total game score by adding the score for time and steps.
     * 
     * @return Total score as a string.
     */
    public String calculateScore() {
        
        effScore = (1000 - (steps * 20));
        timeScore = (int) (time.getTime() * 2);
        int score = effScore + timeScore;
        scoreString = Integer.toString(score);
        
        return scoreString;
    }
    
    /**
     *
     * @return
     */
    public int getSteps() {
        return steps;
    }
    
    /**
     *
     * @param loadSteps
     */
    public void setSteps(int loadSteps) {
        steps = loadSteps;
    }
    
    /**
     *
     * @return
     */
    public boolean highscore() {
        int scoreInt = Integer.parseInt(calculateScore());
        int highscoreInt = Integer.parseInt(highScoreLoad());
        if(scoreInt > highscoreInt) {
            highScoreSave();
            return true;
        } else {
            return false;
        }
    }
    
    /**
     *
     * @param item
     * @param room
     */
    public void setItemLocation(Item item, String room) {
        switch(room) {
            case "garden":
                item.setRoom(garden);
                break;
            case "shed":
                item.setRoom(shed);
                break;
            case "mountainside":
                item.setRoom(mountainside);
                break;
            case "forest":
                item.setRoom(forest);
                break;
            case "mountain":
                item.setRoom(mountain);
                break;
            case "bridge":
                item.setRoom(bridge);
                break;
            case "river":
                item.setRoom(river);
                break;
            case "waterfall":
                item.setRoom(waterfall);
                break;
            case "neighbourHouse":
                item.setRoom(neighbourHouse);
                break;
        }
    }
    
    /**
     *
     * @return
     */
    public String[] getRoomItems(){
        String items = currentRoom.getRoomItems().keySet().toString();
        items = items.replace("[", "");
        items = items.replace("]", "");
        return items.split(", ");
    }
    
    /**
     *
     * @return
     */
    public String[] getInventoryItems() {
        String items = inventory.keySet().toString();
        items = items.replace("[", "");
        items = items.replace("]", "");
        return items.split(", ");
    }

    /**
     *
     * @return
     */
    public int getGameTime() {
       return time.getTime();
    }
    
    /**
     *
     * @param time
     */
    public void setGameTime(int time) {
        this.time.setTime(time);
    }
    
    /**
     *
     * @return
     */
    public String getPlayerRoom() {
        return currentRoom.getRoomName();
    }
    
    /**
     *
     * @param room
     */
    public void setPlayerRoom(String room) {
        this.currentRoom = Room.getRoom(room);
    }
    
    /**
     *
     * @return
     */
    public String getPetRoom() {
        return pet.getCurrentRoom().getRoomName();
    }
    
    /**
     *
     * @param room
     */
    public void setPetRoom(String room) {
        this.pet.setCurrentRoom(Room.getRoom(room));
    }
    
    /**
     *
     * @return
     */
    public String getEvilNPCRoom() {
        return evilNPC.getCurrentRoom().getRoomName();
    }
    
    /**
     *
     * @param room
     */
    public void setEvilNPCRoom(String room) {
        this.evilNPC.setCurrentRoom(Room.getRoom(room));
    }
    
    /**
     *
     * @return
     */
    public int getNeighbourInteractCount() {
        return neighbour.getInteractCount();
    }
    
    /**
     *
     * @param interactCount
     */
    public void setNeighbourInteractCount(int interactCount) {
        neighbour.setInteractCount(interactCount);
    }
    
    /**
     *
     * @return
     */
    public int getProgress() {
        return progress;
    }
    
    /**
     *
     * @param progress
     */
    public void setGameProgress(int progress) {
        Game.progress = progress;
    }
    
    /**
     *
     * @return
     */
    public String getPlayerInventory() {
        return inventory.keySet().toString();
    }
    
    /**
     *
     * @param item
     */
    public void setInventory (String item) {
        Game.inventory.put(ItemEnum.valueOf(item), Item.getItem(ItemEnum.valueOf(item)));
    }
    
    /**
     *
     */
    public void save(){
        saveAndLoad.save();
        c.toStoryField(WordList.GAME_SAVE);
    }
    
    /**
     *
     */
    public void load(){
        saveAndLoad.load();
    }
    
    /**
     *
     * @return
     */
    public String getScore() {
        return scoreString;
    }
    
    /**
     *
     */
    public void highScoreSave() {
        score.Save();
    }
    
    /**
     *
     * @return
     */
    public String highScoreLoad() {
        if(new File("highscore.txt").exists()){
            return score.Load();
        } else {
            return "0";
        }
    }
    
    /**
     *
     * @param pickedItems
     */
    public void setPickedItems(ArrayList<String> pickedItems) {
        this.pickedItems = pickedItems;
    }
    
    /**
     *
     * @return
     */
    public ArrayList<String> getPickedItems() {
        return pickedItems;
    }
    
    /**
     *
     * @return
     */
    public boolean getDoorLock() {
        return door.getLock();
    }
    
    /**
     *
     * @param lock
     */
    public void setDoorLock(Boolean lock) {
        door.setLock(lock);
    }
    
    /**
     *
     * @return
     */
    public boolean getLadderDoorLock() {
        return ladderDoor.getLock();
    }
    
    /**
     *
     * @param lock
     */
    public void setLadderDoorLock(Boolean lock) {
        ladderDoor.setLock(lock);
    }
    
    /**
     *
     */
    public void itemClear() {
        garden.itemClear();
        shed.itemClear();
        mountainside.itemClear();
        forest.itemClear();
        mountain.itemClear();
        bridge.itemClear();
        river.itemClear();        
        waterfall.itemClear();
        neighbourHouse.itemClear();
    }
    
    /**
     *
     * @param item
     * @param room
     */
    public void loadItem(String item, String room) {
        switch(room) {
            case "garden":
                garden.getRoomItems().put(ItemEnum.valueOf(item), Item.getItem(ItemEnum.valueOf(item)));
                break;
            case "shed":
                shed.getRoomItems().put(ItemEnum.valueOf(item), Item.getItem(ItemEnum.valueOf(item)));
                break;
            case "mountainside":
                mountainside.getRoomItems().put(ItemEnum.valueOf(item), Item.getItem(ItemEnum.valueOf(item)));
                break;
            case "mountain":
                mountain.getRoomItems().put(ItemEnum.valueOf(item), Item.getItem(ItemEnum.valueOf(item)));
                break;
            case "forest":
                forest.getRoomItems().put(ItemEnum.valueOf(item), Item.getItem(ItemEnum.valueOf(item)));
                break;
            case "bridge":
                bridge.getRoomItems().put(ItemEnum.valueOf(item), Item.getItem(ItemEnum.valueOf(item)));
                break;
            case "river":
                river.getRoomItems().put(ItemEnum.valueOf(item), Item.getItem(ItemEnum.valueOf(item)));
                break;
            case "waterfall":
                waterfall.getRoomItems().put(ItemEnum.valueOf(item), Item.getItem(ItemEnum.valueOf(item)));
                break;
            case "neighbourHouse":
                neighbourHouse.getRoomItems().put(ItemEnum.valueOf(item), Item.getItem(ItemEnum.valueOf(item)));
                break;
        }
    }
    
    /**
     *
     * @return
     */
    public String getEvilNPCItem() {
        return evilNPC.getStolenItem();
    }
    
    /**
     *
     * @param item
     */
    public void setEvilNPCItem(String item) {
        evilNPC.setStolenItem(item);
    }
    
    /**
     *
     * @return
     */
    public String characters() {
        if (currentRoom == pet.getCurrentRoom()) {
            if (currentRoom == evilNPC.getCurrentRoom()) {
                if (currentRoom ==  neighbour.getCurrentRoom()) {
                    return "playerNPCPetNeighbour";
                }
                return "playerNPCPet";
            } else if (currentRoom ==  neighbour.getCurrentRoom()) {
                return "playerPetNeighbour";
            } else {
                return "playerPet";
            }
        } else if (currentRoom == evilNPC.getCurrentRoom()) {
            if (currentRoom ==  neighbour.getCurrentRoom()) {
                return "playerNPCNeighbour";
            } else {
                return "playerNPC";
            }
        } else if (currentRoom ==  neighbour.getCurrentRoom()) {
            return "playerNeighbour";
        } else {
            return "player";
        }
    }
    
}