package dev.maria.qessigner;

import dev.maria.qessigner.commands.ResetDefaultsCommand;
import dev.maria.qessigner.commands.SetDefaultsCommand;
import dev.maria.qessigner.commands.SignCommand;
import picocli.CommandLine;

import java.io.IOException;
import java.security.*;

public class Main {
    public static void main(String[] args) throws IOException {
        if (args.length > 0) {
            String[] argsWithoutCommand = new String[args.length - 1];
            System.arraycopy(args, 1, argsWithoutCommand, 0, args.length - 1);

            if (args[0].equals("sign")) System.exit(new CommandLine(new SignCommand()).execute(argsWithoutCommand));
            else if (args[0].equals("set-defaults")) System.exit(new CommandLine(new SetDefaultsCommand()).execute(argsWithoutCommand));
            else if (args[0].equals("reset-defaults")) System.exit(new CommandLine(new ResetDefaultsCommand()).execute(argsWithoutCommand));
            else System.err.println("Invalid command. Use 'sign', 'set-defaults' or 'reset-defaults'.");
        } else {
            System.err.println("Please enter a command. Use 'sign', 'set-defaults' or 'reset-defaults'.");
        }
    }
}