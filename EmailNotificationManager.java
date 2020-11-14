import java.util.*;
import java.io.*;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;



/**
     provide functionality for sending emails. it is also the email database 
     * @author zhang zhuyan
     */
public class EmailNotificationManager {

    private Hashtable<String, String> EmailDataBase = new Hashtable<String, String>();
    private String fileName = "email.txt";
    private File file;
    private Properties props;
    private Session session;
    private FileWriter fw;
    private PrintWriter pw;

    // Email of sending email
    private final String username = "ntucz2002a1@gmail.com"; // to be added #For the email used make sure "Allow less
                                                             // secure apps: ON" setting is on
    // Password of sending password: Make sure no two factor authentication for this
    // email
    private final String password = "NTU2002A1"; // to be added

    public EmailNotificationManager() {
        try {
            this.file = new File(this.fileName);
            loadFile();
            this.fw = new FileWriter(this.file, true);
            this.pw=new PrintWriter(this.fw);
        } catch (IOException e) {
            System.out.println("An error occured.");
            e.printStackTrace();
        }
        setup();
    }
    private void setup()
    {
        props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		  });

    }

    private void loadFile()
    {
        try{
            Scanner rd=new Scanner(this.file);
            while(rd.hasNextLine())
            {
                String data=rd.nextLine();
                String[] splittedData=data.split(" ",2);
                EmailDataBase.put(splittedData[0],splittedData[1]);

            }
        rd.close();
        
    }
        catch(FileNotFoundException e)
        {
            System.out.println("An error occured.");
            e.printStackTrace();
        }

    }

    
    /**  send email with subject and content to student using metriculationNUmber to identidy the student. 
     * @param studentMetriculationNumber
     * @param Subject
     * @param Content
     */
    public void sendEmail(String studentMetriculationNumber,String Subject,String Content)
    {
        if (EmailDataBase.containsKey(studentMetriculationNumber))
        {
            String email=EmailDataBase.get(studentMetriculationNumber);
            try {

                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(email)); //Email to send to
                message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(email)); // to be added an email addr //Email to send to
                message.setSubject(Subject);
                message.setText(Content);
    
                Transport.send(message);
    
                System.out.println("email notification send successfully");
    
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }

        }
        else
        {
            System.out.println("error, email not found in the dataBase");
        }
    }

    
    /**   assume the studentID does not exist in the database to avoid duplicate
     *   add studentId and its associated email to the database
     * @param studentId
     * @param email
     */
    public void add(String studentId, String email)
    
    {
        assert studentExist(studentId);
        EmailDataBase.put(studentId, email);
        this.pw.println(studentId+" "+ email);
        System.out.println("Student email added successfuly");
        System.out.println(studentId+" "+email);
        this.pw.close();
    }

    
    /** check is the student has email address in the database
     * @param metriculationNumber
     * @return boolean
     */
    public boolean studentExist(String metriculationNumber)
    {
        return EmailDataBase.contains(metriculationNumber);

    }

    
    /** 
     * @param args
     */
    public static void main(String[] args) {
        EmailNotificationManager manager=new EmailNotificationManager();
        manager.sendEmail("U1920187L", "test1", "hi test123");
    }
}
