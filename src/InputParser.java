import java.util.ArrayList;
import java.util.Optional;

public class InputParser {
    private String[] beliefBaseStringArr;
    public InputParser() {}

    public ArrayList<LogicalExpression> parseInput(String[] _beliefBaseStringArr) {
        this.beliefBaseStringArr = _beliefBaseStringArr;

        ArrayList<LogicalExpression> returnList = new ArrayList<LogicalExpression>();

        //[p,p->q]
        for (String item : beliefBaseStringArr) {
            returnList.add(parseStringExpression(item));
        }

        /*
        p->q = Implication(parseInput(p), parseInput(q));
        */


        return returnList;
    }

    private LogicalExpression parseStringExpression(String beliefString) {
        // Base case
        if(beliefString.length() == 1) {
            Symbol symbol = new Symbol(beliefString);
            return symbol;
        }


        //[q->!p] = [!,p]
        String expr = "";
        char[] charArr = beliefString.toCharArray();
        for (int i = 0; i < charArr.length; i++) {
            char c = charArr[i];
            switch(c) {
                case('!'): // NOT
                    String rest = beliefString.substring(i, beliefString.length() - 1);
                    Not not = new Not(parseStringExpression(rest));
                return not;
                case('A'): // AND
                    if(i + 2 < beliefString.length() && (charArr[i + 1] == 'N' && charArr[i + 2] == 'D')) {
                        // p AND q
                        String before = beliefString.substring(0, i - 1);
                        String after = beliefString.substring(i + 2, beliefString.length() - 1);
                        And and = new And(parseStringExpression(before), parseStringExpression(after));
                        return and;
                    }
                break;
                case('O'): // OR
                    if(i + 1 < beliefString.length() && charArr[i + 1] == 'R') {
                        // p OR q
                        String before = beliefString.substring(0, i - 1);
                        String after = beliefString.substring(i + 2, beliefString.length() - 1);
                        And and = new And(parseStringExpression(before), parseStringExpression(after));
                        return and;
                    }
                case('-'): // ->
                    if(i + 1 < beliefString.length() && charArr[i + 1] == '>') {
                        // p -> q
                        String before = beliefString.substring(0, i - 1);
                        String after = beliefString.substring(i + 2, beliefString.length() - 1);
                        Implication implication = new Implication(parseStringExpression(before), parseStringExpression(after));
                        return implication;
                    }
                break;
                case('<'):
                    if(i + 2 < beliefString.length() && (charArr[i + 1] == '-' && charArr[i + 2] == '>')) {
                        // p <-> q
                        String before = beliefString.substring(0, i - 1);
                        String after = beliefString.substring(i + 2, beliefString.length() - 1);
                        Biimplication biimplication = new Biimplication(parseStringExpression(before), parseStringExpression(after));
                        return biimplication;
                    }
                break;
            }
        }



        return null;
    }



    /**
    
     if(item.length() == 1) {
                Symbol symbol = new Symbol(item);
                LEArrList.add(symbol);
            }

            String expr = "";

            //"(p->q)" = p, q

            // aORq
            
            // LE = Symbol(p)
            // LE = Implication(LE, Symbol(q))

            for (char c : item.toCharArray()) {
                switch(expr) {
                    case(""):
                        expr += c;
                    break;
                }
            }
    
     */
}