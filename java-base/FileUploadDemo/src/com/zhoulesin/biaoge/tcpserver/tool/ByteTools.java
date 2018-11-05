package com.zhoulesin.biaoge.tcpserver.tool;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

public class ByteTools {
    private static char[] hexChars = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public ByteTools() {
    }

    public static String bytesToHexString(byte[] printBytes, int position, int bytesLength, int constantNumber) {
        if(printBytes == null) {
            return null;
        } else {
            if(position < 0) {
                position = 0;
            }

            if(position + bytesLength > printBytes.length) {
                bytesLength = printBytes.length - position;
            }

            StringBuffer tmpSb = new StringBuffer(bytesLength * 4);

            for(int i = 0; i < bytesLength; ++i) {
                char firstChar = hexChars[printBytes[position + i] >> 4 & 15];
                char secondChar = hexChars[printBytes[position + i] & 15];
                tmpSb.append(firstChar).append(secondChar).append(' ');
                if(i % constantNumber == 0 && i != 0) {
                    tmpSb.append('\n');
                }
            }

            return tmpSb.toString();
        }
    }

    public static String bytesToHexString(byte[] printBytes, int position, int bytesLength) {
        return bytesToHexString(printBytes, position, bytesLength, 16);
    }

    public static String bytesToHexString(byte[] printBytes) {
        return bytesToHexString(printBytes, 0, printBytes.length);
    }

    public static boolean cmp(byte[] abyte0, byte[] abyte1) {
        if(abyte1 == null) {
            return abyte0 == null;
        } else if(abyte0 == null) {
            return false;
        } else {
            int j = abyte0.length;
            if(j != abyte1.length) {
                return false;
            } else {
                for(int i = 0; i < j; ++i) {
                    if(abyte0[i] != abyte1[i]) {
                        return false;
                    }
                }

                return true;
            }
        }
    }

    public static short byte2ToShort(byte[] abyte, int offset) {
        return (short)((abyte[offset + 0] & 127) << 8 | abyte[offset + 1] & 255);
    }

    public static short byte2ToShort12(byte[] abyte, int offset) {
        return (short)((abyte[offset + 0] & 15) << 8 | abyte[offset + 1] & 255);
    }

    public static short byte2ToShort(byte[] abyte) {
        return byte2ToShort(abyte, 0);
    }

    public static int byte2ToUnsignedShort(byte[] abyte, int offset) {
        return byte2ToShort(abyte, offset) & '\uffff';
    }

    public static int byte2TogetUnsignedShort(byte[] abyte) {
        return byte2ToUnsignedShort(abyte, 0);
    }

    public static int byte4ToInt(byte[] abyte) {
        return byte4ToInt(abyte, 0);
    }

    public static int byte4ToInt(byte[] abyte, int offset) {
        return abyte[offset + 0] << 24 | (abyte[offset + 1] & 255) << 16 | (abyte[offset + 2] & 255) << 8 | abyte[offset + 3] & 255;
    }

    public static long byte4ToUnsignedInt(byte[] abyte) {
        return byte4ToUnsignedInt(abyte, 0);
    }

    public static long byte4ToUnsignedInt(byte[] abyte, int offset) {
        return (long)byte4ToInt(abyte, offset) & 4294967295L;
    }

    public static long byte8ToLong(byte[] abyte, int offset) {
        return (long)(abyte[offset] & 255) << 56 | (long)(abyte[offset + 1] & 255) << 48 | (long)(abyte[offset + 2] & 255) << 40 | (long)(abyte[offset + 3] & 255) << 32 | (long)(abyte[offset + 4] & 255) << 24 | (long)(abyte[offset + 5] & 255) << 16 | (long)(abyte[offset + 6] & 255) << 8 | (long)(abyte[offset + 7] & 255);
    }

    public static long byte5ToLong(byte[] abyte, int offset) {
        return (long)(abyte[offset] & 255) << 32 | (long)(abyte[offset + 1] & 255) << 24 | (long)(abyte[offset + 2] & 255) << 16 | (long)(abyte[offset + 3] & 255) << 8 | (long)(abyte[offset + 4] & 255);
    }

    public static String byte4ToIP(byte[] abyte) {
        String s = "";

        for(int i = 0; i < 4; ++i) {
            s = s + (abyte[i] & 255) + ".";
        }

        s = s.substring(0, s.length() - 1);
        return s;
    }

    public static byte[] shortToByte2(short shortValue) {
        byte[] abyte = new byte[]{(byte)(shortValue >>> 8), (byte)shortValue};
        return abyte;
    }

    public static void shortToByte2(short shortValue, byte[] abyte, int start) {
        abyte[start] = (byte)(shortValue >>> 8);
        abyte[start + 1] = (byte)shortValue;
    }

    public static byte[] shortToByte2(byte dataType, byte packVersion, byte packFlag, short shortValue) {
        byte[] abyte = new byte[]{(byte)(dataType << 7 | packVersion << 5 | packFlag << 4 | shortValue >>> 8 & 15), (byte)shortValue};
        return abyte;
    }

    public static byte[] intToByte4(int intValue) {
        byte[] abyte = new byte[]{(byte)(intValue >>> 24), (byte)(intValue >>> 16), (byte)(intValue >>> 8), (byte)intValue};
        return abyte;
    }

    public static void intToByte4(int intValue, byte[] abyte, int start) {
        abyte[start + 0] = (byte)(intValue >>> 24);
        abyte[start + 1] = (byte)(intValue >>> 16);
        abyte[start + 2] = (byte)(intValue >>> 8);
        abyte[start + 3] = (byte)intValue;
    }

    public static void longTobyte8(long longValue, byte[] abyte, int start) {
        abyte[start + 0] = (byte)((int)(longValue >>> 56));
        abyte[start + 1] = (byte)((int)(longValue >>> 48));
        abyte[start + 2] = (byte)((int)(longValue >>> 40));
        abyte[start + 3] = (byte)((int)(longValue >>> 32));
        abyte[start + 4] = (byte)((int)(longValue >>> 24));
        abyte[start + 5] = (byte)((int)(longValue >>> 16));
        abyte[start + 6] = (byte)((int)(longValue >>> 8));
        abyte[start + 7] = (byte)((int)longValue);
    }

    public static byte[] longTobyte8(long longValue) {
        byte[] abyte = new byte[]{(byte)((int)(longValue >>> 56)), (byte)((int)(longValue >>> 48)), (byte)((int)(longValue >>> 40)), (byte)((int)(longValue >>> 32)), (byte)((int)(longValue >>> 24)), (byte)((int)(longValue >>> 16)), (byte)((int)(longValue >>> 8)), (byte)((int)longValue)};
        return abyte;
    }

    public static byte[] longTobyte5(long longValue) {
        byte[] abyte = new byte[]{(byte)((int)(longValue >>> 32)), (byte)((int)(longValue >>> 24)), (byte)((int)(longValue >>> 16)), (byte)((int)(longValue >>> 8)), (byte)((int)longValue)};
        return abyte;
    }

    public static void longTobyte5(long longValue, byte[] abyte, int stat) {
        abyte[stat + 0] = (byte)((int)(longValue >>> 32));
        abyte[stat + 1] = (byte)((int)(longValue >>> 24));
        abyte[stat + 2] = (byte)((int)(longValue >>> 16));
        abyte[stat + 3] = (byte)((int)(longValue >>> 8));
        abyte[stat + 4] = (byte)((int)longValue);
    }

    public static void main(String[] args) {
        System.out.println(Long.toHexString(314169217L));
        System.out.print("" + Long.valueOf(314169217L));
    }

    public static byte[] IPToByte4(String ip) {
        String[] s = ip.split("[.]");
        byte[] abyte = new byte[4];

        for(int i = 0; i < 4; ++i) {
            abyte[i] = (byte)Integer.parseInt(s[i]);
        }

        return abyte;
    }

    public static void longTobyte6(long longValue, byte[] abyte, int stat) {
        abyte[stat + 0] = (byte)((int)(longValue >>> 40));
        abyte[stat + 1] = (byte)((int)(longValue >>> 32));
        abyte[stat + 2] = (byte)((int)(longValue >>> 24));
        abyte[stat + 3] = (byte)((int)(longValue >>> 16));
        abyte[stat + 4] = (byte)((int)(longValue >>> 8));
        abyte[stat + 5] = (byte)((int)longValue);
    }

    public static void intTobyte4(int longValue, byte[] abyte, int stat) {
        abyte[stat + 0] = (byte)(longValue >>> 24);
        abyte[stat + 1] = (byte)(longValue >>> 16);
        abyte[stat + 2] = (byte)(longValue >>> 8);
        abyte[stat + 3] = (byte)longValue;
    }

    public static String dumpBytes(byte[] bytes) {
        if(bytes == null) {
            return "";
        } else {
            StringBuffer sb = new StringBuffer();

            for(int i = 0; i < bytes.length; ++i) {
                if(i % 16 == 0 && i != 0) {
                    sb.append("  ");
                }

                if(i % 32 == 0 && i != 0) {
                    sb.append("\n");
                }

                String s = Integer.toHexString(bytes[i]);
                if(s.length() < 2) {
                    s = "0" + s;
                }

                if(s.length() > 2) {
                    s = s.substring(s.length() - 2);
                }

                sb.append(s);
            }

            return sb.toString();
        }
    }

    public static synchronized String getUserKey() {
        return null;
    }
}
