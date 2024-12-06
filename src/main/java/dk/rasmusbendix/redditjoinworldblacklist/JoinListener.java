package dk.rasmusbendix.redditjoinworldblacklist;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class JoinListener implements Listener {

    private final FileConfiguration config;
    private final JavaPlugin plugin;

    public JoinListener(RedditJoinWorldBlacklistPlugin plugin) {
        this.config = plugin.getConfig();
        this.plugin = plugin;
    }

    @EventHandler
    public void playerJoinEvent(PlayerJoinEvent e) {

        if(!config.getBoolean("use-delay")) {
            teleportToSpawnIfBlacklistedWorld(e.getPlayer());
            return;
        }

        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            teleportToSpawnIfBlacklistedWorld(e.getPlayer());
        }, config.getLong("delay-in-ticks"));

    }

    private void teleportToSpawnIfBlacklistedWorld(Player player) {
        if(isInBlacklistedWorld(player))
            teleportToSpawn(player);
    }

    private boolean isInBlacklistedWorld(Player player) {

        for(String world : config.getStringList("blacklisted-worlds")) {
            if(player.getWorld().getName().equalsIgnoreCase(world)) {
                return true;
            }
        }

        return false;

    }

    private void teleportToSpawn(Player player) {
        String spawnWorld = config.getString("spawn-world", "world");
        World world = Bukkit.getWorld(spawnWorld);
        if(world == null) {
            plugin.getLogger().warning("Failed to find spawn world! " + spawnWorld + " does not exist.");
            return;
        }

        player.teleport(world.getSpawnLocation());

    }

}
