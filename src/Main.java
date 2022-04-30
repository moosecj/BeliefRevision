import java.lang.Character.UnicodeBlock;
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
        Implication pthenq = new Implication(p, q);

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
            LEset = logicalExpressionParser();
            System.out.println("Parser");
            for (LogicalExpression item : LEset) {
                System.out.println(item.toString());
            }
        }
        
    }

    private static ArrayList<LogicalExpression> logicalExpressionParser() {
        Scanner scanner = new Scanner(System.in);
        ArrayList<LogicalExpression> LEset = new ArrayList<LogicalExpression>();

        // Print outs
        System.out.println("Please enter a belief base. \nThe Syntax is as following (Syntax definition = syntax example):\nSymbol = p\nNot Symbol = !p\nSymbol and Symbol = p AND q\nSymbol or Symbol = p OR q\nSymbol Implies Symbol = p -> q\nSymbol Biimples Symbol = p <-> q");
        System.out.println("An example could be 'p, p -> q' or 'p, p -> (q AND r)'");

        String userInput = scanner.nextLine();
        userInput = userInput.replaceAll("\\s+","");

        if(userInput.toLowerCase() == "exit") {System.out.println("HEJ"); System.exit(0); }

        String[] BeliefBase = userInput.split(",");

        InputParser inputParser = new InputParser();
        LEset = inputParser.parseInput(BeliefBase);

        
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
