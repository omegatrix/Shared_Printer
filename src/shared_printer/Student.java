package shared_printer;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Arnold Anthonypillai
 */
public class Student extends Thread implements Utility
{
    private final String studentName;
    private final ThreadGroup threadGroup;
    private final String printerID;
    private final LaserPrinter laserPrinter;
    private final List<Document> docList;
    
    public Student(String studentName, ThreadGroup threadGroup, LaserPrinter laserPrinter) 
    {
        super(threadGroup, studentName);
        
        this.studentName = studentName;
        this.threadGroup = threadGroup;
        this.printerID = laserPrinter.getPrinterID();
        this.laserPrinter = laserPrinter;
        this.docList = new ArrayList<>();

        System.out.println(ANSI_YELLOW + "\nStudent " + studentName + " is in thread state " + String.valueOf(this.getState()) + ANSI_RESET);
    }
    
    @Override
    public void run()
    {
        System.out.println(ANSI_YELLOW + "\nStudent " + studentName + " is in thread state " + String.valueOf(this.getState()) + ANSI_RESET);
        
        Document cv = new Document(studentName, "Curriculum Vitae", 2);
        Document cw_1 = new Document(studentName, "Coursework 1", 7);
        Document article = new Document(studentName, "Article", 13);
        Document projectPlan = new Document(studentName, "Project Plan", 16);
        Document yearlyReview = new Document(studentName, "Yearly Review", 25);
        
        docList.add(cv);
        docList.add(cw_1);
        docList.add(article);
        docList.add(projectPlan);
        docList.add(yearlyReview);
                
        int randomNum;
            
        for(int i = 0; i < docList.size(); i++)
        {
            Document currentDoc = docList.get(i);

            System.out.println
            (   
                ANSI_YELLOW
                + "\nStudent " + currentDoc.getUserID() 
                + " is requesting to print document " + currentDoc.getDocumentName()
                + " that has " + Integer.toString(currentDoc.getNumberOfPages()) 
                + " of pages"
                + ANSI_RESET
            );

            laserPrinter.printDocument(currentDoc);

            try 
            {
                randomNum = (int)(Math.random() * 10 + 1); //random number from 1 - 10
                
                System.out.println
                (
                    ANSI_YELLOW + "\nStudent " + currentDoc.getUserID() 
                    + " is sleeping " + Integer.toString(randomNum) + " seconds before making another print request!!" 
                    + ANSI_RESET
                );
                
                Thread.sleep(randomNum * ONE_SECOND); //sleep for 
            } 
            catch (InterruptedException ex) 
            {
                Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
            
        System.out.println(ANSI_YELLOW + "\nStudent " + studentName + " thread is terminating!!" + ANSI_RESET);
    }

    public String getStudentName() 
    {
        return studentName;
    }
    
    public String getThreadGroupName() 
    {
        return threadGroup.getName();
    }

    public String getPrinterID() 
    {
        return printerID;
    }

    public List<Document> getDocList() 
    {
        return docList;
    }
}




