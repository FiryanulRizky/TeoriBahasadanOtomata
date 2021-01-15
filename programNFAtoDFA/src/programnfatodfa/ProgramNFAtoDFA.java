/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programnfatodfa;
//pemanggilan library yang diperlukan dalam program
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.*;
/**
 *
 * @author Firyanul Rizky
 */
public class ProgramNFAtoDFA {

  //    pendeklarasian variabel yang digunakan dalam program
	static String line = null;
	static String l1 = null;
	static String l2 = null;
	static String l3 = null;
	static String l4 = null;
	static String l5 = null;
	static String [] states;
	static String [] goals;
	static String [] alphabets;
	static String start;
	static String [] transitions;
	static ArrayList<Transition> transitionList = new ArrayList<>();
	public static void main(String[] args) throws IOException {
                /*penggunaan fungsi FileReader untuk membaca file ini.txt dimana
                    dalam file .txt tersebut berisikan 5 tuple yang akan digunakan
                    untuk konversi dari NFA ke DFA
                */
		FileReader fileReader = new FileReader("dataset.txt");
               //penggunaan fungsi BufferedReader untuk membaca isi dari file yang telah dibaca FileReader
		BufferedReader br = new BufferedReader(fileReader);
                System.out.println("--------------");
                System.out.println("| NFA to DFA |");
                System.out.println("--------------");
		//baca file in.txt
                while((line = br.readLine())!= null){
			//baca line dimana menyimpan variabel dari state yang ada
			transitionList.clear();
			l1 = br.readLine(); //final state
			l2 = br.readLine(); //Alphabet
			l3 = br.readLine(); //Start State
			l4 = br.readLine(); //Transitions
			l5 = br.readLine(); //untuk pemisah antara nfa
			if(!checkLines()){ //pengecekan line kosong atau tidak
				System.err.print("empty line.");
				continue;
			}
                        //pengecekan untuk elemen state akan dipisah dengan ","
			states = line.split(",");
                        //pengecekan untuk elemen l1(final state) akan dipisah dengan ","
			goals = l1.split(",");
			if(!checkGoal()){// pengecekan goal
				continue;
			}
                        //pengecekan untuk elemen l2(alphabets) akan dipisah dengan ","
			alphabets = l2.split(",");
                        //inisialisasi l3(start state)
			start = l3;
			if(!checkStart()){ //pengecekan start state
				System.err.println("Invalid start state "+start);
				continue;
			}
                        //pengecekan untuk elemen l4(transitios) akan dipisah dengan "#"
			transitions = l4.split("#");
			boolean error = false;
			boolean error2 = false;
                        
			for(String transition : transitions){ /* fungsi untuk pengecekan elemen transition pada transitions
                            */
				error2 = false;
				String [] transitionArray = transition.split(",");
				if(transitionArray.length != 3){
					error = true;
					break;
				}
				for ( int i = 0 ; i <2 ;i++){
					if(!inArray(transitionArray[i], states)){
						error2 = true;
						System.err.println("Invalid transition. "+transitionArray[i]+" is not included in the states.");
						break;
					}	
				}
				if(!inArray(transitionArray[2], alphabets) &&!transitionArray[2].equals("$")){
					error2 = true;
					System.err.println("Invalid transition. "+transitionArray[2]+" is not included in the alphabet.");
				}
				if(error2){
					break;
				}
				transitionList.add(new Transition(transitionArray[0],transitionArray[1],transitionArray[2]));
			}
			if(error){
				System.err.println("Invalid transition. Transitions should be of size 3");
				continue;
			}
			if(error2){
				continue;
			}
                        System.out.println("------------------------------------");
                        System.out.println("NFA");
                        System.out.println("------------------------------------");
                        System.out.println("QN\t= "+line);
                        System.out.println("FN\t= "+l1);
                        System.out.println("S\t= "+l2);
                        System.out.println("Start\t= "+l3);
                        System.out.println("dN\t= "+l4);
			System.out.println("------------------------------------");
                        //fungsi utama untuk mengkonversi NFA ke DFA
			System.out.println("Equivalent DFA: ");
                        System.out.println("------------------------------------");
			ArrayList<String> initialStateDFA = getAllEpsilonClosure(start, transitionList.toArray(new Transition[transitionList.size()]));
			ArrayList<Transition> NFATransitions= new ArrayList<>();
			ArrayList<ArrayList<String>> allStates = new ArrayList<>();
			NFATransitions = makeTransitions(initialStateDFA, transitionList.toArray(new Transition[transitionList.size()]), alphabets);
			for(int i = 0; i < NFATransitions.size() ; i ++) {
				addToStates(allStates,NFATransitions.get(i).fromAL);
				addToStates(allStates,NFATransitions.get(i).toAL);
				addTransitionsIfNotExists(NFATransitions,makeTransitions(NFATransitions.get(i).toAL, transitionList.toArray(new Transition[transitionList.size()]), alphabets));
			}
			//mengeprint semua States
			String DFAStates = "";
			for(int i = 0 ; i<allStates.size();i++) {
				ArrayList<String> stateInAllStates = allStates.get(i);
				DFAStates += printStates(stateInAllStates);
				if(i<allStates.size()-1) {
					DFAStates += ",";
				}
			}
			System.out.println("QD : " + DFAStates);
			
			//PRINTING GOAL STATES
			String DFAGoals = "";
			for(int i = 0 ; i<allStates.size();i++) {
				ArrayList<String> stateInAllStates = allStates.get(i);
				if(hasAcceptState(goals, stateInAllStates)) {
					DFAGoals += printStates(stateInAllStates);
					if(i<allStates.size()-1) {
						DFAGoals += ",";
					}
				}
			}
			System.out.println("FD : " + DFAGoals);
			
			//PRINTING ALPHABET
			System.out.println("S : " + l2);
			
			//PRINTING INITIAL STATE
			String DFAInitState = printStates(initialStateDFA);
			System.out.println("Start : " + DFAInitState);
			
			//PRINTING ALL TRANSITIONS
			String DFATransitions = "";
			for(int i = 0 ; i<NFATransitions.size();i++) {
				DFATransitions+=printStates(NFATransitions.get(i).fromAL);
				DFATransitions+=",";
				DFATransitions+=printStates(NFATransitions.get(i).toAL);
				DFATransitions+=",";
				DFATransitions+=NFATransitions.get(i).alphabet;
				if(i < NFATransitions.size() - 1) {
					DFATransitions+="#";
				}
			}
			System.out.println("dD : " + DFATransitions);
			
			
			constructAndSolveDFA(DFAStates, DFAGoals, l2, DFAInitState, DFATransitions);
		}
		br.close();
	}
	
        //fungsi konversi dari NFA ke DFA
	public static void constructAndSolveDFA(String DFAstates, String DFAacceptStates, String DFAAlphabet, String DFAinitState, String DFAtransitions) {
		String line = DFAstates;
		String l1 = DFAacceptStates;
		String l2 = DFAAlphabet;
		String l3 = DFAinitState;
		String l4 = DFAtransitions;
                //pengecekan untuk elemen state akan dipisah dengan ","
		states = line.split(",");
                //pengecekan untuk elemen l1(final state) akan dipisah dengan ","
		goals = l1.split(",");
		if(!checkGoal()){ //pengecekan goal
			return;
		}
                //pengecekan untuk elemen l2(alphabets) akan dipisah dengan ","
		alphabets = l2.split(",");
                //inisialisasi start state = l3
		start = l3;
		if(!checkStart()){ //pengecekan start
			System.err.println("Invalid start state "+start);
			return;
		}
                //pengecekan untuk elemen l4(transitions) akan dipisah dengan "#"
		transitions = l4.split("#");
		boolean error = false;
		boolean error2 = false;
		for(String transition : transitions){ //pengecekan elemen transition pada transitions
			error2 = false;
			String [] transitionArray = transition.split(",");
			if(transitionArray.length != 3){
				error = true;
				break;
			}
			for ( int i = 0 ; i <2 ;i++){
				if(!inArray(transitionArray[i], states)){
					error2 = true;
					System.err.println("Invalid transition. "+transitionArray[i]+" is not included in the states.");
					break;
				}	
			}
			if(!inArray(transitionArray[2], alphabets) &&!transitionArray[2].equals("$")){
				error2 = true;
				System.err.println("Invalid transition. "+transitionArray[2]+" is not included in the alphabet.");
			}
			if(error2){
				break;
			}
			transitionList.add(new Transition(transitionArray[0],transitionArray[1],transitionArray[2]));
		}
		if(error){ //kondisi bahwa satu transition harus memiliki size 3
			System.err.println("Invalid transition. Transitions should be of size 3");
			return;
		}
		if(error2){
			return;
		}
		boolean error4 = false;
		for(String state : states){ //pengecekan elemen state pada states
			for(String alphabet : alphabets){
				if(!existsTransition(state,alphabet)){
					error4 = true;
					System.err.println("Missing transition for state " +state+" on input "+ alphabet );
					break;
				}
			}
		}
		if(error4){
			return;
		}
                System.out.println("");
	}

        
	private static void addToStates(ArrayList<ArrayList<String>> allStates, ArrayList<String> someStates) {
			if(!allStates.contains(someStates)) {
				allStates.add(someStates);
			}		
	}
        //fungsi untuk menambahkan transition jika belum dituliskan
	public static void addTransitionsIfNotExists(ArrayList<Transition> nFATransitions,ArrayList<Transition> newTransitions) {
		for(int i = 0 ; i< newTransitions.size() ; i++) {
			Collections.sort(newTransitions.get(i).fromAL);
			Collections.sort(newTransitions.get(i).toAL);
			int j;
			for (j = 0;j < nFATransitions.size(); j++) {
				Collections.sort(nFATransitions.get(j).fromAL);
				Collections.sort(nFATransitions.get(j).toAL);
				if(nFATransitions.get(j).fromAL.equals(newTransitions.get(i).fromAL) && nFATransitions.get(j).toAL.equals(newTransitions.get(i).toAL) && nFATransitions.get(j).alphabet.equals(newTransitions.get(i).alphabet)){
					break;
				}
			}
			if(j == nFATransitions.size()) {
				nFATransitions.add(newTransitions.get(i));
			}
		}
	}
	private static boolean checkStart() { //pengecekan start state
		return inArray(start, states);
	}
	private static boolean checkGoal() { /* fungsi untuk pengecekan apakah 
            elemen goal terdapat pada goals
            */
		for(String goal : goals){
			if(goal.equals("")){
				continue;
			}
			if(!inArray(goal,states)){ /* pengecekan elemen goal pada state */
				System.err.println("Invalid accept state "+goal);
				return false;
			}
		}
		return true;
	}
	private static boolean checkLines() { /*fungsi untuk membaca line dalam
            file jika kosong akan ditampilkan error sesuai dengan kondisi */
		if(l1 == ""){
			System.err.println("First line is an");
			return false;
		}
		if(l2 == ""){
			System.err.println("Second line is an");
			return false;
		}
		if(l3 == ""){
			System.err.println("Third line is an");
			return false;
		}
		if(l4 == ""){
			System.err.println("Fourth line is an");
			return false;
		}
		if(l5 == ""){
			System.err.println("Last line is not an");
			return false;
		}
		return true;
	}
	
	private static boolean inArray(String s , String [] array){
		for(int i = 0 ; i < array.length;i++){
			if(array[i].equals(s)){
				return true;
			}
		}
		return false;
	}
	
	public static ArrayList<String> getEpsilonClosure(String state,Transition[]transitions){
		ArrayList<String> result = new ArrayList<>();
		result.add(state);
		for(int i = 0 ; i<transitions.length;i++) {
			if(transitions[i].alphabet.equals("$") && transitions[i].from.equals(state)&&!result.contains(transitions[i].to)) {
				result.add(transitions[i].to);
			}
		}
		return result;
	}
	
	public static ArrayList<String> getAllEpsilonClosure(String state,Transition[]transitions){
		ArrayList<String> result = getEpsilonClosure(state, transitions);
		for(int i = 0 ; i < result.size();i++) {
			ArrayList<String> newOutcome = getEpsilonClosure(result.get(i), transitions);
			for(int j = 0 ; j<newOutcome.size();j++) {
				if (!result.contains(newOutcome.get(j))) {
					result.add(newOutcome.get(j));
				}
			}
		}
		return result;
	}
	
	public static boolean hasAcceptState(String[] acceptStates, ArrayList<String> stateOfStates) {
		for(int i = 0 ; i < stateOfStates.size();i++) {
			for( int j = 0 ; j < acceptStates.length ;j++) {
				if(acceptStates[j].equals(stateOfStates.get(i))) {
					return true;
				}
			}
		}
		return false;
	}
	
	public static ArrayList<String> getStatesForGivenInput(ArrayList<String> stateOfStates, Transition[]transitions, String alphabet){
		ArrayList<String> result = new ArrayList<>();
		for(int i = 0 ; i < stateOfStates.size() ; i++) {
			for(int j = 0 ; j < transitions.length ;j++) {
				if(transitions[j].alphabet.equals(alphabet) && transitions[j].from.equals(stateOfStates.get(i))&&!result.contains(transitions[j].to)) {
					result.add(transitions[j].to);
					addIfNotContains(result, getAllEpsilonClosure(transitions[j].to, transitions));
				}
			}
		}
		return result;
	}
	public static void addIfNotContains(ArrayList<String> result, ArrayList<String> arrayToBeAdded) {
		for(int i = 0; i<arrayToBeAdded.size();i++) {
			if(!result.contains(arrayToBeAdded.get(i))) {
				result.add(arrayToBeAdded.get(i));
			}
		}
	}
	
	public static ArrayList<Transition> makeTransitions(ArrayList<String> stateOfStates,Transition[]transitions,String[]alphabets) {
		ArrayList<Transition> result= new ArrayList<>();
		for(int i = 0 ; i< alphabets.length ; i++) {
			ArrayList<String> toStates = getStatesForGivenInput(stateOfStates, transitions, alphabets[i]);
			if(toStates.size() == 0) {
				toStates.add("Dead");
			}
			result.add(new Transition(stateOfStates, toStates, alphabets[i]));
		}
		return result;
	}
	public static String printStates(ArrayList<String>states) {
		String r = "";
		for(int i = 0 ; i<states.size();i++) {
			r+=states.get(i);
			if(i < states.size() - 1) {
				r+="*";
			}
		}
		return r;
	}
	private static boolean existsTransition(String state, String alphabet) {
		for(int i = 0 ; i < transitionList.size() ; i++){
			if(transitionList.get(i).from.equals(state) && transitionList.get(i).alphabet.equals(alphabet)){
				return true;
			}
		}
		return false;
	}
}

class Transition {
	String from;
	String to;
	ArrayList<String> fromAL;
	ArrayList<String> toAL;
	String alphabet;
	public Transition(String from, String to, String alphabet){
		this.from = from;
		this.to = to;
		this.alphabet = alphabet;
	}
	public Transition(ArrayList<String> from, ArrayList<String> to, String alphabet){
		this.fromAL = from;
		this.toAL = to;
		this.alphabet = alphabet;
	}
    
}