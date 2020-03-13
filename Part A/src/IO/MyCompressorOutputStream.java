package IO;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.Vector;

public class MyCompressorOutputStream extends OutputStream {

    OutputStream out;

    public MyCompressorOutputStream(OutputStream out) {
        this.out = out;
    }
    @Override
    public void write(int b) throws IOException {
        if (b>=-128 && b<128) {
            out.write(b);
            out.write(0);
        }
        else if (b>=128){
            int [] res = splitToTwoBytes(b);
            out.write(res[0]);
            out.write(res[1]);
        }
    }
//older
    @Override
    public void write(byte [] b) throws IOException {
        Vector<Integer> compressed = compressLZW(b);

        for (int i=0; i<compressed.size(); i++){
            write(compressed.elementAt(i));
        }
    }

    private Vector<Integer> compressLZW (byte [] b){

        Vector<Vector<Byte>> dictionary = new Vector<>();
        Vector<Integer> res = new Vector<>();

        //initialize dictionary -128-127
        for (int i=-128; i<128; i++){
            Vector<Byte> v = new Vector<>();
            v.add((byte)i);
            dictionary.add(v);
        }
        Byte c;
        Vector<Byte> p = new Vector<>();
        int index = 0;
        p.add(b[index]);
        index++;

        while (index<b.length){
            c = b[index];
            Vector<Byte> pc =new Vector<>();
            for(int i=0; i<p.size(); i++){
                pc.add(p.elementAt(i));
            }
            pc.add(c);

            if(dictionary.contains(pc)){
                p.add(c);
            }
            else{
                for(int i=0; i<dictionary.size();i++){
                    if (dictionary.elementAt(i).equals(p)){
                        res.add(i);
                    }
                }
                p.add(c);
                dictionary.add(p);
                p= new Vector<>();
                p.add(c);
            }
            index++;
        }
        if(p.size()>0){
            res.add(dictionary.indexOf(p));
        }
        return res;
    }

    private int[] splitToTwoBytes (int num){
        int []res = new int[2];
        int [] binaryNum = convertToBinary(num,16);
        res[0] = convertToInteger(binaryNum,0,7);
        res[1] = convertToInteger(binaryNum,8,15);
        return res;
    }

    private int[] convertToBinary(int num, int size) {
        boolean negative = false;
        boolean done = false;
        int[] res = new int[size];
        if (num<0) {
            negative = true;
            num = num*(-1);
        }
        for (int i=size-1; i>=0; i--){
            if(Math.pow(2,i)>num){
                res[i] =0;
            }
            else {
                res[i]=1;
                num = num-(int)Math.pow(2,i);
            }
        }
        if(negative){
            for(int i=0; i<res.length; i++){
                if(res[i] ==0 )
                    res[i]=1;
                else
                    res[i]=0;
            }
            int index =  0;
            while (!done){
                if(res[index]==0){
                    done= true;
                    res[index] = 1;
                }
                else{
                    res[index] = 0;
                }
                index++;
            }
        }
        return res;
    }

    private int convertToInteger(int[] binaryNum, int start, int end) {
        int res=0;
        int index =0;
        for(int i=start;i<end; i++){
            res = res + (int)Math.pow(2,index) * binaryNum[i];
            index++;
        }
        res= res -(int)Math.pow(2,index) * binaryNum[end];
        return res;
    }

}
