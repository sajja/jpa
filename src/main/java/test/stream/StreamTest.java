package test.stream;

import sun.nio.cs.UTF_32BE_BOM;

import java.io.*;
import java.util.Arrays;
import java.util.stream.Stream;

public class StreamTest {
    static byte[] UTF_8_BOM = new byte[]{(byte) 0xef, (byte) 0xbb, (byte) 0xbf};
    static byte[] UTF_16_BE_BOM = new byte[]{(byte) 0xFE, (byte) 0xFF};
    static byte[] payload = "Hello world".getBytes();

    public static byte[] getPayload(byte[] bom) {
        byte[] payload = "Hello world".getBytes();
        int pLen = payload.length;
        int bomL = UTF_8_BOM.length;
        byte[] res = new byte[pLen + bomL];

        if (bom != null && bom.length != 0) {
            System.arraycopy(UTF_8_BOM, 0, res, 0, bomL);
            System.arraycopy(payload, 0, res, bomL, pLen);
            System.out.println(new String(res));
            return res;
        } else {
            return payload;
        }
    }

    public static InputStream stripBOM(PushbackInputStream pbis) throws IOException {
        byte[] bom = new byte[3];
        int len = pbis.read(bom);
        if (bom[0] == UTF_8_BOM[0] && bom[0] == UTF_8_BOM[0] && bom[0] == UTF_8_BOM[0]) {
        } else {
            pbis.unread(len);
        }
        return pbis;
    }

    public static void printBytes(InputStream is) throws IOException {
        int i = 0;
        while ((i = is.read()) != -1) {
            System.out.print(i);
        }
    }

    public static void printString(InputStream is) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        int i;
        byte[] data = new byte[20];

        while ((i = is.read(data, 0, data.length)) != -1) {
            bos.write(data, 0, i);
        }
        System.out.print(" : " + new String(data));
    }

    public static void main(String[] args) throws IOException {
        byte[] utf8 = getPayload(UTF_8_BOM);
        byte[] noBom = getPayload(null);

//        ByteArrayInputStream isWithBom = new ByteArrayInputStream(utf8);
//        ByteArrayInputStream isWithBom1 = new ByteArrayInputStream(utf8);
//        ByteArrayInputStream isWithBom2 = new ByteArrayInputStream(utf8);
//        ByteArrayInputStream isWithOutBom = new ByteArrayInputStream(noBom);
//        ByteArrayInputStream isWithOutBom1 = new ByteArrayInputStream(noBom);
//        ByteArrayInputStream isWithOutBom2 = new ByteArrayInputStream(noBom);
//        System.out.println("BOM IS");
//        printBytes(isWithBom);
//        printString(isWithBom1);
//        System.out.println("\nBOM Stripped");
//        printBytes(stripBOM(new PushbackInputStream(isWithBom2)));
//        System.out.println("\nNO BOM IS");
//        printBytes(isWithOutBom);
//        printString(isWithOutBom1);
//        System.out.println("\nBOM Stripped");
//        printString(stripBOM(new PushbackInputStream(isWithOutBom2)));


        byte[]data  = "Hello world".getBytes();


        PushbackInputStream pbis = new PushbackInputStream(new ByteArrayInputStream(data),3);
        byte[]bom = new byte[3];
        int len = pbis.read(bom);
        pbis.unread(bom);
        printString(pbis);
    }

}
