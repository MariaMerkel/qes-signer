package dev.maria.qessigner;

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
import eu.europa.esig.dss.service.http.commons.TimestampDataLoader;
import eu.europa.esig.dss.service.ocsp.OnlineOCSPSource;
import eu.europa.esig.dss.service.tsp.OnlineTSPSource;
import eu.europa.esig.dss.spi.validation.CommonCertificateVerifier;
import eu.europa.esig.dss.token.Pkcs11SignatureToken;

public class PAdESUtils {
    public static boolean sign (String pkcs11, boolean pinPad, String pin, String inputFile, String outputFile, String timestampServer, String timestampKeystore, String timestampKeystorePassword) {
        try {
            Pkcs11SignatureToken token = new Pkcs11SignatureToken(
                    pkcs11,
                    () -> pinPad ? null : pin.toCharArray(),
                    -1,
                    0,
                    null);

            CertificateToken certificateToken = token.getKeys().get(0).getCertificate();
            DSSDocument document = new FileDocument(inputFile);

            CommonCertificateVerifier commonCertificateVerifier = new CommonCertificateVerifier();
            commonCertificateVerifier.setCheckRevocationForUntrustedChains(true);
            commonCertificateVerifier.setOcspSource(new OnlineOCSPSource());
            commonCertificateVerifier.setCrlSource(new OnlineCRLSource());

            PAdESService service = new PAdESService(commonCertificateVerifier);

            if (timestampServer != null) {
                OnlineTSPSource tsp = new OnlineTSPSource(timestampServer);
                TimestampDataLoader tspDataLoader = new TimestampDataLoader();
                if (timestampKeystore != null) {
                    tspDataLoader.setSslKeystore(new FileDocument(timestampKeystore));
                    tspDataLoader.setSslKeystorePassword(timestampKeystorePassword.toCharArray());
                }
                tsp.setDataLoader(tspDataLoader);
                service.setTspSource(tsp);
            }

            PAdESSignatureParameters parameters = new PAdESSignatureParameters();
            parameters.setDigestAlgorithm(DigestAlgorithm.SHA256);
            parameters.setSigningCertificate(certificateToken);
            parameters.setAppName("qes-signer.maria.dev");

            if (timestampServer != null) {
                parameters.setSignatureLevel(SignatureLevel.PAdES_BASELINE_LTA);
            } else {
                parameters.setSignatureLevel(SignatureLevel.PAdES_BASELINE_B);
            }

            ToBeSigned dataToSign = service.getDataToSign(document, parameters);
            SignatureValue value = token.sign(dataToSign, parameters.getDigestAlgorithm(), token.getKeys().get(0));
            DSSDocument signedDocument = service.signDocument(document, parameters, value);

            signedDocument.save(outputFile);
            return true;
        } catch (Exception e) {
            System.err.println("Error signing document: " + e.getMessage());
            return false;
        }
    }
}
