/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programnaturallanguage;
import java.util.Scanner;
import java.io.*;
import java.util.Vector;
/**
 *
 * @author user
 */
public class ProgramNaturalLanguage {
    static int h,i,j,k,l=0,m,a,b,c,bbar,p=0,q=0,pklm,br,z,bvar;
    static boolean status = false;
    static boolean lex[] = new boolean[50];
    static boolean lex2[] = new boolean[300];
    static String akhir,klmt,pesan,start,read;
    static String baris[]=new String[50];
    static String varbaru[]=new String[50];
    static String var[]=new String[50]; //untuk variabel (S, A, B)
    static String var2[]=new String[50]; //untuk variabel (S, A, B)
    static String ktr[]=new String[300];
    static String ktr2[]=new String[300];
    static String ktrbaru[]=new String[300];
    static String ter[][]=new String[300][300]; //untuk terminal (a,b,c)
    static String ter2[][]=new String[300][300]; //untuk terminal (a,b,c)
    static int bter[]=new int [100];
    static int bter2[]=new int [300];
    static String x[][][]=new String[50][50][50];
    static int px[][]=new int [50][50];
    static String x1[][][]=new String[300][300][300];
    static String xt[][]=new String[50][50];
    static String kalimat[]=new String[300];
    static String kata[]=new String[300];
    static String tree[][]=new String[300][300];
    static String parent[]=new String[300];
    static String child[][]=new String[300][300];
    /**
     * @param args the command line arguments
     */
    //methode untuk mengambil file txt, kemudian data diambil per-baris, kemudian split data per-baris yang mengandung tanda "="
    public static void ambilFileRule() throws FileNotFoundException{
        File grammar = new File("grammarA4.txt");
        Scanner in=new Scanner(grammar);  //menscan data dalam file 
        Vector<String> v = new Vector<String>();
        while(in.hasNextLine()){  //mengambil data berdasarkan baris pada file
            v.add(in.nextLine());
        }
        
        //melakukan perulangan sesuai banyaknya baris pada rule
        //lalu memisahkan antara tanda ->, | dan " ", dan "" dengan kalimat untuk dimasukan kedalam variabel array
        
        for(i=0;i<v.size();i++){
            baris[i]=v.get(i);
            lex[i]=false;
            String[] bagian = baris[i].split(" -> ");
            j=0;
            for(String bg :bagian){
                if(j==0){ var[i]=bg; }
                else{ 
                    ktr[i]=bg;
                    String[] bagian2 = ktr[i].split(" ");
                    k=0;
                    for(String bg2 :bagian2){
                        if(bg2.equals("|")){
                            lex[i]=true;
                        }
                        if(!bg2.equals("|")){ 
                            ter[i][k]=""; 
                            char[] simbol = bg2.toCharArray();
                            char[] simbol2 = new char[20];
                            int m=0;
                            for(l=0;l<simbol.length;l++){
                                if(simbol[l]!='"'){
                                   simbol2[m] =simbol[l];
                                   ter[i][k]=ter[i][k].concat(Character.toString(simbol2[m]));
                                   m++;
                                }
                            }
                            k++;
                        }
                    }
                }
                j++;
            }
            bter[i]=k;
        }
        bbar=v.size();
    }
    
    public static void ambilFileKalimat(int no) throws FileNotFoundException{
        File sentences = new File("kalimatA2.txt");
        Scanner in=new Scanner(sentences);
        Vector<String> v=new Vector<String>();
        while(in.hasNextLine()){  //mengambil data berdasarkan line
            v.add(in.nextLine());
        }
        for(i=0;i<v.size();i++){
            kalimat[i]=v.get(i);
            if(no==i+1){
                klmt=kalimat[i];
                simpanFile("Kalimat '"+klmt+"'");
                String[] klm = kalimat[i].split(" ");
                j=0;
                for(String kt :klm){
                    kata[j]=kt;
                    j++;
                }
                pklm=j;
                br=pklm;
                System.out.println("pklm= "+pklm);
            }
        }
    }
   
    //mengambil variabel sebalah kanan dan memisahkannya sesuai indexnya misal : 
    // kalimat -> S P
    // variabel[0] = kalimat, ter[0][0] = S, ter[0][1] = P
    public static void setTer(int p, String ktr2[]){
        String[] bagian2 = ktr2[p].split(" ");
        k=0;
        for(String bg2 :bagian2){

            if(bg2.equals("|")){
                lex2[p]=true;
            }
            if(!bg2.equals("|")){ 
                ter2[p][k]=""; 
                char[] simbol = bg2.toCharArray();
                char[] simbol2 = new char[20];
                int m=0;
                for(l=0;l<simbol.length;l++){

                    if(simbol[l]!='"'){
                        simbol2[m] =simbol[l];
                        ter2[p][k]=ter2[p][k].concat(Character.toString(simbol2[m]));
                        m++;
                    }
                }
                k++;
            }
        }
        bter2[p]=k;
    }
    
    //method untuk merubah file rule di txt, menjadi CNF, dengan mengecek tiap baris rule jika sudah 2 simbol di kanan
    //jika tidak maka 2 simbol pertama akan dijadikan 1 dan di oper ke variabel bernama varbar
    public static void chomsky(){
        for(i=0;i<bbar;i++){
            if(!lex[i]){
                if(bter[i]==1){
                    for(j=0;j<bbar;j++){
                        if(var[j].equals(ktr[i])){
                            var2[p]=var[i];
                            if(lex[j]){
                                ktr2[p]=ktr[j];
                                setTer(p, ktr2);
                                p++;
                            }else{
                                if(bter[j]!=2){
                                    for(l=0;l<bbar;l++){
                                        if(lex[l]){
                                            if(var[l].equals(ktr[j])){
                                                ktr2[p]=ktr[l];
                                                setTer(p, ktr2);
                                                p++;
                                                break;
                                            }
                                        }
                                    }
                                }else{
                                    ktr2[p]=ktr[j];
                                    setTer(p, ktr2);
                                    p++;
                                }
                            }
                        }
                    }
                }
                else if(bter[i]>2){
                    varbaru[q]="";
                    ktrbaru[q]="";
                    for(j=0;j<bter[i];j++){
                        if(j!=bter[i]-1){
                            varbaru[q]=varbaru[q].concat(ter[i][j]);
                            if(j==bter[i]-2)
                                ktrbaru[q]=ktrbaru[q].concat(ter[i][j]);
                            else
                                ktrbaru[q]=ktrbaru[q].concat(ter[i][j]).concat(" ");
                        }else{
                            akhir=ter[i][j];
                        }
                    }

                    var2[p]=var[i];
                    ktr2[p]=varbaru[q].concat(" ").concat(akhir);
                    setTer(p, ktr2);
                    p++;
                    q++;
                }else if(bter[i]==2){
                    var2[p]=var[i];
                    ktr2[p]=ktr[i];
                    setTer(p, ktr2);
                    p++;
                }
            }
            else{
                var2[p]=var[i];
                ktr2[p]=ktr[i];
                setTer(p, ktr2);
                p++;
            }
        }
        bvar=p;
    }
    
    //method ini untuk menyempurnakan method chomsky() dimana semisal sudah diubah mejadi 2 var yang di kanan akan di cek lagi
    // Misal : pada method chomsky ->
    //Kalimat -> S P O diubah menjadi Kalimat -> SP O
    // Pada method varBaru() dari SP yang sudah digabung di pecah lagi menjadi 
    // SP -> S P
    public static void varBaru(){
        p=bvar;
        for(i=0;i<varbaru.length;i++){
            if(varbaru[i]!=null&&ktrbaru[i]!=null){
                if(i==0){
                    var2[p]=varbaru[i];
                    ktr2[p]=ktrbaru[i];
                    String[] bktr = ktrbaru[i].split(" ");
                    if(bktr.length==2){
                        setTer(p, ktr2);
                    }
                    p++;
                }else{
                    if(!varbaru[i].equals(varbaru[i-1])){
                        var2[p]=varbaru[i];
                        String[] bktr = ktrbaru[i].split(" ");
                        if(bktr.length==2){
                            ktr2[p]=ktrbaru[i];
                            setTer(p, ktr2);
                        }else{
                            akhir=bktr[2];
                            ktr2[p]=varbaru[0].concat(" ").concat(akhir);
                            setTer(p, ktr2);
                        }
                        p++;
                    }
                }
            }
        }
        bvar=p;
    }
    
    //method untuk menentukan nilai pada baris pertama
    //method yang bertujuan untuk mengecek dari rule dan kata yang disediakan
    public static void startRow(int z, int pklm, int bvar, String kata[]){
        for(i=0;i<pklm-z;i++){
            System.out.println("kata["+i+"] = "+kata[i]);
            a=i;
            b=0;
            for(j=0;j<bvar;j++){
                for(k=0;k<bter2[j];k++){
                    if(kata[i].equals(ter2[j][k])){  
                        x[i][a][b]=var2[j];
                        b++;
                        px[i][a]=b;             
                    }
                }
            }
        }    
    }
    
    //method sesuai dengan algoritma CYK
    public static void nextRow(int z, int pklm, int bvar){
        for(i=0;i<pklm-z;i++){
            a=i+z;
            b=0;
            c=0;
            for(h=i;h<z+i;h++){
                for(j=0;j<px[i][h];j++){
                    for(k=0;k<px[h+1][a];k++){
                        x1[i][a][b]= x[i][h][j].concat(" ").concat(x[h+1][a][k]);
                        for(l=0;l<bvar;l++){
                            if(x1[i][a][b].equals(ktr2[l])){
                                x[i][a][c]=var2[l];
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
            if(x[0][pklm-1][i].equals("kalimat")){
                status=true;
            }else if(!x[0][pklm-1][i].equals("kalimat")&&status==true){
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
            System.out.print("\n----------------------------------------------------------------------------------------------------------------------------\n");
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
    //method untuk menyimpan hasil (output) kedalam file hasil.txt
    public static void simpanFile(String text){
        File data= new File("hasil.txt");
        try(FileWriter fw = new FileWriter(data, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw))
        {
            out.println(text);
        } catch (IOException e) {
            System.out.println("File tidak dapat disimpan");
        }
    }
    //method untuk menampilkan bentuk parsing tree
    public static String ulangTree(String var[], int k, int a, int b){
        for(i=0;i<bbar;i++){
            for(j=0;j<bter[i];j++){
                if(ter[i][j].equals(var[k])){
                    if(var[i].equals(start)){
                        if(b==0){
                            System.out.println("\t\t\t\t"+var[i]);
                            simpanFile("\t\t\t\t"+var[i]);
                            b++;
                        }
                    }else{
                        if(a==1){
                            System.out.println("\t\t"+var[i]);
                            simpanFile("\t\t"+var[i]);
                        }else if(a==2){
                            System.out.println("\t\t\t"+var[i]);
                            simpanFile("\t\t\t"+var[i]);
                        }
                        a++;
                        ulangTree(var, i, a,b);
                        
                    }
                }
            }
        }
        return var[i];
    }
    public static void main(String[] args) throws FileNotFoundException {
        Scanner input= new Scanner(System.in);
        int no;
        // TODO code application logic here
        ambilFileRule();
        chomsky();
        System.out.println("bvar= "+bvar);
        varBaru();
        System.out.println("bvar= "+bvar);
        System.out.print("Masukan Nomor Kalimat : ");
        no=input.nextInt();
        ambilFileKalimat(no);
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
            System.out.println("\nKalimat '"+klmt+"' merupakan bahasa baku");
            simpanFile("Merupakan bahasa baku");
            showTable(pklm, kata, z);     //memanggil method showTable untuk mencetak bentuk tabel cyk  
            System.out.println("\nTampil pohon parsing:\n");
            simpanFile("Pohon Parsing : ");
            ambilFileRule();
            start="kalimat";
            a=1;
            b=0;
            z=0;
            while(z<pklm){
                read=kata[z];
                System.out.println(""+read);
                simpanFile(read);
                for(j=0;j<bbar;j++){
                    for(k=0;k<bter[j];k++){
                        if(ter[j][k].equals(read)){
                            System.out.println("\t"+var[j]);
                            simpanFile("\t"+var[j]);
                            ulangTree(var, j,a,b);
                        }
                    }
                }
                z++;
            }
            simpanFile("==============================================================================================================================");
        }
        else{
            System.out.println("\nKalimat '"+klmt+"' merupakan bahasa tidak baku");
            simpanFile("Merupakan bahasa tidak baku");
            simpanFile("==============================================================================================================================");
        
        } 
    }
}
