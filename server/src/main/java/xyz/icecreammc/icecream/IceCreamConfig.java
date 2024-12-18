package ml.glucosedev.glucose;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.Properties;

public class IceCreamConfig {
    static Logger j = LogManager.getLogger("IceCream");
    public static Properties icecreamProp;
    public IceCreamConfig() throws  IOException {
        File propFile = new File("icecream.cfg");
        if (!propFile.exists()) {
            j.info("Creating IceCream Properties file...");
            try (OutputStream output = new FileOutputStream("./icecream.cfg")) {
                icecreamProp = new Properties();

                icecreamProp.setProperty("join-message", "%player% &ejoined the game"); //TODO
                icecreamProp.setProperty("leave-message", "%player% &eleft the game");//TODO
                icecreamProp.setProperty("tnt-explodes", String.valueOf(true));//TODO
                icecreamProp.setProperty("unknown-command", "&cUnknown command... Use /help for help");//TODO
//                icecreamProp.setProperty("console-prompt", ">"); // TODO

                icecreamProp.store(output, "IceCream config file");
                j.info("Created properties file");
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
    }
}
