package org.zywx.wbpalmstar.plugin.uexsecuritykeyboard.RC4;

/**
 * 用于转换十六进制和二进制数据，加密时使用
 * 
 * @author yipeng.zhang
 * @createdAt 2015年6月30日
 */
public class HexConverter {
    private final static String hexStr = "0123456789ABCDEF";

    /**
     * 
     * @param hexString
     * @return 将十六进制转换为字节数组
     */
    public static byte[] hexStringToBinary(String hexString) {
        // hexString的长度对2取整，作为bytes的长度
        int len = hexString.length() / 2;
        byte[] bytes = new byte[len];
        byte high = 0;// 字节高四位
        byte low = 0;// 字节低四位

        for (int i = 0; i < len; i++) {
            // 右移四位得到高位
            high = (byte) ((hexStr.indexOf(hexString.charAt(2 * i))) << 4);
            low = (byte) hexStr.indexOf(hexString.charAt(2 * i + 1));
            bytes[i] = (byte) (high | low);// 高地位做或运算
        }
        return bytes;
    }

    /**
     * 
     * @param bytes
     * @return 将二进制转换为十六进制字符输出
     */
    public static String binaryToHexString(byte[] bytes) {
        String result = "";
        String hex = "";
        for (int i = 0; i < bytes.length; i++) {
            // 字节高4位
            hex = String.valueOf(hexStr.charAt((bytes[i] & 0xF0) >> 4));
            // 字节低4位
            hex += String.valueOf(hexStr.charAt(bytes[i] & 0x0F));
            result += hex;
        }
        return result;
    }
}
