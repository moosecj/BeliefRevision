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
            
            /*if(item.length() == 1) {
                Symbol symbol = new Symbol(item);
                LEArrList.add(symbol);
            }*/

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

        }

        /*
        p->q = Implication(parseInput(p), parseInput(q));
        */


        return returnList;
    }
}