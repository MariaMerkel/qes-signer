package dev.maria.qessigner.commands;

import dev.maria.qessigner.Main;
import picocli.CommandLine;

import java.util.concurrent.Callable;
import java.util.prefs.Preferences;

@CommandLine.Command (name = "reset-defaults", description = "Reset default options")
public class ResetDefaultsCommand implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {
        Preferences prefs = Preferences.userNodeForPackage(Main.class);
        prefs.clear();

        return 0;
    }
}
