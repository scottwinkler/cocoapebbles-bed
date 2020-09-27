package com.cocoapebbles.bed.commands;

import com.cocoapebbles.bed.Main;
import com.cocoapebbles.bed.utils.PlayerBedLocationManager;
import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.Bed;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class SummonBed implements CommandExecutor {
    private ChatColor chatColor = ChatColor.GOLD;
    private Logger logger;
    private Main m;
    public SummonBed(JavaPlugin p){
        this.logger = p.getLogger();
        this.m = (Main) p;
    }
    public void MakeBed(Player player){
        //get location and world to make bed at
        Location location = player.getLocation();
        World world = player.getWorld();
        int X = location.getBlockX();
        int Y = location.getBlockY();
        int Z = location.getBlockZ();

        int X2 = X;
        int Z2 = Z;
        BlockFace blockFace;
        if (world.getBlockAt(X+1,Y,Z).isEmpty()){
            X2=X+1;
            Z2 = Z;
            blockFace = BlockFace.EAST;
        }
        else if (world.getBlockAt(X-1,Y,Z).isEmpty()){
            X2 = X-1;
            Z2 = Z;
            blockFace = BlockFace.WEST;
        }
        else if (world.getBlockAt(X,Y,Z+1).isEmpty()){
            X2=X;
            Z2=Z+1;
            blockFace=BlockFace.SOUTH;
        }
        else if (world.getBlockAt(X,Y,Z-1).isEmpty()){
            X2=X;
            Z2=Z-1;
            blockFace=BlockFace.NORTH;
        }
        else {
            player.sendMessage(ChatColor.RED + "[BED] You do not have room to create a bed!");
            return;
        }

        TeleportUp(player,1);

        // Make Head of Bed
        Location location2 = new Location(world,X2,Y,Z2);
        MakeBedPart(Bed.Part.HEAD, blockFace, location2);

        // Make Foot of Bed
        Location location1 = new Location(world,X,Y,Z);
        MakeBedPart(Bed.Part.FOOT, blockFace, location1);

        //success
        sendPlayerMessage(player,location2);
    }

    private void sendPlayerMessage(Player player,Location location){
        String name = player.getUniqueId().toString();
        player.sendMessage(chatColor + "[BED] Bed created!");
    }

    public void MakeBedPart(Bed.Part part, BlockFace blockFace, Location location){
        World world = location.getWorld();
        Block block = world.getBlockAt(location);
        BlockState state = block.getState();
        state.setType(Material.PINK_BED);
        state.update(true);
        BlockData data = state.getBlockData();
        Bed bed = (Bed) data;
        bed.setPart(part);
        bed.setFacing(blockFace);
        state.setBlockData(data);
        state.update(true);
    }

    public void TeleportUp(Player player, int blocksTeleported){
        Location location = player.getLocation();
        player.teleport(location.add(0,blocksTeleported,0));
    }

    public Boolean isNightTime(Player player){
        World world = player.getWorld();
        long time = world.getTime();
        if (time>=12517){
            return true;
        }
        getHelpfulTimeMessage(player);
        return false;
    }

    public void getHelpfulTimeMessage(Player player){
        World world = player.getWorld();
        long time = world.getTime();

        player.sendMessage(ChatColor.RED + "[BED] Sorry, you cannot create a bed now");
        player.sendMessage(ChatColor.RED +"[BED] The time is: " + time);
        player.sendMessage(ChatColor.RED  + "[BED] Try again after 12517");
    }

    public Boolean hasHeadRoom(Player player){
        World world = player.getWorld();
        Location location = player.getLocation();
        if (world.getBlockAt(location.add(0,1,0)).getType()!=Material.AIR){
            player.sendMessage(ChatColor.RED + "[BED] Not enough head room!");
            return false;
        }
        return true;
    }


    public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel,
                             String[] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage("[Bed] You must be a player to use this command!");
            return false;
        }
        Player player = (Player) sender;
        if(isNightTime(player)&& hasHeadRoom(player)){
            MakeBed(player);
        }
        return true;
    }

}
