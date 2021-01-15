package programminimasidfa;
import java.util.Scanner;
public class ProgramMinimasiDFA {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int simbol,state,state2, stateBaru=0,i,j,k,l,m,n,a,b,c,x;
        String[] bsimbol = new String[10];
        String[] bstates = new String[10];
        String[][] bdelta = new String[20][20];
        String[][][] bdelta2 = new String[20][20][20];
        String[][] bdelta3 = new String[20][20];
        String[][] akhir = new String[20][20];
        int[][] bl = new int[20][20];
        String start, end, dfa;
        System.out.println("");
        System.out.println("Penentuan DFA Minimum");
        System.out.println("Masukkan banyak simbol : ");
        simbol = input.nextInt();
        for(i=0;i<=simbol;i++) {
            System.out.print("simbol["+i+"] : ");
            bsimbol[i] = input.next();
        }
        System.out.println("Masukkan banyak states");
        state = input.nextInt();
        for(i=0;i<state;i++){
            System.out.print("bstate["+i+"] : ");
            bstates[i] = input.next();
        }
        System.out.print("\nMasukkan state awal : ");
        start = input.next();
        System.out.print("Masukkan final state : ");
        end = input.next();
        
        System.out.println("\nMasukan fungsi transisi : ");
        System.out.println("- Jika ada transisi yang kosong masukan 0\n- Jika lebih dari satu tambahkan tanda ','");
      
        for(i=0;i<state;i++){
            for(j=0;j<simbol;j++){
                System.out.print("delta["+i+"]["+j+"] : "); 
                bdelta[i][j] = input.next();
                System.out.println("delta["+i+"]["+j+"] : "+bdelta[i][j]); 
            }
        }
        System.out.println("\n==Tabel transisi DFA=="); 
        System.out.println("----------------------");
        System.out.print("      "); 
        for(i=0;i<simbol;i++){
            System.out.print("     "+(bsimbol[i])+"      "); 
        }
        System.out.print("\n"); 
        for(i=0;i<state;i++){
            System.out.print((bstates[i])+"    ");
            for(j=0;j<simbol;j++){
                System.out.print("    "+(bdelta[i][j])+"      ");
            }
            System.out.print("\n"); 
        }
        x=state;
        for(i=0;i<state;i++){
            for(j=0;j<simbol;j++){
                if(bdelta[i][j].length()>2){
                   stateBaru=stateBaru+1;
                   bstates[x]= bdelta[i][j];
                   System.out.println("states["+x+"] : "+bstates[x]);
                   System.out.println("delta["+i+"]["+j+"].length : "+bdelta[i][j].length());
                   String[] bagian = bdelta[i][j].split(",");
                    
                   bl[i][j]=bagian.length;
                   System.out.println("bagian["+i+"]["+j+"].length : "+bl[i][j]);
                   k=0;
                   for(String bg :bagian){
                        System.out.println("bagian["+k+"]: "+bagian[k]);
                        bdelta2[i][j][k]=bg;
                         System.out.println("delta2["+i+"]["+j+"]["+k+"] : "+(bdelta2[i][j][k]));
                         if("0".equals(bdelta[k][j])){
                             akhir[i][j]=akhir[i][j]; 
                             System.out.println("akhir["+i+"]["+j+"] : "+akhir[i][j]);
                         }else{
                             akhir[i][j]=bdelta[k][j]; 
                             System.out.println("akhir["+i+"]["+j+"] : "+akhir[i][j]);
                         }
                         k++;  
                    }
                }
                x=x+1;
            }
        }
        
        System.out.println("stateBaru= "+stateBaru);
        System.out.println("\n");
        x=state;
        state2=state+stateBaru;
        System.out.println("bstate2= "+state2);
        for(i=0;i<state2;i++){
            for(j=0;j<simbol;j++){
                System.out.println("x1= "+x);
                for(k=0;k<simbol;k++){
                    bdelta[x][k]="";
                }
                for(k=0;k<simbol;k++){
                    System.out.println("k= "+k);
                    if(bdelta[i][j].length()>2){
                        System.out.println("bl["+i+"]["+j+"].length : "+bl[i][j]);
                        for(l=0;l<bl[i][j];l++){
                            System.out.println("delta2["+i+"]["+j+"]["+l+"] : "+(bdelta2[i][j][l]));
                            for(m=0;m<state;m++){
                                System.out.println("delta["+x+"]["+k+"] : "+bdelta[x][k]);
                                if(bdelta2[i][j][l].equals(bstates[m])){
                                    System.out.println("delta[m][k] : "+bdelta[m][k]);
                                    if("0".equals(bdelta[m][k])){
                                        bdelta[x][k]=bdelta[x][k];
                                        System.out.println("delta[x][k]["+x+"]["+k+"] : "+bdelta[x][k]);
                                    }
                                    else{
                                        System.out.println("akhir[i][k] : "+akhir[i][k]);
                                        if(bdelta[m][k].equals(akhir[i][k])){
                                            bdelta[x][k]=bdelta[x][k].concat(bdelta[m][k]);
                                            System.out.println("abcdelta["+x+"]["+k+"] : "+bdelta[x][k]);
                                        }else{
                                            bdelta[x][k]=bdelta[x][k].concat(bdelta[m][k]).concat(",");
                                            System.out.println("abcdelta["+x+"]["+k+"] : "+bdelta[x][k]);
                                        }
                                        
                                        
                                    }
                                }
                            }
                            
                        }
                    }  
                    if(bdelta[x][k].length()>2){
                        for(n=0;n<state2;n++){
                            if(bdelta[x][k].equals(bstates[n])){
                                stateBaru=stateBaru;
                            }else{
                                stateBaru=stateBaru+1;
                                bstates[state2]=bdelta[x][k];
                            }
                            System.out.println("statebaru= "+stateBaru);
                            System.out.println("states["+state2+"]= "+bstates[state2]);
                        }
                    }
                    state2=state2+1;
                }
                x=x+1;
                System.out.println("x2= "+x);
            }
        }
     }
     
}