package eu.ethernity.uhcrun.commands.setup;

import eu.ethernity.uhcrun.UHCRun;
import eu.ethernity.uhcrun.commands.ICommand;
import eu.ethernity.uhcrun.configs.MessagesConfig;
import eu.ethernity.uhcrun.map.Map;
import eu.ethernity.uhcrun.utils.Utils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class SetTeamSizeCommand implements ICommand {
    @Override
    public String getLabel() {
        return "teamsize";
    }

    @Override
    public String getUsage() {
        return "/uhcrun teamsize <game> <size>";
    }

    @Override
    public String getPermission() {
        return "uhcrun.admin.teamsize";
    }

    @Override
    public String getDescription() {
        return "Sets team size for game";
    }

    @Override
    public Integer getMinArgs() {
        return 3;
    }

    @Override
    public Integer getMaxArgs() {
        return 3;
    }

    @Override
    public boolean perform(UHCRun plugin, CommandSender sender, String[] args) {
        Player player = (Player) sender;
        if(plugin.getMapManager().getMap(args[1]) == null) {
            player.sendMessage(Utils.tc(MessagesConfig.getConfig().getString("invalid-map")));
            return false;
        }
        if(Integer.parseInt(args[3]) > 5){
            player.sendMessage(Utils.tc(MessagesConfig.getConfig().getString("too-big-team-size")));
            return false;
        }

        plugin.getMapManager().getMap(args[1]).setTeamSize(Integer.parseInt(args[2]));
        player.sendMessage(Utils.tc(MessagesConfig.getConfig().getString("team-size-changed").replace("<size>", args[3])));
        return true;
    }

    @Override
    public List<String> tabComplete(UHCRun plugin, CommandSender sender, String[] args) {
        List<String> tabComplete = new ArrayList<>();
        for(Map map : plugin.getMapManager().getMaps())
            tabComplete.add(map.getName());
        return tabComplete;
    }
}
