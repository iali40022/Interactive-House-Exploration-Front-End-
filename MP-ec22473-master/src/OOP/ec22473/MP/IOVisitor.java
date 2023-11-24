package OOP.ec22473.MP;


import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

class IOVisitor implements Visitor {
         
    private PrintStream out;
    private Scanner in;
    private int purse;
    private Item[] items; 
    private int next;
    
    public IOVisitor(PrintStream ps, InputStream is) {
        out = ps;
        in = new Scanner(is);
        purse = 0;
        items = new Item[1000];
        next = 0;
    }
    
    
    private static final char[] yOrN = { 'y', 'n'};
   
    public void tell(String m) {
        out.println(m);
    }
    
    public char getChoice(String d, char[] a) {
        out.println(d);
        String t = in.nextLine();
        if (t.length() > 0) 
            return t.charAt(0);
        else {
            if (a.length > 0) {
                out.println("Returning "+a[0]);
                return a[0];
            } else {
                out.println("Returning '?'");
                return '?';
            } 
        }
    }
    
    public boolean giveItem(Item x) {
        out.println("You are being offered: "+x.name);
        if (next >= items.length) {
            out.println("But you have no space and must decline.");
            return false;
        }
        if (getChoice("Do you accept (y/n)?", yOrN) == 'y') {
            items[next] = x;
            next++;
            return true;
        } else return false;
    }
    
    public boolean hasIdenticalItem(Item x) {
        for (int i=0; i<next;i++) 
            if (x == items[i]) 
                return true;
        return false;
    }
        
    public boolean hasEqualItem(Item x) {
        for (int i=0; i<next;i++) 
            if (x.equals(items[i])) 
                return true;
        return false;
    }
    
    public void giveGold(int n) {
        out.println("You are given "+n+" gold pieces.");
        purse += n;
        out.println("You now have "+purse+" pieces of gold.");
    }
        
    public int takeGold(int n) {
        
        if (n<0) {
            out.println("A scammer tried to put you in debt to the tune off "+(-n)+"pieces of gold,");
            out.println("but you didn't fall for it and returned the 'loan'.");
            return 0;
        }
        
        int t = 0;
        if (n > purse) t = purse;
        else t = n;
        
        out.println(t+" pieces of gold are taken from you.");
        purse -= t;
        out.println("You now have "+purse+" pieces of gold.");
        
        return t;
    }
}
