package com.cocoapebbles.bed;


import com.cocoapebbles.bed.events.PlayerBedEnter;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.Listener;
import com.cocoapebbles.bed.commands.SummonBed;
import com.cocoapebbles.bed.events.PlayerBedLeave;

import java.util.ArrayList;
import java.util.HashMap;
public class Main extends JavaPlugin implements Listener{
    ArrayList<String> quotes;

    @Override
    public void onEnable() {
        loadConfig();
        registerCommands();
        registerEvents();
    }

    @Override
    public void onDisable() {

    }
    private void registerCommands(){
        getCommand("bed").setExecutor(new SummonBed(this));
    }

    private void registerEvents(){
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new PlayerBedLeave(this,quotes),this);
        pm.registerEvents(new PlayerBedEnter(this),this);
    }

    private void loadConfig(){
        this.saveDefaultConfig();
        FileConfiguration config = this.getConfig();
        //ArrayList<String> quotes = new ArrayList<String>(){};
        //quotes.add("Insta-bed is a service brought to you by Southern prosperity");
        //quotes.add("A lemon gives by taking");
        //quotes.add("For the emperor");
       // ArrayList<String> quotes = (ArrayList<String>) config.getList("quotes");
        //this.getConfig().addDefault("quotes",quotes);
        //this.saveConfig();
        ArrayList<String> quotes = (ArrayList<String>) config.getList("quotes");
        this.quotes = quotes;
    }
}

