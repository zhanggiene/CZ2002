public interface NotificationManager{
    public void sendNotification(String studentMetriculationNumber
                                        ,String Subject
                                        ,String Content);
    public void addStudent(String studentId, String email);
}
