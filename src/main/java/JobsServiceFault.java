public class JobsServiceFault {
    private static final String DEFAULT_MESSAGE = "all Arg cannot be null or empty";
    protected String message;
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public static JobsServiceFault defaultInstance() {
        JobsServiceFault fault = new JobsServiceFault();
        fault.message = DEFAULT_MESSAGE;
        return fault;
    }
}
