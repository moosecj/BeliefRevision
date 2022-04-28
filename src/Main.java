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
        ArrayList<LogicalExpression> LEset = logicalExpressionParser();
    }

    private static ArrayList<LogicalExpression> logicalExpressionParser() {
        Scanner scanner = new Scanner(System.in);
        ArrayList<LogicalExpression> LEset = new ArrayList<LogicalExpression>();

        // Print outs
        System.out.println("Please enter a belief base. \nThe Syntax is as following (Syntax definition = syntax example):\nSymbol = p\nNot Symbol = !p\nSymbol and Symbol = p AND q\nSymbol or Symbol = p OR q\nSymbol Implies Symbol = p -> q\nSymbol Biimples Symbol = p <-> q");
        System.out.println("An example could be 'p, p -> q' or p, p -> (q AND r)");

        String userInput = scanner.nextLine();
        userInput.replaceAll("\\s+","");
        String[] BeliefBase = userInput.split(",");

        InputParser inputParser = new InputParser();
        inputParser.parseInput(BeliefBase);

        
        // for(int i = 0; i < BeliefBases.length; i++){
        //     switch(BeliefBases[i]){
        //         case true:
        //             break;
        //         default:
        //         Symbol S = new Symbol(b, rank)
            
        // }
        
        return LEset;

    }
}
