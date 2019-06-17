package telegrammrentalbot.rentbot.constants;

import java.util.HashMap;

public class Menu {

    private HashMap<String, String> startingMenu = new HashMap<>();
    private HashMap<String, String> CitiesByRegion = new HashMap<>();

    public HashMap<String, String> getStartingMenu() {
        startingMenu.put("SOUTH","1");
        startingMenu.put("NORTH","2");
        startingMenu.put("EAST","3");
        startingMenu.put("WEST","4");
        return startingMenu;
    }


    public HashMap<String, String> getCitiesByRegion() {

        return CitiesByRegion;
    }
}
