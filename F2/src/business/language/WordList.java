/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business.language;

import business.CommandWord;

/**
 *
 * @author Student
 */
public class WordList {
    
//    class CommandWord
    public static final String GO = "go";
    public static final String QUIT = "quit";
    public static final String HELP = "help";
    public static final String UNKNOWN = "?";
    public static final String PICK = "pick";
    public static final String USE = "use";
    public static final String DROP = "drop";
    public static final String INVENTORY = "inventory";
    public static final String UNLOCK = "unlock";
    public static final String COMBINE = "combine";
    public static final String INTERACT = "interact";
    public static final String SAVE = "save";
    public static final String LOAD = "load";
    
//    class Game.
    public static final String WELCOME = "";
    public static final String DESCRIPTION = "As you are cooking a delicous meal "
                                           + "in your cozy cottage, your trusty "
                                           + "pet companion runs out of the door, "
                                           + "and into the woods you quickly run after it, to get it back\n" 
                                           + "You need to go find your pet and get back before your dinner gets burnt (10 minutes)";
    public static final String GET_HELP = "When you have found your " + WordList.PET_TYPE + "you can press '" + CommandWord.HELP + "' to get hints from your pet about what to do.";
    public static final String ITEMS_IN_ROOM = "These items are in the room: ";
    public static final String DONT_KNOW_WHAT_YOU_MEAN = "I don't know what you mean...";
    public static final String PRINT_HELP = "Get the house key to go home before your dinner gets burnt\n"
                                            + "For hints interact with your pet \n"
                                            + "\n"
                                             + "Your command words are:";
    public static final String GO_WHERE = "Go where?";
    public static final String END_DESCRIPTION = "you win";
    public static final String GAME_SAVE = "Game Saved";
    public static final String EVIL_STOLE_BEGIN = "A thief stole your ";
    public static final String EVIL_STOLE_END = " and ran away\n";
    public static final String EVIL_RETURN = "You take back your ";
    public static final String EVIL_RETURN_END = " from the thief";
    public static final String EVIL_WANTS_RETURN_BEGIN = "You attempt to take back your ";
    public static final String EVIL_WANTS_RETURN_END = " from the thief, but your inventory is full.";
    public static final String EVIL_NO_ITEM = "A thief tries to steal from you, but your inventory is empty.";
    
    
//    progress message.
    public static final String NO_PET = "Go get your pet\n";
    public static final String GET_PET = "I should get my pet before going home";
    public static final String AFTER_PET = "You have found your pet, go home";
    public static final String ASK_NEIGHBOR = "You gave your neighbour a spare key, go ask him if he still has it";
    public static final String FIND_SHOVEL = "Your neighbour lost his shovel on the mountain, you need to find it";
    public static final String CRAFT_LADDER = "To get on the mountain you need something to climb, maybe you could craft a ladder";
    public static final String GET_SHOVEL = "Use ladder to climb the mountain and get the shovel";
    public static final String GET_KEY = "Take the shovel to your neighbour, so he can give you the key";
    public static final String GO_HOME = "We should go home now";
    
//    interact message.
    public static final String WRONG_INTERACT = "Can't interact";
    public static final String PET_TYPE = "pet";
    public static final String NEIGHBOUR = "You see your neighbour in the room.";
    public static final String FOUND_PET = "You have found your " + PET_TYPE + ". It will now follow you.";
    public static final String NEIGHBOUR_QUEST = "Neighbour: Welcome! I do have your spare key, but I have buried it, "
                                               + "so it couldn't be stolen. To get it I'll need you to get my shovel. "
                                               + "Last time I saw my shovel was on the mountain. "
                                               + "You can come again later and borrow my hammer if needed";
    public static final String TREESTUMP = "You see a treestump in the room. Maybe it could be used as a crafting table";
    
//    Doors
    public static final String HOME_DOOR = "The door is locked. The wind most have slammed the door shut, so it locked itself. You need to get the key to open it.";
    public static final String STILL_NEED_PET = "Before I go home, I still need to find my pet.";
    public static final String MOUNTAIN_DOOR = "You can't climb the steep mountain, you need a ladder to get up.";
    public static final String NO_DOOR = "I can't go this way.\n";
    public static final String GOING_DOOR = "Going through door";
    
//        Inventory
    public static final String ITEM_NOT_IN_ROOM = "That item is not in the room!";
    public static final String INVENTORY_FULL = "Inventory is full";
    public static final String IN_INVENTORY = "In your inventory is: ";
    public static final String NO_ITEMS = "No items in the inventory";
    
    
}
