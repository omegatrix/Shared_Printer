package shared_printer;

/**
 *
 * @author Arnold Anthonypillai
 */
public class SharedPrintingSystem 
{
    
    public static void main(String[] args) 
    {
        PrintingSystem printingSystem = new PrintingSystem();
        
        printingSystem.initiateThreads();
        printingSystem.checkThreadStatus();
    }
    
}
