package shared_printer;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Arnold Anthonypillai
 */
public class PrintingSystem implements ANSI_Colours
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
    
    private static final List<Thread> studentThreadList = new ArrayList();
     
    public PrintingSystem()
    {
        System.out.println
        (
            "******************************************************************************************************************************" 
            + "\n* " + ANSI_WHITE + "This is a Printing system that demonstrate the concurrency features in Java such as mutual exclusion, thread handling etc. * "
            + "\n* The system is highlighted with different colours to differentiate the thread activities as follows:                        * " + ANSI_RESET
            + "\n* " + ANSI_GREEN + "LaserPrinter - Thread Monitor class" + ANSI_RESET + "                                                                                        *"
            + "\n* " + ANSI_YELLOW + "Student threads - 4 instances of student threads" + ANSI_RESET + "                                                                           *"
            + "\n* " + ANSI_PURPLE + "Paper technician thread - 1 instance of this thread" + ANSI_RESET + "                                                                        *"
            + "\n* " + ANSI_CYAN + "Toner technician thread - 1 instance of this thread" + ANSI_RESET + "                                                                        *"
            + "\n******************************************************************************************************************************"
        );
        
        laserPrinter = new LaserPrinter("LaserPrinter", 5, 9);
        
        studentThreadGroup = new ThreadGroup("Student_Thread_Group");
        technicianThreadGroup = new ThreadGroup("Technician_Thread_Group");
        
        student_1 = new Student("Aron Good", studentThreadGroup, laserPrinter);
        student_2 = new Student("Jaime Button", studentThreadGroup, laserPrinter);
        student_3 = new Student("Elliot Elenor", studentThreadGroup, laserPrinter);
        student_4 = new Student("Brandon Barnes", studentThreadGroup, laserPrinter);

        paperTechnician = new PaperTechnician("paperTechnician", technicianThreadGroup, laserPrinter);
        tonerTechnician = new TonerTechnician("tonerTechnician", technicianThreadGroup, laserPrinter);
        
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
    
    public void checkStudentThreads()
    {
        studentThreadList.add(student_1);
        studentThreadList.add(student_2);
        studentThreadList.add(student_3);
        studentThreadList.add(student_4);
        
        studentThreadList.forEach((studentThread) -> {
            try 
            {
                studentThread.join();
            }
            catch (InterruptedException ex) 
            {
                Logger.getLogger(PrintingSystem.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        paperTechnician.setExit(true);
        tonerTechnician.setExit(true);
    }
}
