//
// Created by anagu on 24/6/2021.
//
#include "iostream"
#include<math.h>
#include <vector>
#include <time.h>


using namespace std;

class Encryption{

private:
    double publicKey;      // e
    double privateKey;  // d
    double modulo;         // n
    
    vector<int> primesList {2, 3, 5, 7, 11, 13, 17, 19, 23, 29,
        31, 37, 41, 43, 47, 53, 59, 61, 67,
        71, 73, 79, 83, 89, 97, 101, 103,
        107, 109, 113, 127, 131, 137, 139,
        149, 151, 157, 163, 167, 173, 179,
        181, 191, 193, 197, 199, 211, 223,
        227, 229, 233, 239, 241, 251, 257,
        263, 269, 271, 277, 281, 283, 293,
        307, 311, 313, 317, 331, 337, 347,
        349, 353, 359, 367, 373, 379, 383,
        389, 397, 401, 409, 419, 421, 431,
        433, 439, 443, 449, 457, 461, 463,
        467, 479, 487, 491, 499, 503, 509,
        521, 523, 541, 547, 557, 563, 569,
        571, 577, 587, 593, 599, 601, 607,
        613, 617, 619, 631, 641, 643, 647,
        653, 659, 661, 673, 677, 683, 691,
        701, 709, 719, 727, 733, 739, 743,
        751, 757, 761, 769, 773, 787, 797,
        809, 811, 821, 823, 827, 829, 839,
        853, 857, 859, 863, 877, 881, 883,
        887, 907, 911, 919, 929, 937, 941,
        947, 953, 967, 971, 977, 983, 991, 997};

    int gcd(int a, int b) {
        int t;
        while(1) {
            t= a%b;
            if(t==0)
                return b;
            a = b;
            b= t;
        }
    }
    
    double dmod(double x, double y) {
        return x - (int)(x/y) * y;
    }

public:

    void setPublicKey(double givenPublicKey) {
        this->publicKey = givenPublicKey;
    }
    
    int getPublicKey(){
        return this->publicKey;
    }

    void setPrivateKey(double givenPrivateKey) {
        this->privateKey = givenPrivateKey;
    }
    
    double getPrivateKey(){
        return this->privateKey;
    }

    void setModulo(double givenModulo) {
        this->modulo = givenModulo;
    }
    
    int getModulo(){
        return this->modulo;
    }

    double encrypt(double message, double e, double module) {

        double c = pow(message,e); //encrypt the message
        //double encrypted = fmod(c,module);
        return c;
    }

    double decrypt(double message,double privateKey, double module) {
        //double m = pow(message,privateKey);
        double encrypted = fmod(message,module);
        //cout << encrypted << endl;

        double m = pow(message,privateKey);
        double decrypt =dmod(m,module);

        return ceil(decrypt);
    }

    int generateKeys(int randSeed){
        srand(int(time(NULL)) ^ randSeed);
        int p = primesList[ rand() % primesList.size()-1];
        int q = primesList[ rand() % primesList.size()-1];

        double n=p*q;//calculate n
        setModulo(n);

        double track;
        double phi= (p - 1) * (q - 1);//calculate phi

        double e=2;
        //for checking that 1 < e < phi(n) and gcd(e, phi(n)) = 1; i.e., e and phi(n) are coprime.
        while(e < phi) {
            track = gcd(e, phi);
            if(track==1)
                break;
            else
                e++;
        }

        //e stands for encrypt, public key
        setPublicKey(e);

        //d stands for decrypt, private key
        //choosing d such that it satisfies d*e = 1 mod phi
        double d1=1/e;
        double d=fmod(d1, phi);

        setPrivateKey(d);
    }


};
