package com.github.ukraien1449.obidah.Events;

import com.github.ukraien1449.obidah.Obidah;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.UUID;

public class PlayerJoinEvent implements Listener {
    String UUID;

    Obidah plugin;

    public PlayerJoinEvent(Obidah plugin) {
        this.plugin = plugin;
    }
    @EventHandler
    public void onPlayerJoin(org.bukkit.event.player.PlayerJoinEvent event) throws Exception {
        Player player = event.getPlayer();
        if(!player.hasPlayedBefore()){
            UUID = player.getUniqueId().toString();
            postr();
        }
    }
    public void postr() throws Exception{
        try{
            Connection con = plugin.getConnection();
            PreparedStatement posted = con.prepareStatement("INSERT INTO userdata(UUID) VALUES ('"+ UUID + "') ON DUPLICATE KEY UPDATE UUID='"+ UUID +"'");
            posted.executeUpdate();
        }catch(Exception e){}

    }
}
