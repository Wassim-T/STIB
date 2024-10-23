package model.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * This singleton gives access to all properties from config.properties.
 *
 */
public class ConfigManager {

    private static final String FILE = "config.properties";

    private final Properties prop;

    private final String url;

    private static ConfigManager INSTANCE;
    // mieux de faire new dans get instance pour des questions de debug plus facile a traquer si y a une erreur
    private ConfigManager(){
        prop = new Properties();
        url = getClass().getClassLoader().getResource(FILE).getFile();

    }

    /**
     * Loads the properties from this url.
     *
     * @throws IOException if no file is found.
     */
    public void load() throws IOException{
        try (InputStream inputStream = new FileInputStream(url)){
            prop.load(inputStream);
        } catch (IOException e) {
            throw new IOException("chargement impossible :"+e);
        }
    }

    /**
     * Returns the value from the key name.
     *
     * @param name key to found.
     * @return the value from the key-value pair.
     */
    public String getProperties(String name) {
        return prop.getProperty(name);
    }

    /**
     * Returns the instance of the singleton.
     * @return the instance of the singleton.
     */
    public static ConfigManager getInstance(){
        if (INSTANCE == null){
            INSTANCE = new ConfigManager();
            try {
                INSTANCE.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return INSTANCE;
    }
}
