
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
public class HashPassword{

    public static String hashPassword(String password)
    {
        try
        {
        MessageDigest md=MessageDigest.getInstance("MD5");
        md.update(password.getBytes());
        byte[] b=md.digest();
        StringBuffer sb=new StringBuffer();
        for(byte b1: b)
        {
            sb.append(Integer.toHexString(b1 & 0xff).toString());

        }
        return sb.toString();
        }
        catch(NoSuchAlgorithmException e)
        {
            System.out.println("no such algorithm Exception");
            e.printStackTrace();

        }
        return "error";
        
    }

    public static void main(String[] args)
    {
        String password="password";
            System.out.println(hashPassword(password));
    }
}