package utils;

public class CryptoUtil {
    
    // Java Program to Implement the RSA Algorithm
    private static CryptoUtil cryptoUtil = new CryptoUtil();
    private double publicKey;
    private double privateKey;
    private double module;

    private CryptoUtil(){

    }

    public static CryptoUtil getInstance(){
        return CryptoUtil.cryptoUtil;
    }

    public double getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(double publicKey) {
        this.publicKey = publicKey;
    }

    public double getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(double privateKey) {
        this.privateKey = privateKey;
    }

    public double getModule() {
        return module;
    }

    public void setModule(double module) {
        this.module = module;
    }
    
    static int gcd(int a, int b)
    {
        int t;
        while(true) {
            t= a%b;
            if(t==0)
                return b;
            a = b;
            b= t;
        }
    }

    void generateKeys(int p, int q){
        double d = 0, n;
        int phi, e;

        // The number to be encrypted and decrypted
       

        n = p * q;
        this.setModule(n);

        phi = (p - 1) * (q - 1);
        //System.out.println("the value of ph = " + phi);

        e = 2;
        while(e < phi){
            if (gcd(e, phi) == 1) {
                break;
            }
            else{
                e++;
            }            
        }
        this.setPublicKey(e);

        //System.out.println("the value of e = " + e);
        
        double d1;
        d1 =((double)1/(double)e);
        String formatted = String.format("d1 = 1/ %d is: %.2f", e, d1);
        System.out.println(formatted);

        d= d1 % phi;
        formatted = String.format("d = d1 %.2f modulo %d is: %.2f", d1, phi, d);
        System.out.println(formatted);
      /*for (i = 0; i <= 9; i++) {
            double x = 1 + (i * phi);

            // d is for private key exponent
            if (x % e == 0) {
                d = x / e;
                break;
            }
        }*/
        this.setPrivateKey(d);
        formatted = String.format("Public Key is: %.2f", publicKey);
        System.out.println(formatted);
        formatted = String.format("Private Key is: %.2f", privateKey);
        System.out.println(formatted);
        System.out.println("Module is: " + module);
    }
    
    //String = bet \n energy
    //String publicKey
    public double encrypt(double message, double publicKey){
        double c;   
        c = (Math.pow(message, publicKey)) ;
        //double legible = c % module;
        //System.out.println("Encrypted message is : " + legible);
        System.out.println("Enctypted message non readable is : "+ c);
     
        return c;  
    }
    
    double decrypt(double message, double privateKey){
        
        double m = Math.pow(message, privateKey);
        double decrypt = m % module;
        System.out.println("Decrypted message is : "+ decrypt);
        return Math.ceil(decrypt);
        
        /*
        BigInteger msgback;
        message = message % module;
        // converting int value of module to BigInteger
        BigInteger N = BigInteger.valueOf(module);
        // converting float value of message to BigInteger
        BigInteger C = BigDecimal.valueOf(message).toBigInteger();
        msgback = (C.pow((int)privateKey)).mod(N);
        
        return msgback;*/
    }

}
