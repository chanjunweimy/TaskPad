public class GetShortcutPath  
{  
  public GetShortcutPath(String[] args)  
  {  
    if(args.length > 0)  
    {  
      javax.swing.JOptionPane.showMessageDialog(null,"Full Path: "+args[0]); 
    }  
    else  
    {  
      try{Runtime.getRuntime().exec("wscript.exe GetShortcutPath.vbs");}  
      catch(Exception e){e.printStackTrace();}  
      System.out.println("HI!2");
    }  
    System.exit(0);  
  }  
  public static void main(String[] args) {new GetShortcutPath(args);}  
} 