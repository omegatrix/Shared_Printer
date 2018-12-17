package shared_printer;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Arnold Anthonypillai
 */
public class PaperTechnician extends Thread implements ANSI_Colours
{
    private final String paperTechnicianName;
    private final ThreadGroup threadGroup;
    private final String printerID;
    private final LaserPrinter laserPrinter;
    private final byte MAX_ATTEMPT;
    
    private volatile boolean exit;
    
    public PaperTechnician(String paperTechnicianName, ThreadGroup threadGroup, LaserPrinter laserPrinter) 
    {
        this.paperTechnicianName = paperTechnicianName;
        this.threadGroup = threadGroup;
        this.printerID = laserPrinter.getPrinterID();
        this.laserPrinter = laserPrinter;
        this.MAX_ATTEMPT = 3;
        this.exit = false;
        
        System.out.println(ANSI_PURPLE + "\nPaper technician " + paperTechnicianName + " thread has started!!" + ANSI_RESET);
    }
    
    @Override
    public void run()
    {   
        byte attemptNo = 0;
        
        while(true)
        {   
            if(exit)
            {
                System.out.println(ANSI_PURPLE + "\nPaper technician " + paperTechnicianName + " thread has terminated!!" + ANSI_RESET);
                break;
            }
            
            while(attemptNo < MAX_ATTEMPT)
            {
                synchronized(laserPrinter)
                {
                    if(laserPrinter.paperTrayNeedsRefilling())
                    {
                        laserPrinter.refillPaper();
                    }
                }

                attemptNo ++;

                try 
                {
                    Thread.sleep(5000);
                } 
                catch (InterruptedException ex) 
                {
                    Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            attemptNo = 0;
        }
    }

    public void setExit(boolean exit) 
    {
        this.exit = exit;
    }

    public String getPaperTechnicianName() 
    {
        return paperTechnicianName;
    }

    public String getThreadGroupName() 
    {
        return threadGroup.getName();
    }

    public String getPrinterID() 
    {
        return printerID;
    }

    public LaserPrinter getLaserPrinter() 
    {
        return laserPrinter;
    }

}
