package mineclass.mineclass.callbacks;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.server.network.ServerPlayerEntity;

public final class ServerPlayerEvents {
    /**
     * An event that is called when the data from an old player is copied to a new player.
     *
     * <p>This event is typically called before a player is completely respawned.
     * Mods may use this event to copy old player data to a new player.
     */
    public static final Event<ServerPlayerEvents.CopyFrom> COPY_FROM = EventFactory.createArrayBacked(ServerPlayerEvents.CopyFrom.class, callbacks -> (oldPlayer, newPlayer, alive) -> {
        for (CopyFrom callback : callbacks) {
            callback.copyFromPlayer(oldPlayer, newPlayer, alive);
        }
    });

    /**
     * An event that is called after a player has been respawned.
     *
     * <p>Mods may use this event for reference clean up on the old player.
     */
    public static final Event<ServerPlayerEvents.AfterRespawn> AFTER_RESPAWN = EventFactory.createArrayBacked(ServerPlayerEvents.AfterRespawn.class, callbacks -> (oldPlayer, newPlayer, alive) -> {
        for (AfterRespawn callback : callbacks) {
            callback.afterRespawn(oldPlayer, newPlayer, alive);
        }
    });

    /**
     * An event that is called when a player has first joined a Minecraft server.
     * A player is considered to be first joining if the player has no existing player data.
     */
    public static final Event<ServerPlayerEvents.FirstJoin> FIRST_JOIN = EventFactory.createArrayBacked(ServerPlayerEvents.FirstJoin.class, callbacks -> player -> {
        for (FirstJoin callback : callbacks) {
            callback.firstJoined(player);
        }
    });

    private ServerPlayerEvents() {
    }

    @FunctionalInterface
    public interface CopyFrom {
        /**
         * Called when player data is copied to a new player.
         *
         * @param oldPlayer the old player
         * @param newPlayer the new player
         * @param alive     whether the old player is still alive
         */
        void copyFromPlayer(ServerPlayerEntity oldPlayer, ServerPlayerEntity newPlayer, boolean alive);
    }

    @FunctionalInterface
    public interface AfterRespawn {
        /**
         * Called after player a has been respawned.
         *
         * @param oldPlayer the old player
         * @param newPlayer the new player
         * @param alive     whether the old player is still alive
         */
        void afterRespawn(ServerPlayerEntity oldPlayer, ServerPlayerEntity newPlayer, boolean alive);
    }

    @FunctionalInterface
    public interface FirstJoin {
        void firstJoined(ServerPlayerEntity player);
    }
}