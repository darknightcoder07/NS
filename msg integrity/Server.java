import javax.net.ssl.*;
import java.io.*;
import java.security.*;
public class Server {
public static void main(String[] args) {
try {
// Load the keystore
char[] keystorePassword = "password".toCharArray();
char[] keyPassword = "password".toCharArray();
KeyStore keyStore = KeyStore.getInstance("JKS");
try (FileInputStream fis = new FileInputStream("samlKeystore.jks")) {
keyStore.load(fis, keystorePassword);
}
// Set up the key manager factory
KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
kmf.init(keyStore, keyPassword);
// Set up the SSL context
SSLContext sslContext = SSLContext.getInstance("TLS");
sslContext.init(kmf.getKeyManagers(), null, null);
// Create the server socket factory
SSLServerSocketFactory ssf = sslContext.getServerSocketFactory();
SSLServerSocket serverSocket = (SSLServerSocket) ssf.createServerSocket(9999);
System.out.println("Server started. Waiting for client connection...");
// Accept client connections
SSLSocket socket = (SSLSocket) serverSocket.accept();
// Set up input and output streams
BufferedReader in = new BufferedReader(new
InputStreamReader(socket.getInputStream()));
PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
// Read message from client
String message = in.readLine();
System.out.println("Received message from client: " + message);
// Send response back to client
out.println("Message received by server");
// Close streams and socket
out.close();
in.close();
socket.close();
serverSocket.close();
} catch (Exception e) {
e.printStackTrace();
}
}
}