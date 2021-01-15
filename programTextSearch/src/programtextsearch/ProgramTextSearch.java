package programtextsearch;
import java.util.Scanner;
import java.io.*;
import java.util.Vector;

public class ProgramTextSearch { /*class untuk data text yang akan di search*/
    public static void main(String[] args) throws FileNotFoundException {
        Scanner input= new Scanner(System.in);
        int bKalimat=0,pilih,x=0,y=0,a=0;/*Deklarasi string kalimat, indeks pemilihan huruf dalam kalimat, serta hasil seleksi kalimat*/
        int bHuruf[]=new int[1000];/*Deklarasi array indeks huruf dalam kalimat*/
        int index1[]=new int[1000];/*Deklarasi array indeks seleksi huruf*/
        int index2[]=new int[1000];/*Deklarasi array indeks hasil seleksi*/
        String start;
        String kalimat="";
        String read="";
        String baris[]=new String[1000];/*Deklarasi array indeks baris dalam kalimat*/
        String huruf1[][]=new String[1000][1000];/*Deklarasi array indeks seleksi huruf pertama*/
        String delta[][]=new String[1000][1000];/*Deklarasi array indeks hasil seleksi*/
        for(int i=0;i<11;i++){/*Perulangan dalam pencarian seleksi paragraf*/
            System.out.print("Masukan paragraf-"+(i+1)+" :");
            kalimat=input.nextLine();
            File data= new File("dataset.txt");/*Seleksi file yang akan dijadikan objek searching*/
            try(FileWriter fw = new FileWriter(data, true);/*Proses penympanan file menggunakan buffered reader*/
                BufferedWriter bw = new BufferedWriter(fw);/*deklarasi input*/
                PrintWriter out = new PrintWriter(bw))/*deklarasi input*/
            {
                out.println(kalimat);
            } catch (IOException e) {
               System.out.println("File tidak dapat disimpan");
            }
            Scanner in=new Scanner(data);
            Vector<String> v=new Vector<String>(); /*deklarasi vektor untuk pengambilan data kalimat*/
            while(in.hasNextLine()){  /*melakukan perulangan pengambilan data kalimat berdasarkan baris/line*/
                v.add(in.nextLine());
            }
            bKalimat=v.size(); /*melakukan pengambilan data berdasarkan size*/
            for(int j=i;j<v.size();j++){ /*melakukan perulangan pengambilan data per baris/line*/
                baris[j]=v.get(j); /*pengambilan data setara dengan jumlah baris/line*/
                char[] huruf = baris[j].toCharArray(); /*array char data huruf per jumlah baris/line*/
                File data2= new File("dataset"+i+".txt"); /*pembuatan file baru sesuai data pencarian*/
                try(FileWriter fw = new FileWriter(data2, true);
                    BufferedWriter bw = new BufferedWriter(fw);
                    PrintWriter out = new PrintWriter(bw))
                {
                    for(int k=0;k<huruf.length;k++){ /*menampilkan output data huruf setara dengan jumlah baris/line*/
                        out.println(huruf[k]);/*output array data huruf*/
                    }
                } catch (IOException e) {
                    System.out.println("Kata tidak dapat disimpan");
                }
                Scanner in2=new Scanner(data2);
                Vector<String> v2=new Vector<String>(); /*deklarasi vektor kedua untuk menampilkan data seleksi*/
                while(in2.hasNextLine()){ /*perulangan while untuk penentuan output data seleksi berdasarkan baris*/  
                    v2.add(in2.nextLine());
                }
                bHuruf[j]=huruf.length;
                for(int k=0;k<bHuruf[j];k++){ /*perulangan untuk menampilkan data seleksi huruf*/
                    huruf1[j][k]=v2.get(k); /*output array yang ditampilkan*/
                }
            }
        }
        /*menu pemilihan seleksi pencarian kata*/
        System.out.println("Menu pencarian kata :");
        System.out.print("1. bahasa formal dan informal\n2. linux ubuntu server\n3.bahasa pemrograman java\n\n");
        System.out.print("Masukan pilihan menu : ");
        pilih=input.nextInt();
        if(pilih==1){
            String[] simbol={"b","a","h","s","f","o","r","m","l","d","n","i"," ",".","(",")",","};/*deklarasi array string simbol*/
            start="q0";/*deklarasi start state q0*/
            String[] states={"q0","q1","q2","q3","q4","q5","q6","q7","q8","q9",
                "q10","q11","q12","q13","q14","q15","q16","q17","q18","q19",
                "q20","q21","q22","q23"};/*deklarasi array state*/
            String[][] delta1={{"q1","q0","q0","q0","q7","q0","q0","q0","q0","q13","q0","q16","q0","q0","q0","q0","q0"}};/*deklarasi transisi percabangan pertama*/
            for(int i=0;i<1;i++){/*perulangan dalam penentuan transisi jika jumlah state <1*/ 
                for(int j=0;j<simbol.length;j++){/*perulangan dalam pembagian transisi dan simbol jika state <1*/
                    delta[i][j]=delta1[i][j];
                }
            }
            for(int i=1;i<states.length;i++){ /*perulangan dalam penentuan transisi berdasarkan jumlah state*/ 
                for(int j=0;j<simbol.length;j++){ /*perulangan dalam penentuan simbol menyesuaikan transisi dan jumlah state*/
                    delta[i][j]=null;/*transisi pertama = null*/
                }
            }
            /*penentuan transisi berdasarkan state asal dan tujuan*/
            delta[1][1]="q2"; delta[2][2]="q3"; delta[3][1]="q4"; delta[4][3]="q5"; delta[5][1]="q6";
            delta[7][5]="q8"; delta[8][6]="q9"; delta[9][7]="q10"; delta[10][1]="q11"; delta[11][8]="q12";
            delta[13][1]="q14"; delta[14][10]="q15"; delta[16][10]="q17"; delta[17][4]="q18"; delta[18][5]="q19"; 
            delta[19][6]="q20"; delta[20][7]="q21"; delta[21][1]="q22"; delta[22][8]="q23"; 
            for(int i=0;i<bKalimat;i++){/*perulangan dalam indeks kalimat*/
                for(int j=0;j<bHuruf[i];j++){/*perulangan dalam array indeks huruf*/
                    read=huruf1[i][j];/*pengambilan array indeks huruf*/
                    for(int k=0;k<states.length;k++){/*perulangan dalam indeks states*/
                        if(states[k].equals(start)){/*kondisi dalam penentuan transisi states tujuan dengan state awal*/
                            x=k;/*states k diinisialisasi sebagai state awal x*/
                        }
                    }
                    for(int l=0;l<simbol.length;l++){/*perulangan indeks simbol dengan type Uppercase*/
                        String hurufbesar=simbol[l].toUpperCase();/*deklarasi string simbol uppercase*/
                        if(simbol[l].equals(read)||hurufbesar.equals(read)){/*kondisi dalam penentuan transisi state*/
                            y=l;/*states l diinisialisasi sebagai state awal y*/
                    }
                    start=delta[x][y];/*deklarasi array state transisi*/
                    if(start!=null){/*penentuan kondisi jika state awal terdapat input string untuk ditransisikan*/ 
                        if(start.equals("q6")||start.equals("q12")||start.equals("q15")||start.endsWith("q23")){/*penentuan kondisi transisi state tujuan*/
                            start="q0";/*state awal q0*/
                            index1[a]=i+1;/*indeks perpindahan transisi +1*/
                            a++;
                        }
                    } else if(start==null){/*penentuan kondisi jika state awal tidak terdapat input string untuk ditransisikan*/
                        start="q0";/*state awal q0*/
                    }
                }
              }
            }
            int b=index1[0];/*deklarasi array indeks transisi state awal*/
            int j=0;/*deklarai array indeks transisi state tujuan*/
            for(int i=0;i<a;i++){/*perulangan indeks transisi*/
                if(index1[i]==b){/*kondisi transisi state tidak berpindah*/
                    index2[j]=b;
                }else {
                    b=index1[i];/*kondisi penentuan transisi dan indeks hasil yang diterima*/
                    j++;
                    index2[j]=b;
                }
            }
            System.out.print("Kata tersebut terdapat pada paragraf : ");
            for(int i=0;i<j+1;i++){/*perulangan untuk menampilkan output indeks hasil*/
                System.out.print(index2[i]+", ");
            }
        } 
        else if(pilih==2){
            String[] simbol={"l","i","n","u","x","b","t","s","e","r","v"," ",".","(",")",","};/*deklarasi array string simbol*/
            start="q0";/*deklarasi start state q0*/
            String[] states={"q0","q1","q2","q3","q4","q5","q6","q7","q8","q9",
                "q10","q11","q12","q13","q14","q15","q16","q17"};/*deklarasi array state*/
            String[][] delta1={{"q1","q0","q0","q6","q0","q0","q0","q12","q0","q0","q0","q0","q0","q0","q0","q0"}};/*deklarasi transisi percabangan pertama*/
            for(int i=0;i<1;i++){/*perulangan dalam penentuan transisi jika jumlah state <1*/ 
                for(int j=0;j<simbol.length;j++){/*perulangan dalam pembagian transisi dan simbol jika state <1*/
                    delta[i][j]=delta1[i][j];
                }
            }
            for(int i=1;i<states.length;i++){/*perulangan dalam penentuan transisi berdasarkan jumlah state*/ 
                for(int j=0;j<simbol.length;j++){/*perulangan dalam penentuan simbol menyesuaikan transisi dan jumlah state*/
                    delta[i][j]=null;/*transisi pertama = null*/
                }
            }
            /*penentuan transisi berdasarkan state asal dan tujuan*/
            delta[1][1]="q2"; delta[2][2]="q3"; delta[3][3]="q4"; delta[4][4]="q5"; delta[6][5]="q7";
            delta[7][3]="q8"; delta[8][2]="q9"; delta[9][6]="q10"; delta[10][3]="q11"; delta[12][8]="q13";
            delta[13][9]="q14"; delta[14][10]="q15"; delta[15][8]="q16"; delta[16][9]="q17";
            for(int i=0;i<bKalimat;i++){/*perulangan dalam indeks kalimat*/
                for(int j=0;j<bHuruf[i];j++){/*perulangan dalam array indeks huruf*/
                    read=huruf1[i][j];/*pengambilan array indeks huruf*/
                    for(int k=0;k<states.length;k++){/*perulangan dalam indeks states*/
                        if(states[k].equals(start)){/*kondisi dalam penentuan transisi states tujuan dengan state awal*/
                            x=k;/*states k diinisialisasi sebagai state awal x*/
                        }
                    }
                    for(int l=0;l<simbol.length;l++){/*perulangan indeks simbol dengan type Uppercase*/
                        String hurufbesar=simbol[l].toUpperCase();/*deklarasi string simbol uppercase*/
                        if(simbol[l].equals(read)||hurufbesar.equals(read)){/*kondisi dalam penentuan transisi state*/
                            y=l;/*states l diinisialisasi sebagai state awal y*/
                        }
                    }
                    start=delta[x][y];/*deklarasi array state transisi*/
                    if(start!=null){/*penentuan kondisi jika state awal terdapat input string untuk ditransisikan*/ 
                        if(start.equals("q5")||start.equals("q11")||start.equals("q17")){/*penentuan kondisi transisi state tujuan*/
                            start="q0";/*state awal q0*/
                            index1[a]=i+1;/*indeks perpindahan transisi +1*/
                            a++;
                        }
                    }else if(start==null){/*penentuan kondisi jika state awal tidak terdapat input string untuk ditransisikan*/
                        start="q0";/*state awal q0*/
                    }
                }
            }
            int b=index1[0];/*deklarai array indeks transisi state awal*/
            int j=0;/*deklarai array indeks transisi state tujuan*/
            for(int i=0;i<a;i++){/*perulangan indeks transisi*/
                if(index1[i]==b){/*kondisi transisi state tidak berpindah*/
                    index2[j]=b;
                }else{
                    b=index1[i];/*kondisi penentuan transisi dan indeks hasil yang diterima*/
                    j++;
                    index2[j]=b;
                }
            }
            System.out.print("Kata tersebut terdapat pada paragraf : ");
            for(int i=0;i<j+1;i++){/*perulangan untuk menampilkan output indeks hasil*/
                System.out.print(index2[i]+", ");
            }
        }
        else if(pilih==3){
            String[] simbol={"b","a","h","s","p","e","m","r","o","g","n","j","v"," ",".","(",")",","};/*deklarasi array string simbol*/
            start="q0";/*deklarasi start state q0*/
            String[] states={"q0","q1","q2","q3","q4","q5","q6","q7","q8","q9",
                "q10","q11","q12","q13","q14","q15","q16","q17","q18","q19",
                "q20","q21"};/*deklarasi array state*/
            String[][] delta1={{"q1","q0","q0","q0","q7","q0","q0","q0","q0","q0","q0","q18","q0","q0","q0","q0","q0","q0"}};/*deklarasi transisi percabangan pertama*/
            for(int i=0;i<1;i++){/*perulangan dalam penentuan transisi jika jumlah state <1*/ 
                for(int j=0;j<simbol.length;j++){/*perulangan dalam pembagian transisi dan simbol jika state <1*/
                    delta[i][j]=delta1[i][j];
                }
            }
            for(int i=1;i<states.length;i++){/*perulangan dalam penentuan transisi berdasarkan jumlah state*/ 
                for(int j=0;j<simbol.length;j++){/*perulangan dalam penentuan simbol menyesuaikan transisi dan jumlah state*/
                    delta[i][j]=null;/*transisi pertama = null*/
                }
            }
            delta[1][1]="q2"; delta[2][2]="q3"; delta[3][1]="q4"; delta[4][3]="q5"; delta[5][1]="q6";
            delta[7][5]="q8"; delta[8][6]="q9"; delta[9][7]="q10"; delta[10][8]="q11"; delta[11][9]="q12";
            delta[12][7]="q13"; delta[13][1]="q14"; delta[14][6]="q15"; delta[15][1]="q16"; delta[16][10]="q17"; 
            delta[18][1]="q19"; delta[19][12]="q20"; delta[20][1]="q21"; 
            for(int i=0;i<bKalimat;i++){/*perulangan dalam indeks kalimat*/
                for(int j=0;j<bHuruf[i];j++){/*perulangan dalam array indeks huruf*/
                    read=huruf1[i][j];/*pengambilan array indeks huruf*/
                    for(int k=0;k<states.length;k++){/*perulangan dalam indeks states*/
                        if(states[k].equals(start)){/*kondisi dalam penentuan transisi states tujuan dengan state awal*/
                            x=k;/*states k diinisialisasi sebagai state awal x*/
                        }
                    }
                    for(int l=0;l<simbol.length;l++){/*perulangan indeks simbol dengan type Uppercase*/
                        String hurufbesar=simbol[l].toUpperCase();/*deklarasi string simbol uppercase*/
                        if(simbol[l].equals(read)||hurufbesar.equals(read)){/*kondisi dalam penentuan transisi state*/
                            y=l;/*states l diinisialisasi sebagai state awal y*/
                        }
                    }
                    start=delta[x][y];/*deklarasi array state transisi*/
                    if(start!=null){/*penentuan kondisi jika state awal terdapat input string untuk ditransisikan*/ 
                        if(start.equals("q6")||start.equals("q17")||start.equals("q21")){/*penentuan kondisi transisi state tujuan*/
                            start="q0";/*state awal q0*/
                            index1[a]=i+1;/*indeks perpindahan transisi +1*/
                            a++;
                        }
                    }else if(start==null){/*penentuan kondisi jika state awal tidak terdapat input string untuk ditransisikan*/
                        start="q0";/*state awal q0*/
                    }
                }
            }
            int b=index1[0];/*deklarai array indeks transisi state awal*/
            int j=0;/*deklarai array indeks transisi state tujuan*/
            for(int i=0;i<a;i++){/*perulangan indeks transisi*/
                if(index1[i]==b){/*kondisi transisi state tidak berpindah*/
                    index2[j]=b;
                }else{
                    b=index1[i];/*kondisi penentuan transisi dan indeks hasil yang diterima*/
                    j++;
                    index2[j]=b;
                }
            }
            System.out.print("Kata tersebut terdapat pada paragraf : ");
            for(int i=0;i<j+1;i++){/*perulangan untuk menampilkan output indeks hasil*/
                System.out.print(index2[i]+", ");
            }
        }
    }
}