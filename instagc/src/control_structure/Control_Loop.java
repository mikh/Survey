package control_structure;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import web.Client;
import logging.Log;
import control_structure.DEFINE;

public class Control_Loop {
	public static void main(String[] args){
		System.out.println("Starting Instagc survey engine.");
		System.out.println("Starting logger.");
		Log ll = new Log(DEFINE.LOGGING_LEVEL, DEFINE.LOG_FILE_PATH);
		ll.write(2, "\r\nStarting INSTAGC Survey Engine v" + DEFINE.VERSION + "\r\n");
	
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		ll.write(2, "Starting at " + dateFormat.format(new Date()) + "\r\n");
		long time_start = System.currentTimeMillis(), time_elapsed;
		
		time_elapsed = System.currentTimeMillis();
		ll.write(2, "Starting Client.\r\n");
		Client cc = new Client(DEFINE.BASE_URL);
		ll.write(2, "Client ready. Took " + (System.currentTimeMillis() - time_elapsed) + "ms.\r\n");

		time_elapsed = System.currentTimeMillis();
		ll.write(2, "Logging in.\r\n");
		login(cc);
		ll.write(2, "Login Complete. Took " + (System.currentTimeMillis() - time_elapsed) + "ms.\r\n");

		
		ll.write(2, "All operations complete at " + dateFormat.format(new Date()) + ". Took " + (System.currentTimeMillis() - time_start) + "ms.\r\n");
		ll.write(2, "Performing cleanup.\r\n\r\n");
		time_elapsed = System.currentTimeMillis();

		ll.close();
		
		System.out.println("Cleanup complete. Took " + (System.currentTimeMillis() - time_elapsed) + "ms.");
		System.out.println("All operations complete.");
	}

	private static void login(Client cc){
		try{
			cc.getPage(DEFINE.LOGIN_PAGE);
			while(cc.getPageTitle().equals(DEFINE.LOGIN_PAGE_TITLE)){
				cc.sendKeys(DEFINE.USERNAME_FIELD_ID, DEFINE.USERNAME, false);
				cc.sendKeys(DEFINE.PASSWORD_FIELD_ID, DEFINE.PASSWORD, false);
				cc.submit(DEFINE.PASSWORD_FIELD_ID, false);
			}
		} catch(Exception e){
			System.out.println("Error. Login Failed. Exception thrown");
			e.printStackTrace();
			System.exit(-1);
		}
	}
}


