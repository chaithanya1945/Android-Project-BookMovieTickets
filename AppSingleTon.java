package movie.com.bookmovietickets;

public class AppSingleTon {
	private static AppSingleTon appSingleTon = new AppSingleTon();
	private CustomApplication application;

	private  AppSingleTon(){
		
	}
	
	public static final AppSingleTon getInstance(){
		if(appSingleTon == null){
			appSingleTon = new AppSingleTon();
		}
		return appSingleTon;
		
	}

	public CustomApplication getApplication() {
		return application;
	}

	public void setApplication(CustomApplication application) {
		this.application = application;
	}
	
}
