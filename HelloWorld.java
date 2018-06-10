public class HelloWorld 
{
 
       public static void main (String[] args)
       {
             // Ausgabe Hello World! in file
             try (PrintWriter out = new PrintWriter("hello-world.txt")) {
               out.println("Hello World!");
             }
       }
}
