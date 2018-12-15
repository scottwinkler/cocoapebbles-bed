package com.cocoapebbles.bed;


import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.Listener;
import com.cocoapebbles.bed.commands.SummonBed;
import com.cocoapebbles.bed.events.PlayerBedLeave;

public class Main extends JavaPlugin implements Listener{
    @Override
    public void onEnable() {
        registerCommands();
        registerEvents();
    }
    @Override
    public void onDisable() {

    }
    public void registerCommands(){
        getCommand("bed").setExecutor(new SummonBed(this));
    }
    public void registerEvents(){
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new PlayerBedLeave(this),this);
    }
}

