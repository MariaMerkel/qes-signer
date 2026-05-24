package dev.maria.qessigner;

import dev.maria.qessigner.commands.ResetDefaultsCommand;
import dev.maria.qessigner.commands.SetDefaultsCommand;
import dev.maria.qessigner.commands.SignCommand;
import eu.europa.esig.dss.enumerations.DigestAlgorithm;
import eu.europa.esig.dss.enumerations.SignatureLevel;
import eu.europa.esig.dss.model.DSSDocument;
import eu.europa.esig.dss.model.FileDocument;
import eu.europa.esig.dss.model.SignatureValue;
import eu.europa.esig.dss.model.ToBeSigned;
import eu.europa.esig.dss.model.x509.CertificateToken;
import eu.europa.esig.dss.pades.PAdESSignatureParameters;
import eu.europa.esig.dss.pades.signature.PAdESService;
import eu.europa.esig.dss.service.crl.OnlineCRLSource;
import eu.europa.esig.dss.service.http.commons.CommonsDataLoader;
import eu.europa.esig.dss.service.http.commons.TimestampDataLoader;
import eu.europa.esig.dss.service.ocsp.OnlineOCSPSource;
import eu.europa.esig.dss.service.tsp.OnlineTSPSource;
import eu.europa.esig.dss.spi.DSSUtils;
import eu.europa.esig.dss.spi.client.http.DataLoader;
import eu.europa.esig.dss.spi.validation.CommonCertificateVerifier;
import eu.europa.esig.dss.token.Pkcs11SignatureToken;
import org.apache.hc.client5.http.ssl.TrustAllStrategy;
import org.apache.hc.core5.ssl.TrustStrategy;
import picocli.CommandLine;

import java.io.IOException;
import java.security.*;
import java.util.List;

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