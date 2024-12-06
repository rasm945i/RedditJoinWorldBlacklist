package dk.rasmusbendix.redditjoinworldblacklist;

import org.bukkit.plugin.java.JavaPlugin;

public final class RedditJoinWorldBlacklistPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        saveDefaultConfig();
        getServer().getPluginManager().registerEvents(new JoinListener(this), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
