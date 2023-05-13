package com.andreidodu.util;

import com.andreidodu.constants.ApplicationConst;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class ImageUtil {


    public static byte[] convertBase64StringToBytes(final String base64String) throws UnsupportedEncodingException {
        final String dataSegment = base64String.substring(base64String.indexOf(",") + 1);
        byte[] byteData = dataSegment.getBytes("UTF-8");
        return Base64.getDecoder().decode(byteData);
    }

    public static String calculateFileName(final String seed, final String base64ImageFull, final byte[] imageBytesData) throws NoSuchAlgorithmException, IOException {
        final byte[] signedImageBytesData = createNewArrayWithBytesAtTheEnd(imageBytesData, seed.getBytes());
        final String bytesHashString = calculateBytesHashString(signedImageBytesData);
        final String fileExtension = calculateFileExtension(base64ImageFull);
        return bytesHashString + "." + fileExtension;
    }

    public static byte[] createNewArrayWithBytesAtTheEnd(final byte[] target, final byte[] bytesToBeAdded) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        output.write(target);
        output.write(bytesToBeAdded);
        return output.toByteArray();
    }

    public static String calculateBytesHashString(final byte[] data) throws NoSuchAlgorithmException {
        final byte[] hash = MessageDigest.getInstance("MD5").digest(data);
        return new BigInteger(1, hash).toString(16);
    }

    public static String calculateFileExtension(final String base64ImageString) {
        return base64ImageString.substring("data:image/".length(), base64ImageString.indexOf(";base64"));
    }

    public static void writeImageOnFile(final String fileName, final byte[] data) throws IOException {
        final String fullFilePath = ApplicationConst.FILES_DIRECTORY + "/" + fileName;
        FileOutputStream outputStream = new FileOutputStream(fullFilePath);
        outputStream.write(data);
        outputStream.close();
    }
}
