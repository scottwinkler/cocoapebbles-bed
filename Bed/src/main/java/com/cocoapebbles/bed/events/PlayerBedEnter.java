package com.cocoapebbles.bed.events;

import com.cocoapebbles.bed.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;

public class PlayerBedEnter implements Listener {
    Main m;

    private void sendMessage(String s){
        Bukkit.getServer().broadcastMessage(s);
    }
    @EventHandler
    public void onPlayerBedEnter(PlayerBedEnterEvent event){
        ChatColor chatColor = ChatColor.AQUA;
        Player player = event.getPlayer();
        sendMessage(chatColor+"[SERVER] "+player.getDisplayName()+" went to bed");
        sendMessage(chatColor+"[SERVER] Sleeping players:");

        for(Player p : Bukkit.getOnlinePlayers()){
            ChatColor  c = p.isSleeping() ? ChatColor.GREEN : ChatColor.RED;
            if (p.getUniqueId()==player.getUniqueId()){
                c = ChatColor.GREEN;
            }
            sendMessage(c + "    " + p.getDisplayName());
        }
    }
    public PlayerBedEnter(Main plugin){
        this.m = plugin;
    }
}
