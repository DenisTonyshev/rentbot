package telegrammrentalbot.rentbot.constants;

import java.util.*;

public class MenuB {

    private HashMap<String, List<String>> regionsCities = new HashMap<>();

    public HashMap<String, List<String>> getStartingMenu() {
        regionsCities.put("SOUTH", null);
        regionsCities.put("NORTH", null);
        regionsCities.put("EAST", null);
        regionsCities.put("WEST", null);

        addCityToMap("SOUTH", new String[]{"a", "B", "v", "T"});
        addCityToMap("NORTH", new String[]{"a", "B", "v", "T"});
        addCityToMap("EAST", new String[]{"a", "B", "v", "T"});
        addCityToMap("WEST", new String[]{"a", "B", "v", "T"});
        return regionsCities;
    }

    public void catchResponseFromClient(){

    }


    private void addCityToMap(String region, String[] arrCity) {
        regionsCities.replace(region,null, new ArrayList<>(Arrays.asList(arrCity)));
    }


}