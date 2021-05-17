package com.github.ukraien1449.obidah.Events;

import com.github.ukraien1449.obidah.Obidah;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PlayerDeathEvent implements Listener {
    int deaths;
    double tokens;
    int toinsertdeaths;
    double stoinserttokens;

    double killerstokens;
    int kills;
    double toinsertkillerstokens;
    int toinsertkillerskills;

    Player killed;
    Player killer;
    Obidah plugin;

    public PlayerDeathEvent(Obidah plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerDeath(org.bukkit.event.entity.PlayerDeathEvent event){
        killed = event.getEntity();
        if(event.getEntity().getKiller() != null){
            if(event.getEntity().getKiller() instanceof Player){
                killer = event.getEntity().getKiller();
                insertToKilled();
                insertToKiller();
            }
        }
    }
public void insertToKilled(){
    try{
        Connection con = plugin.getConnection();
        PreparedStatement statement = con.prepareStatement("SELECT * FROM userdata where UUID ='" + killed.getUniqueId().toString() +"'");
        ResultSet result = statement.executeQuery();
        while(result.next()){
            tokens = result.getInt("tokens");
            deaths = result.getInt("deaths");
        }

    }catch(Exception e){System.out.println(e);}
    stoinserttokens = tokens - .5;
    toinsertdeaths = deaths +1 ;
    try{
        Connection con = plugin.getConnection();
        PreparedStatement posted = con.prepareStatement("INSERT INTO userdata(tokens, UUID) VALUES (',"+ stoinserttokens +", "+ killed.getUniqueId().toString() +"') ON DUPLICATE KEY UPDATE tokens='"+ stoinserttokens +"'");
        posted.executeUpdate();
    }catch(Exception e){}
    try{
        Connection con = plugin.getConnection();
        PreparedStatement posted = con.prepareStatement("INSERT INTO userdata(deaths, UUID) VALUES ('"+ toinsertdeaths+ ", "+ killed.getUniqueId().toString() +"') ON DUPLICATE KEY UPDATE deaths='"+ toinsertdeaths +"'");
        posted.executeUpdate();
    }catch(Exception e){}
}
    public void insertToKiller(){
        try{
            Connection con = plugin.getConnection();
            PreparedStatement statement = con.prepareStatement("SELECT * FROM userdata where UUID ='" + killer.getUniqueId().toString() +"'");
            ResultSet result = statement.executeQuery();
            while(result.next()){
                killerstokens = result.getInt("tokens");
                kills = result.getInt("kills");
            }

        }catch(Exception e){System.out.println(e);}
        toinsertkillerstokens = tokens +1;
        toinsertkillerskills = kills +1 ;
        try{
            Connection con = plugin.getConnection();
            PreparedStatement posted = con.prepareStatement("INSERT INTO userdata(tokens, UUID) VALUES (',"+ toinsertkillerstokens +", "+ killer.getUniqueId().toString() +"') ON DUPLICATE KEY UPDATE tokens='"+ toinsertkillerstokens +"'");
            posted.executeUpdate();
        }catch(Exception e){}
        try{
            Connection con = plugin.getConnection();
            PreparedStatement posted = con.prepareStatement("INSERT INTO userdata(kills, UUID) VALUES ('"+ toinsertkillerskills+ ", "+ killer.getUniqueId().toString() +"') ON DUPLICATE KEY UPDATE kills='"+ toinsertkillerskills +"'");
            posted.executeUpdate();
        }catch(Exception e){}
    }
}
