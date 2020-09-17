
/** An example to illustrate creating a class that uses a class
 * defined in another file
 *
 * @author rlsummerscales
 *
 */

public class UseStack {
 
    public static void main(String[] args) {
        Stack<String> s = new Stack<String>();
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            if (!item.equals("-")) s.push(item);
            else if (!s.isEmpty()) StdOut.print(s.pop() + " ");
        }
        StdOut.println("(" + s.size() + " left on stack)");
        for (String stackString : s) {
         System.out.println(stackString);
        }
    }
}