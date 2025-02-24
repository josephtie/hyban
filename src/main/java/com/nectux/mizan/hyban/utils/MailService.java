//package com.nectux.mizan.hyban.utils;
//
//import java.io.IOException;
//import java.util.Date;
//import java.util.Properties;
//
//import javax.mail.Message;
//import javax.mail.MessagingException;
//import javax.mail.NoSuchProviderException;
//import javax.mail.PasswordAuthentication;
//import javax.mail.Session;
//import javax.mail.Transport;
//import javax.mail.internet.AddressException;
//import javax.mail.internet.InternetAddress;
//import javax.mail.internet.MimeBodyPart;
//import javax.mail.internet.MimeMessage;
//import javax.mail.internet.MimeMultipart;
//
//public class MailService {
//
//	public void sendMail(String mailReceiver, String mailSubject, String mailMessage) throws Exception {
//		final String username = new String("rhpaie@iconseils.net");
//		final String password = new String("Iconseils@rhpaie");
//
//		String smtpHost = "mail.supremecluster.com";
//	    String from = "rhpaie@iconseils.net";
//
//	    Properties props = new Properties();
//
//		props.put("mail.smtp.starttls.enable", "true");
//		props.put("mail.smtp.host", new String("mail.supremecluster.com"));
//		props.put("mail.smtp.port", new String("2525"));
//		props.put("mail.debug", "true");
//
//	    Session session = Session.getDefaultInstance(props);
//	    session.setDebug(true);
//
//	    MimeMessage message = new MimeMessage(session);
//	    message.setFrom(new InternetAddress(from));
//	    message.addRecipient(Message.RecipientType.TO, new InternetAddress(mailReceiver));
//	    message.setSubject(mailSubject);
//	    message.setText(mailMessage);
//
//	    Transport tr = session.getTransport("smtp");
//	    tr.connect(smtpHost, username, password);
//	    message.saveChanges();
//
//	    // tr.send(message);
//	    /** Genere l'erreur. Avec l authentification, oblige d utiliser sendMessage meme pour une seule adresse... */
//
//	    tr.sendMessage(message,message.getAllRecipients());
//	    tr.close();
//
//
//	}
//
//
//	public void SendMultipartMessage(String mailReceiver , String mailMessage , String fichier) {
//	try {
//		final String username = new String("joel.adjidan@iconseils.net");
//		final String password = new String("iconseils123");
//
//		Properties props = new Properties();
//		props.put("mail.smtp.auth", "true");
//		props.put("mail.smtp.starttls.enable", "false");
//		props.put("mail.smtp.host", new String("mail.supremecluster.com"));
//		props.put("mail.smtp.port", new String("25"));
//		props.put("mail.debug", "true");
//
//		Session session = Session.getInstance(props,
//				new javax.mail.Authenticator() {
//					protected PasswordAuthentication getPasswordAuthentication() {
//						return new PasswordAuthentication(username, password);
//					}
//				});
//
//		Message message = new MimeMessage(session);
//		message.setFrom(new InternetAddress(username));
//		message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mailReceiver));
//		//message.setSubject(mailSubject);
//		message.setText(mailMessage);
//
//	    // Partie 1: Le texte
//	    MimeBodyPart messageText = new MimeBodyPart();
//	    messageText.setText("JavaMail vous dit bonjour!.");
//
//	    // Partie 2: Le fichier joint
//	    MimeBodyPart messageFichier = new MimeBodyPart();
//	   // String file = "/home/sylvain/HelloJavaMail.png";
//	    messageFichier.attachFile(fichier);
//
//	    // On regroupe les deux dans le message
//	    MimeMultipart mp = new MimeMultipart();
//	    mp.addBodyPart(messageText);
//	    mp.addBodyPart(messageFichier);
//	    message.setContent(mp);
//
//	    Transport.send(message);
//	}
//	catch(IOException e) {
//	    System.err.println("Impossible de lire le fichier joint");
//	    System.err.println(e);
//	}
//	catch(NoSuchProviderException e) {
//	    System.err.println("Pas de transport disponible pour ce protocole");
//	    System.err.println(e);
//	}
//	catch(AddressException e) {
//	    System.err.println("Adresse invalide");
//	    System.err.println(e);
//	}
//	catch(MessagingException e) {
//	    System.err.println("Erreur dans le message");
//	    System.err.println(e);
//	}
//    }
//	private final static String MAILER_VERSION = "Java";
//	public static boolean envoyerMailSMTP(String serveur, boolean debug) {
//	boolean result = false;
//	try {
//	Properties prop = System.getProperties();
//	prop.put("mail.supremecluster.com", serveur);
//
//
//	Session session = Session.getDefaultInstance(prop,null);
//	Message message = new MimeMessage(session);
//	message.setFrom(new InternetAddress("joseph.tiebiyouan@iconseils.net"));
//	InternetAddress[] internetAddresses = new InternetAddress[1];
//	internetAddresses[0] = new InternetAddress("Joel.adjidan@iconseils.net");
//	message.setRecipients(Message.RecipientType.TO,internetAddresses);
//	message.setSubject("Test");
//	message.setText("test mail");
//	message.setHeader("X-Mailer", MAILER_VERSION);
//	message.setSentDate(new Date());
//	session.setDebug(debug);
//    Transport.send(message);
//	result = true;
//	} catch (AddressException e) {
//	e.printStackTrace();
//	} catch (MessagingException e) {
//	e.printStackTrace();
//	}
//	return result;
//	}
//
//}
