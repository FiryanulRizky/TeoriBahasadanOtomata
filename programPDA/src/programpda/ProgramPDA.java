package programpda;
import java.util.Scanner;
import java.util.Stack;

public class ProgramPDA {
    static int j, bstate, bsimbol, bsalp, x = 0, y = 0, z = 0;
    static Stack <String> ch = new Stack<>();
    static Stack <String> ch2 = new Stack<>();
    
    //method untuk menset index dalam array start state
    public static void setIn(String[] states, String[] simbol, String[] salp, String sstate, String read){
        for(j=0;j<bstate;j++){
            if(states[j].equals(sstate)){
                x=j;
            }
        }
        for(j=0;j<bsimbol+1;j++){
            if(simbol[j].equals(read)){
                y=j;
            }
        }
        for(j=0;j<bsalp;j++){
            if(salp[j].equals(ch.peek())){
                z=j;
            }
        }
    }
    public static void main(String[] args) {
        // TODO code application logic here
        
        Scanner input= new Scanner(System.in);
        int i, k, l;
        String[] simbol = {"a","b","c","d","e"};
        String[] states = new String[10];
        String[] salp = new String[10];
        String[][][] delta = new String[20][20][20];
        String[][][][] delta1 = new String[20][20][20][20];
        String sstate, fstate, ssimbol, string, read;
        boolean status = false;
        System.out.println("Pushdown Automata");
        
        //user menginputkan banyak state pada pda dan nilainya akan ditampung dalam varianel bstate
        System.out.print("\nMasukan banyak state : ");
        bstate = input.nextInt();
        //proses pemberian nilai pada setiap state
        for(i=0;i<bstate;i++){
            states[i] = Integer.toString(i);
            System.out.println("states["+i+"] : "+states[i]); 
        }
        //user menginputkan banyak simbol pada pda dan nilainya akan ditampung dalam varianel bsimbol
        System.out.print("\nMasukan banyak simbol : ");
        bsimbol=input.nextInt();
        //proses pemberian nilai pada setiap simbol
        for(i=0;i<bsimbol;i++){
            System.out.println("simbol["+i+"] : "+simbol[i]);
        }
        /*user menginputkan banyak stack alphabet yang akan digunakan pada proses stack(penumpukan) pada pda dan 
        nilainya akan ditampung dalam varianel bsalp */
        System.out.print("\nMasukan banyak stack alphabet : ");
        bsalp=input.nextInt();
        //setiap stack alphabet akan ditampung dalam array salp[]
        for(i=0;i<bsalp;i++){
            System.out.print("stack alphabet["+i+"] : ");
            salp[i]=input.next();
        }
        //user menginputkan start state pada pda dan nilainya akan ditampung dalam varianel sstate
        System.out.print("\nMasukan start state : ");
        sstate=input.next();
        //user menginputkan final state pada pda dan nilainya akan ditampung dalam varianel fstate
        System.out.print("Masukan final state : ");
        fstate=input.next();
        //user menginputkan start simbol pada pda dan nilainya akan ditampung dalam varianel ssimbol
        System.out.print("Masukan start simbol : ");
        ssimbol=input.next();
        //user menginputkan fungsi transisi pada pda dan nilainya akan ditampung dalam array delta[][][]
        System.out.println("\nMasukan fungsi transisi : ");
        for(i=0;i<bstate;i++){
            for(j=0;j<=bsimbol;j++){
                for(k=0;k<bsalp;k++){
                    if(j==bsimbol){
                        System.out.print("delta["+(states[i])+"][e]["+(salp[k])+"] : ");
                        simbol[j]="e";
                        delta[i][j][k]=input.next();
                    }else{
                        System.out.print("delta["+(states[i])+"]["+(simbol[j])+"]["+(salp[k])+"] : ");
                        delta[i][j][k]=input.next();
                    }
                    /*karena terdapat inputan yang berisi tanda "," sebagai pemisah antara state dengan simbol 
                    maka dilakukan proses pemisahan menggunakan split lalu hasilnya akan ditampung pada array delta1[][][][]*/
                    String[] bagian = delta[i][j][k].split(",");
                    l=0;
                    for(String bg :bagian){
                        delta1[i][j][k][l]=bg;
                        l++;
                    }
                }
            }
        }
        //user menginputkan string yang akan dicek dan nilainya akan ditampung dalam variabel string
        System.out.print("\nMasukan string yang ingin di cek : ");
        string=input.next();
        //string yang diinputkan dipisahkan persimbol dan ditampung dalam array string1
        char[] string1=string.toCharArray();
        //mula-mula stack ch di push dengan ssimbol dan ch2 di push dengan "e"
        ch.push(ssimbol);
        ch2.push("e");
        //stack ch2 diisi dengan setiap simbol yang ingin dicek
        for(i=(string1.length-1);i>=0;i--){
            ch2.push(Character.toString(string1[i]));
        }
        //proses menentukan apakah string yang diinputkan diterima oleh pda
        for(i=0;i<=string1.length;i++){
            //variabel read menampung top(nilai teratas) dari stack ch2
            read=ch2.peek();
            setIn(states, simbol, salp, sstate, read); //memanggil methode setIn
            if(delta1[x][y][z][0]!=null&&delta1[x][y][z][1]!=null){
                //variabel sstate memiliki nilai dari delta1
                sstate=delta1[x][y][z][0];
                //jika delta1 tidak sama dengan "e" maka stack ch akan mempush delta1 dan top dari stack ch2 akan dipop
                if(!delta1[x][y][z][1].equals("e")){
                    ch.push(delta1[x][y][z][1]);
                    ch2.pop();
                }else{
                    ch.pop();
                }
            }
            //jika sstate sama dengan fstate 
            if(sstate.equals(fstate)){
                for(j=0;j<=string1.length;j++){
                    if(!ch.peek().equals(ssimbol)&&ch2.peek().equals("e")){
                        ch.pop();
                    }else if(ch.peek().equals(ssimbol)&&ch2.peek().equals("e")){
                        status=true;
                    }else{
                        status=false;
                    }
                }
            }
        }
        if(status){
            System.out.println("String tersebut diterima oleh PDA");
        }else{
            System.out.println("String tersebut tidak diterima oleh PDA");
        }
    }
    
}
