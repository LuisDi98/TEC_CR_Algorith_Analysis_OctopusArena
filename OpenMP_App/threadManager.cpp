
#include <omp.h>
#include <string>
#include <iostream>
#include <vector>
#include <algorithm>    // std::sort
#include <sstream>
#include <windows.h>
#include "DataProvider.cpp"
#include "encryption.cpp"
using namespace std;

class ThreadManager{
    //debe recibir el path hacia archivo userPublicKeys
    void prepareKeys(string filePath,string fileName) {
        DataProvider* txtManager = new DataProvider();
        int my_rank = omp_get_thread_num();
        int thread_count = omp_get_num_threads();
        printf("Key thread %d of %d\n", my_rank+1, thread_count);
        string publicKeysFileName = "userPublicKeys";
        string privateKeysFileName = "userPrivateKeys";
        string publicKeysFilePath = filePath + fileName;
        string privateKeysFilePath = txtManager->replace(filePath,publicKeysFileName,privateKeysFileName) + fileName;
        Encryption* encryption = new Encryption();
        encryption->generateKeys(my_rank+1);
        txtManager->writeFile(publicKeysFilePath, to_string(encryption->getPublicKey()));
        string private_module = to_string(encryption->getPrivateKey()) + "\n" + to_string(encryption->getModulo());
        txtManager->writeFile(privateKeysFilePath, private_module);
        cout << "\nUser : " << fileName << endl;
        cout << "PriK: " << encryption->getPrivateKey() << endl;
        cout << "PublK: " << encryption->getPublicKey()<< endl;
    }

    void decrypting(string filePath,string fileName){
        DataProvider* txtManager = new DataProvider();
/*        int my_rank = omp_get_thread_num();
        int thread_count = omp_get_num_threads();
        printf("Decryption thread %d of %d\n", my_rank+1, thread_count);*/


        string encryptionFileName = "userEncryption";
        string decryptionFileName = "userDecryption";
        string privateKeysFileName = "userPrivateKeys";

        string encryptionFilePath = filePath + fileName;
        string decryptionFilePath = txtManager->replace(filePath,encryptionFileName,decryptionFileName) + fileName;
        string privateKeysFilePath = txtManager->replace(filePath,encryptionFileName,privateKeysFileName) + fileName;
        cout << "decryptionFilePath: " << decryptionFilePath << endl;


        string fileKeys = txtManager->readFile(privateKeysFilePath);
        vector<string> keys = txtManager->getLineData(fileKeys);

        string fileEncryption = txtManager->readFile(encryptionFilePath);
        vector<string> message = txtManager->getLineData(fileEncryption);

        double privateKey, module, bet, energy;

        try{
            privateKey = stod(keys[0]);
            module = stod(keys[1]);
            bet = stod(message[0]);
            energy = stod(message[1]);
        }
        catch(exception e){
            cout << "something Wrong" << endl;
        }

        Encryption* crypt = new Encryption();
        bet = crypt->decrypt(bet,privateKey,module);
        energy = crypt->decrypt(energy,privateKey,module);
        string dec = to_string(bet) + "\n" + to_string(energy);
        txtManager->writeFile(decryptionFilePath,dec);

    }

public:
    void startThreads(string path) {

        DataProvider * txtManager = new DataProvider();
        int previousFileCount = 0;
        vector<string> previousFiles = {};

        int previousEncryptionFileCount = 0;
        vector<string> previousEncryptionFiles = {};

        //omp_set_thread_num(fileCount); // Number of threads is equal to the number of files

        while(true){
            vector<string> filePaths = txtManager->getFilePaths(path);

            int fileCount = filePaths.size();
            if ( previousFileCount < fileCount){

                sort(previousFiles.begin(),previousFiles.end());
                sort(filePaths.begin(),filePaths.end());

                vector<string> diff;
                set_difference(filePaths.begin(), filePaths.end(), previousFiles.begin(), previousFiles.end(),
                               std::inserter(diff, diff.begin()));
                cout << "\n\nNEW FILE COUNT :"<<diff.size() << endl;

                #pragma omp parallel for
                for (int i = 0; i < diff.size(); i++) {
                    Sleep(500 * omp_get_thread_num());
                    prepareKeys(path, diff[i]);
                }
            }
            else{}
            previousFiles = filePaths;
            previousFileCount = fileCount;

            Sleep(250);


            string encryptionPath = "D:\\Users\\aguil\\Documents\\TEC_Files\\7 - IS 2021\\Analisis de Algoritmos\\Proyecto\\Proyecto II\\TEC_CR_Algorith_Analysis_OctopusArena\\userEncryption\\";
            vector<string> encryptionFiles = txtManager->getFilePaths(encryptionPath);
            int encryptionFileCount = encryptionFiles.size();
            if ( previousEncryptionFileCount < encryptionFileCount){
                sort(encryptionFiles.begin(),encryptionFiles.end());
                sort(previousEncryptionFiles.begin(),previousEncryptionFiles.end());

                vector<string> diff;
                set_difference(encryptionFiles.begin(), encryptionFiles.end(), previousEncryptionFiles.begin(), previousEncryptionFiles.end(),
                               std::inserter(diff, diff.begin()));
                cout << "\n\nNEW FILE COUNT :"<<diff.size() << endl;

                #pragma omp parallel for
                for (int i = 0; i < diff.size(); i++) {
                    Sleep(250 * omp_get_thread_num());
                    decrypting(encryptionPath, diff[i]);
                }

            }
            previousEncryptionFiles = encryptionFiles;
            previousEncryptionFileCount = encryptionFileCount;

            Sleep(250);

        }

        char a;
        cin >> a;
    }
};
