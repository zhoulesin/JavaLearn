package com.zhoulesin.biaoge.tcpserver.tool;

public class DataEncryption {
	private static final int KEYLENGTH = 16;

    private DataEncryption() {
    }

    private static byte[] encrypt(byte[] bSourceData, String key) {
        byte[] szBuffer = null;

        try {
            byte[] bKey = key.getBytes();
            szBuffer = new byte[bSourceData.length];
            int k = 0;

            for(int i = 0; i < bSourceData.length; ++i) {
                szBuffer[i] = (byte)(bSourceData[i] ^ bKey[k]);
                ++k;
                k %= bKey.length;
            }
        } catch (Exception var6) {
            ;
        }

        return szBuffer;
    }

    public static void encrypt(byte[] bSourceData, byte[] bKey) {
        try {
            int k = 0;

            for(int i = 0; i < bSourceData.length; ++i) {
                bSourceData[i] ^= bKey[k];
                ++k;
                k %= bKey.length;
            }
        } catch (Exception var4) {
            ;
        }

    }

    private static byte[] unEncrypt(byte[] encryptData, String key) {
        byte[] bKey = key.getBytes();
        byte[] szBuffer = new byte[encryptData.length];
        int k = 0;

        for(int i = 0; i < encryptData.length; ++i) {
            szBuffer[i] = (byte)(encryptData[i] ^ bKey[k]);
            ++k;
            k %= bKey.length;
        }

        return szBuffer;
    }

    public static void unEncrypt(byte[] encryptData, byte[] bKey) {
        int k = 0;

        for(int i = 0; i < encryptData.length; ++i) {
            encryptData[i] ^= bKey[k];
            ++k;
            k %= bKey.length;
        }

    }

    private static String getEncryptionKey(String source) {
        return getEncryptionKey(source, 16);
    }

    private static String getEncryptionKey(String source, int keyLength) {
        String key = "";
        if(source != null) {
            int length = source.length();
            if(length > keyLength) {
                key = source.substring(0, keyLength);
            } else if(length < keyLength) {
                StringBuffer blank = new StringBuffer("");

                for(int i = 0; i < keyLength - length; ++i) {
                    blank.append(source.charAt(i % length));
                }

                key = source + blank.toString();
            } else {
                key = source;
            }
        }

        return key;
    }

    public static byte[] encryption(byte[] source, String key) {
        if(source != null && !"".equals(source) && key != null && !"".equals(key)) {
            String tmpKey = getEncryptionKey(key);
            return tmpKey != null && !"".equals(tmpKey) && 16 == tmpKey.length()?encrypt(source, tmpKey):null;
        } else {
            return null;
        }
    }

    public static byte[] unEncryption(byte[] source, String key) {
        if(source != null && source.length > 0 && key != null && !"".equals(key)) {
            String tmpKey = getEncryptionKey(key);
            return tmpKey != null && 16 == tmpKey.length()?unEncrypt(source, tmpKey):null;
        } else {
            return null;
        }
    }
}
