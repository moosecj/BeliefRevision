import java.security.KeyStore.TrustedCertificateEntry;
import java.util.*;
public class BaseChecker {
    
    boolean[][] truthTable;

    public static ArrayList<String> getSymbols(ArrayList<LogicalExpression> LEset){
        ArrayList<String> symbolArray = new ArrayList<>();
        for (LogicalExpression logicalExpression : LEset) {
            symbolArray.addAll(getSymbolsForLEset(logicalExpression));
        }
        ArrayList<String> ret = removeDuplicates(symbolArray);
        return ret;
    }

    public static ArrayList<String> getSymbolsForLEset(LogicalExpression le){
        if(le instanceof Symbol){
            ArrayList<String> symbolArray = new ArrayList<>();
            symbolArray.add(((Symbol)le).toString());
            return symbolArray;
        }
        if(le instanceof And){
            ArrayList<String> symbolArray = new ArrayList<>();
            symbolArray.addAll(getSymbolsForLEset(((And) le).getLogicalExpression1()));
            symbolArray.addAll(getSymbolsForLEset(((And) le).getLogicalExpression2()));
            return symbolArray;
        }
        if(le instanceof Or){
            ArrayList<String> symbolArray = new ArrayList<>();
            symbolArray.addAll(getSymbolsForLEset(((Or) le).getLogicalExpression1()));
            symbolArray.addAll(getSymbolsForLEset(((Or) le).getLogicalExpression2()));
            return symbolArray;
        }
        if(le instanceof Parenthesis){
            ArrayList<String> symbolArray = new ArrayList<>();
            symbolArray.addAll(getSymbolsForLEset(((Parenthesis) le).getLogicalExpression()));
            return symbolArray;
        }
        if(le instanceof Not){
            ArrayList<String> symbolArray = new ArrayList<>();
            symbolArray.addAll(getSymbolsForLEset(((Not) le).getLogicalExpression()));
            return symbolArray;
        }
        return null;
    }

    public static ArrayList<String> removeDuplicates(ArrayList<String> stringArray)
    {
        ArrayList<String> newstringArray = new ArrayList<String>();
        for (String string : stringArray) {

            if (!newstringArray.contains(string)) {
                newstringArray.add(string);
            }
        }
        return newstringArray;
    }

    int getBit(int n, int k) {
        return (n >> k) & 1;
    }

    public void fillTruthTable(ArrayList<String> symbolList, ArrayList<LogicalExpression> LEset) {
        this.truthTable = new boolean[(int)Math.pow(2, symbolList.size())][symbolList.size()+LEset.size()];
        
        for (int row = 0; row < truthTable.length; row++) {
            for (int col = 0; col < truthTable[row].length-LEset.size(); col++) {;
                truthTable[row][col] = getBit(row, col) == 1;
            }
        }
        

    }
    
    public boolean checkBase(ArrayList<LogicalExpression> LEset, ArrayList<String> symbolList) {

        for (int row = 0; row < truthTable.length; row++) {
            for (LogicalExpression le : LEset) {
                truthTable[row][symbolList.size()+LEset.indexOf(le)] = tryLeSet(le, truthTable[row], symbolList);
            }
        } 
        for (int row = 0; row < truthTable.length; row++) {
            boolean[] boolVal = Arrays.copyOfRange(truthTable[row], symbolList.size(),symbolList.size()+LEset.size());
            boolean temp = true;
            for (boolean yes : boolVal) {
                if (!yes) temp = false; 
            }
            if (temp) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<Integer> cleanBase(ArrayList<LogicalExpression> LEset, ArrayList<String> symbolList){
        int[] rankSum = new int[truthTable.length];
        for (int row = 0; row < truthTable.length; row++) {
            boolean[] boolVal = Arrays.copyOfRange(truthTable[row], symbolList.size(),symbolList.size()+LEset.size());
            rankSum[row] = 0;
            for(int i = 0; i < boolVal.length;i++){
                if(boolVal[i]==false){
                    rankSum[row] += 100 - LEset.get(i).getRank();
                }
            }

        }

        int lowestIndex = 0;
        for(int i = 0; i < rankSum.length;i++){
            if(rankSum[lowestIndex]>rankSum[i]){
                lowestIndex = i;
            }
        }
        System.out.println(lowestIndex);

        ArrayList<Integer> toRet = new ArrayList<Integer>();
        boolean[] boolVal = Arrays.copyOfRange(truthTable[lowestIndex], symbolList.size(),symbolList.size()+LEset.size());
        for(int i = 0; i < boolVal.length;i++){
            if(!boolVal[i]){
                toRet.add(i);
            }
        }

        return toRet;
    }


    public boolean tryLeSet(LogicalExpression le, boolean[] truthValues, ArrayList<String> symbols){

        if(le instanceof Symbol){
            ArrayList<String> symbolArray = new ArrayList<>();
            String symbol = (((Symbol)le).toString());
            return truthValues[symbols.indexOf(symbol)];
        }
        if(le instanceof And){
            Boolean b1 = tryLeSet(((And) le).getLogicalExpression1(),truthValues,symbols);
            Boolean b2 = tryLeSet(((And) le).getLogicalExpression2(),truthValues,symbols);
            return b1 && b2;
        }
        if(le instanceof Or){
            Boolean b1 = tryLeSet(((Or) le).getLogicalExpression1(),truthValues,symbols);
            Boolean b2 = tryLeSet(((Or) le).getLogicalExpression2(),truthValues,symbols);
            return b1 || b2;
        }
        if(le instanceof Parenthesis){
            Boolean b1 = tryLeSet(((Parenthesis) le).getLogicalExpression(),truthValues,symbols);
            return b1;
        }
        if(le instanceof Not){
            Boolean b1 = tryLeSet(((Not) le).getLogicalExpression(),truthValues,symbols);
            return !b1;
        }
        return false;

    }

}
