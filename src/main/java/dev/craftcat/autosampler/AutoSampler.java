package dev.craftcat.autosampler;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameStoppedServerEvent;
import org.spongepowered.api.plugin.Dependency;
import org.spongepowered.api.plugin.Plugin;

@Plugin(
        id = "autosampler",
        name = "AutoSampler",
        description = "A mod that autosamples when tps is low",
        authors = {
                "Sardine123"
        }
)
public class AutoSampler {

    @Inject
    private Logger logger;

    @Listener
    public void onServerStart(GameStartedServerEvent event) {
    }

    @Listener
    public void onServerStop(GameStoppedServerEvent event) {

    }
}
