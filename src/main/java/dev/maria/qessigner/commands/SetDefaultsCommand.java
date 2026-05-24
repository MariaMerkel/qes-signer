package dev.maria.qessigner.commands;

import dev.maria.qessigner.Main;
import picocli.CommandLine;

import java.util.concurrent.Callable;
import java.util.prefs.Preferences;

@CommandLine.Command (name = "set-defaults", description = "Set default options")
public class SetDefaultsCommand implements Callable<Integer> {
    @CommandLine.Option(names = {"-p", "--pkcs11"}, description = "PKCS#11 module path")
    private String pkcs11;

    @CommandLine.Option(names = {"--pin"}, description = "Smart Card PIN")
    private String pin;

    @CommandLine.Option(names = {"--pinpad"}, description = "Use Smart Card Reader PIN Pad")
    private boolean pinPad;

    @CommandLine.Option(names = {"-t", "--timestamp-server"}, description = "Timestamp server URL")
    private String timestampServer;

    @CommandLine.Option(names = {"-tk", "--timestamp-keystore"}, description = "Timestamp keystore path")
    private String timestampKeystore;

    @CommandLine.Option(names = {"-tkp", "--timestamp-keystore-password"}, description = "Timestamp keystore password")
    private String timestampKeystorePassword;

    @Override
    public Integer call() throws Exception {
        Preferences prefs = Preferences.userNodeForPackage(Main.class);
        if (pkcs11 != null) prefs.put("pkcs11", pkcs11);
        if (pin != null) prefs.put("pin", pin);
        if (pinPad) prefs.putBoolean("pinPad", true);
        if (timestampServer != null) prefs.put("timestampServer", timestampServer);
        if (timestampKeystore != null) prefs.put("timestampKeystore", timestampKeystore);
        if (timestampKeystorePassword != null) prefs.put("timestampKeystorePassword", timestampKeystorePassword);

        return 0;
    }
}
