package com.sample.pdfdigitalsign;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PublicKey;
import java.security.SignatureException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.Collection;
import org.apache.pdfbox.cos.COSArray;
import org.apache.pdfbox.cos.COSBase;

import org.apache.pdfbox.cos.COSDictionary;
import org.apache.pdfbox.cos.COSInputStream;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.cos.COSObject;
import org.apache.pdfbox.cos.COSStream;
import org.apache.pdfbox.cos.COSString;
import org.apache.pdfbox.io.IOUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.interactive.digitalsignature.PDSignature;
import org.apache.pdfbox.util.Hex;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cms.CMSException;
import org.bouncycastle.cms.CMSProcessable;
import org.bouncycastle.cms.CMSProcessableByteArray;
import org.bouncycastle.cms.CMSSignedData;
import org.bouncycastle.cms.SignerInformation;
import org.bouncycastle.cms.jcajce.JcaSimpleSignerInfoVerifierBuilder;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.tsp.TSPException;
import org.bouncycastle.tsp.TimeStampToken;
import org.bouncycastle.util.Selector;
import org.bouncycastle.util.Store;
import org.bouncycastle.util.StoreException;

public final class DigitalSignVerify
{
    private final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
    
    private DigitalSignVerify()
    {
    }
    
    public static void main(String[] args) throws IOException, CertificateException,
                                                  NoSuchAlgorithmException,
                                                  NoSuchProviderException,
                                                  TSPException
    {
        DigitalSignVerify show = new DigitalSignVerify();
        show.DigitalSignVerify( args );
    }

    private void DigitalSignVerify(String[] args) throws IOException, CertificateException,
                                                     NoSuchAlgorithmException,
                                                     NoSuchProviderException,
                                                     TSPException
    {
        if( args.length != 2 )
        {
            System.out.println("enter the args correctly");
        }
        else
        {
        	// enter the pass phrase
            String password = args[0];
            //enter the input file to be verified
            File infile = new File(args[1]);
            PDDocument document = null;
            try
            {
                document = PDDocument.load(infile, password);
                for (PDSignature sig : document.getSignatureDictionaries())
                {
                    COSDictionary sigDict = sig.getCOSObject();
                    COSString contents = (COSString) sigDict.getDictionaryObject(COSName.CONTENTS);

                    // download the signed content
                    FileInputStream fis = new FileInputStream(infile);
                    byte[] buf = null;
                    try
                    {
                        buf = sig.getSignedContent(fis);
                    }
                    finally
                    {
                        fis.close();
                    }

                    System.out.println("Signature found");

                    int[] byteRange = sig.getByteRange();
                    if (byteRange.length != 4)
                    {
                        System.err.println("Signature byteRange must have 4 items");
                    }
                    else
                    {
                        long fileLen = infile.length();
                        long rangeMax = byteRange[2] + (long) byteRange[3];
                        // multiply content length with 2 (because it is in hex in the PDF) and add 2 for < and >
                        int contentLen = contents.getString().length() * 2 + 2;
                        if (fileLen != rangeMax || byteRange[0] != 0 || byteRange[1] + contentLen != byteRange[2])
                        {
                            // a false result doesn't necessarily mean that the PDF is a fake
                            // see this answer why:
                            // https://stackoverflow.com/a/48185913/535646
                            System.out.println("Signature does not cover whole document");
                        }
                        else
                        {
                            System.out.println("Signature covers whole document");
                        }
                    }

                    if (sig.getName() != null)
                    {
                        System.out.println("Name:     " + sig.getName());
                    }
                    if (sig.getSignDate() != null)
                    {
                        System.out.println("Modified: " + DATE_FORMAT.format(sig.getSignDate().getTime()));
                    }
                    String subFilter = sig.getSubFilter();
                    if (subFilter != null)
                    {
                        if (subFilter.equals("adbe.pkcs7.detached") || 
                            subFilter.equals("ETSI.CAdES.detached"))
                        {
                            verifyPKCS7(buf, contents, sig);
                            
                        }
                        else if (subFilter.equals("adbe.pkcs7.sha1"))
                        {
                            // example: PDFBOX-1452.pdf
                            byte[] certData = contents.getBytes();
                            CertificateFactory factory = CertificateFactory.getInstance("X.509");
                            ByteArrayInputStream certStream = new ByteArrayInputStream(certData);
                            Collection<? extends Certificate> certs = factory.generateCertificates(certStream);
                            System.out.println("certs=" + certs);

                            byte[] hash = MessageDigest.getInstance("SHA1").digest(buf);
                            verifyPKCS7(hash, contents, sig);
                            
                        }
                        else if (subFilter.equals("adbe.x509.rsa_sha1"))
                        {
                            COSString certString = (COSString) sigDict.getDictionaryObject(COSName.CERT);
                            if (certString == null)
                            {
                                System.err.println("The /Cert certificate string is missing in the signature dictionary");
                                return;
                            }
                            byte[] certData = certString.getBytes();
                            CertificateFactory factory = CertificateFactory.getInstance("X.509");
                            ByteArrayInputStream certStream = new ByteArrayInputStream(certData);
                            Collection<? extends Certificate> certs = factory.generateCertificates(certStream);
                            System.out.println("certs=" + certs);                       
                        }
                        else if (subFilter.equals("ETSI.RFC3161"))
                        {
                            TimeStampToken timeStampToken = new TimeStampToken(new CMSSignedData(contents.getBytes()));
                            System.out.println("Time stamp gen time: " + timeStampToken.getTimeStampInfo().getGenTime());
                            System.out.println("Time stamp tsa name: " + timeStampToken.getTimeStampInfo().getTsa().getName());

                            CertificateFactory factory = CertificateFactory.getInstance("X.509");
                            ByteArrayInputStream certStream = new ByteArrayInputStream(contents.getBytes());
                            Collection<? extends Certificate> certs = factory.generateCertificates(certStream);
                            System.out.println("certs=" + certs);
                        }
                        else
                        {
                            System.err.println("Unknown certificate type: " + subFilter);
                        }
                    }
                    else
                    {
                        throw new IOException("Missing subfilter for cert dictionary");
                    }
                }
                analyseDSS(document);
            }
            catch (CMSException ex)
            {
                throw new IOException(ex);
            }
            catch (OperatorCreationException ex)
            {
                throw new IOException(ex);
            }
            finally
            {
                if (document != null)
                {
                    document.close();
                }
            }
            System.out.println("Analyzed: " + args[1]);
        }
    }

    /**
     * Verify a PKCS7 signature.
     *
     * @param byteArray the byte sequence that has been signed
     * @param contents the /Contents field as a COSString
     * @param sig the PDF signature (the /V dictionary)
     * @throws CertificateException
     * @throws CMSException
     * @throws StoreException
     * @throws OperatorCreationException
     */
    private void verifyPKCS7(byte[] byteArray, COSString contents, PDSignature sig)
            throws CMSException, CertificateException, StoreException, OperatorCreationException,
                   NoSuchAlgorithmException, NoSuchProviderException
    {
        // inspiration:
        // http://stackoverflow.com/a/26702631/535646
        // http://stackoverflow.com/a/9261365/535646
        CMSProcessable signedContent = new CMSProcessableByteArray(byteArray);
        CMSSignedData signedData = new CMSSignedData(signedContent, contents.getBytes());
        Store<X509CertificateHolder> certificatesStore = signedData.getCertificates();
        Collection<SignerInformation> signers = signedData.getSignerInfos().getSigners();
        SignerInformation signerInformation = signers.iterator().next();
        System.out.println(signerInformation);
        Collection<X509CertificateHolder> matches =
                certificatesStore.getMatches((Selector<X509CertificateHolder>) signerInformation.getSID());
        X509CertificateHolder certificateHolder = matches.iterator().next();
        X509Certificate certFromSignedData = new JcaX509CertificateConverter().getCertificate(certificateHolder);
        System.out.println("certFromSignedData: " + certFromSignedData);
        certFromSignedData.checkValidity(sig.getSignDate().getTime());

        if (isSelfSigned(certFromSignedData))
        {
            System.err.println("Certificate is self-signed");
        }
        else
        {
            System.out.println("Certificate is not self-signed");
        }

        if (signerInformation.verify(new JcaSimpleSignerInfoVerifierBuilder().build(certFromSignedData)))
        {
            System.out.println("Signature verified");
        }
        else
        {
            System.out.println("Signature verification failed");
        }
    }

    /**
     * Analyzes the DSS-Dictionary (Document Security Store) of the document. Which is used for
     * signature validation. The DSS is defined in PAdES Part 4 - Long Term Validation.
     *
     * @param document PDDocument, to get the DSS from
     */
    private void analyseDSS(PDDocument document) throws IOException
    {
        PDDocumentCatalog catalog = document.getDocumentCatalog();
        COSBase dssElement = catalog.getCOSObject().getDictionaryObject("DSS");

        if (dssElement instanceof COSDictionary)
        {
            COSDictionary dss = (COSDictionary) dssElement;
            System.out.println("DSS Dictionary: " + dss);
            COSBase certsElement = dss.getDictionaryObject("Certs");
            if (certsElement instanceof COSArray)
            {
                printStreamsFromArray((COSArray) certsElement, "Cert");
            }
            COSBase ocspsElement = dss.getDictionaryObject("OCSPs");
            if (ocspsElement instanceof COSArray)
            {
                printStreamsFromArray((COSArray) ocspsElement, "Ocsp");
            }
            COSBase crlElement = dss.getDictionaryObject("CRLs");
            if (crlElement instanceof COSArray)
            {
                printStreamsFromArray((COSArray) crlElement, "CRL");
            }
        }
    }

    /**
     * Go through the elements of a COSArray containing each an COSStream to print in Hex.
     *
     * @param elements COSArray of elements containing a COS Stream
     * @param description to append on Print
     * @throws IOException
     */
    private void printStreamsFromArray(COSArray elements, String description) throws IOException
    {
        for (COSBase baseElem : elements)
        {
            COSObject streamObj = (COSObject) baseElem;
            if (streamObj.getObject() instanceof COSStream)
            {
                COSStream cosStream = (COSStream) streamObj.getObject();

                COSInputStream input = cosStream.createInputStream();
                byte[] streamBytes = IOUtils.toByteArray(input);

                System.out.println(description + " (" + elements.indexOf(streamObj) + "): "
                        + Hex.getString(streamBytes));
            }
        }
    }

    // https://svn.apache.org/repos/asf/cxf/tags/cxf-2.4.1/distribution/src/main/release/samples/sts_issue_operation/src/main/java/demo/sts/provider/cert/CertificateVerifier.java
    
    /**
     * Checks whether given X.509 certificate is self-signed.
     */
    private boolean isSelfSigned(X509Certificate cert)
            throws CertificateException, NoSuchAlgorithmException, NoSuchProviderException
    {
        try
        {
            // Try to verify certificate signature with its own public key
            PublicKey key = cert.getPublicKey();
            cert.verify(key);
            return true;
        }
        catch (SignatureException sigEx)
        {
            return false;
        }
        catch (InvalidKeyException keyEx)
        {
            return false;
        }
    }
}

