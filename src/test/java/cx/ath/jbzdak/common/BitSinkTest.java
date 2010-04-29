package cx.ath.jbzdak.common;

import cx.ath.jbzdak.common.bitFields.BitSink;
import cx.ath.jbzdak.common.bitFields.ByteArrayBitSink;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * User: Jacek Bzdak jbdak@gmail.com
 * Date: Apr 13, 2010
 */
public class BitSinkTest {

    ByteArrayBitSink bitSink;

    @Before
    public void before(){
        bitSink = new ByteArrayBitSink();
    }

    @Test
    public void testPutBit(){
        for(int ii = 0; ii < 7; ii++){
            testBit1(ii);
            testBit2(ii);
        }
    }



    private void testBit1(int bit){
        bitSink = new ByteArrayBitSink();
        for(int ii = 0; ii < 7; ii++){
            bitSink.putBit(ii==bit);
        }
        Assert.assertArrayEquals(new byte[]{(byte)(1<<bit)}, bitSink.getResults());
    }

    private void testBit2(int bit){
        bitSink = new ByteArrayBitSink();
        for(int ii = 0; ii < 7; ii++){
            bitSink.putBit(ii!=bit);
        }
        byte b = (byte) ~(1 << bit);
        Assert.assertArrayEquals(new byte[]{b}, bitSink.getResults());
    }
}

