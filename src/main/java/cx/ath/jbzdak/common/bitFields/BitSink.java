package cx.ath.jbzdak.common.bitFields;

/**
 * User: Jacek Bzdak jbdak@gmail.com
 * Date: Apr 13, 2010
 */
public abstract class BitSink {

    private static final boolean turnOnExceptions = true;

    private byte currentByte;

    private byte bytePosition;

    protected abstract void sendByte(byte b);

    public void putBit(boolean bit){
        if(bit){
            currentByte|=1<<bytePosition++;
        }else{
            byte mask = (byte)~(1<<bytePosition++);
            currentByte&=mask;
        }
        checkByte();
    }
    
    public void putBits(byte b, int offset, int bitNum){
        if(turnOnExceptions){
            if(bitNum + offset > 8){
                throw new IllegalArgumentException();
            }
        }
        b>>=offset;//rotate by offset to right so first written bit is youngest
        byte writeFirst = (byte)(b<<offset);
        writePart(writeFirst, bitNum);
        byte bytePosition = this.bytePosition;
        if(checkByte()){
            int toWrite = 8 - bytePosition;
            b>>= toWrite;
            currentByte|=b;
            writePart(b, toWrite);
        }
    }

    private void writePart(byte b, int bitNum){
        b<<=(8-bitNum);
        b>>=(8-bitNum);
        b<<=bytePosition;
        currentByte|=b;
        bytePosition+=bitNum;
    }

    private void sendByte(){
        sendByte(currentByte);
        currentByte = bytePosition = 0;
    }

    private boolean checkByte(){
        if(bytePosition>=7){
            sendByte();
            return true;
        }
        return false;
    }







}
