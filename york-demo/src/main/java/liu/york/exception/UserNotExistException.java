package liu.york.exception;

public class UserNotExistException extends RuntimeException{

    private  String id;
    public UserNotExistException(String id){
        super("user not exist");
        this.id = id;
    }

}
