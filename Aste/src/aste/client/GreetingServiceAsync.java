package aste.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.mycompany.shared.Utente;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface GreetingServiceAsync {
	void greetServer(String input, AsyncCallback<String> callback) throws IllegalArgumentException;

	void login(String input, AsyncCallback<Boolean> asyncCallback);

	void start(AsyncCallback<String> asyncCallback);

	void signIn(Utente utente, AsyncCallback<String> asyncCallback);
}
