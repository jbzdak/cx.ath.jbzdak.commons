package cx.ath.jbzdak.common.bitFields;

import java.io.ByteArrayOutputStream;

/**
 * User: Jacek Bzdak jbdak@gmail.com
 * Date: Apr 13, 2010
 */
public class ByteArrayBitSink extends BitSink{

    private ByteArrayOutputStream os = new ByteArrayOutputStream();

    @Override
    protected void sendByte(byte b) {
        os.write(b);
    }

    public byte[] getResults(){
        return os.toByteArray();
    }
}
