//
// Created by anagu on 25/6/2021.
//

#include <fstream>
#include <Windows.h>
#include <vector>
#include <iostream>

using namespace std;

class DataProvider{

public:

    vector<string> getLineData(string data){
        stringstream ss(data);
        string line;
        vector<string> lineData;
        while(getline(ss, line)){
            lineData.push_back(line);
        }
        return lineData;
    }

    string replace(string original, string stringToFind, string stringToReplace){
        //  "C:\\Users\\anagu\\OneDrive\\Documentos\\TEC\\SemestreI2021\\Analisis\\TEC_CR_Algorith_Analysis_OctopusArena\\userEncryption\\"
        // buscando "userEncryption"
        // reemplazar "userDecryption"
        int start_pos = original.find(stringToFind);
        int size =  stringToFind.size();
        string newString = original.replace(start_pos, size, stringToReplace);
        return newString;
    }

    vector<string> getFilePaths(string folder)
    {
        vector<string> names;
        string search_path = folder + "/*.*";
        WIN32_FIND_DATA fd;
        HANDLE hFind = ::FindFirstFile(search_path.c_str(), &fd);
        if(hFind != INVALID_HANDLE_VALUE) {
            do {
                // read all (real) files in current folder
                // , delete '!' read other 2 default folder . and ..
                if(! (fd.dwFileAttributes & FILE_ATTRIBUTE_DIRECTORY) ) {
                    names.push_back(fd.cFileName);
                    //cout << fd.cFileName;
                }
            }while(::FindNextFile(hFind, &fd));
            ::FindClose(hFind);
        }
        return names;
    }

    // write or re-write files
    void writeFile(const string& filepath, const string& fileText){
        ofstream myfile;
        myfile.open (filepath);
        myfile << fileText;
        myfile.close();
    }

    string readFile(const string& filePath){
        string line;
        ifstream myFile (filePath);
        string fileText;
        if (myFile.is_open())
        {
            while ( getline (myFile, line) )
            {
                fileText += line + '\n';
                //cout << line << '\n';
            }
            //fileText.erase(line.size()-1);
            myFile.close();
            return fileText;
        }
        else return "NOT_FOUND";
    }

};
