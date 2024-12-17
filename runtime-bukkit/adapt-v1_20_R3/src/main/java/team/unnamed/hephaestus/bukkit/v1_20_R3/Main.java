package team.unnamed.hephaestus.bukkit.v1_20_R3;

import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import team.unnamed.creative.central.CreativeCentralProvider;
import team.unnamed.creative.central.event.pack.ResourcePackGenerateEvent;

import java.io.File;
import java.io.IOException;

public class Main extends JavaPlugin {
    @Override
    public void onEnable() {
        CreativeCentralProvider.get().eventBus().listen(this, ResourcePackGenerateEvent.class, (event) -> {
            Bukkit.getScheduler().runTaskLater(this, (task) -> {
                File rawFolder = new File(getDataFolder(), "raw");
                File outFolder = new File(getDataFolder(), "../creative-central/output");
                rawFolder.mkdirs();
                System.out.println("Updated raw folder.");
                try {
                    FileUtils.copyDirectory(rawFolder, outFolder);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }, 10L);
        });
    }

    @Override
    public void onDisable() {}
}
