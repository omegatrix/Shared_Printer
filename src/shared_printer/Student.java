package shared_printer;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Arnold Anthonypillai
 */
public class Student extends Thread implements ANSI_Colours
{
    private final String studentName;
    private final ThreadGroup threadGroup;
    private final String printerID;
    private final LaserPrinter laserPrinter;
    private final List<Document> docList;
    
    public Student(String studentName, ThreadGroup threadGroup, LaserPrinter laserPrinter) 
    {
        this.studentName = studentName;
        this.threadGroup = threadGroup;
        this.printerID = laserPrinter.getPrinterID();
        this.laserPrinter = laserPrinter;
        this.docList = new ArrayList<>();
        
        System.out.println(ANSI_YELLOW + "\nStudent " + studentName + " thread has started!!" + ANSI_RESET);
    }
    
    @Override
    public void run()
    {
        Document cv = new Document(studentName, "Curriculum Vitae", 2);
        Document cw_1 = new Document(studentName, "Coursework 1", 10);
        Document article = new Document(studentName, "Article", 17);
        Document projectPlan = new Document(studentName, "Project Plan", 27);
        Document yearlyReview = new Document(studentName, "Yearly Review", 34);
        
        docList.add(cv);
        docList.add(cw_1);
        docList.add(article);
        docList.add(projectPlan);
        docList.add(yearlyReview);
        
        List<Document> docListJobComplete = new ArrayList<>();
    
        while(true)
        {
            if(docList.equals(docListJobComplete))
            {
                System.out.println(ANSI_YELLOW + "\nStudent " + studentName + " thread has ended!!" + ANSI_RESET);
                break;
            }
            
            for(int i = 0; i < docList.size(); i++)
            {
                System.out.println
                (   
                    ANSI_YELLOW
                    + "\nStudent " + docList.get(i).getUserID() 
                    + " is requesting to print document " + docList.get(i).getDocumentName()
                    + " that has " + docList.get(i).getNumberOfPages() 
                    + " of pages"
                    + ANSI_RESET
                );
                
                int numOfPages = docList.get(i).getNumberOfPages();
                Document currentDoc = docList.get(i);
                
                while(true)
                {
                    synchronized(laserPrinter)
                    {
                        if(laserPrinter.hasEnoughPaper(numOfPages) && laserPrinter.hasEnoughToner(numOfPages))
                        {
                            laserPrinter.printDocument(currentDoc);
                            docListJobComplete.add(currentDoc);
                            break;
                        }
                    }

                    try 
                    {
                        System.out.println(ANSI_YELLOW + "\nNeeds re-filling!!" + ANSI_RESET);
                        Thread.sleep(5000);
                    } 
                    catch (InterruptedException ex) 
                    {
                        Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
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

    public LaserPrinter getLaserPrinter() 
    {
        return laserPrinter;
    }

    public List<Document> getDocList() 
    {
        return docList;
    }
}




