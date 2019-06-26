package telegrammrentalbot.rentbot.constants;

import java.util.ArrayList;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public interface consts {
    AtomicLong advId = new AtomicLong(0);
    String REGION_SOUTH = "SOUTH";
    String REGION_NORTH = "NORTH";
    String REGION_CENTER = "CENTER";
    String MAIN_MENU = "MAIN MENU";
    List<String> REGIONS_MENU = new ArrayList<>((Arrays.asList("SOUTH", "NORTH", "CENTER")));
    List<String> SOUTH_CITIES = new ArrayList<>((Arrays.asList("BEERSHEBA", "ASHDOD", "ASHKELON", MAIN_MENU)));
    List<String> NORTH_CITIES = new ArrayList<>((Arrays.asList("HAIFA", "AKKO", "NETANYA", "RAANANNA", "HERZLIA", "CEISARIA", MAIN_MENU)));
    List<String> CENTER_CITIES = new ArrayList<>((Arrays.asList("TEL-AVIV", "PETAH-TIKVA", "RAMAT-GAN", MAIN_MENU)));


}
