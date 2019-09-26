package leaderos.offlinedepo.secured;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import fr.xephi.authme.api.v3.AuthMeApi;
import fr.xephi.authme.events.LoginEvent;

public class AuthmeJoinListener implements Listener {
	
	private Main plugin;
	public AuthmeJoinListener(Main plugin) {
		this.plugin = plugin;
	}
	AuthMeApi authmeApi = AuthMeApi.getInstance();
	
	@EventHandler
	public void authmeLogin(LoginEvent e) throws IOException {
		Player player = e.getPlayer();
		File g = new File("plugins/LeaderOS-Depo/depo.yml");
	  	FileConfiguration d = YamlConfiguration.loadConfiguration(g);
		if (d.contains(player.getName())) {
			List<String> cmd = d.getStringList(player.getName() + ".commands");
			for (String cmdn : cmd) {
				Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), cmdn);
				if (plugin.getConfig().getBoolean("debug") == true) Bukkit.getServer().getConsoleSender().sendMessage(Main.color("&b&lLEADEROS-&fDEBUG &aKomut: &d" + cmdn ));
			}
			Bukkit.getServer().getConsoleSender().sendMessage(Main.color("&b&lLEADEROS &c" + player.getName() + " &aGiriþ yaptýðýndan depodaki ürünleri teslim edildi."));
			d.set(player.getName(), null);
			
			d.save(g);
			
		}
	}

}
