package one.oth3r.sit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.text.MutableText;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class config {
    public static String lang = defaults.lang;
    public static boolean keepActive = defaults.keepActive;
    public static boolean sitWhileSeated = defaults.sitWhileSeated;
    public static boolean stairsOn = defaults.stairsOn;
    public static boolean slabsOn = defaults.slabsOn;
    public static boolean carpetsOn = defaults.carpetsOn;
    public static boolean fullBlocksOn = defaults.fullBlocksOn;
    public static boolean customOn = defaults.customOn;
    public static List<String> customBlocks = defaults.customBlocks;
    enum HandRequirement {
        empty,
        restrictive,
        none;
        public static HandRequirement get(String s) {
            try {
                return HandRequirement.valueOf(s);

            } catch (IllegalArgumentException e) {
                return empty;
            }
        }
    }
    public static HandRequirement mainReq = defaults.mainReq;
    public static boolean mainBlock = defaults.mainBlock;
    public static boolean mainFood = defaults.mainFood;
    public static boolean mainUsable = defaults.mainUsable;
    public static List<String> mainWhitelist = defaults.mainWhitelist;
    public static List<String> mainBlacklist = defaults.mainBlacklist;
    public static HandRequirement offReq = defaults.offReq;
    public static boolean offBlock = defaults.offBlock;
    public static boolean offFood = defaults.offFood;
    public static boolean offUsable = defaults.offUsable;
    public static List<String> offWhitelist = defaults.offWhitelist;
    public static List<String> offBlacklist = defaults.offBlacklist;
    public static File configFile() {
        return new File(FabricLoader.getInstance().getConfigDir().toFile()+"/Sit!.properties");
    }
    public static void load() {
        if (!configFile().exists() || !configFile().canRead()) {
            save();
            load();
            return;
        }
        try (FileInputStream fileStream = new FileInputStream(configFile())) {
            Properties properties = new Properties();
            properties.load(fileStream);
            String ver = (String) properties.computeIfAbsent("version", a -> String.valueOf(defaults.version));
            // if the old version system (v1.0) remove "v:
            if (ver.contains("v")) ver = ver.substring(1);
            loadVersion(properties,Double.parseDouble(ver));
            save();
        } catch (Exception f) {
            //read fail
            f.printStackTrace();
            save();
        }
    }
    public static void loadVersion(Properties properties, double version) {
        try {
            Gson gson = new GsonBuilder().disableHtmlEscaping().create();
            Type listType = new TypeToken<ArrayList<String>>() {}.getType();
            lang = (String) properties.computeIfAbsent("lang", a -> defaults.lang);
            //CONFIG
            keepActive = Boolean.parseBoolean((String) properties.computeIfAbsent("keep-active", a -> String.valueOf(defaults.keepActive)));
            sitWhileSeated = Boolean.parseBoolean((String) properties.computeIfAbsent("sit-while-seated", a -> String.valueOf(defaults.sitWhileSeated)));
            stairsOn = Boolean.parseBoolean((String) properties.computeIfAbsent("stairs", a -> String.valueOf(defaults.stairsOn)));
            slabsOn = Boolean.parseBoolean((String) properties.computeIfAbsent("slabs", a -> String.valueOf(defaults.slabsOn)));
            carpetsOn = Boolean.parseBoolean((String) properties.computeIfAbsent("carpets", a -> String.valueOf(defaults.carpetsOn)));
            fullBlocksOn = Boolean.parseBoolean((String) properties.computeIfAbsent("full-blocks", a -> String.valueOf(defaults.fullBlocksOn)));
            customOn = Boolean.parseBoolean((String) properties.computeIfAbsent("custom", a -> String.valueOf(defaults.customOn)));
            customBlocks = new Gson().fromJson((String)
                    properties.computeIfAbsent("custom-blocks", a -> gson.toJson(defaults.customBlocks)), listType);
            mainReq = HandRequirement.valueOf((String) properties.computeIfAbsent("hand.main.requirement", a -> String.valueOf(defaults.mainReq)));
            mainBlock = Boolean.parseBoolean((String) properties.computeIfAbsent("hand.main.block", a -> String.valueOf(defaults.mainBlock)));
            mainFood = Boolean.parseBoolean((String) properties.computeIfAbsent("hand.main.food", a -> String.valueOf(defaults.mainFood)));
            mainUsable = Boolean.parseBoolean((String) properties.computeIfAbsent("hand.main.usable", a -> String.valueOf(defaults.mainUsable)));
            mainWhitelist = new Gson().fromJson((String)
                    properties.computeIfAbsent("hand.main.whitelist", a -> gson.toJson(defaults.mainWhitelist)), listType);
            mainBlacklist = new Gson().fromJson((String)
                    properties.computeIfAbsent("hand.main.blacklist", a -> gson.toJson(defaults.mainBlacklist)), listType);
            offReq = HandRequirement.valueOf((String) properties.computeIfAbsent("hand.off.requirement", a -> String.valueOf(defaults.offReq)));
            offBlock = Boolean.parseBoolean((String) properties.computeIfAbsent("hand.off.block", a -> String.valueOf(defaults.offBlock)));
            offFood = Boolean.parseBoolean((String) properties.computeIfAbsent("hand.off.food", a -> String.valueOf(defaults.offFood)));
            offUsable = Boolean.parseBoolean((String) properties.computeIfAbsent("hand.off.usable", a -> String.valueOf(defaults.offUsable)));
            offWhitelist = new Gson().fromJson((String)
                    properties.computeIfAbsent("hand.off.whitelist", a -> gson.toJson(defaults.offWhitelist)), listType);
            offBlacklist = new Gson().fromJson((String)
                    properties.computeIfAbsent("hand.off.blacklist", a -> gson.toJson(defaults.offBlacklist)), listType);
            if (version == 1.0) {
                mainReq = HandRequirement.valueOf((String) properties.computeIfAbsent("main-hand-requirement", a -> String.valueOf(defaults.mainReq)));
                mainBlock = Boolean.parseBoolean((String) properties.computeIfAbsent("main-hand-block", a -> String.valueOf(defaults.mainBlock)));
                mainFood = Boolean.parseBoolean((String) properties.computeIfAbsent("main-hand-food", a -> String.valueOf(defaults.mainFood)));
                mainUsable = Boolean.parseBoolean((String) properties.computeIfAbsent("main-hand-usable", a -> String.valueOf(defaults.mainUsable)));
                mainWhitelist = new Gson().fromJson((String)
                        properties.computeIfAbsent("main-hand-whitelist", a -> gson.toJson(defaults.mainWhitelist)), listType);
                mainBlacklist = new Gson().fromJson((String)
                        properties.computeIfAbsent("main-hand-blacklist", a -> gson.toJson(defaults.mainBlacklist)), listType);
                offReq = HandRequirement.valueOf((String) properties.computeIfAbsent("off-hand-requirement", a -> String.valueOf(defaults.offReq)));
                offBlock = Boolean.parseBoolean((String) properties.computeIfAbsent("off-hand-block", a -> String.valueOf(defaults.offBlock)));
                offFood = Boolean.parseBoolean((String) properties.computeIfAbsent("off-hand-food", a -> String.valueOf(defaults.offFood)));
                offUsable = Boolean.parseBoolean((String) properties.computeIfAbsent("off-hand-usable", a -> String.valueOf(defaults.offUsable)));
                offWhitelist = new Gson().fromJson((String)
                        properties.computeIfAbsent("off-hand-whitelist", a -> gson.toJson(defaults.offWhitelist)), listType);
                offBlacklist = new Gson().fromJson((String)
                        properties.computeIfAbsent("off-hand-blacklist", a -> gson.toJson(defaults.offBlacklist)), listType);
            }
        } catch (Exception e) {
            Sit.LOGGER.info("ERROR LOADING CONFIG - PLEASE REPORT WITH THE ERROR LOG");
            e.printStackTrace();
        }
    }
    public static MutableText lang(String key, Object... args) {
        LangReader.loadLanguageFile();
        return LangReader.of("config.sit."+key, args).getTxT();
    }
    public static void save() {
        try (var file = new FileOutputStream(configFile(), false)) {
            Gson gson = new GsonBuilder().disableHtmlEscaping().create();
            file.write("# Sit! Config\n".getBytes());
            file.write(("version="+defaults.version).getBytes());
            file.write(("\n# all available languages: en_us").getBytes());
            file.write(("\nlang=" + lang).getBytes());
            file.write(("\n\n# "+lang("general.keep_active.description").getString()).getBytes());
            file.write(("\nkeep-active=" + keepActive).getBytes());
            file.write(("\n# "+lang("general.sit_while_seated.description").getString()).getBytes());
            file.write(("\nsit-while-seated=" + sitWhileSeated).getBytes());
            file.write(("\n# "+lang("general.sittable.description").getString()).getBytes());
            file.write(("\nstairs=" + stairsOn).getBytes());
            file.write(("\nslabs=" + slabsOn).getBytes());
            file.write(("\ncarpets=" + carpetsOn).getBytes());
            file.write(("\nfull-blocks=" + fullBlocksOn).getBytes());
            file.write(("\ncustom=" + customOn).getBytes());
            file.write(("\n# "+lang("general.sittable_blocks.description")
                    .append("\n# ").append(lang("general.sittable_blocks.description_2"))
                    .append(lang("general.sittable_blocks.description_3",
                            lang("general.sittable_blocks.description_3_2"),
                            lang("general.sittable_blocks.description_3_3"),
                            lang("general.sittable_blocks.description_3_4"),
                            lang("general.sittable_blocks.description_3_5")))
                    .append("\n# ").append(lang("general.sittable_blocks.description_4"))
                    .append("\n# ").append(lang("general.sittable_blocks.description_5"))
                    .append("\n# ").append(lang("general.sittable_blocks.description_6"))
                    .append("\n# ").append(lang("general.sittable_blocks.description_7"))
                    .append("\n# ").append(lang("general.sittable_blocks.description_8")).getString()).getBytes());
            file.write(("\ncustom-blocks="+gson.toJson(customBlocks)).getBytes());
            file.write(("\n\n# "+lang("hand")).getBytes());
            file.write(("\n# "+lang("hand.requirements.description")
                    .append("\n# ").append(lang("hand.requirements.description_2"))
                    .append("\n# ").append(lang("hand.requirements.description_3"))
                    .append("\n# ").append(lang("hand.requirements.description_4")).getString()).getBytes());
            file.write(("\nhand.main.requirement=" + mainReq).getBytes());
            file.write(("\nhand.main.block=" + mainBlock).getBytes());
            file.write(("\nhand.main.food=" + mainFood).getBytes());
            file.write(("\nhand.main.usable=" + mainUsable).getBytes());
            file.write(("\nhand.main.whitelist="+gson.toJson(mainWhitelist)).getBytes());
            file.write(("\nhand.main.blacklist="+gson.toJson(mainBlacklist)).getBytes());
            file.write(("\nhand.off.requirement=" + offReq).getBytes());
            file.write(("\nhand.off.block=" + offBlock).getBytes());
            file.write(("\nhand.off.food=" + offFood).getBytes());
            file.write(("\nhand.off.usable=" + offUsable).getBytes());
            file.write(("\nhand.off.whitelist="+gson.toJson(offWhitelist)).getBytes());
            file.write(("\nhand.off.blacklist="+gson.toJson(offBlacklist)).getBytes());
            // send packets to update the settings on the server
            if (SitClient.inGame) SitClient.sendPackets();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static class defaults {
        public static double version = 1.1;
        public static String lang = "en_us";
        public static boolean keepActive = true;
        public static boolean sitWhileSeated = true;
        public static boolean stairsOn = true;
        public static boolean slabsOn = true;
        public static boolean carpetsOn = true;
        public static boolean fullBlocksOn = false;
        public static boolean customOn = false;
        public static List<String> customBlocks = List.of("minecraft:campfire|.46|1|lit=false","minecraft:soul_campfire|.46|1|lit=false");
        public static HandRequirement mainReq = HandRequirement.empty;
        public static boolean mainBlock = false;
        public static boolean mainFood = false;
        public static boolean mainUsable = false;
        public static List<String> mainWhitelist = new ArrayList<>();
        public static List<String> mainBlacklist = new ArrayList<>();
        public static HandRequirement offReq = HandRequirement.restrictive;
        public static boolean offBlock = true;
        public static boolean offFood = false;
        public static boolean offUsable = true;
        public static List<String> offWhitelist = List.of("minecraft:torch","minecraft:soul_torch","minecraft:redstone_torch");
        public static List<String> offBlacklist = new ArrayList<>();
    }
}
