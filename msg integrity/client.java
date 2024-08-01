import javax.net.ssl.*;
import java.io.*;
import java.security.*;
public class Client {
public static void main(String[] args) throws Exception {
// Load the truststore
char[] truststorePassword = "password".toCharArray();
KeyStore trustStore = KeyStore.getInstance("JKS");
FileInputStream fis = new FileInputStream("samlKeystore.jks");
trustStore.load(fis, truststorePassword);
// Set up the trust manager factory
TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
tmf.init(trustStore);
// Set up the SSL context
SSLContext sslContext = SSLContext.getInstance("TLS");
sslContext.init(null, tmf.getTrustManagers(), null);
// Create the socket factory
SSLSocketFactory sf = sslContext.getSocketFactory();
SSLSocket socket = (SSLSocket) sf.createSocket("localhost", 9999);
// Set up input and output streams
PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
BufferedReader in = new BufferedReader(new
InputStreamReader(socket.getInputStream()));
// Send message to server
out.println("Hello from client");
// Read response from server
String response = in.readLine();
System.out.println("Response from server: " + response);
// Close streams and socket
out.close();
in.close();
socket.close();
}
}