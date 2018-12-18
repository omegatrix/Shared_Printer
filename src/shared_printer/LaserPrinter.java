package shared_printer;

/**
 *
 * @author Arnold Anthonypillai
 */
public class LaserPrinter implements ServicePrinter, ANSI_Colours 
{
    private Document document;
    private final String printerID;
    private int currentPaperQuantity;
    private int currentTonerLevel;
    private int numOfPrintedDoc;
    
    public LaserPrinter(String printerID, int currentPaperQuantity, int currentTonerLevel) 
    {
        this.printerID = printerID;
        this.currentPaperQuantity = currentPaperQuantity;
        this.currentTonerLevel = currentTonerLevel;
        this.numOfPrintedDoc = 0;
        
        System.out.println(ANSI_GREEN + "\nLaserPrinter monitor has started!!" + ANSI_RESET);
    }
    
    @Override
    public void replaceTonerCartridge() 
    {
        System.out.println(ANSI_CYAN + "\nReplacing cartridge..." + ANSI_RESET);
        currentTonerLevel = Full_Toner_Level;
        System.out.println(ANSI_CYAN + "\nCartridge replaced successfully!" + ANSI_RESET);
        
        System.out.println(toString());
    }

    @Override
    public void refillPaper() 
    {
        System.out.println(ANSI_PURPLE + "\nRe-filling paper..." + ANSI_RESET);
        currentPaperQuantity += SheetsPerPack;
        System.out.println(ANSI_PURPLE + "\nPaper tray re-filled successfully!" + ANSI_RESET);
        
        System.out.println(toString());
    }

    @Override
    public void printDocument(Document document) 
    {
        System.out.println(ANSI_GREEN +"\nPrinting in progress..." + ANSI_RESET);
        
        currentPaperQuantity -= document.getNumberOfPages();
        currentTonerLevel -= document.getNumberOfPages();
        numOfPrintedDoc ++;

        System.out.println
        (       
            ANSI_GREEN 
            + "\nPrint Job requested by " + document.getUserID()
            + " document " + document.getDocumentName()
            + " is complete!! " + toString()
            + ANSI_RESET
        );

        System.out.println(toString());
    }
    
    public String toString()
    {
        return 
        (
            ANSI_GREEN
            +
            "\n[ PrinterID: " + printerID + ", " 
            + "Paper Level: " + currentPaperQuantity + ", " 
            + "Toner Level: " + currentTonerLevel + ", " 
            + "Document Printed: " + numOfPrintedDoc + " ]"
            + ANSI_RESET
        );
    }
    
    public boolean paperTrayNeedsRefilling()
    {
        return (currentPaperQuantity + SheetsPerPack) <= Full_Paper_Tray;
    }
    
    public boolean cartridgeNeedsReplacing()
    {
        return currentTonerLevel < Minimum_Toner_Level;
    }
    
    public boolean hasEnoughPaper(int documentSize)
    {
        return (currentPaperQuantity - documentSize) >= 0;
    }
    
    public boolean hasEnoughToner(int documentSize)
    {
        return (currentTonerLevel - documentSize) >= 0;
    }
    
    public Document getDocument() 
    {
        return document;
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
}
