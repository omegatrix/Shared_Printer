package shared_printer;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Arnold Anthonypillai
 */
public class PaperTechnician extends Thread implements Utility
{
    private final String paperTechnicianName;
    private final ThreadGroup threadGroup;
    private final String printerID;
    private final LaserPrinter laserPrinter;
    
    public PaperTechnician(String paperTechnicianName, ThreadGroup threadGroup, LaserPrinter laserPrinter) 
    {
        super(threadGroup, paperTechnicianName);
        
        this.paperTechnicianName = paperTechnicianName;
        this.threadGroup = threadGroup;
        this.printerID = laserPrinter.getPrinterID();
        this.laserPrinter = laserPrinter;

        System.out.println(ANSI_PURPLE + "\nPaper technician " + paperTechnicianName + " is in thread state " + String.valueOf(this.getState()) + ANSI_RESET);
    }
    
    @Override
    public void run()
    {   
        System.out.println(ANSI_PURPLE + "\nPaper technician " + paperTechnicianName + " is in thread state " + String.valueOf(this.getState()) + ANSI_RESET);
        
        int attemptNo = 1;
        int randomNum;
                 
        while(attemptNo <= MAX_ATTEMPT)
        {
            System.out.println
            (
                ANSI_PURPLE + "\nPaper technician " + paperTechnicianName 
                + " is making the attempt no: " + Integer.toString(attemptNo) + " to re-fill the paper tray"
                + ANSI_RESET
            );

            laserPrinter.refillPaper();

            try 
            {
                randomNum = (int)(Math.random() * 10 + 1); //random number from 1 - 10

                System.out.println
                (
                    ANSI_PURPLE + "\nPaper technician " + paperTechnicianName 
                    + " is sleeping " + Integer.toString(randomNum) 
                    + " seconds after atempt no: " + Integer.toString(attemptNo) + " !!"
                    + ANSI_RESET
                );

                Thread.sleep(randomNum * ONE_SECOND);
            }

            catch (InterruptedException ex) 
            {
                Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            attemptNo ++;
        }
        
        System.out.println(ANSI_PURPLE + "\nPaper technician " + paperTechnicianName + " thread is terminating!!" + ANSI_RESET);
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
}
