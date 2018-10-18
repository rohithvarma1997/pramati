# Basic Commands:
```
Common JAR file operations Operation 		Command

To create a JAR file 	                	jar cf jar-file input-file(s)
To view the contents of a JAR file 		    jar tf jar-file
To extract the contents of a JAR file 		jar xf jar-file
To extract specific files from a JAR file 	jar xf jar-file archived-file(s)
To run an application packaged as a JAR file (requires the Main-class manifest header) 	java -jar app.jar


To invoke an applet packaged as a JAR file:	

<applet code=AppletClassName.class
        archive="JarFileName.jar"
        width=width height=height>
</applet>
```

# Updating a JAR File:
```
The Jar tool provides a u option which you can use to update the contents of an existing JAR file by modifying its manifest or by adding files.

The basic command for adding files has this format:

> jar uf jar-file input-file(s)

In this command:

- The u option indicates that you want to update an existing JAR file.
- The f option indicates that the JAR file to update is specified on the command line.
- jar-file is the existing JAR file that is to be updated.
- input-file(s) is a space-delimited list of one or more files that you want to add to the JAR file.

Any files already in the archive having the same pathname as a file being added will be overwritten.
```
**eg:**
```
Suppose that you want to add the file images/new.gif to the JAR file. You could accomplish that by issuing this command from the parent directory of the images directory:

> jar uf TicTacToe.jar images/new.gif
```

# Setting an Entry Point with the JAR Tool:
```
1. The 'e' flag (for 'entrypoint') creates or overrides the manifest's Main-Class attribute. It can be used while creating or updating a JAR file. Use it to specify the application entry point without editing or creating the manifest file.
For example, this command creates app.jar where the Main-Class attribute value in the manifest is set to MyApp:

> jar cfe app.jar MyApp MyApp.class

2. You can directly invoke this application by running the following command:
> java -jar app.jar

3. If the entrypoint class name is in a package it may use a '.' (dot) character as the delimiter. For example, if Main.class is in a package called foo the entry point can be specified in the following ways:

> jar cfe Main.jar foo.Main foo/Main.class
```

# Adding Classes to the JAR File's Classpath:
```
- You may need to reference classes in other JAR files from within a JAR file.

- For example, in a typical situation an applet is bundled in a JAR file whose manifest references a different JAR file (or several different JAR files) that serves as utilities for the purposes of that applet.

- You specify classes to include in the Class-Path header field in the manifest file of an applet or application. The Class-Path header takes the following form:

> Class-Path: jar1-name jar2-name directory-name/jar3-name

- By using the Class-Path header in the manifest, you can avoid having to specify a long -classpath flag when invoking Java to run the your application.
Note:
The Class-Path header points to classes or JAR files on the local network, not JAR files within the JAR file or classes accessible over Internet protocols. To load classes in JAR files within a JAR file into the class path, you must write custom code to load those classes. For example, if MyJar.jar contains another JAR file called MyUtils.jar, you cannot use the Class-Path header in MyJar.jar's manifest to load classes in MyUtils.jar into the class path. 
```
**Example:**
```
1. We want to load classes in MyUtils.jar into the class path for use in MyJar.jar. These two JAR files are in the same directory.

2. We first create a text file named Manifest.txt with the following contents:

> Class-Path: MyUtils.jar

- `Warning: The text file must end with a new line or carriage return. The last line will not be parsed properly if it does not end with a new line or carriage return.`

3. We then create a JAR file named MyJar.jar by entering the following command:

> jar cfm MyJar.jar Manifest.txt MyPackage/*.class

4. This creates the JAR file with a manifest with the following contents:

Manifest-Version: 1.0
Class-Path: MyUtils.jar
Created-By: 1.7.0_06 (Oracle Corporation)

5. The classes in MyUtils.jar are now loaded into the class path when you run MyJar.jar.
```
