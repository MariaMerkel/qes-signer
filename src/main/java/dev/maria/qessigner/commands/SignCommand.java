package dev.maria.qessigner.commands;

import dev.maria.qessigner.Main;
import dev.maria.qessigner.PAdESUtils;
import picocli.CommandLine;

import java.util.concurrent.Callable;
import java.util.prefs.Preferences;

@CommandLine.Command (name = "sign", description = "Sign a document")
public class SignCommand implements Callable<Integer> {
    @CommandLine.Option(names = {"-p", "--pkcs11"}, description = "PKCS#11 module path")
    private String pkcs11;

    @CommandLine.Parameters(index = "0", description = "Input file")
    private String inputFile;

    @CommandLine.Parameters(index = "1", description = "Output file")
    private String outputFile;

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

        if (pkcs11 == null) pkcs11 = prefs.get("pkcs11", null);
        if (pin == null) pin = prefs.get("pin", null);
        if (!pinPad) pinPad = prefs.getBoolean("pinPad", false);
        if (timestampServer == null) timestampServer = prefs.get("timestampServer", null);
        if (timestampKeystore == null) timestampKeystore = prefs.get("timestampKeystore", null);
        if (timestampKeystorePassword == null) timestampKeystorePassword = prefs.get("timestampKeystorePassword", null);

        if (pkcs11 == null) {
            System.err.println("Error: PKCS#11 module path is required");
            return 1;
        }

        if (timestampKeystore != null && timestampKeystorePassword == null) {
            System.err.println("Error: Timestamp keystore password is required");
            return 1;
        }

        if (!pinPad && pin == null) {
            System.out.print("Enter PIN: ");
            pin = new String(System.console().readPassword());
        }

        return PAdESUtils.sign(pkcs11, pinPad, pin, inputFile, outputFile, timestampServer, timestampKeystore, timestampKeystorePassword) ? 0 : 1;
    }
}
