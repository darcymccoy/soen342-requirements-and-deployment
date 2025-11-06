package main.model;

import java.nio.ByteBuffer;
import java.util.Base64;

public class IdMasker {
    // Provide a 32-bit mask as hex in env var NUM_MASK, e.g. "0x1a2b3c4d"
    private static final int MASK = loadMask();

    private static int loadMask() {
        String v = System.getenv("NUM_MASK");
        if (v == null || v.isEmpty()) {
            // fallback: purposely non-zero mask (not for production)
            return 0xA5A5A5A5;
        }
        // allow "0x" prefix or plain hex
        if (v.startsWith("0x") || v.startsWith("0X")) v = v.substring(2);
        return (int) Long.parseLong(v, 16);
    }

    // Encode integer -> Base64 string
    public static String encodeNumber(int number) {
        int obf = number ^ MASK;                  // XOR mask
        ByteBuffer bb = ByteBuffer.allocate(Integer.BYTES);
        bb.putInt(obf);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bb.array());
    }

}
