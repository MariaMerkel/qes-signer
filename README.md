# QES Signer
QES Signer is a cross-platform command line tool for signing PDF files using EU-compliant advanced and qualified electronic signatures.

It requires the signing certificate to be installed on a token with a PKCS#11 driver available. It supports both PIN pad and software PIN entry.

If a timestamping server is specified, signatures are created to the PAdES-LTA standard for long-time archival capabilities. If no timestamping server is specified, signatures are created to the PAdES-B standard.

The installers are built using a free license kindly provided by the [multi-platform installer builder install4j](https://www.ej-technologies.com/install4j).

## Signing Files

To sign a document, use the following command:

```bash
sign [--pinpad] [-p=<pkcs11>] [--pin=<pin>] [-t=<timestampServer>]
            [-tk=<timestampKeystore>] [-tkp=<timestampKeystorePassword>]
            <inputFile> <outputFile>

      <inputFile>         Input file
      <outputFile>        Output file
  -p, --pkcs11=<pkcs11>   PKCS#11 module path
      --pin=<pin>         Smart Card PIN
      --pinpad            Use Smart Card Reader PIN Pad
  -t, --timestamp-server=<timestampServer>
                          Timestamp server URL
      -tk, --timestamp-keystore=<timestampKeystore>
                          Timestamp keystore path
      -tkp, --timestamp-keystore-password=<timestampKeystorePassword>
                          Timestamp keystore password
```

The keystore can be any format supported by Java, such as PKCS#12.

## Storing defaults
You can store details for sign command options so you don't need to specify recurring options such as the PKCS#11 module path.

To do this, use the following command:

```bash
set-defaults [--pinpad] [-p=<pkcs11>] [--pin=<pin>]
                    [-t=<timestampServer>] [-tk=<timestampKeystore>]
                    [-tkp=<timestampKeystorePassword>]
  -p, --pkcs11=<pkcs11>   PKCS#11 module path
      --pin=<pin>         Smart Card PIN
      --pinpad            Use Smart Card Reader PIN Pad
  -t, --timestamp-server=<timestampServer>
                          Timestamp server URL
      -tk, --timestamp-keystore=<timestampKeystore>
                          Timestamp keystore path
      -tkp, --timestamp-keystore-password=<timestampKeystorePassword>
                          Timestamp keystore password
```

## Reset defaults
To zero all defaults, use the following command:

```bash
reset-defaults
```
