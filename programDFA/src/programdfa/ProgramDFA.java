package programdfa;
import java.util.Scanner;

public class ProgramDFA {

    public static void main(String[] args) {
        
        Scanner input= new Scanner(System.in);
        int pilih, i, j, k, pjg, x=0, y=0;
        String transisi[][]=new String[5][5];
        String states[]=new String[5];
        String terminal[]=new String[5];
        String string[]=new String[5];
        String awal, akhir, read, akhir1, akhir2;
        System.out.println("Pilihan : ");
        System.out.println("1. Himpunan semua string yang mengandung substring 101");
        System.out.println("2. Himpunan semua string yang mengandung prefiks 101");
        System.out.println("3. Himpunan semua string yang mengandung sufiks 101");
        System.out.println("4. Himpunan semua string yang mengandung jumlah simbol 0 genap");
        System.out.println("5. Himpunan semua string yang mengandung jumlah simbol 1 ganjil");
        
        for(String b = "y"; b.equals("y") || b.equals("Y");){
            
            System.out.println("Pilih : ");
            pilih= input.nextInt();
            states[0]="q0";
            states[1]="q1";
            states[2]="q2";
            states[3]="q3";
            states[4]="q4";
            terminal[0]="0";
            terminal[1]="1";
            
            switch (pilih){
            case 1:
                
                transisi[0][0]="q0";
                transisi[0][1]="q1";
                transisi[1][0]="q2";
                transisi[1][1]="q1";
                transisi[2][0]="q1";
                transisi[2][1]="q3";
                transisi[3][0]="q3";
                transisi[3][1]="q3";
                System.out.println("Tabel transisi : ");
                for(i=0;i<4;i++){
                    for(j=0;j<2;j++){
                        System.out.print(transisi[i][j]+"  ");    
                    }
                    System.out.println("\n");
                }
                awal="q0";
                akhir="q3";
                System.out.print("masukkan panjang string : ");
                pjg = input.nextInt();

                System.out.print("masukkan semua string : ");
                for(j=0;j<pjg;j++){
                   string[j]=input.next();
                }
                
                for(k=0;k<string.length;k++){
                    read=string[k];
                    for(i=0;i<4;i++){
                        if(states[i].equals(awal)){
                            x=i;
                        }
                    }
                    for(j=0;j<2;j++){
                        if(terminal[j].equals(read)){
                            y=j;
                        }
                    }
                    awal=transisi[x][y];
                    if(k==(string.length-1)){
                        if(awal.equals(akhir)){
                            System.out.println("Ya");
                        }else{
                            System.out.println("Tidak");
                        }
                    }
                }
                break;
            case 2:
                transisi[0][0]="q4";
                transisi[0][1]="q1";
                transisi[1][0]="q2";
                transisi[1][1]="q0";
                transisi[2][0]="q1";
                transisi[2][1]="q3";
                transisi[3][0]="q3";
                transisi[3][1]="q3";
                transisi[4][0]="q4";
                transisi[4][1]="q4";
                System.out.println("Tabel transisi : ");
                for(i=0;i<5;i++){
                    for(j=0;j<2;j++){
                        System.out.print(transisi[i][j]+"  ");    
                    }
                    System.out.println("\n");
                }
                awal="q0";
                akhir="q3";
                System.out.print("masukkan panjang string : ");
                pjg = input.nextInt();

                System.out.print("masukkan semua string : ");
                for(j=0;j<pjg;j++){
                   string[j]=input.next();
                }
                
                for(k=0;k<string.length;k++){
                    read=string[k];
                    for(i=0;i<5;i++){
                        if(states[i].equals(awal)){
                            x=i;
                        }
                    }
                    for(j=0;j<2;j++){
                        if(terminal[j].equals(read)){
                            y=j;
                        }
                    }
                    awal=transisi[x][y];
                    if(k==(string.length-1)){
                        if(awal.equals(akhir)){
                            System.out.println("Ya");
                        }else{
                            System.out.println("Tidak");
                        }
                    }
                }
                break;
            case 3:
                break;
            case 4:
                transisi[0][0]="q3";
                transisi[0][1]="q1";
                transisi[1][0]="q2";
                transisi[1][1]="q0";
                transisi[2][0]="q1";
                transisi[2][1]="q3";
                transisi[3][0]="q0";
                transisi[3][1]="q2";
                System.out.println("Tabel transisi : ");
                for(i=0;i<4;i++){
                    for(j=0;j<2;j++){
                        System.out.print(transisi[i][j]+"  ");    
                    }
                    System.out.println("\n");
                }
                awal="q0";
                akhir1="q0";
                akhir2="q1";
                System.out.print("masukkan panjang string : ");
                pjg = input.nextInt();

                System.out.print("masukkan semua string : ");
                for(j=0;j<pjg;j++){
                   string[j]=input.next();
                }
                
                for(k=0;k<string.length;k++){
                    read=string[k];
                    for(i=0;i<4;i++){
                        if(states[i].equals(awal)){
                            x=i;
                        }
                    }
                    for(j=0;j<2;j++){
                        if(terminal[j].equals(read)){
                            y=j;
                        }
                    }
                    awal=transisi[x][y];
                    if(k==(string.length-1)){
                        if(awal.equals(akhir1)||awal.equals(akhir2)){
                            System.out.println("Ya");
                        }else{
                            System.out.println("Tidak");
                        }
                    }
                }
                break;
            case 5:
                transisi[0][0]="q3";
                transisi[0][1]="q1";
                transisi[1][0]="q2";
                transisi[1][1]="q0";
                transisi[2][0]="q1";
                transisi[2][1]="q3";
                transisi[3][0]="q0";
                transisi[3][1]="q2";
                System.out.println("Tabel transisi : ");
                for(i=0;i<4;i++){
                    for(j=0;j<2;j++){
                        System.out.print(transisi[i][j]+"  ");    
                    }
                    System.out.println("\n");
                }
                awal="q0";
                akhir1="q1";
                akhir2="q2";
                System.out.print("masukkan panjang string : ");
                pjg = input.nextInt();

                System.out.print("masukkan semua string : ");
                for(j=0;j<pjg;j++){
                   string[j]=input.next();
                }
                
                for(k=0;k<string.length;k++){
                    read=string[k];
                    for(i=0;i<4;i++){
                        if(states[i].equals(awal)){
                            x=i;
                        }
                    }
                    for(j=0;j<2;j++){
                        if(terminal[j].equals(read)){
                            y=j;
                        }
                    }
                    awal=transisi[x][y];
                    if(k==(string.length-1)){
                        if(awal.equals(akhir1)||awal.equals(akhir2)){
                            System.out.println("Ya");
                        }else{
                            System.out.println("Tidak");
                        }
                    }
                }
                break;
            default:
                System.out.println("Inputan salah");

        }
    }
}
}