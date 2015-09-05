package com.leontg77.timer.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.leontg77.timer.Main;

/**
 * Player utilities class
 * <p>
 * Contains methods for sending an packet to a player and getting the NMS class.
 * 
 * @author LeonTG77
 */
public class PacketUtils {
	
	/**
	 * Send the given packet to the given player.
	 * 
	 * @param player the player sending it to.
	 * @param packet the packet being sent.
	 */
	public static void sendPacket(Player player, Object packet) {
        try {
        	Object handle = player.getClass().getMethod("getHandle").invoke(player);
        	Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
        	playerConnection.getClass().getMethod("sendPacket", getNMSClass("Packet")).invoke(playerConnection, packet);
        } catch (Exception e) {
			Main.plugin.getLogger().severe(ChatColor.RED + "Could not send packet to " + player.getName() + ", are you using 1.8 or higher?");
        }
	}

	/**
	 * Gets the NMS class for the given name.
	 * 
	 * @param name the name getting.
	 * @return The NMS class.
	 */
	public static Class<?> getNMSClass(String name) {
        String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
        
        try {
        	return Class.forName("net.minecraft.server." + version + "." + name);
        } catch (ClassNotFoundException e) {
			Main.plugin.getLogger().severe(ChatColor.RED + "Could not get action bar packet class, are you using 1.8 or higher?");
        	return null;
        }
	}
}