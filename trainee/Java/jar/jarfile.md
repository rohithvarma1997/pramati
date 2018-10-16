#Jar file creation:
**creating a jar file in eclipse:**
```
1. create new project with a package name
2. right click on package and add new class file
3. create one or more class files with all the functions in each file
4. Then right click on the project and export it as jar file and choose the location
```
**creating Jarfile in CMD**
```
The basic format of the command for creating a JAR file is:

*jar cf jar-file input-file(s)*

The options and arguments used in this command are:
1. The c option indicates that you want to create a JAR file.
2. The f option indicates that you want the output to go to a file rather than to stdout.
3. jar-file is the name that you want the resulting JAR file to have. You can use any filename for a JAR file. By convention, JAR filenames are given a .jar extension, though this is not required.
4. The input-file(s) argument is a space-separated list of one or more files that you want to include in your JAR file. The input-file(s) argument can contain the wildcard * symbol. If any of the "input-files" are directories, the contents of those directories are added to the JAR archive recursively.
```
**using jar file in another application or project**
```
1. create a new project
2. right click and go to properties-->java bulid path-->libraries and addExternar Jarfile
3. then in the project import the jar file classes with the package name(s) specified in jar file 
```

#creating runnable Jar file:
**creating runnable jar file and running it**
```
1. create new project with a package name
2. right click on package and add new class file
3. create one or more class files with all the functions in each file
4. Then right click on the project and export it as Runnable Jarfile and choose the manifest attribute from which application or project or java file the jarfile should run and create it
```
**creating runnable jar file in cmd**
```
In CMD go to the working directory
1. include path of JDK for current directory
*path c:\Program Files\Java\jdk1.7.0_25\bin;%path%*
2. compile the .java files to get the class files:
*javac *.java* 
3. Create a manifest file and your jar file: 
*echo Main-Class: JarFileName >manifest.txt*
*jar cvfm JarFileName.jar manifest.txt *.class or jar cvfe JarFileName.jar JarFileName *.class*
4. run the file with eclipse directly opening it in eclipse or in cmd with command
*java -jar run.jar*
```
```
krohithvarma@IMCHLT089:~$ java -jar run.jar
There are 5 types of interitance
1.single inheritance
2.multiple inheritance
3.multilevel inheritance
4.hierarchical inheritance
5.hybrid inheritance
```
jar cvf program.jar -C path/to/classes



