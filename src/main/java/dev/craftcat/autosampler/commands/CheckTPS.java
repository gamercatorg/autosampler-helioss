package dev.craftcat.autosampler.commands;

import java.time.Instant;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;

import club.minnced.discord.webhook.WebhookClient;
import club.minnced.discord.webhook.WebhookClientBuilder;
import club.minnced.discord.webhook.send.WebhookEmbed;
import club.minnced.discord.webhook.send.WebhookEmbedBuilder;

public class CheckTPS implements CommandExecutor {

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {

        double tps = Sponge.getServer().getTicksPerSecond();

        String serverName = args.<String>getOne("server").get();

        WebhookClientBuilder builder = new WebhookClientBuilder("https://discord.com/api/webhooks/731478994347622430/sWyBTPaKQ8OsCEnoQp6kPF4lql7_wldtk3z2EQQ5EmcM-83YbCWpHKxf_4wvvcTBt3yK"); // or id, token
        builder.setThreadFactory((job) -> {
            Thread thread = new Thread(job);
            thread.setName("NPS upload");
            thread.setDaemon(true);
            return thread;
        });
        builder.setWait(true);
        WebhookClient client = builder.build();

        char whatToDo = 'i';

        if (tps < 5.0) {
            Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "say LAG HAS BEEN DETECTED... FIXING!");
        }

        while (tps < 5.0) {

            switch (whatToDo) {
                case 'i':
                    Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "cofh killall item");
                    whatToDo = 'k';
                case 'k':
                    Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "cofh killall");
                    whatToDo = 'b';
                case 'b':
                    Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "cofh killall bat");
                    whatToDo = 's';
                case 's':
                    Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "sampler start");
                    try {
                        Thread.sleep(30000);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "sampler stop");
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }

                    String exportTime = Instant.now().toString();
                    Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "sampler export " + exportTime + "-autosampled");

                    WebhookEmbed embed = new WebhookEmbedBuilder()
                        .setColor(0x10DB09)
                        .setDescription("An NPS file has been exported in server " + serverName + ". File name: " + exportTime + "-autosampled.nps")
                        .build();

                    client.send(embed)
                        .thenAccept((message) -> System.out.printf("Message with embed has been sent [%s]%n", message.getId()));



            }

            

            



        }
        
        return CommandResult.empty();

    }
    
}