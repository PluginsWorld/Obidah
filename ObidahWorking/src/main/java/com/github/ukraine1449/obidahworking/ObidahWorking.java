package com.github.ukraine1449.obidahworking;

import com.github.ukraine1449.obidahworking.Events.PlayerDeathEvent;
import com.github.ukraine1449.obidahworking.Events.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public final class ObidahWorking extends JavaPlugin {

    public Connection getConnection() throws Exception{
        String ip = getConfig().getString("ip");
        String password = getConfig().getString("password");
        String username = getConfig().getString("username");
        String dbn = getConfig().getString("database name");
        try{
            String driver = "com.mysql.jdbc.Driver";
            String url = "jdbc:mysql://"+ ip + ":3306/" + dbn;
            System.out.println(url);
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(url, username, password);
            System.out.println("Connected");
            return conn;
        }catch(Exception e){}
        return null;
    }
    public void createTable()throws Exception{
        try{
            Connection con = getConnection();
            PreparedStatement create = con.prepareStatement("CREATE TABLE IF NOT EXISTS userdata(UUID varchar(255), kills int, deaths int,tokens double, PRIMARY KEY (UUID))");
            create.executeUpdate();

        }catch(Exception e){}
    }

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new PlayerDeathEvent(this), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinEvent(this), this);
        try {
            createTable();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
