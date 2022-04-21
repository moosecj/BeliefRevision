public class propositionallogic {
    public static boolean negation(boolean p){
        return !p;
    }

    public static boolean conjunction(boolean p, boolean q){
        return p && q;
    }

    public static boolean disjunction(boolean p, boolean q){
        return p || q;
    }

    public static boolean implication(boolean p, boolean q){
        if(p == true && q == true){
            return true;
        }else if(p == true && q == false){
            return false;
        }else if(p == false && q == true){
            return true;
        }else{
            return true;
        }
    }

    public static boolean biconditional(boolean p, boolean q){
        if(p == true && q == true){
            return true;
        }else if(p == true && q == false){
            return false;
        }else if(p == false && q == true){
            return false;
        }else{
            return true;
        }
    }
}
