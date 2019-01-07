package shared_printer;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Arnold Anthonypillai
 */
public class LaserPrinter implements ServicePrinter, Utility 
{
    private Document document;
    private final String printerID;
    private int currentPaperQuantity;
    private int currentTonerLevel;
    private int numOfPrintedDoc;
    private boolean alreadyWaited;
    
    public LaserPrinter(String printerID) 
    {
        this.printerID = printerID;
        this.currentPaperQuantity = Full_Paper_Tray;
        this.currentTonerLevel = Full_Toner_Level;
        this.numOfPrintedDoc = 0;
        this.alreadyWaited = false;
        
        System.out.println
        (
            ANSI_GREEN 
            + "\nLaserPrinter monitor has started with paper level of: " + this.currentPaperQuantity 
            + " and toner level of: " + this.currentTonerLevel 
            + ANSI_RESET
        );
    }
    
    @Override
    public synchronized void replaceTonerCartridge() 
    {
        alreadyWaited = false;
        
        while(!cartridgeNeedsReplacing())
        {
            if(alreadyWaited) //toner technician has already waited
            {
                break;
            }
            
            try 
            {
                System.out.println(ANSI_GREEN + "\nToner technician is waiting 5 seconds before checking if it can be replaced again!!" + ANSI_RESET);
                wait(FIVE_SECOND);//TIMED_WAITING
            }
            
            catch (InterruptedException ex) 
            {
                Logger.getLogger(LaserPrinter.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            alreadyWaited = true;
        }
        
        if(cartridgeNeedsReplacing())
        {
            System.out.println(ANSI_GREEN + "\nCartridge is being replaced..." + ANSI_RESET);
            currentTonerLevel = Full_Toner_Level;
            System.out.println(ANSI_GREEN + "\nCartridge has been replaced successfully!!" + ANSI_RESET);
            System.out.println(toString());
        }

        notifyAll(); //notify the threads that are in the waitset
    }

    @Override
    public synchronized void refillPaper() 
    {
        alreadyWaited = false;
        
        while(!paperTrayNeedsRefilling())
        {
            if(alreadyWaited) //paper technician has already waited
            {
                break;
            }
            
           try 
           {
               System.out.println(ANSI_GREEN + "\nPaper technician is waiting 5 seconds before checking if it can be re-filled again!!" + ANSI_RESET);
               wait(FIVE_SECOND); //TIMED_WAITING
           }
           
           catch (InterruptedException ex) 
           {
               Logger.getLogger(LaserPrinter.class.getName()).log(Level.SEVERE, null, ex);
           }
           
           alreadyWaited = true;
        }
        
        if(paperTrayNeedsRefilling())
        {
            System.out.println(ANSI_GREEN + "\nPaper tray is being re-filled..." + ANSI_RESET);
            currentPaperQuantity += SheetsPerPack;
            System.out.println(ANSI_GREEN + "\nPaper tray has been re-filled successfully!" + ANSI_RESET);
            System.out.println(toString());
        }
        
        notifyAll(); //notify the threads that are in the waitset
    }

    @Override
    public synchronized void printDocument(Document document) 
    {
        this.document = document;
        
        while(!hasEnoughPaper() || !hasEnoughToner()) //either paper or toner is not sufficient
        {
            try 
            {
                System.out.println(ANSI_GREEN + this.document.getUserID() + " is waiting until the resources are available!!" + ANSI_RESET);
                wait(); //WAITING
            } 
             
            catch (InterruptedException ex) 
            {
                Logger.getLogger(LaserPrinter.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        System.out.println(ANSI_GREEN +"\nPrinting in progress..." + ANSI_RESET);

        //reduce the resources accordingly
        currentPaperQuantity -= this.document.getNumberOfPages();
        currentTonerLevel -= this.document.getNumberOfPages();
        numOfPrintedDoc ++;
        
        System.out.println
        (       
            ANSI_GREEN 
            + "\nPrint Job requested by " + this.document.getUserID()
            + " document " + this.document.getDocumentName()
            + " that has " + this.document.getNumberOfPages() 
            + " is complete!! "
            + ANSI_RESET
        );
        
        System.out.println(toString());
        
        notifyAll(); //notify the threads that are in the waitset
    }
    
    public synchronized String toString()
    {
        return 
        (
            ANSI_GREEN
            + "\n[ PrinterID: " + printerID + ", " 
            + "Paper Level: " + currentPaperQuantity + ", " 
            + "Toner Level: " + currentTonerLevel + ", " 
            + "Document Printed: " + numOfPrintedDoc + " ]"
            + ANSI_RESET
        );
    }

    private boolean paperTrayNeedsRefilling()
    {
        return (currentPaperQuantity + SheetsPerPack) <= Full_Paper_Tray;
    }
    
    private boolean cartridgeNeedsReplacing()
    {
        return currentTonerLevel < Minimum_Toner_Level;
    }
    
    private boolean hasEnoughPaper()
    {
        int documentSize = this.document.getNumberOfPages();
        return (currentPaperQuantity - documentSize) > 0;
    }
    
    private boolean hasEnoughToner()
    {
        int documentSize = this.document.getNumberOfPages();
        return (currentTonerLevel - documentSize) > 0;
    }

    public String getPrinterID() 
    {
        return printerID;
    }
    
    public int getCurrentPaperQuantity() 
    {
        return currentPaperQuantity;
    }

    public int getCurrentTonerLevel() 
    {
        return currentTonerLevel;
    }

    public int getNumOfPrintedDoc() 
    {
        return numOfPrintedDoc;
    }
}
