package eu.ethernity.uhcrun.players.party;

import eu.ethernity.uhcrun.UHCRun;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class PartyManager {

    private final UHCRun plugin;
    private final List<Party> parties = new ArrayList<>();

    public PartyManager(UHCRun plugin) {
        this.plugin = plugin;
    }

    public void createParty(Player player) {
        parties.add(new Party(player));
    }

    public void addToParty(String name, Player player) {
        for(Party party : parties)
            if(party.getName().equals(name)) {
                party.addToParty(player);
                return;
            }
    }

    public Party getParty(Player player) {
        for(Party party : parties)
            if(party.getName().equals(player.getName()))
                return party;
        return null;
    }
}
