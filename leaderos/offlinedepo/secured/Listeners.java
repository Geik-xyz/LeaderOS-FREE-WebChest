package leaderos.offlinedepo.secured;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import craftrise.events.BukkitSocketOnCommandEvent;

public class Listeners implements Listener{
	
	private Main plugin;
	public Listeners(Main plugin) {
		this.plugin = plugin;
	}
	
	
	@EventHandler
	public void onCommandEvent(BukkitSocketOnCommandEvent e) throws IOException {
		File g = new File("plugins/LeaderOS-Depo/depo.yml");
	  	FileConfiguration d = YamlConfiguration.loadConfiguration(g);
	  	String[] newPi = e.getCommand().split(" ");
	  	String pname;
	  	boolean onlineCheck = false;
	  	if (e.getCommand().startsWith("leaderos")) {
	  		pname = newPi[1];
	  		e.setCancelled(true);
	  		for (Player p : Bukkit.getOnlinePlayers()) {
	  			if (p.getName().equalsIgnoreCase(newPi[1])) {
	  				newPi = removeElements(newPi, newPi[0]);
	  				newPi = removeElements(newPi, newPi[0]);
	  				String newCMD = convertArrayToStringUsingStreamAPI(newPi).replace("%p%", p.getName());
	  				Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {public void run() {Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), newCMD);}},1);
	  				onlineCheck = true;
	  				Bukkit.getServer().getConsoleSender().sendMessage(Main.color("&b&lLEADEROS &c" + pname + " &aBir ürün satýn aldý ve teslim edildi."));
	  				if (plugin.getConfig().getBoolean("debug") == true)  Bukkit.getServer().getConsoleSender().sendMessage(Main.color("&b&lLEADEROS-&fDEBUG &aKomut: &d" + newCMD ));
	  				break;
	  			} else onlineCheck = false;
	  		}
	  		if (onlineCheck == false) {
	  			if (d.contains(pname)) {
	  				List<String> cmd = d.getStringList(pname + ".commands");
	  				newPi = removeElements(newPi, newPi[0]);
	  				newPi = removeElements(newPi, newPi[0]);
	  				String newCMD = convertArrayToStringUsingStreamAPI(newPi).replace("%p%", pname);
	  				cmd.add(newCMD);
	  				d.set(pname + ".commands", cmd);
	  				d.save(g);
	  				Bukkit.getServer().getConsoleSender().sendMessage(Main.color("&b&lLEADEROS &c" + pname + " &adepoya yeni bir ürün kayýt etti."));
	  				if (plugin.getConfig().getBoolean("debug") == true) Bukkit.getServer().getConsoleSender().sendMessage(Main.color("&b&lLEADEROS-&fDEBUG &aKomut: &d" + newCMD ));
	  				
	  			} else {
	  				List<String> cmd = new ArrayList<String>();
	  				newPi = removeElements(newPi, newPi[0]);
	  				newPi = removeElements(newPi, newPi[0]);
	  				String newCMD = convertArrayToStringUsingStreamAPI(newPi).replace("%p%", pname);
	  				cmd.add(newCMD);
	  				d.set(pname + ".commands", cmd);
	  				d.save(g);
	  				Bukkit.getServer().getConsoleSender().sendMessage(Main.color("&b&lLEADEROS &c" + pname + " &aOffline olduðu için depoya yeni kayýt açýlýyor."));
	  				if (plugin.getConfig().getBoolean("debug") == true) Bukkit.getServer().getConsoleSender().sendMessage(Main.color("&b&lLEADEROS-&fDEBUG &aKomut: &d" + newCMD ));
	  			}
	  		}
	  	}
		else return;
	}
	
	public static String convertArrayToStringUsingStreamAPI(String[] strArray) {
        String joinedString = String.join(" ", strArray);
        return joinedString;
    }
	public static String[] removeElements(String[] arr, String key) 
    { 
          int index = 0; 
          for (int i=0; i<arr.length; i++) 
             if (arr[i] != key) 
                arr[index++] = arr[i]; 
         return Arrays.copyOf(arr, index); 
    }
	
	

}
