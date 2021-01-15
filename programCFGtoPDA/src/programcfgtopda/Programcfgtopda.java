package programcfgtopda;
import java.util.Scanner;
import java.util.Stack;
import java.io.*;
import java.util.Vector;

public class Programcfgtopda { 
    static Stack <String> ch = new Stack<>();
    static Stack <String> ch2 = new Stack<>();
    static String ssimbol="z0";
    static String[] states = new String[10];
    static int i,j,k,l=0,m,pklm,br,bbar,x,y,belum=0,sudah=0, b=0;
    static String kata[]=new String[50];
    static String baris[]=new String[50];
    static String var[]=new String[50]; //untuk variabel (S, A, B)
    static String ter[][]=new String[50][50]; //untuk terminal (a,b,c)
    static String ktr[]=new String[50];
    static int bter[]=new int [50];
    static int n[]=new int [50];
    static String sim[]=new String[100];
    static boolean adasimbol = false;
    static boolean adasimbol1[] = new boolean[50];
    
    public static void inisialisasi(String[][][] delta){  
        for(i=0; i<100; i++){
            for(j=0; j<100; j++){
                for(k=0; k<100; k++){
                    delta[i][j][k]=null;
                }
            }
        }
    }
    public static void rekursif(String[][][] delta){ //ni
//        System.out.println("ch2.size="+ch2.size());
//        if(ch2.size()!=0){
            String[][][] delta2 = delta;
            
                if(ch2.peek().equals(ch.peek())){
                    
                }else{
                    ch2.push("e");
                    System.out.println("ch.peek: "+ch.peek());
                    System.out.println("ch2.peek: "+ch2.peek());
                    for(j=0;j<m;j++){
                        if(sim[j].equals(ch2.peek())){
                            x=j;
                            System.out.println("x: "+x);
                            System.out.println("sim["+j+"] = "+sim[j]);
                        }
                        if(j==0){
                            for(k=0;k<bbar;k++){
                                System.out.println("k: "+k);
                                if(adasimbol1[k]){
                                    for(l=0;l<bter[k];l++){
                                        if(ter[k][l].equals(ch.peek())){
                                            System.out.println("delta2["+x+"]["+k+"]["+l+"] = "+delta2[x][k][l]);
                                            ch.push(delta2[x][k][l]);
                                            ch2.pop();
                                        }   
                                        
                                    }
                                }else{
                                    if(var[k].equals(ch.peek())){
                                        ch.pop();
                                        System.out.println("delta2["+x+"]["+k+"][0] = "+delta2[x][k][0]);
                                        System.out.println("bter["+k+"] = "+bter[k]);
                                        for(l=(bter[k])-1;l>=0;l--){
                                            System.out.println("ter["+k+"]["+l+"]= "+ter[k][l]);
                                            ch.push(ter[k][l]);
                                        }
                                        
                                        ch2.pop();
                                    }
                                    
                                }
                                System.out.println("ch.size: "+ch.size());
                                System.out.println("ch2.size: "+ch2.size());
//                                if(!ch2.peek().equals(ch.peek())){
//                                    ch2.push("e");
//                                }
                            }
                        }
                        else{
//                            System.out.println("masuk sini");
                            if(ch.peek().equals(sim[j])){
//                                ch
                            }
                        }
                    }
//                    rekursif(delta2);
                }
                System.out.println("ch.peek: "+ch.peek());
                System.out.println("ch2.peek: "+ch2.peek());
//        } 
//            rekursif(delta);
    }
    public static void main(String[] args) throws FileNotFoundException {
        Scanner input= new Scanner(System.in);
        String kalimat;
        
        
        String[][][] delta = new String[100][100][100];
        
        
        System.out.print("Masukan Kalimat : ");
        kalimat=input.nextLine();//menerima input kalimat..
        String[] klm = kalimat.split(" ");//menyimpan kalimat diarray string klm..
        i=0;
        for(String kt :klm){//meloping per kata pada sebuah kalimat untuk disimpan di kata..
            kata[i]=kt;//kt disimpan di kata..
            System.out.println("kata["+i+"] = "+kata[i]);
            i++;
        }
        pklm=i;//pklm adalah i yang terdapat kata
        br=pklm;//br adalah pklm 
        System.out.println("pklm= ");
        ch.push("z0");//mendeklarasikan variabel state ke z0..
        ch.push("e");//mendeklarasikan variabel epsilon..
        ch.push("S");//mendeklarasikan variabel S
        for(i=pklm-1;i>=0;i--){//looping kalimat untuk menampilkan isi array
            ch2.push(kata[i]);
            System.out.println(""+ch2.peek());//menampilkan kalimat per kata, dari i
        }
        ch2.push("e");
        System.out.println(""+ch2.peek());

        File grammar = new File("D:\\grammar2.txt");//dicocokan dengan kata didalam file
        Scanner in = new Scanner(grammar);
        Vector<String> v=new Vector<String>();
        while(in.hasNextLine()){  //mengambil data berdasarkan line
            v.add(in.nextLine());
        }
        
        for(i=0;i<v.size();i++){
            baris[i]=v.get(i);
            System.out.println("\nbaris["+i+"] = "+baris[i]);
            String[] bagian = baris[i].split(" ");
            j=0;
            for(String bg :bagian){
                System.out.println("bg["+j+"] = "+bg);
                if(j==0){ 
                    var[i]=bg; 
                }
                else{ 
                    ktr[i]=bg;
                    System.out.println("ktr["+i+"] = "+ktr[i]);
                    String[] bagian2 = bg.split(" ");
                    l=0;
                    for(String bg2 :bagian2){
//                        System.out.println("bg2["+k+"] = "+bg2);
                        if(bg2.equals("|")){
                            adasimbol=true;
                            System.out.println("adasimbol = ada");
                        }else if(adasimbol==true&&!bg2.equals("|")){
                            adasimbol=true;
                        }else{
                            adasimbol=false;
                        }
                        if(!bg2.equals("|")){ 
                            ter[i][l]=bg2; 
                            System.out.println("ter["+i+"]["+l+"] = "+ter[i][l]);
//                            if(adasimbol==true){
//                                sim[m]=ter[i][l-1];
//                                System.out.println("sim["+m+"] = "+sim[m]);
//                                m++;
//                            }
                            l++;
                        }
                    }
                }
                j++;
            }
            if(adasimbol==true){
                adasimbol1[i]=true;
                System.out.println("adasimbol1["+i+"] = ada");
            }else{
                adasimbol1[i]=false;
                System.out.println("adasimbol1["+i+"] = tidak ada");
            }
            System.out.println("var["+i+"] = "+var[i]);
            bter[i]=l;
            System.out.println("bter["+i+"] = "+bter[i]);
        }
        bbar=i;
        System.out.println("bbar : "+bbar);
        m=1;
        sim[0]="e";
        n[0]=bbar;
        inisialisasi(delta);
//        System.out.println("\n\n==delta[90][90][90] = "+delta[90][90][90]+"==\n\n");
//        for(i=0;i<3;i++){
//            states[i] = Integer.toString(i);
//            System.out.println("states["+i+"] : "+states[i]); 
//        }
//        delta[0][0]="S";
        for(i=0;i<bbar;i++){
            System.out.println("adasimbol1["+i+"] = "+adasimbol1[i]);
            if(adasimbol1[i]==true){
                System.out.println("bter["+i+"] = "+bter[i]);
                for(j=0;j<bter[i];j++){
                    System.out.println("ter["+i+"]["+j+"] = "+ter[i][j]);
//                    n++;
                    delta[0][i][j]=ter[i][j];
                    System.out.println("delta[0]["+i+"]["+j+"] = "+delta[0][i][j]);
                    sim[m]=ter[i][j];
                    System.out.println("sim["+m+"] = "+sim[m]);
                    m++;
                }
//                n++;
            }else{
                delta[0][i][0]=ktr[i];
                System.out.println("delta[0]["+i+"][0] = "+delta[0][i][0]);
//                n=i;
            }
//            System.out.println("n : "+n);
        }
        System.out.println("m : "+m);
        
        for(i=1;i<m;i++){
            System.out.println("sim["+i+"] = "+sim[i]);
            delta[i][i][0]="e";
            System.out.println("delta["+i+"]["+i+"][0] = "+delta[i][i][0]);
//            n[i]=1;
        }
        System.out.println("m : "+m);
         System.out.println("bbar : "+bbar);
//        for(i=0;i<bbar;i++){
//            delta[0][i]=
//        }
    
        for(i=0; i<100; i++){
            for(j=0; j<100; j++){
                for(k=0; k<100; k++){
//                    System.out.println("delta["+i+"]["+j+"]["+k+"] = "+delta[i][j][k]);
                }
            }
        }
    

//        rekursif(delta);
        int a=0;
        System.out.println("a="+a);
        while(a==0){
            sudah=0; belum=0;
            System.out.println("\n==awal==\n");
            System.out.println("ch.peek: "+ch.peek()+"  ch.size : "+ch.size());
            System.out.println("ch2.peek: "+ch2.peek()+"  ch2.size : "+ch2.size());
            if(ch2.peek().equals(ch.peek())){
                    System.out.println("\njika sama\n");
                    System.out.println("a="+a);
            }else{
                System.out.println("\njika tidak\n");
                    ch2.push("e");
                    System.out.println("ch.peek: "+ch.peek()+"  ch.size : "+ch.size());
                    System.out.println("ch2.peek: "+ch2.peek()+"  ch2.size : "+ch2.size());
                    for(j=0;j<m;j++){
                        if(sudah==0){
                        if(sim[j].equals(ch2.peek())){
                            x=j;
                            System.out.println("x: "+x);
                            System.out.println("sim["+j+"] = "+sim[j]);
                        }
                        if(x==0){
                            for(k=0;k<bbar;k++){
                                System.out.println("k: "+k);
                                if(belum==0){
                                    
                                if(adasimbol1[k]){
                                    System.out.println("\njika ada simbol\n");
                                    System.out.println("var["+k+"] = "+var[k]);
                                    if(var[k].equals(ch.peek())){
                                        ch.pop();
                                        for(l=0;l<bter[k];l++){
                                            System.out.println("b="+b);
                                            if(b==0){
                                                System.out.println("ter["+k+"]["+l+"]= "+ter[k][l]);
                                            ch.push(ter[k][l]);
                                            ch2.pop();
                                             System.out.println("ch.peek: "+ch.peek()+"  ch.size : "+ch.size());
                                             System.out.println("ch2.peek: "+ch2.peek()+"  ch2.size : "+ch2.size());
                                            if(ch2.peek().equals(ch.peek())){
                                                System.out.println("sama");
                                                ch.pop();
                                                ch2.pop();
                                                ch2.push("e");
                                                System.out.println("ch.peek: "+ch.peek()+"  ch.size : "+ch.size());
                                             System.out.println("ch2.peek: "+ch2.peek()+"  ch2.size : "+ch2.size());
                                            }else{
                                                System.out.println("tidak sama");
                                                ch2.push("e");
                                                ch.pop();
                                                System.out.println("ch.peek: "+ch.peek()+"  ch.size : "+ch.size());
                                             System.out.println("ch2.peek: "+ch2.peek()+"  ch2.size : "+ch2.size());
                                            }
                                            
                                            }
                                            if(ch.peek().equals("z0")){
                                                b=1;
                                                
                                            }
                                            System.out.println("b="+b);
                                        }
                                        if(ch2.peek().equals("e")){
                                            ch2.pop();
                                        }
                                        belum=1;
                                        sudah=1;
                                    }
                                }else{
                                    System.out.println("\njika tidak ada simbol\n");
                                    if(var[k].equals(ch.peek())){
                                        ch.pop();
                                        System.out.println("delta["+x+"]["+k+"][0] = "+delta[x][k][0]);
                                        System.out.println("bter["+k+"] = "+bter[k]);
                                        for(l=(bter[k])-1;l>=0;l--){
                                            System.out.println("ter["+k+"]["+l+"]= "+ter[k][l]);
                                            ch.push(ter[k][l]);
                                        }
                                        
                                        ch2.pop();
                                        belum=1;
                                        sudah=1;
                                    }
                                    
                                }
                                System.out.println("ch.peek: "+ch.peek()+"  ch.size : "+ch.size());
                                }
                            }
                                if(ch.peek().equals("z0")){
                                    a=1;
                                }
                            }
                        }
                    }
                    System.out.println("a="+a);
                }
                System.out.println("ch.peek: "+ch.peek()+"  ch.size : "+ch.size());
        }
        if(ch.peek().equals("z0")){
            System.out.println("String dpt diterima");
        }else{
            System.out.println("String tdk dpt diterima");
        }
    }
    
}
