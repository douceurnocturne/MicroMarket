package servlet;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class SessionListener implements HttpSessionListener {


  @Override
  public void sessionCreated(HttpSessionEvent evt) {
	
	System.out.println("sessionCreated - add one session into counter");
  }

  @Override
  public void sessionDestroyed(HttpSessionEvent evt) {
	
	System.out.println("sessionDestroyed - deduct one session from counter");
  }
}