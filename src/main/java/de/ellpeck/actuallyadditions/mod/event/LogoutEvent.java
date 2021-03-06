/*
 * This file ("LogoutEvent.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import de.ellpeck.actuallyadditions.mod.items.ItemWingsOfTheBats;

public class LogoutEvent{

    @SubscribeEvent
    public void onLogOutEvent(PlayerEvent.PlayerLoggedOutEvent event){
        //Remove Player from Wings' Fly Permission List
        ItemWingsOfTheBats.removeWingsFromPlayer(event.player, true);
        ItemWingsOfTheBats.removeWingsFromPlayer(event.player, false);
    }

}
