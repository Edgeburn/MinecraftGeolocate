package com.edgeburnmedia.ipapi.ipapi;

import org.bukkit.plugin.java.JavaPlugin;

public final class IpApi extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getCommand("geolocate").setExecutor(new GeolocateCommand());
        getCommand("locateip").setExecutor(new LocateIpCommand());

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
