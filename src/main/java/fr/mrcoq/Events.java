package fr.mrcoq;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerEvent;

public class Events implements Listener {


    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        Client client = Plugin.getInstance().getClient();

        client.sendMessage(Client.LogType.CHAT, e.getPlayer().getName() + " > " + e.getMessage());
    }

    @EventHandler
    public void onBreakBlock(BlockBreakEvent e) {
        Client client = Plugin.getInstance().getClient();

        Location loc = e.getBlock().getLocation();
        client.sendMessage(Client.LogType.EVENT, e.getPlayer().getName() + " break block " + e.getBlock().getType() + " at x=" + loc.getBlockX() + " y=" + loc.getBlockY() + " z=" + loc.getBlockZ() );
    }

    @EventHandler
    public void onBreakBlock(BlockPlaceEvent e) {
        Client client = Plugin.getInstance().getClient();

        Location loc = e.getBlock().getLocation();
        client.sendMessage(Client.LogType.EVENT, e.getPlayer().getName() + " place block " + e.getBlock().getType() + " at x=" + loc.getBlockX() + " y=" + loc.getBlockY() + " z=" + loc.getBlockZ() );
    }

}
