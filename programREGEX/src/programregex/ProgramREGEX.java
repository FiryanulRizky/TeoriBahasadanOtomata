/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programregex;
import java.util.Scanner;
import java.util.Stack;
import java.util.ArrayList;
/**
 *
 * @author Firyanul Rizky
 */
public class ProgramREGEX {

   static int pilih, bsimbol, bstate,i,j,k,l,x=0,y=0,on,on2,in,n;
    
    static String[] simbol = {"a","b","c","d","e"}; //string array yang menampung simbol/alphabet dari Finite Automata
    static String[] states = new String[10]; //string array yang menampung states-states dari Finite Automata
    static String[] re = new String[10];
    static String[] re2 = new String[10];
    static String[] re3 = new String[10];
    static boolean [] cekpjm = new boolean[10];
    static String[][] delta = new String[20][20];
    static String[][] pjm = new String[20][20];
    static String[][] save = new String[20][20];
    static String[][] save2 = new String[20][20];
    static String [][] save3 = new String[20][20];
    static String[][] pjm2 = new String[20][20];
    static String[][] pjm3 = new String[20][20];
    static String[][] pjm4 = new String[20][20];
    static String[][] pjm5 = new String[20][20];
    static String[][] temp1 = new String[20][20];
    static String[][] abc = new String[20][20];
    static String[][] a = new String[20][20];
    static String[][] b = new String[20][20];
    static String[][] a2 = new String[20][20];
    static String[][] b2 = new String[20][20];
    static String[][][] delta2 = new String[20][20][20];
    static String init_dfa[] = new String[30];
    static String fin_dfa[] = new String[30];
    static int[] z = new int[10];
    static int[] temp = new int[10];
    static int[] sc = new int[10];
    static int[] temp2 = new int[10];
    static int init [] = new int [20];
    static int fin[] = new  int[20];
    static int e=0,f=0,_e=0,_f=0;
    static boolean status;
    static String end;
    
    //method untuk menentukan nilai awal regular expresion dari setiap state dengan memberikan nilai "null"
    public static void inisialisasi(String[][] pjm, String[][] a, String[][] b){
        for(i=0;i<bstate;i++){
            for(j=0;j<10;j++){
                pjm[i][j]=null;
                a[i][j]=null;
                b[i][j]=null;
            }
        }
    }
    /*method untuk mengecek apakah terdapat nilai pada variabel a2 yang sama dengan states[i] 
    jika ada maka nilai count akan bertambah 1*/
    public static int jumlah(int i,String s, int z){ 
        int count=0;
        for( j =0;j<z;j++){
            if(a2[i][j].equals(s)){ count++; }
        }
        return count;
    }
    //method untuk menentukan nilai awal dari fungsi transisi finite automata dengan memberikan nilai -1 pada variabel nfa_table
    public static void initialise(int [][] nfa_table){
        for(int i =0;i<100;i++){
            for(int j=0;j<5;j++){
                nfa_table[i][j]=-1;
            }
        }
    }
    //method untuk mencetak state awal dan final state
    public static void print_initial_final(){
        System.out.println("Initial Start State : ");
        for(int i=0;i<e;i++){
            System.out.println(" "+init[i]); //state awal disimpan dalam variabel init
        }
        System.out.println("Final State : ");
        for(int i=0;i<e;i++){
            System.out.println(" "+fin[i]); //final state disimpan dalam variabel fin
        }
    }
    //method untuk menset nilai fin[i] menjadi fin[i+1] 
    public static void reduce_fin(int x){
        for(int i=x; i<f-1;i++){
            fin[i]=fin[i+1];
            f=f-1;
        }
    }
    //method untuk mencari bentuk preprocessornya
    public static String preprocessor(String s){
        char [] x = new char [1000];
        int l = s.length();
        char [] karakter = new char[100];
        for(int i=0;i<l+1;i++)
            karakter[i]=' ';
        char [] temp = s.toCharArray();
        for(int i=0;i<l;i++){
            karakter[i] = temp[i];
        }
        int j=0;
        
        x[j]='(';
        j+=1;
        int i=0;
        
        while(i<l){
            x[j]=karakter[i];
            j+=1;
                if(karakter[i]>=97 && karakter[i]<=122  && karakter[i+1]>=97 && karakter[i+1]<=122){
                    x[j]='.';
                    j+=1;
                }else if(karakter[i]==')'&&karakter[i+1]=='('){
                    x[j]='.';
                    j+=1;
                }else if(karakter[i]>=97 && karakter[i]<=122 && karakter[i+1]=='('){
                    x[j]='.';
                    j+=1;
                }else if(karakter[i]==')'&& karakter[i+1]>=97 && karakter[i+1]<=122){
                    x[j]='.';
                    j+=1;
                     
                }else if(karakter[i]=='*'  && (karakter[i+1]=='(' || (karakter[i+1]>=97 && karakter[i+1]<122))){
                    x[j]='.';
                    j+=1;
                }
            i++;
        }
        x[j]=')';
        j+=1;
        String p = "";
        for( i=0;i<j;i++){
            p+=x[i];
        }
        return p;
    }
    //method untuk untuk mendapatkan bentuk postfixnya
    public static String  postfix(String s){
        int l = s.length();
        char [] a = new char[5000];
        Stack <Character> ch = new Stack<>();
        int j=0;
         char [] karakter = s.toCharArray();
        for(int i =0;i<l;i++){
           char x = karakter[i];
           switch(x){
            case 'a' :  a[j] ='a';
                        j+=1;
                        break;
            case 'b':   a[j]='b';
                        j+=1;
                        break;
            case '(':   ch.push('(');
                        break;
            case ')':   while(!ch.empty()){
                            if(ch.peek()=='('){
                                ch.pop();
                                break;
                            }else{
                                a[j]=ch.peek();
                                    ch.pop();
                                    j += 1;
                            }
                        }
                        break;
            case '.':   if(ch.empty()){
                            ch.push('.');
                        }else {
                            char temp = ch.peek();
                            if(temp=='(')
                                ch.push('.');
                            else if(temp=='*'){
                                a[j]=ch.peek();
                                ch.pop();
                                j += 1;
                                if(ch.peek()== '.'){
                                    a[j] = '.';
                                    j += 1;
                                }else{
                                    ch.push('.');
                                }
                            }else if(temp=='.'){
                                a[j]=ch.peek();
                                ch.pop();
                                j += 1;
                                ch.push('.');
                            }else if(temp == '+'){
                               ch.push('.');
                            }
                        }
                        break;
            case '+':   if(ch.empty()){
                            ch.push('+');
                        }else{
                            char temp = ch.peek();
                            if(temp == '(')
                                ch.push('+');
                            else if(temp == '*'){
                                a[j] = ch.peek();
                                ch.pop();
                                j += 1;
                                ch.push('+');
                            }else if(temp == '.'){
                                a[j] = ch.peek();
                                j += 1;
                                ch.pop();
                                ch.push('+');
                            }
                        }
                            break;
            case '*':   if(ch.empty()){
                            ch.push('*');
                        }else{
                            char temp = ch.peek();
                            if(temp == '(' || temp == '.' || temp == '+' )
                                ch.push('*');
                            else{
                                a[j] = ch.peek();
                                ch.pop();
                                j += 1;
                                ch.push('*');
                            }
                        }
                        break;
           }
        }
        String p="";
        for(int i =0 ;i<j;i++){
            p+=a[i];
        }
        return p;        
    }
    //method untuk mencetak bentuk tabel transisi
    public static void print_nfa_table(int [][] nfa_table,int states){
        System.out.println("Transisi Tabel FA ");
        System.out.format("State\ta\tb\te\te\t");
        System.out.println("");
        System.out.println("");
        
        for(int i=1;i<states;i++){
            for(int j=0;j<5;j++){
                if(nfa_table[i][j]==-1){
                    System.out.format("--\t");
                }
                else{
                    System.out.format("%d\t",nfa_table[i][j]);
                }
            }
            System.out.println("");
        }
        System.out.println("");
         print_initial_final();
    }
    //method untuk mendapatkan nilai state 
    public static int reg_nfa(String s, int [][] nfa_table){
        int l = s.length();
        int states = 1;
        int m,n,j,counter = 0;
        char [] karakter = s.toCharArray();
        for(int i=0;i<l;i++){
            char x = karakter[i];
            switch(x){
                case 'a': nfa_table[states][0]=states;
                          init[e]=states;
                          e+=1;
                          states+=1;
                          nfa_table[states-1][1] = states;
                          fin[f]=states;
                          f+=1;
                          nfa_table[states][0]=states;
                          states+=1;
                          break;
                case 'b': nfa_table[states][0]=states;
                          init[e]=states;
                          e+=1;
                          states+=1;
                          nfa_table[states-1][2] = states;
                          fin[f]=states;
                          f+=1;
                          nfa_table[states][0] = states;
                          states+=1;
                          break;
                case '.': m=fin[f-2];
                          n=init[e-1];
                          nfa_table[m][3]=n;
                          reduce_fin(f-2);
                          e-=1;
                          break;
                case '+': for(j=e-1,counter=0;counter<2;counter++){
                              m=init[j-counter];
                              nfa_table[states][3+counter]=m;
                          }
                          e=e-2;
                          init[e]=states;
                          e+=1;
                          nfa_table[states][0]=states;
                          states+=1;
                          for(j=f-1,counter=0;counter<2;counter++){
                              m=fin[j-counter];
                              nfa_table[m][3]=states;
                          }
                          f=f-2;
                          fin[f]=states;
                          f+=1;
                          nfa_table[states][0]=states;
                          states+=1;
                          break;
                case '*' : m=init[e-1];
                           nfa_table[states][3]=m;
                           nfa_table[states][0]=states;
                           init[e-1] = states;
                           states+=1;
                           n=fin[f-1];
                           nfa_table[n][3]=m;
                           nfa_table[n][4]=states;
                           nfa_table[states-1][4]=states;
                           fin[f-1]=states;
                           nfa_table[states][0]=states;
                           states+=1;
                           break;  
            }
        }
        
        return states;
    }
    public static void main(String[] args) {
        //instansi stack dengan nama ch dan ch2
        Stack <String> ch = new Stack<>(); 
        Stack <String> ch2 = new Stack<>(); 
        //instansi scanner dengan nama input
        Scanner input= new Scanner(System.in);
        System.out.println("Menu konversi :");
        System.out.print("1. DFA/NFA ke Regular Expression \n2. Regular Expression ke DFA/NFA\n\n");
        System.out.print("Masukan pilihan menu : ");
        pilih=input.nextInt(); //variabel pilih menyimpan data pilihan menu
        //jika user memilih 1 akan masuk ke proses konversi DFA/NFA ke Regular Expression
        if(pilih==1){
            System.out.println("Konversi DFA/NFA ke Regular Expression\n");
            System.out.print("Masukan banyak simbol dari DFA/NFA : ");
            bsimbol=input.nextInt(); //variabel bsimbol akan menyimpan banyaknya simbol dari DFA/NFA
            //mencetak simbol dari DFA/NFA
            for(i=0;i<bsimbol;i++){
                System.out.println("simbol["+i+"] : "+simbol[i]); 
            }
            System.out.print("\nMasukan banyak states : ");
            bstate = input.nextInt(); //variabel bstate akan menyimpan banyaknya state dari DFA/NFA
            for(i=0;i<bstate;i++){
                states[i] = Integer.toString(i); //array states akan menyimpan state sesuai indexnya 
                System.out.println("states["+i+"] : "+states[i]); 
            }
            System.out.print("\nMasukan final state : ");
            end = input.next(); //variabel end akan menyimpan final state
            System.out.println("\nMasukan fungsi transisi : ");
            System.out.println("- Jika ada transisi yang kosong masukan 'null'\n- Jika lebih dari satu tambahkan tanda ','");
            //memanggil method untuk inisialisasi awal bentuk regex dari setiap state
            inisialisasi(pjm,a,b);
            
            in=0; //variabel in diiniasialsi dengan nilai awal 0
            //menginputkan fungsi transisi dari finite automata
            for(i=0;i<bstate;i++){
                for(j=0;j<bsimbol;j++){
                    /*pada tahap ini user akan diminta menginputkan fungsi transisi dari DFA/NFA yang ingin dikonversi ke dalam bentuk
                    regex yang akan disimpan dalam variabel delta[i][j] dimana nilai i diambil dari state2 yang ada dan nilai j diambil 
                    dari simbol2 yang ada.*/
                    System.out.print("delta["+(states[i])+"]["+(simbol[j])+"] : "); 
                    delta[i][j] = input.next(); 
                    /*Setelah itu, karena user ada yang menginputkan transisi lebih dari satu pada bentuk NFA, maka data yang ditampung 
                    pada setiap variabel delta[i][j] akan dipisahkan jika terdapat tanda "," dan kemudian akan disimpan dalam variabel bagian*/
                    String[] bagian = delta[i][j].split(",");
                    k=0; //nilai k diinisialisasi dengan nilai 0
                    for(String bg :bagian){
                        /*selanjutnya adalah proses penyimpanan data yang telah dibagi tadi berdasarkan tanda "," dengan nama "bg" kedalam sebuah array 3 dimensi yaitu
                        delta2[i][j][k] dimana nilai i yaitu nilai statenya, j yaitu nilai simbolnya dan k yaitu nilai bagiannya*/
                        delta2[i][j][k]=bg;
                        for(l=0;l<bstate;l++){
                            /*selanjutnya yaitu memberikan nilai baru pada variabel a, b, dan pjm dengan ketentuan jika delta2[i][j[k] sama dengan states[l] 
                            (dimana nilai l sama dengan states yang diulang) maka variabel a akan menampung states[i] variabel b akan menampung simbol[j] 
                            dan variabel pjm akan menampung hasil concat(penggabungan string) dari variabel a dan b, lalu nilai in akan bertambah 1*/
                            if(delta2[i][j][k].equals(states[l])){
                                a[l][in]=states[i];
                                b[l][in]=simbol[j];
                                pjm[l][in]=a[l][in].concat(b[l][in]);
                                in++;
                            }
                        }
                        //setelah perulangan selesai maka nilai k akan bertambah 1
                        k++;  
                    }
                }
            }
            //proses selanjutnya yaitu mengambil nilai pjm yang tidak null kemudian disimpan dalam variabel pjm2
            for(i=0;i<bstate;i++){
                re[i]="";
                k=0;
                for(j=0;j<in;j++){
                    if(pjm[i][j]!=null){
                        pjm2[i][k]=pjm[i][j];
                        a2[i][k]=a[i][j];
                        b2[i][k]=b[i][j];
                        re[i]=re[i].concat(pjm2[i][k]).concat("+"); //menggabungkan nilai yang ada pada pjm2 dengan tanda "+"
                        k++; 
                        z[i]=k;
                    }
                }
            }
            
            for(i=0;i<bstate;i++){
                on=0;
                re2[i]="";
                temp[i]=jumlah(i, states[i], z[i]); //memanggil fungsi jumlah unutk mendapatkan nilai count 
                for(j=0;j<z[i];j++){
                    if(!end.equals(states[i])){ //jika states[i] tidak sama dengan end (final state)
                        //jika a2[i][j] sama dengan states[i] maka b2[i][j] akan diambil dan disimpan pada variabel save[i][on] lalu nilai on bertambah satu
                        if(a2[i][j].equals(states[i])){ 
                            save[i][on]=b2[i][j];
                            on++;
                            int cek=0;
                            //jika on lebih besar dari 1 dan on sama dengan temp[i] maka akan dipush suatu simbol regex
                            if(on>1 && on==temp[i]){
                                ch.push("(");
                                for(k=0;k<on;k++){
                                    ch.push(save[i][k]);
                                    //jika k sama dengan on-1 maka akan dipush simbol ")*" jika tidak maka akan dipush simbol "+"
                                    if(k==on-1){
                                        ch.push(")*");
                                    }else{
                                        ch.push("+");
                                    } 
                                }
                            }
                            //jika on sama dengan 1 dan on sama dengan temp[i] maka akan dipush suatu simbol regex
                            else if (on==1 && temp[i]==1){
                                if(j==z[i]-1){ //jika j sama dengan z[i]-1
                                    ch.push(save[i][on-1]);
                                    ch.push("*");
                                }
                                else if(j<=z[i]-2){ //jika j kurang dari sama dengan z[i]-2
                                    ch.push(a2[i][j]);
                                    ch.push(b2[i][j]);
                                    ch.push("+");
                                }
                            }
                        }
                        //jika a2[i][j] tidak sama dengan states[i]
                        else{
                            if(a2[i][j].equals(end)){ //jika a2[i][j] sama dengan final state
                                if(j==z[i]-1){ //jika j sama dengan z[i]-1
                                    ch.push(a2[i][j]);
                                    ch.push(b2[i][j]);
                                }else if(j<=z[i]-2){ //jika j kurang dari sama dengan z[i]-2
                                    ch.push(a2[i][j]);
                                    ch.push(b2[i][j]);
                                    ch.push("+");
                                }
                            }else{ //jika a2[i][j] tidak sama dengan final state
                                for(k=0;k<bstate;k++){
                                    if(a2[i][j].equals(states[k])){
                                        if(temp[k]==0 && z[k]>1){
                                            if(j==z[i]-1){
                                                ch.push("(");
                                                ch.push(re2[k]);
                                                ch.push(")");
                                                ch.push(b2[i][j]);
                                            }else if(j<=z[i]-2){
                                                ch.push("(");
                                                ch.push(re2[k]);
                                                ch.push(")");
                                                ch.push(b2[i][j]);
                                                ch.push("+");
                                            }
                                        }else if(temp[k]==0 && z[k]==0){
                                            ch.push(b2[i][j]);
                                        }else{
                                            if(re2[k]!=null){
                                                ch.push(re2[k]);
                                                ch.push(b2[i][j]);
                                            }else{
                                                ch.push(a2[k][0]);
                                                ch.push(b2[k][0]);
                                                ch.push(b2[i][j]);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } 
                }
                /*selanjutnya adalah pengambilan nilai yang tersimpan didalam stack, prosesnya yaitu dengan cara mempop data kemudian
                disimpan dalam variabel pjm3[i][l]*/
                l=0;
                while(!ch.empty()){
                    pjm3[i][l]=ch.pop();
                    l++;
                }
                /*proses unutk menggabungkan nilai yang ada pada pjm3 kemudian disimpan dalam varaibel re2[i]*/
                for(int m=l;m>=0;m--){
                    if(pjm3[i][m]!=null){
                        re2[i]=re2[i].concat(pjm3[i][m]);
                    }
                }
            }
            on=0;
            k=0;
            int m=0;
            //proses untuk penyederhanaan regex pada final state
            for(i=0;i<bstate;i++){
                if(states[i].equals(end)){ //jika states[i] sama dengan final states maka akan masuk proses selanjutnya
                    for(j=0;j<z[i];j++){ 
                        if(a2[i][j].equals(states[i])){ //jika a2 sama dengan states[i]
                            if(temp[i]==1 && z[i]==1){ //jika temp[i] sama dengan 1 dan z[i] sama dengan 1 maka akan dipush b2 dan *
                                ch.push(b2[i][j]);
                                ch.push("*");
                                //mengambil data yang ada pada stack kemudian ditampung pada variabel temp1 lalu k bertambah 1
                                while(!ch.empty()){
                                    temp1[i][k]=ch.pop();
                                    k++;
                                }
                                //menggabungkan data pada temp1 lalu memasukkannya kedalam variabel pjm4
                                pjm4[i][m]="";
                                for(int q=k;q>=0;q--){
                                    if(temp1[i][q]!=null){
                                        pjm4[i][m]=pjm4[i][m].concat(temp1[i][q]);
                                    }
                                }
                                m++;
                            }
                            /*jika temp[i] tidak sama dengan 1 dan z[i] tidak sama dengan 1 maka a2 dan b2 akan digabung kemudian 
                            ditampung dalam variabel save2 kemudian save2 akan kembali lagi ditampung oleh variabel pjm4 kemudian m dan on bertambah 1*/
                            else{ 
                                save2[i][on]=a2[i][j].concat(b2[i][j]);
                                pjm4[i][m]=save2[i][on];
                                m++;
                                on++;
                            }
                        }else{ // jika a2 tidak sama dengan states[i]
                            for(k=0;k<bstate;k++){
                                //jika a2 sama dengan states[i] kemudian akan dipush re2[k] dan b2
                                if(a2[i][j].equals(states[k])){ 
                                    ch.push(re2[k]);
                                    ch.push(b2[i][j]);
                                }
                            }
                            //mengambil data yang ada pada stack kemudian ditampung pada variabel temp1 lalu k bertambah 1
                            while(!ch.empty()){
                                temp1[i][k]=ch.pop();
                                k++;
                            }
                            //menggabungkan data pada temp1 lalu memasukkannya kedalam variabel pjm4
                            pjm4[i][m]="";
                            for(int q=k;q>=0;q--){
                                if(temp1[i][q]!=null){
                                    pjm4[i][m]=pjm4[i][m].concat(temp1[i][q]);
                                }
                            }
                            m++;
                        }
                    }
                    //proses mengecek apakah terdapat nilai pada tempchar yang sama dengan states[i] jika iya maka cekpjm akan bernilai true
                    temp2[i]=0;
                    for(n=0;n<m;n++){
                        char[] tempchar = pjm4[i][n].toCharArray(); //menjadikan pjm4 dalam bentuk char yang akan ditampung oleh variabel tempchar
                        for(int r=0;r<tempchar.length;r++){ //perulangan sesuai panjang tempchar
                            char [] st = states[i].toCharArray(); //menjadikan states[i] dalam bentuk char yang akan ditampung oleh variabel st
                            if(st[0]==tempchar[r]){ //jika st sama dengan tempchar maka temp2[i] akan bertambah 1 dan sc[i] akan menampung nilai r kemudian cekpjm akan bernilai true
                                temp2[i]++;
                                sc[i]=r;
                                cekpjm[n]=true;
                            }else if(st[0]!=tempchar[r] && cekpjm[n]==true){ //jika st[0] tidak sama dengan tempchar dan cekpjm[n] berniali true maka cekpjm tetap bernilai true
                                cekpjm[n]=true;
                            }else{ //selain yang diatas maka cekpjm berniali false
                                cekpjm[n]=false;
                            }
                        }
                    }
                    //proses ini merupakan proses final penentuan bentuk regex yang sebenarnya dari dfa/nfa yang diinputkan user
                    int status=0;
                    int cek=0;
                    for(n=0;n<m;n++){
                        save3[i][cek]="";
                        char[] tempchar = pjm4[i][n].toCharArray();
                        for(int r=0;r<tempchar.length;r++){
                            char [] st = states[i].toCharArray();
                            if(st[0]==tempchar[r]){ //jika st sama dengan tempchar             
                                for(int s=r+1;s<tempchar.length;s++){ 
                                    //tempchar diubah ke dalam bentuk string lalu digabungkan dengan tempchar selanjutnya dan akan ditampung oleh variabel save3
                                    save3[i][cek]= save3[i][cek].concat(Character.toString(tempchar[s]));
                                }
                                //nilai cek bertambah satu setiap st sama dengan tempchar
                                cek++;
                                if(cek>1 && cek==temp2[i]){ //jika nilai cek lebih besar dari 1 dan sama dengan temp2[i]
                                    ch2.push("(");
                                    for(k=0;k<cek;k++){
                                        ch2.push(save3[i][k]);
                                        if(k==cek-1){
                                            ch2.push(")*");
                                        }else{
                                            ch2.push("+");
                                        } 
                                    }
                                }
                                else if(cek==1 && cek==temp2[i]){ //jika nilai cek sama dengan 1 dan sama dengan temp2[i]
                                    ch2.push("(");
                                    ch2.push(save3[i][cek-1]);
                                    ch2.push(")*");
                                }
                            } else if(st[0]!=tempchar[r] && cekpjm[n]==false){ //jika st tidak sama dengan tempchar dan cekpjm sama dengan false
                                String tempc=Character.toString(tempchar[r]); //tempchar akan diubah kedalam bentuk string kemudian ditampung oleh variabel tempc
                                ch2.push(tempc); //tempc dipush kedalam stuck
                            }else if(st[0]!=tempchar[r] && cekpjm[n]==true && sc[i]>r){ //jika st tidak sama dengan tempchar dan cekpjm sama dengan false
                                String tempc=Character.toString(tempchar[r]); //tempchar akan diubah kedalam bentuk string kemudian ditampung oleh variabel tempc
                                ch2.push(tempc); //tempc dipush kedalam stuck
                            } 
                        }
                    }
                    //mengambil data yang ada pada stack kemudian ditampung pada variabel abc lalu p bertambah 1
                    int p=0;
                    while(!ch2.empty()){
                        abc[i][p]=ch2.pop();
                        p++;
                    }
                    //menggabungkan data pada abc lalu memasukkannya kedalam variabel re3 jika abc tidak sam dengan null
                    re3[i]="";
                    for(int q=p;q>=0;q--){
                        if(abc[i][q]!=null){
                            re3[i]=re3[i].concat(abc[i][q]);
                        }
                    }
                    //hasil konfersi akan dicetak
                    System.out.println("Hasil Konversi dalam bentuk Regular Expresion : "+re3[i]);
                }
            }
        }else if(pilih==2){
            Scanner in = new Scanner(System.in);
            int [][] nfa_table = new int [100][5];
            initialise(nfa_table); //memanggil method initialise
            int states=0;
            //user memasukan regex kemudian akan ditampung oleh variabel s;
            System.out.println("Masukan Regex : ");
            String s;
            s = in.next();
            s = preprocessor(s); //memanggil method preprocessor kemudian akan ditampung lagi oleh variabel s
            System.out.println("Setelah preprocessor : ");
            System.out.println(s); //menampilkan hasil yang ditampung oleh variabel s
            s = postfix(s); //memanggil method postfix kemudian akan ditampung lagi oleh variabel s
            System.out.println("Setelah dipostifx : ");
            System.out.println(s); //menampilkan hasil yang ditampung oleh variabel s
            states=reg_nfa(s,nfa_table); //memanggil method reg_nfa kemudian ditampung pada variabel states
            print_nfa_table(nfa_table,states); //memanggil method print_nfa_table
        }
     }
     
}