package shared_printer;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Arnold Anthonypillai
 */
public class PrintingSystem implements Utility
{
    private final LaserPrinter laserPrinter;

    private final ThreadGroup studentThreadGroup;
    private final ThreadGroup technicianThreadGroup;

    private final Student student_1;
    private final Student student_2;
    private final Student student_3;
    private final Student student_4;

    private final PaperTechnician paperTechnician;
    private final TonerTechnician tonerTechnician;
     
    public PrintingSystem()
    {
        System.out.println
        (
            "******************************************************************************************************************************" 
            + "\n* " + ANSI_WHITE + "This is a Printing system that demonstrate the concurrency features in Java such as mutual exclusion, thread handling etc. * "
            + "\n* The system is highlighted with different colours to differentiate the thread activities as follows:                        * " + ANSI_RESET
            + "\n* " + ANSI_GREEN + "LaserPrinter - Thread Monitor class" + ANSI_RESET + "                                                                                        *"
            + "\n* " + ANSI_YELLOW + "Student thread - 4 instances of this class" + ANSI_RESET + "                                                                                 *"
            + "\n* " + ANSI_PURPLE + "Paper technician thread - 1 instance of this class" + ANSI_RESET + "                                                                         *"
            + "\n* " + ANSI_CYAN + "Toner technician thread - 1 instance of this class" + ANSI_RESET + "                                                                         *"
            + "\n******************************************************************************************************************************"
        );
        
        laserPrinter = new LaserPrinter("LaserPrinter");
        
        studentThreadGroup = new ThreadGroup("Student_Thread_Group");
        technicianThreadGroup = new ThreadGroup("Technician_Thread_Group");
        
        student_1 = new Student("Aron Good", studentThreadGroup, laserPrinter);
        student_2 = new Student("Jaime Button", studentThreadGroup, laserPrinter);
        student_3 = new Student("Elliot Elenor", studentThreadGroup, laserPrinter);
        student_4 = new Student("Brandon Barnes", studentThreadGroup, laserPrinter);

        paperTechnician = new PaperTechnician("Paper Guy", technicianThreadGroup, laserPrinter);
        tonerTechnician = new TonerTechnician("Toner Guy", technicianThreadGroup, laserPrinter);

    }

    public void initiateThreads()
    {
        student_1.start();
        student_2.start();
        student_3.start();
        student_4.start();

        paperTechnician.start();
        tonerTechnician.start();
    }
    
    public void checkThreadStatus()
    {
        Thread[] threadList; 
        int count;
        
        //student thread group join() 
        threadList = new Thread[studentThreadGroup.activeCount()];
        count = studentThreadGroup.enumerate(threadList);
        for (int i = 0; i < count; i++) 
        {
            try 
            {
                threadList[i].join();
            }
            
            catch (InterruptedException ex) 
            {
                Logger.getLogger(PrintingSystem.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        //technician thread group join()
        threadList = new Thread[technicianThreadGroup.activeCount()];
        count = technicianThreadGroup.enumerate(threadList);
        for (int i = 0; i < count; i++) 
        {
            try 
            {
                threadList[i].join();
            }
            
            catch (InterruptedException ex) 
            {
                Logger.getLogger(PrintingSystem.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        System.out.println("\nThread " + student_1.getName() + " is in thread state " + String.valueOf(student_1.getState()));
        System.out.println("\nThread " + student_2.getName() + " is in thread state " + String.valueOf(student_2.getState()));
        System.out.println("\nThread " + student_3.getName() + " is in thread state " + String.valueOf(student_3.getState()));
        System.out.println("\nThread " + student_4.getName() + " is in thread state " + String.valueOf(student_4.getState()));
        
        System.out.println("\nThread " + tonerTechnician.getName() + " is in thread state " + String.valueOf(tonerTechnician.getState()));
        System.out.println("\nThread " + paperTechnician.getName() + " is in thread state " + String.valueOf(paperTechnician.getState()));
        
        
        System.out.println
        (
            "\nFinal status of the printer \nPaper level: " + laserPrinter.getCurrentPaperQuantity() 
            + "\nToner level: " + laserPrinter.getCurrentTonerLevel() 
            + "\nDocument printed: " + laserPrinter.getNumOfPrintedDoc()
        );
    }
}
