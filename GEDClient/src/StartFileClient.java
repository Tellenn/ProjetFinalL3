import java.rmi.Naming;
import java.util.Scanner;
 
 
public class StartFileClient {
 
	public static void main(String[] args) {
		try{
			FileClient c=new FileClient("imed");			
              //AdresseIP
			FileServerInt server=(FileServerInt)Naming.lookup("rmi://152.77.82.229/abc");
			server.login(c);
			System.out.println("Listening.....");			
			Scanner s=new Scanner(System.in);			
			while(true){
				String line=s.nextLine();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}	
 
}