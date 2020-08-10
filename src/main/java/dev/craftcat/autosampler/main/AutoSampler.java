package dev.craftcat.autosampler.main;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameStoppedServerEvent;
import org.spongepowered.api.plugin.Dependency;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.text.Text;

import dev.craftcat.autosampler.commands.CheckTPS;



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

    private static AutoSampler instance;

    @Listener
    public void onServerStart(GameStartedServerEvent event) {
        /* Setting the instance */
        instance = this;
        logger.info("AutoSampler has started!");
        
    }

    @Listener
    public void init(GameInitializationEvent event) {
        createAndRegisterCommands();
    }

    @Listener
    public void onServerStop(GameStoppedServerEvent event) {
        logger.info("AutoSampler has stopped!");

    }

    public static AutoSampler getInstance(){
        return instance;
    }

    private void createAndRegisterCommands() {
        CommandSpec checktpsSpec = CommandSpec.builder()
            .description(Text.of("Server command to automatically check tps"))
            .permission("autosampler.sample")
            .arguments(
                GenericArguments.remainingJoinedStrings(Text.of("server"))
            )
            .executor(new CheckTPS())
            .build();

        Sponge.getCommandManager().register(this, checktpsSpec, "triggerchecktps", "tct");

    }

}
