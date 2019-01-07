package shared_printer;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Arnold Anthonypillai
 */
public class TonerTechnician extends Thread implements Utility
{
    private final String tonerTechnicianName;
    private final ThreadGroup threadGroup;
    private final String printerID;
    private final LaserPrinter laserPrinter;

    public TonerTechnician(String tonerTechnicianName, ThreadGroup threadGroup, LaserPrinter laserPrinter) 
    {
        super(threadGroup, tonerTechnicianName);
        
        this.tonerTechnicianName = tonerTechnicianName;
        this.threadGroup = threadGroup;
        this.printerID = laserPrinter.getPrinterID();
        this.laserPrinter = laserPrinter;

        System.out.println(ANSI_CYAN + "\nToner technician " + tonerTechnicianName + " is in thread state " + String.valueOf(this.getState()) + ANSI_RESET);
    }
    
    @Override
    public void run()
    {
        System.out.println(ANSI_CYAN + "\nToner technician " + tonerTechnicianName + " is in thread state " + String.valueOf(this.getState()) + ANSI_RESET);
        
        int attemptNo = 1; 
        int randomNum;

            while(attemptNo <= MAX_ATTEMPT)
            {
                System.out.println
                (
                    ANSI_CYAN + "\nToner technician " + tonerTechnicianName 
                    + " is making the attempt no: " + Integer.toString(attemptNo) + " to replace the toner cartridge"
                    + ANSI_RESET
                );
                
                laserPrinter.replaceTonerCartridge();

                try 
                {
                    randomNum = (int)(Math.random() * 10 + 1); //random number from 1 - 10
                    
                    System.out.println
                    (
                        ANSI_CYAN + "\nToner technician " + tonerTechnicianName 
                        + " is sleeping " + randomNum 
                        + " seconds after atempt no: " + attemptNo + " !!"
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
        
        System.out.println(ANSI_CYAN + "\nToner technician " + tonerTechnicianName + " thread is terminating!!" + ANSI_RESET);
    }

    public String getTonerTechnicianName() 
    {
        return tonerTechnicianName;
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
