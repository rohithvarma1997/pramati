# About
This floder consists of two programs which are in directory  ```src/main/java/com/sample/pdfdigitalsign``` namely 
> ```FinalSignature.java``` - which is used to create a digital signature in a pdf
> ```DigitalSignVerify.java``` - which is used to verify the digital signature

The Digital Signature creation and verification of pdf is done by 'PDFBOX' which is a java library for performing different operations with pdfs

This is a maven project and ```pom.xml``` file contains all the required libraries like pdfbox,bouncycastle etc to perform the operations

# Usage:command line args
> FinalSignature.java [KeyStoreFile KeyStoreFilePIN FileToSign BackgroundImageOfSignature]

`OR`

> mvn exec:java -e -Dexec.mainClass="com.sample.pdfdigitalsign.FinalSignature" -Dexec.args="KeyStoreFile KeyStoreFilePIN FileToSign BackgroundImageOfSignature"

- Example:

> mvn exec:java -e -Dexec.mainClass="com.sample.pdfdigitalsign.FinalSignature" -Dexec.args="/home/krohithvarma/github/pramati/pdfdigitalsign/src/main/java/com/sample/pdfdigitalsign/file.p12 123456 /home/krohithvarma/github/pramati/pdfdigitalsign/src/main/java/com/sample/pdfdigitalsign/bitcoin.pdf /home/krohithvarma/github/pramati/pdfdigitalsign/src/main/java/com/sample/pdfdigitalsign/lol.jpg"



