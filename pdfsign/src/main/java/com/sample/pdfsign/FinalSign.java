package com.sample.pdfsign;

import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.URL;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.text.*;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.examples.signature.CreateSignatureBase;
import org.apache.pdfbox.examples.signature.SigUtils;
import org.apache.pdfbox.io.IOUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.common.PDStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.form.PDFormXObject;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationWidget;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAppearanceDictionary;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAppearanceStream;
import org.apache.pdfbox.pdmodel.interactive.digitalsignature.ExternalSigningSupport;
import org.apache.pdfbox.pdmodel.interactive.digitalsignature.PDSignature;
import org.apache.pdfbox.pdmodel.interactive.digitalsignature.SignatureInterface;
import org.apache.pdfbox.pdmodel.interactive.digitalsignature.SignatureOptions;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.apache.pdfbox.pdmodel.interactive.form.PDSignatureField;
import org.apache.pdfbox.util.Hex;
import org.apache.pdfbox.util.Matrix;


public class FinalSign extends CreateSignatureBase
{
    private SignatureOptions signatureOptions;
    private boolean lateExternalSigning = false;
    private File imageFile;
    public String name="rohith";
    Date date = Calendar.getInstance().getTime();  
    DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");  
    String currentTime = dateFormat.format(date); 

    public FinalSign(KeyStore keystore, char[] pin)
            throws KeyStoreException, UnrecoverableKeyException, NoSuchAlgorithmException, IOException, CertificateException
    {
        super(keystore, pin);
    }

    public File getImageFile()
    {
        return imageFile;
    }

    public void setImageFile(File imageFile)
    {
        this.imageFile = imageFile;
    }

    public boolean isLateExternalSigning()
    {
        return lateExternalSigning;
    }

    public void setLateExternalSigning(boolean lateExternalSigning)
    {
        this.lateExternalSigning = lateExternalSigning;
    }

    /**
     * Sign pdf file and create new file that ends with "_signed.pdf".
     *
     * @param inputFile The source pdf document file.
     * @param signedFile The file to be signed.
     * @param humanRect rectangle from a human viewpoint (coordinates start at top left)
     * @param tsaUrl optional TSA url
     * @throws IOException
     */
    public void signPDF(File inputFile, File signedFile, Rectangle2D humanRect, String tsaUrl) throws IOException
    {
        this.signPDF(inputFile, signedFile, humanRect, tsaUrl, null);
    }

    /**
     * Sign pdf file and create new file that ends with "_signed.pdf".
     *
     * @param inputFile The source pdf document file.
     * @param signedFile The file to be signed.
     * @param humanRect rectangle from a human viewpoint (coordinates start at top left)
     * @param tsaUrl optional TSA url
     * @param signatureFieldName optional name of an existing (unsigned) signature field
     * @throws IOException
     */
    public void signPDF(File inputFile, File signedFile, Rectangle2D humanRect, String tsaUrl, String signatureFieldName) throws IOException
    {
        if (inputFile == null || !inputFile.exists())
        {
            throw new IOException("Document for signing does not exist");
        }

        setTsaUrl(tsaUrl);

        // creating output document and prepare the IO streams.
        FileOutputStream fos = new FileOutputStream(signedFile);

        PDDocument doc = PDDocument.load(inputFile);
        int accessPermissions = SigUtils.getMDPPermission(doc);
        if (accessPermissions == 1)
        {
            throw new IllegalStateException("No changes to the document are permitted due to DocMDP transform parameters dictionary");
        }

        PDSignature signature = null;
        PDAcroForm acroForm = doc.getDocumentCatalog().getAcroForm();
        PDRectangle rect = null;

        // sign a PDF with an existing empty signature, as created by the CreateEmptySignatureForm example.
        if (acroForm != null)
        {
            signature = findExistingSignature(acroForm, signatureFieldName);
            if (signature != null)
            {
                rect = acroForm.getField(signatureFieldName).getWidgets().get(0).getRectangle();
            }
        }

        if (signature == null)
        {
            // create signature dictionary
            signature = new PDSignature();
        }

        if (rect == null)
        {
            rect = createSignatureRectangle(doc, humanRect);
        }

        // Optional: certify
        // can be done only if version is at least 1.5 and if not already set
        // doing this on a PDF/A-1b file fails validation by Adobe preflight (PDFBOX-3821)
        // PDF/A-1b requires PDF version 1.4 max, so don't increase the version on such files.
        if (doc.getVersion() >= 1.5f && accessPermissions == 0)
        {
            SigUtils.setMDPPermission(doc, signature, 2);
        }

        if (acroForm != null && acroForm.getNeedAppearances())
        {
            // PDFBOX-3738 NeedAppearances true results in visible signature becoming invisible 
            // with Adobe Reader
            if (acroForm.getFields().isEmpty())
            {
                // we can safely delete it if there are no fields
                acroForm.getCOSObject().removeItem(COSName.NEED_APPEARANCES);
                // note that if you've set MDP permissions, the removal of this item
                // may result in Adobe Reader claiming that the document has been changed.
                // and/or that field content won't be displayed properly.
                // ==> decide what you prefer and adjust your code accordingly.
            }
            else
            {
                System.out.println("/NeedAppearances is set, signature may be ignored by Adobe Reader");
            }
        }

        // default filter
        signature.setFilter(PDSignature.FILTER_ADOBE_PPKLITE);

        // subfilter for basic and PAdES Part 2 signatures
        signature.setSubFilter(PDSignature.SUBFILTER_ADBE_PKCS7_DETACHED);

        signature.setName("Name");
        signature.setLocation("Location");
        signature.setReason("Reason");

        // the signing date, needed for valid signature
        signature.setSignDate(Calendar.getInstance());
        

        // do not set SignatureInterface instance, if external signing used
        SignatureInterface signatureInterface = isExternalSigning() ? null : this;

        // register signature dictionary and sign interface
        signatureOptions = new SignatureOptions();
        signatureOptions.setVisualSignature(createVisualSignatureTemplate(doc, 0, rect));
        signatureOptions.setPage(9);
        doc.addSignature(signature, signatureInterface, signatureOptions);

        if (isExternalSigning())
        {
            System.out.println("Signing externally " + signedFile.getName());
            ExternalSigningSupport externalSigning = doc.saveIncrementalForExternalSigning(fos);
            // invoke external signature service
            byte[] cmsSignature = sign(externalSigning.getContent());

            // Explanation of late external signing (off by default):
            // If you want to add the signature in a separate step, then set an empty byte array
            // and call signature.getByteRange() and remember the offset signature.getByteRange()[1]+1.
            // you can write the ascii hex signature at a later time even if you don't have this
            // PDDocument object anymore, with classic java file random access methods.
            // If you can't remember the offset value from ByteRange because your context has changed,
            // then open the file with PDFBox, find the field with findExistingSignature() or
            // PDDocument.getLastSignatureDictionary() and get the ByteRange from there.
            // Close the file and then write the signature as explained earlier in this comment.
            if (isLateExternalSigning())
            {
                // this saves the file with a 0 signature
                externalSigning.setSignature(new byte[0]);

                // remember the offset (add 1 because of "<")
                int offset = signature.getByteRange()[1] + 1;

                // now write the signature at the correct offset without any PDFBox methods
                RandomAccessFile raf = new RandomAccessFile(signedFile, "rw");
                raf.seek(offset);
                raf.write(Hex.getBytes(cmsSignature));
                raf.close();
            }
            else
            {
                // set signature bytes received from the service and save the file
                externalSigning.setSignature(cmsSignature);
            }
        }
        else
        {
            // write incremental (only for signing purpose)
            doc.saveIncremental(fos);
        }
        doc.close();
        
        // Do not close signatureOptions before saving, because some COSStream objects within
        // are transferred to the signed document.
        // Do not allow signatureOptions get out of scope before saving, because then the COSDocument
        // in signature options might by closed by gc, which would close COSStream objects prematurely.
        // See https://issues.apache.org/jira/browse/PDFBOX-3743
        IOUtils.closeQuietly(signatureOptions);
    }

    private PDRectangle createSignatureRectangle(PDDocument doc, Rectangle2D humanRect)
    {
        float x = (float) humanRect.getX();
        float y = (float) humanRect.getY();
        float width = (float) humanRect.getWidth();
        float height = (float) humanRect.getHeight();
        PDPage page = doc.getPage(0);
        PDRectangle pageRect = page.getCropBox();
        PDRectangle rect = new PDRectangle();
        // signing should be at the same position regardless of page rotation.
        switch (page.getRotation())
        {
            case 90:
                rect.setLowerLeftY(x);
                rect.setUpperRightY(x + width);
                rect.setLowerLeftX(y);
                rect.setUpperRightX(y + height);
                break;
            case 180:
                rect.setUpperRightX(pageRect.getWidth() - x);
                rect.setLowerLeftX(pageRect.getWidth() - x - width);
                rect.setLowerLeftY(y);
                rect.setUpperRightY(y + height);
                break;
            case 270:
                rect.setLowerLeftY(pageRect.getHeight() - x - width);
                rect.setUpperRightY(pageRect.getHeight() - x);
                rect.setLowerLeftX(pageRect.getWidth() - y - height);
                rect.setUpperRightX(pageRect.getWidth() - y);
                break;
            case 0:
            default:
                rect.setLowerLeftX(x);
                rect.setUpperRightX(x + width);
                rect.setLowerLeftY(pageRect.getHeight() - y - height);
                rect.setUpperRightY(pageRect.getHeight() - y);
                break;
        }
        return rect;
    }

    // create a template PDF document with empty signature and return it as a stream.
    private InputStream createVisualSignatureTemplate(PDDocument srcDoc, int pageNum, PDRectangle rect) throws IOException
    {
        PDDocument doc = new PDDocument();

        PDPage page = new PDPage(srcDoc.getPage(pageNum).getMediaBox());
        doc.addPage(page);
        PDAcroForm acroForm = new PDAcroForm(doc);
        doc.getDocumentCatalog().setAcroForm(acroForm);
        PDSignatureField signatureField = new PDSignatureField(acroForm);
        PDAnnotationWidget widget = signatureField.getWidgets().get(0);
        List<PDField> acroFormFields = acroForm.getFields();
        acroForm.setSignaturesExist(true);
        acroForm.setAppendOnly(true);
        acroForm.getCOSObject().setDirect(true);
        acroFormFields.add(signatureField);

        widget.setRectangle(rect);

        // from PDVisualSigBuilder.createHolderForm()
        PDStream stream = new PDStream(doc);
        PDFormXObject form = new PDFormXObject(stream);
        PDResources res = new PDResources();
        form.setResources(res);
        form.setFormType(1);
        PDRectangle bbox = new PDRectangle(rect.getWidth(), rect.getHeight());
        float height = bbox.getHeight();
        Matrix initialScale = null;
        switch (srcDoc.getPage(pageNum).getRotation())
        {
            case 90:
                form.setMatrix(AffineTransform.getQuadrantRotateInstance(1));
                initialScale = Matrix.getScaleInstance(bbox.getWidth() / bbox.getHeight(), bbox.getHeight() / bbox.getWidth());
                height = bbox.getWidth();
                break;
            case 180:
                form.setMatrix(AffineTransform.getQuadrantRotateInstance(2));
                break;
            case 270:
                form.setMatrix(AffineTransform.getQuadrantRotateInstance(3));
                initialScale = Matrix.getScaleInstance(bbox.getWidth() / bbox.getHeight(), bbox.getHeight() / bbox.getWidth());
                height = bbox.getWidth();
                break;
            case 0:
            default:
                break;
        }
        form.setBBox(bbox);
        PDFont font = PDType1Font.HELVETICA_BOLD;

        // from PDVisualSigBuilder.createAppearanceDictionary()
        PDAppearanceDictionary appearance = new PDAppearanceDictionary();
        appearance.getCOSObject().setDirect(true);
        PDAppearanceStream appearanceStream = new PDAppearanceStream(form.getCOSObject());
        appearance.setNormalAppearance(appearanceStream);
        widget.setAppearance(appearance);

        PDPageContentStream cs = new PDPageContentStream(doc, appearanceStream);

        // for 90Â° and 270Â° scale ratio of width / height
        // not really sure about this
        // why does scale have no effect when done in the form matrix???
        if (initialScale != null)
        {
            cs.transform(initialScale);
        }

        // show background (just for debugging, to see the rect size + position)
        cs.setNonStrokingColor(Color.yellow);
        cs.addRect(-5000, -5000, 10000, 10000);
        cs.fill();

        // show background image
        // save and restore graphics if the image is too large and needs to be scaled
        cs.saveGraphicsState();
        cs.transform(Matrix.getScaleInstance(0.25f, 0.25f));
        PDImageXObject img = PDImageXObject.createFromFileByExtension(imageFile, doc);
        cs.drawImage(img, 0, 0);
        cs.restoreGraphicsState();

        // show text
        float fontSize = 10;
        float leading = fontSize * 1.5f;
        cs.beginText();
        cs.setFont(font, fontSize);
        cs.setNonStrokingColor(Color.black);
        cs.newLineAtOffset(fontSize, height - leading);
        cs.setLeading(leading);
        cs.showText("Digitally signed by ");
        cs.showText(name);
        cs.newLine();
        cs.showText(" at ");
        cs.showText(currentTime);
        cs.newLine();
        cs.endText();

        cs.close();

        // no need to set annotations and /P entry
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        doc.save(baos);
        doc.close();
        return new ByteArrayInputStream(baos.toByteArray());
    }

    // Find an existing signature (assumed to be empty). You will usually not need this.
    private PDSignature findExistingSignature(PDAcroForm acroForm, String sigFieldName)
    {
        PDSignature signature = null;
        PDSignatureField signatureField;
        if (acroForm != null)
        {
            signatureField = (PDSignatureField) acroForm.getField(sigFieldName);
            if (signatureField != null)
            {
                // retrieve signature dictionary
                signature = signatureField.getSignature();
                if (signature == null)
                {
                    signature = new PDSignature();
                    // after solving PDFBOX-3524
                    // signatureField.setValue(signature)
                    // until then:
                    signatureField.getCOSObject().setItem(COSName.V, signature);
                }
                else
                {
                    throw new IllegalStateException("The signature field " + sigFieldName + " is already signed.");
                }
            }
        }
        return signature;
    }

    /**
     * Arguments are
     * [0] key store
     * [1] pin
     * [2] document that will be signed
     * [3] image of visible signature
     *
     * @param args
     * @throws java.security.KeyStoreException
     * @throws java.security.cert.CertificateException
     * @throws java.io.IOException
     * @throws java.security.NoSuchAlgorithmException
     * @throws java.security.UnrecoverableKeyException
     */
    public static void main(String[] args) throws KeyStoreException, CertificateException,
            IOException, NoSuchAlgorithmException, UnrecoverableKeyException
    {
        // generate with
        // keytool -storepass 123456 -storetype PKCS12 -keystore file.p12 -genkey -alias client -keyalg RSA

        String tsaUrl = null;
        // External signing is needed if you are using an external signing service, e.g. to sign
        // several files at once.
        boolean externalSig = false;

        System.out.println(Calendar.getInstance().getTime());
        File ksFile = new File(args[0]);
        KeyStore keystore = KeyStore.getInstance("PKCS12");
        char[] pin = args[1].toCharArray();
        keystore.load(new FileInputStream(ksFile), pin);

        File documentFile = new File(args[2]);

        FinalSign signing = new FinalSign(keystore, pin.clone());

        signing.setImageFile(new File(args[3]));

        File signedDocumentFile;
        String name = documentFile.getName();
        String substring = name.substring(0, name.lastIndexOf('.'));
        signedDocumentFile = new File(documentFile.getParent(), substring + "_signed.pdf");

        signing.setExternalSigning(externalSig);

        // Set the signature rectangle
        // Although PDF coordinates start from the bottom, humans start from the top.
        // So a human would want to position a signature (x,y) units from the
        // top left of the displayed page, and the field has a horizontal width and a vertical height
        // regardless of page rotation.
        Rectangle2D humanRect = new Rectangle2D.Float(200, 400, 150, 50);

        signing.signPDF(documentFile, signedDocumentFile, humanRect, tsaUrl, "Signature1");
    }



}