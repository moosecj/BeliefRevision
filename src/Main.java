import java.util.ArrayList;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) throws Exception {

        ArrayList<LogicalExpression> LEset = new ArrayList<LogicalExpression>();
        boolean reset = true;

        while(true){
            Scanner scanner = new Scanner(System.in);
            String userInput = "";
            if(reset){
                System.out.println("Please enter a belief base:");
            
                // Get beliefbase
                userInput = scanner.nextLine();
                userInput = userInput.replaceAll("\\s+","");

                if(userInput.toLowerCase().equals("exit")) { System.exit(0); }

                if(userInput.toLowerCase().equals("agm")) { AGMPostulates agm = new AGMPostulates(); continue; }

                if(userInput.toLowerCase().equals("reset")) {reset = true; continue; }

                if(userInput.toLowerCase().equals("help")) {
                    System.out.println("The Syntax is as following (Syntax definition = syntax example):\nSymbol = p\nNot Symbol = !p\nSymbol and Symbol = p AND q\nSymbol or Symbol = p OR q\nSymbol Implies Symbol = p -> q\nSymbol Biimples Symbol = p <-> q");
                    System.out.println("An example could be 'p, p -> q' or 'p, p -> (q AND r)'");
                    System.out.println("'agm' - this runs the agm test and prints the output");
                    System.out.println("'reset' - this prompts the user to start over with a new beleif base");
                    System.out.println("'exit' - terminates the program");
                    continue;
                }

                String[] beliefBase = userInput.split(",");

                InputParser inputParser = new InputParser();
                LEset = inputParser.parseInput(beliefBase);
                reset = false;

            }
            
            // Get belief change
            System.out.println("Input a belief change.");
            userInput = scanner.nextLine();
            userInput = userInput.replaceAll("\\s+","");
            //if(userInput.contains(",")) { System.out.println("The belief change can only be a singular belief!"); continue; }

            if(userInput.toLowerCase().equals("exit")) { System.exit(0); }

            if(userInput.toLowerCase().equals("agm")) { AGMPostulates agm = new AGMPostulates(); continue; }
            
            if(userInput.toLowerCase().equals("reset")) {reset = true; continue; }

            if(userInput.toLowerCase().equals("help")) {
                System.out.println("The Syntax is as following (Syntax definition = syntax example):\nSymbol = p\nNot Symbol = !p\nSymbol and Symbol = p AND q\nSymbol or Symbol = p OR q\nSymbol Implies Symbol = p -> q\nSymbol Biimples Symbol = p <-> q");
                System.out.println("An example could be 'p, p -> q' or 'p, p -> (q AND r)'");
                System.out.println("'agm' - this runs the agm test and prints the output");
                System.out.println("'reset' - this prompts the user to start over with a new beleif base");
                System.out.println("'exit' - terminates the program");
                continue;
            }
        
            String[] beliefChange = userInput.split(",");


            // Check for rank input
            if(beliefChange.length == 2) {
                try {
                    int rank = Integer.parseInt(beliefChange[1]);
                    logicalExpressionParserWithRank(LEset, beliefChange, rank);
                    
                } catch (NumberFormatException e) {
                    System.out.println("Rank must be of type int!");
                    continue;
                }
            } else if(beliefChange.length <= 1) {
                LEset = logicalExpressionParser(LEset, beliefChange);
            } else {
                System.out.println("Belief change must be either singular or of type: expression, rank");
            }

        }
    }

    private static ArrayList<LogicalExpression> logicalExpressionParser(ArrayList<LogicalExpression> LEset, String[] _beliefChange) {
        //ArrayList<LogicalExpression> LEset = new ArrayList<LogicalExpression>();

        InputParser inputParser = new InputParser();
        //LEset = inputParser.parseInput(beliefBase);

        ArrayList<LogicalExpression> beliefChangeArr = inputParser.parseInput(_beliefChange);

        //if(beliefChangeArr.size() > 2) { System.out.println("The belief change can only be a singular belief!"); return null; }

        LogicalExpression beliefChange = beliefChangeArr.get(0);

        
        //beliefChange.setRank(0);

        
        BeliefRevisionAgent brAgent = new BeliefRevisionAgent(LEset);

     
        LEset = brAgent.revise(beliefChange);
        System.out.println("Revised belief base:");

        brAgent.print();

        return LEset;

    }

    private static ArrayList<LogicalExpression> logicalExpressionParserWithRank(ArrayList<LogicalExpression> LEset, String[] _beliefChange, int rank) {
        //ArrayList<LogicalExpression> LEset = new ArrayList<LogicalExpression>();

        InputParser inputParser = new InputParser();
        //LEset = inputParser.parseInput(beliefBase);

        ArrayList<LogicalExpression> beliefChangeArr = inputParser.parseInput(_beliefChange);

        LogicalExpression beliefChange = beliefChangeArr.get(0);
        beliefChange.setRank(rank);

        BeliefRevisionAgent brAgent = new BeliefRevisionAgent(LEset);

        LEset = brAgent.revise(beliefChange);
        System.out.println("Revised belief base:");

        for (LogicalExpression l : brAgent.getBase()) {
            System.out.println(l.toString() + ", " + l.getRank());
        }

        brAgent.print();
        
        return LEset;

    }
}
