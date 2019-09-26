package leaderos.offlinedepo.secured;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;


public class Main extends JavaPlugin{
	
	private Listeners listeners;
	private AuthmeJoinListener authmejoin;
	private LoginWithoutAuthme withoutAuthme;
	File depo = new File("plugins/LeaderOS-Depo", "depo.yml");
	
	public void onEnable() {
		if(getServer().getPluginManager().getPlugin("WebSender") == null) {
			Bukkit.getServer().getConsoleSender().sendMessage(Main.color("&4Websender eksik olduðundan eklenti durduruldu."));
			getServer().getPluginManager().disablePlugin(this);
		} else {
			if (getServer().getPluginManager().getPlugin("AuthMe") == null) {
				this.listeners = new Listeners(this);
				Bukkit.getPluginManager().registerEvents(this.listeners, this);
				this.withoutAuthme = new LoginWithoutAuthme(this);
				Bukkit.getPluginManager().registerEvents(this.withoutAuthme, this);
				Bukkit.getServer().getConsoleSender().sendMessage(Main.color("&f&lLEADEROS &aAuthme baðlantýsý olmadýðýndan giriþler authmesiz saðlanacak."));
			} else {
				this.listeners = new Listeners(this);
				Bukkit.getPluginManager().registerEvents(this.listeners, this);
				this.authmejoin = new AuthmeJoinListener(this);
				Bukkit.getPluginManager().registerEvents(this.authmejoin, this);
				Bukkit.getServer().getConsoleSender().sendMessage(Main.color("&f&lLEADEROS &aAuthme baðlantýsý saðlandý ve giriþlere baðlandý."));
			}
			metrics();
		}
	}
	
	
	
	public static String color(String yazirengi){return ChatColor.translateAlternateColorCodes('&', yazirengi);}
	
	public void metrics() {
		Metrics metrics = new Metrics(this);
        metrics.addCustomChart(new Metrics.DrilldownPie("java_version", () -> {
        Map<String, Map<String, Integer>> map = new HashMap<>();
        String javaVersion = System.getProperty("java.version");
        Map<String, Integer> entry = new HashMap<>();
        entry.put(javaVersion, 1);
        if (javaVersion.startsWith("1.7")) {
            map.put("Java 1.7", entry);
        } else if (javaVersion.startsWith("1.8")) {
            map.put("Java 1.8", entry);
        } else if (javaVersion.startsWith("1.9")) {
            map.put("Java 1.9", entry);
        } else {
            map.put("Other", entry);
        }
        return map;
        }));
	}
}
