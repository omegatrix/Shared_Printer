/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shared_printer;

/**
 *
 * @author User
 */
public class SharedPrintingSystem 
{
    
    public static void main(String[] args) 
    {
        PrintingSystem printingSystem = new PrintingSystem();
        
        printingSystem.initiateThreads();
        printingSystem.checkStudentThreads();
    }
    
}
