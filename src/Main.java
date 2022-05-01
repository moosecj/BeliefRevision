import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    /*
    INPUT: Belief base + Belief change
    SYMBOL implies (notSYMBOL impliet SYMBOL), SYMBOL, SYMBOL

    Belief base
    B = (p, p <-> q)
    B = (q, p <-> q)

    Belief set
    Cn(B) = (p, q, p <-> q)
    

    SYMBOLS = any char
    IMPLICATION = ->
    BIIMPLICATION = <->
    AND = AND
    OR = OR
    NOT = !
    PARANTHESIS = ()

    Example input:
    "{p, p -> q}" "!p"

    Symbol = 

    Logical Expression:
        SYMBOLS = any char
        IMPLICATION = ->
        BIIMPLICATION = <->
        AND = AND
        OR = OR
        NOT = !
        PARANTHESIS = ()

    Logical Expression(Logical Expression)

    Logical Expression:
        Logical Expression -> Logical Expression
            Symbol -> Logical Expression
                p -> Logical Expression
                    p -> Symbol
                        p -> q


    [Logical Expression, Logical Expression, ..., Logical Expression]
    
    
     */

    public static void main(String[] args) throws Exception {
        Symbol p = new Symbol("p");
        Symbol q = new Symbol("q");
        Symbol s = new Symbol("s");
        LogicalExpression pthenq = new Implication(p, q);

        System.out.println(pthenq + " contains " + p + ": " + pthenq.contains(p));
        
        System.out.println(pthenq + " contains " + s + ": " + pthenq.contains(s));

        ArrayList<LogicalExpression> LEset = new ArrayList<LogicalExpression>();

        LEset.add(p);
        LEset.add(pthenq);
        LEset.add(s);
        
        //ArrayList<LogicalExpression> LEset = logicalExpressionParser();
        System.out.println("BEFORE CONTRACT");
        for (LogicalExpression item : LEset) {
            System.out.println(item.toString());
        }

        BeliefRevisionAgent brAgent = new BeliefRevisionAgent(LEset);

        brAgent.contract(LEset, q);

        System.out.println("AFTER CONTRACT");
        for (LogicalExpression item : LEset) {
            System.out.println(item.toString());
        }


        while(true){
            Scanner scanner = new Scanner(System.in);
            // Print outs
            //System.out.println("Please enter a belief base. \nThe Syntax is as following (Syntax definition = syntax example):\nSymbol = p\nNot Symbol = !p\nSymbol and Symbol = p AND q\nSymbol or Symbol = p OR q\nSymbol Implies Symbol = p -> q\nSymbol Biimples Symbol = p <-> q");
            //System.out.println("An example could be 'p, p -> q' or 'p, p -> (q AND r)'");
            System.out.println("Please enter a belief base:");

            // Get beliefbase
            String userInput = scanner.nextLine();
            userInput = userInput.replaceAll("\\s+","");

            if(userInput.toLowerCase().equals("exit")) { System.exit(0); }

            String[] beliefBase = userInput.split(",");

            // Get belief change
            System.out.println("Input a belief change.");
            userInput = scanner.nextLine();
            userInput = userInput.replaceAll("\\s+","");
            if(userInput.contains(",")) { System.out.println("The belief change can only be a singular belief!"); continue; }
            System.out.println(userInput);
            String[] beliefChange = new String[]{userInput};

            LEset = logicalExpressionParser(beliefBase, beliefChange);
            System.out.println("Parser");
            for (LogicalExpression item : LEset) {
                System.out.println(item.toString());
            }
            LEset = CNFConverter.cNFConverter(LEset);
            System.out.println("Converted:");
            for (LogicalExpression item : LEset) {
                System.out.println(item.toString());
            }
            BaseChecker bc = new BaseChecker();
            ArrayList<String> symb = bc.getSymbols(LEset);
            bc.fillTruthTable(symb, LEset);
            bc.checkBase(LEset, symb);
        }
        // !p, p->q = !q, q?
    }

    private static ArrayList<LogicalExpression> logicalExpressionParser(String[] beliefBase, String[] _beliefChange) {
        ArrayList<LogicalExpression> LEset = new ArrayList<LogicalExpression>();

        InputParser inputParser = new InputParser();
        LEset = inputParser.parseInput(beliefBase);

        ArrayList<LogicalExpression> beliefChangeArr = inputParser.parseInput(_beliefChange);
        LogicalExpression beliefChange = beliefChangeArr.get(0);
        
        BeliefRevisionAgent brAgent = new BeliefRevisionAgent(LEset);

        System.out.println("BEFORE");
        for (LogicalExpression item : LEset) {
            System.out.println(item.toString());
        }

        System.out.println("ADDITION");
        //String w = (beliefChange instanceof Not) ? "1" : "2";
        LEset = brAgent.contract(LEset, (beliefChange instanceof Not) ? ((Not) beliefChange).getLogicalExpression() : new Not(beliefChange));
        LEset = brAgent.expand(LEset, beliefChange);


        /*System.out.println("CONTRACTION");
        ArrayList<LogicalExpression> LEsetContract = brAgent.contract(LEset, beliefChange);

        for (LogicalExpression item : LEsetContract) {
            System.out.println(item.toString());
        }*/



        // for(int i = 0; i < BeliefBases.length; i++){
        //     switch(BeliefBases[i]){
        //         case true:
        //             break;
        //         default:
        //         Symbol S = new Symbol(b, rank)
            
        // }
        
        return LEset;

    }

    // public String baseToString(ArrayList<LogicalExpression> base) {
    //     String res = "\{";
    //     for(LogicalExpression le : base) {
            
    //     }

    //     return res;
    // } 

    // private String extractValue(LogicalExpression le) {
    //     ret = "";


    //     return ret;
    // }
}
