package eu.ethernity.uhcrun.commands.party;

import eu.ethernity.uhcrun.UHCRun;
import eu.ethernity.uhcrun.commands.ICommand;
import org.bukkit.command.CommandSender;

import java.util.List;

public class AddPlayerCommand implements ICommand {
    @Override
    public String getLabel() {
        return null; //TODO: add player
    }

    @Override
    public String getAlias() {
        return null;
    }

    @Override
    public String getUsage() {
        return null;
    }

    @Override
    public String getPermission() {
        return null;
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public Integer getMinArgs() {
        return null;
    }

    @Override
    public Integer getMaxArgs() {
        return null;
    }

    @Override
    public boolean perform(UHCRun plugin, CommandSender sender, String[] args) {
        return false;
    }

    @Override
    public List<String> tabComplete(UHCRun plugin, CommandSender sender, String[] args) {
        return null;
    }
}
