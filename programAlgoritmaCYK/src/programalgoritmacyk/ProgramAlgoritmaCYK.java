package programalgoritmacyk;
import java.util.Scanner;
import java.io.*;
import java.util.Vector;

public class ProgramAlgoritmaCYK {
    //pembuatan varibel yang digunakan dalam program
    static int h,i,j,k,l=0,a,b,c;
    static boolean status = false;
    static String baris[]=new String[50];
    static String var[]=new String[50]; //untuk variabel (S, A, B)
    static String ktr[]=new String[50];
    static String ter[][]=new String[50][50]; //untuk terminal (a,b,c)
    static int bter[]=new int [50];
    static String x[][][]=new String[50][50][50];
    static int px[][]=new int [50][50];
    static String x1[][][]=new String[50][50][50];
    static String xt[][]=new String[50][50];
    
    //methode untuk mengambil file txt, kemudian data diambil per-baris, kemudian split data per-baris yang mengandung tanda "="
    public static void ambilFile() throws FileNotFoundException{
        File grammar = new File("dataset.txt");
        Scanner in=new Scanner(grammar);  //menscan data dalam file 
        Vector<String> v=new Vector<String>();
        while(in.hasNextLine()){  //mengambil data berdasarkan baris pada file
            v.add(in.nextLine());
        }
        //melakukan perulangan sesuai banyaknya baris pada fule
        for(i=0;i<v.size();i++){
            baris[i]=v.get(i);  //setiap baris dalam file disimpan dalam array baris lalu diberikan index sesuai urutan barisnya
            String[] bagian = baris[i].split("="); //split (pisahkan) data per-baris yang mengandung tanda "="
            j=0;
            for(String bg :bagian){
                if(j==0){ 
                    var[i]=bg; //elemen pertama setiap baris ditampung oleh array var
                 }
                else{ 
                    ktr[i]=bg;  //selain elemen pertama pada baris ditampung dalam array ktr
                    String[] bagian2 = bg.split(" "); //split lagi dengan memisahkannnya berdasarkan tanda spasi " "
                    k=0;
                    l=0;
                    for(String bg2 :bagian2){
                        if(!bg2.equals("|")){ 
                            ter[i][l]=bg2; //hasil pemisahan elemen disimpan pada array ter
                            l++;
                        }k++;
                    }
                }j++;
            }
            bter[i]=l; //menampung panjang terminal
        }
    }
    //method untuk menentukan nilai pada baris pertama
    public static void startRow(int z, int pklm, int bvar, String kata[]){
        for(i=0;i<pklm-z;i++){
            a=i;
            b=0;
            for(j=0;j<bvar;j++){
                for(k=0;k<bter[j];k++){
                    if(kata[i].equals(ter[j][k])){  
                        x[i][a][b]=var[j];
                        b++;
                        px[i][a]=b;
                    }
                }
            }
        }    
    }
    //method untuk menentukan nilai pada baris setelah baris pertama
    public static void nextRow(int z, int pklm, int bvar){
        for(i=0;i<pklm-z;i++){
            a=i+z;
            b=0;
            c=0;
            for(h=i;h<z+i;h++){
                for(j=0;j<px[i][h];j++){
                    for(k=0;k<px[h+1][a];k++){
                        x1[i][a][b]= x[i][h][j].concat(" ").concat(x[h+1][a][k]+" ");
                        for(l=0;l<bvar;l++){
                            if(x1[i][a][b].equals(ktr[l])){
                                x[i][a][c]=var[l];
                                c++;
                                px[i][a]=c;
                            }
                        }b++;
                    }
                }
            }
        }
    }
    
    //mengecek apakah array production sama dengan production S
    public static void cekAccept(int pklm){
        for(i=0;i<px[0][pklm-1];i++){
            if(x[0][pklm-1][i].equals("S")){
                status=true;
            }else if(!x[0][pklm-1][i].equals("S")&&status==true){
                status=true;
            }else{
                status=false;
            }
        }
    }
    
    //method untuk membuat tabel hasil Parsing 
    public static void showTable(int pklm, String kata[], int z){
        System.out.println("\nTampil tabel :");
            for(i=0;i<pklm;i++){
                System.out.print("\t"+kata[i]+"\t\t");
                xt[i][i]="";
            }
            z=1;
            while(z<pklm){
                for(i=0;i<pklm-z;i++){
                    xt[i][i+z]="";
                }
                z++;
            }
            System.out.print("\n-----------------------------------------------------------------------------\n");
            z=0;
            while(z<pklm){
                if(z==0){
                    for(i=0;i<pklm;i++){
                        for(j=0;j<px[i][i];j++){
                            if(j==px[i][i]-1){
                                xt[i][i]=xt[i][i].concat(x[i][i][j]);
                            }else{
                                xt[i][i]=xt[i][i].concat(x[i][i][j]).concat(", ");
                            }
                        }
                        System.out.print("\t"+xt[i][i]+"\t");
                    }
                    System.out.print("\n");
                }else{
                    for(i=0;i<pklm-z;i++){
                        for(j=0;j<px[i][i+z];j++){
                            if(j==px[i][i+z]-1){
                                xt[i][i+z]=xt[i][i+z].concat(x[i][i+z][j]);
                            }
                            else{
                                xt[i][i+z]=xt[i][i+z].concat(x[i][i+z][j]).concat(", ");
                            }
                        }
                        System.out.print("\t"+xt[i][i+z]+"\t");
                    }
                }
                System.out.print("\n");
                z++;
            }
    }
    public static void main(String[] args) throws FileNotFoundException {
        String kalimat;
        int h,m,pklm,bvar,br,z;
        String kata[]=new String[50];
        Scanner input= new Scanner(System.in);
        
            //user menginputkan kalimat yang akan dicek termasuk bahasa baku atau tidak
            System.out.print("Masukan Kalimat : ");
            kalimat=input.nextLine();  //ditampung oleh variabel kalimat

            //membuat variabel klm berupa array yang berisi data per kata yang diinputkan user dengan method split spasi maka tiap kata akan masuk ke 
            //variabel array
            String[] klm = kalimat.split(" ");
            i=0;
            //membuat perulangan untuk memasukkan kata yang diinput kedalam string kata[i] dan di beri index
            for(String kt :klm){
                kata[i]=kt;
                i++;
            }
            pklm=i; //variabel untuk menentukan banyaknya kata yang ada dalam kalimat yang diinput user
            br=pklm; //variabel counter untuk batas perulangan CYK Parser
            ambilFile();  //memanggil method ambilFile untuk mengambil setiap rules dalam file
            bvar=i;
            z=0;
            while(z<br){
                if(z==0){
                    startRow(z, pklm, bvar, kata);  //memanggil method startRow untuk menentukan nilai baris pertama
                } 
                else{
                    nextRow(z, pklm, bvar); //memanggil method nextRow untuk menentukan nilai baris selanjutnya
                }z++;
            }

            //method cekAccept akan mengecek dan mereturn sebuah boolean jika 1 maka kalimat baku jika 0 maka kalimat tidak baku da
            cekAccept(pklm);
            if(status){
                System.out.println("\nKalimat '"+kalimat+"' merupakan bahasa baku");
                showTable(pklm, kata, z);     //memanggil method showTable untuk mencetak bentuk tabel cyk       
            }
            else{
                System.out.println("\nKalimat '"+kalimat+"' merupakan bahasa tidak baku");
            }
            
    }
    
}