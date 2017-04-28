import java.rmi.Naming;
import java.util.Scanner;
 
 
public class StartFileClient {
 
	public static void main(String[] args) {
		try{
			FileClient c=new FileClient("kyllian");			
              //AdresseIP
			FileServerInt server=(FileServerInt)Naming.lookup("rmi://147.171.173.3/abc");
			server.login(c);
			System.out.println("Listening.....");			
			Scanner s=new Scanner(System.in);			
			while(s.hasNext()){
				String line=s.nextLine();
			}
			System.out.println("Fin du client");
		}catch(Exception e){
			e.printStackTrace();
		}
	}	
 
}