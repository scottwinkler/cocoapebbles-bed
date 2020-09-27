package com.cocoapebbles.bed.utils;

import java.io.File;
import java.io.IOException;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import com.cocoapebbles.bed.Main;

public class PlayerBedLocationManager {
    private Main plugin;
    private Player p;
    private FileConfiguration fc;
    private File file;

    public PlayerBedLocationManager(Main plugin, Player p) {
        this.plugin = plugin;
        this.p = p;
    }

    public void saveBedLocation(Location location) {
        FileConfiguration fc = this.getConfig();
        fc.set("bedLocation",location);
        this.saveConfig();
    }

    public Location getBedLocation(){
        FileConfiguration fc = this.getConfig();
        return (Location) fc.get("bedLocation");
    }

    private Player getOwner() {
        if (p == null) {
            try {
                throw new Exception();
            } catch (Exception e) {
                plugin.getLogger().warning("Err Player is Null!");
                e.printStackTrace();
            }

        }
        return p;

    }
    private File getFile()
    {
        if(file==null)
        {
            file=new File(getDataFolder(),getOwner().getUniqueId().toString()+".yml");
            if(!file.exists()){
                try{
                    file.createNewFile();
                }catch(IOException e){e.printStackTrace();}
            }
        }
        return file;
    }
    private FileConfiguration getConfig()
    {
        if(fc==null)
        {
            fc=YamlConfiguration.loadConfiguration(getFile());
        }
        return fc;
    }

    private File getDataFolder()
    {
        File dir = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().getPath().replace("%20", " "));
        File d = new File(dir.getParentFile().getPath(),plugin.getName());
        if(!d.exists()){d.mkdirs();}
        return d;
    }

    private boolean exists()
    {
        if(fc==null || file == null)
        {
            File temp = new File(getDataFolder(),getOwner().getUniqueId().toString()+".yml");
            if(!temp.exists())
            {
                return false;
            }
            else{
                file=temp;
            }
            return true;
        }
        return true;
    }

    private void saveConfig(){
        try{
            getConfig().save(getFile());
        }catch(IOException e){e.printStackTrace();}
    }
}