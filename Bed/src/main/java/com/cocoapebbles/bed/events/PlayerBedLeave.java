package com.cocoapebbles.bed.events;


import com.cocoapebbles.bed.Main;
import com.cocoapebbles.bed.utils.PlayerBedLocationManager;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.Bed;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedLeaveEvent;

import java.util.ArrayList;

public class PlayerBedLeave implements Listener {
    private Main m;
    private ArrayList<String> quotes;
    private ChatColor chatColor = ChatColor.GOLD;

    public String getRandomMessage(){
        int randomNum = (int) (Math.random()*quotes.size());
        return quotes.get(randomNum);
    }

    public void destroyBed(Block block){
        Bed bed = (Bed) block.getBlockData();
        BlockFace blockFace = bed.getFacing();
        World world = block.getWorld();
        Location location = block.getLocation();
        if (blockFace== BlockFace.EAST){
            world.getBlockAt(location.add(1,0,0)).setType(Material.AIR);
        }
        else if (blockFace==BlockFace.WEST){
            world.getBlockAt(location.subtract(1,0,0)).setType(Material.AIR);
        }
        else if (blockFace==BlockFace.SOUTH){
            world.getBlockAt(location.add(0,0,1)).setType(Material.AIR);
        }
        else if (blockFace==BlockFace.NORTH){
            world.getBlockAt(location.subtract(0,0,1)).setType(Material.AIR);
        }
        block.setType(Material.AIR);
    }

    @EventHandler
    public void onPlayerBedLeave(PlayerBedLeaveEvent event){
        Block block = event.getBed();
        Player player = (Player) event.getPlayer();

        if (block.getType()==Material.PINK_BED){
            player.sendMessage(chatColor + getRandomMessage());
            destroyBed(block);
        }
    }

    public PlayerBedLeave(Main plugin, ArrayList<String> quotes){
        this.m= plugin;
        this.quotes = quotes;
    }
}
