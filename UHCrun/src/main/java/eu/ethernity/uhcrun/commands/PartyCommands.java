package eu.ethernity.uhcrun.commands;

import eu.ethernity.uhcrun.UHCRun;
import eu.ethernity.uhcrun.commands.party.AddPlayerCommand;
import eu.ethernity.uhcrun.configs.MessagesConfig;
import eu.ethernity.uhcrun.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class PartyCommands implements CommandExecutor, TabCompleter {
    private final List<ICommand> subCommands = new ArrayList<>();
    private final UHCRun plugin;

    public PartyCommands(UHCRun plugin) {
        this.plugin = plugin;

        subCommands.add(new AddPlayerCommand());
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (args.length <= 0) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(Utils.tc(MessagesConfig.getConfig().getString("no-permission")));
                return false;
            }

            plugin.getPartyManager().createParty((Player) sender);
            ((Player) sender).sendMessage(Utils.tc(MessagesConfig.getConfig().getString("created-party")));

            return true;
        }

        if(!recognizeCommand(args)) {
            listHelp(sender);
            return false;
        }

        for (ICommand subCommand : subCommands) {
            if (subCommand.getLabel() == null) continue;

            if (subCommand.getLabel().equalsIgnoreCase(args[0])) {
                if (subCommand.getPermission() != null) {
                    if (!sender.hasPermission(subCommand.getPermission())) {
                        sender.sendMessage(Utils.tc(MessagesConfig.getConfig().getString("no-permission")));
                        return false;
                    }
                }

                if (args.length < subCommand.getMinArgs() || args.length > subCommand.getMaxArgs()) {
                    sender.sendMessage(Utils.tc(subCommand.getUsage()));
                    return false;
                }

                subCommand.perform(plugin, sender, args);
                return true;
            }
        }

        sender.sendMessage(Utils.tc(MessagesConfig.getConfig().getString("unknown-command")));
        return true;
    }

    private boolean recognizeCommand(String[] args) {
        for (ICommand subCommand : subCommands)
            if (subCommand.getLabel().equalsIgnoreCase(args[0]))
                return true;
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String s, String[] args) {
        if (args.length > 0) {
            for (ICommand subCommand : subCommands) {
                if (subCommand.getLabel() == null) continue;

                if (subCommand.getLabel().equalsIgnoreCase(args[0])) {
                    if (subCommand.getPermission() != null) {
                        if (!sender.hasPermission(subCommand.getPermission())) {
                            return new ArrayList<>();
                        }
                    }

                    return subCommand.tabComplete(plugin, sender, args);
                }
            }
        }

        List<String> list = new ArrayList<>();

        for (ICommand subCommand : subCommands) {
            if (subCommand.getPermission() == null || sender.hasPermission(subCommand.getPermission())) {
                if (subCommand.getLabel() == null) continue;

                if (subCommand.getLabel().startsWith(args[0])) {
                    list.add(subCommand.getLabel());
                }

                if (args.length == 2) {
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        list.add(player.getDisplayName());
                    }
                }
            }
        }

        return list;
    }

    private void listHelp(CommandSender sender) {
        List<String> helpText = new ArrayList<>();
        helpText.add(MessagesConfig.getConfig().getString("available-commands"));
        for (ICommand command : subCommands) {
            if (sender.hasPermission(command.getPermission()))
                helpText.add("&6 - " + command.getLabel() + " &6- " + command.getDescription());
        }

        for (String text : helpText) {
            sender.sendMessage(Utils.tc(text));
        }
    }
}