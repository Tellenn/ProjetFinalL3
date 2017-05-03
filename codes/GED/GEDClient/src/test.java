import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class test {
	public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException {
		String ip = "152.77.82.60";
		String home = "/home/p/perrink/Bureau/";
		String file = "rmi.jpg";
		String cible = "GED/";
		String client = "Kyllian";
		String nomfol = "RH";
		int idfol = 4;
		int iddoc = 5;
		int iduser = 1;
		FileServerInt server = (FileServerInt) Naming.lookup("rmi://" + ip + "/abc");
		System.out.println("Debut créer dossier");
		idfol = InteractionClient.createFolder(server, nomfol);// TODO

		System.out.println("Debut upload file");
		iddoc = InteractionClient.UploadFile(ip, client, home, file);

		System.out.println("Debut download file");
		InteractionClient.downloadFile(ip, client, home, file, cible);

		System.out.println("Debut info dossier");
		InteractionClient.infoFolder(ip, nomfol);

		System.out.println("Debut getAccessFolder");
		InteractionClient.getAccessFolder(ip, idfol);

		System.out.println("Debut getAccessDoc");
		InteractionClient.getAccessDoc(ip, iddoc);

		System.out.println("Debut getRacineFolder");
		InteractionClient.getRacineFolder(ip, idfol);

		System.out.println("Debut getFilsFolder");
		InteractionClient.getFilsFolder(ip, idfol);

		System.out.println("Debut Sharefile");
		InteractionClient.ShareFile(ip, iddoc, iduser);

		System.out.println("Debut Sharefile");
		InteractionClient.ShareFolder(ip, idfol, iduser);

		System.out.println("Debut deleteAcess doc");
		InteractionClient.deleteAccessDoc(ip, iddoc, iduser);

		System.out.println("Debut deleteAcess folder");
		InteractionClient.deleteAccessFolder(ip, idfol, iduser);

		System.out.println("Debut delete file");
		InteractionClient.DeleteFile(ip, 4, home + file);

		System.out.println("Debut delete folder");
		InteractionClient.DeleteFolder(ip, idfol, home);
	}
}
