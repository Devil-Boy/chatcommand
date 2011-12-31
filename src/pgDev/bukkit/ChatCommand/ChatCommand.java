package pgDev.bukkit.ChatCommand;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.SayCommand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;

public class ChatCommand extends JavaPlugin {
	
	// Permissions support
    static PermissionHandler Permissions;
	
	public void onEnable() {
		// Legacy Permissions set up
		setupPermissions();
		
        // Tell Mr. Console that we're alive
        PluginDescriptionFile pdfFile = this.getDescription();
        System.out.println(pdfFile.getName() + " version " + pdfFile.getVersion() + " is enabled!");
	}
	
	public void onDisable() {
		// We're dead! :(
		System.out.println("ChatCommand disabled!");
	}
	
	// Permissions Methods
    private void setupPermissions() {
        Plugin permissions = this.getServer().getPluginManager().getPlugin("Permissions");

        if (Permissions == null) {
            if (permissions != null) {
                Permissions = ((Permissions)permissions).getHandler();
            } else {
            }
        }
    }
    
    public static boolean hasPermissions(Player player, String node) {
        if (Permissions != null) {
        	return Permissions.has(player, node);
        } else {
            return player.hasPermission(node);
        }
    }
    
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    	if (args.length != 0) { // Make sure there are arguments
	    	if (sender instanceof Player) { // Player ran the command
	    		Player player = (Player) sender;
	    		if (hasPermissions(player, "ChatCommand.use")) {
					String sayMessage = "";
					for (String word : args) {
						if (sayMessage.equals("")) {
							sayMessage = word;
						} else {
							sayMessage = sayMessage + " " + word;
						}
					}
					player.chat(sayMessage);
	    		} else {
	    			player.sendMessage(ChatColor.RED + "YOu do not have the permission required to run this command.");
	    		}
			} else { // Console ran it
				(new SayCommand()).execute(sender, label, args);
			}
	    	return true;
    	} else {
    		return false;
    	}
    }
}
