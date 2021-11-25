
#include <stdio.h>
#include <stdlib.h>
#include <iostream>
#include <math.h>
#include "threadManager.cpp"

using namespace std;

int main(int argc, char* argv[])  {
    //2 random prime numbers
    string path = "D:\\Users\\aguil\\Documents\\TEC_Files\\7 - IS 2021\\Analisis de Algoritmos\\Proyecto\\Proyecto II\\TEC_CR_Algorith_Analysis_OctopusArena\\userPublicKeys\\";
    string userName = "happy_cookie#13.txt";
    Encryption* crypt = new Encryption();
    double message =crypt->decrypt(7.8125E11,0.142857, 3305 );
    DataProvider* txtMan = new DataProvider();
    ThreadManager* threadManager = new ThreadManager();
    threadManager->startThreads(path);
    cin >> userName;
    return 0;
}
