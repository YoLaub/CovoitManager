package com.covoiturage.service;

import com.covoiturage.helpers.AppConfig;
import com.covoiturage.helpers.ConfigManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import sendinblue.ApiClient;
import sendinblue.Configuration;
import sendinblue.auth.ApiKeyAuth;
import sibApi.TransactionalEmailsApi;
import sibModel.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Utilitaire pour l'envoi d'emails transactionnels via l'API Brevo (ex-Sendinblue).
 * <p>
 * Cette classe permet d'envoyer des emails avec pièces jointes en utilisant
 * les services de Brevo. Elle charge la configuration depuis un fichier JSON
 * et gère l'envoi d'emails avec un template HTML personnalisé.
 * </p>
 * <p>
 * <strong>Configuration requise:</strong>
 * Un fichier conf/conf.json doit exister avec les propriétés suivantes:
 * <ul>
 *   <li>apiKey: Clé API Brevo</li>
 *   <li>sender: Adresse email de l'expéditeur</li>
 *   <li>to: Adresse email du destinataire</li>
 *   <li>template: Contenu HTML du template</li>
 * </ul>
 * </p>
 * <p>
 * <strong>Prérequis:</strong>
 * Le fichier "state_of_the_day.pdf" doit être présent dans le répertoire courant
 * pour être joint à l'email.
 * </p>
 */
public class SendMail {

    public static ResponseEntity<?> sendEmail(String recever, String name) throws IOException {
        AppConfig configMail = ConfigManager.loadConfig("conf.json");
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        ApiKeyAuth apiKey = (ApiKeyAuth) defaultClient.getAuthentication("api-key");
        apiKey.setApiKey(configMail.getApiKey());


        try {
            TransactionalEmailsApi api = new TransactionalEmailsApi();

            // Expéditeur
            SendSmtpEmailSender sender = new SendSmtpEmailSender();
            sender.setEmail(configMail.getSender());
            sender.setName("John Doe");

            // Destinataire
            List<SendSmtpEmailTo> toList = new ArrayList<>();
            SendSmtpEmailTo to = new SendSmtpEmailTo();
            to.setEmail(recever);
            to.setName(name);
            toList.add(to);

            // Contenu de l'email
            SendSmtpEmail sendSmtpEmail = new SendSmtpEmail();
            sendSmtpEmail.setSender(sender);
            sendSmtpEmail.setTo(toList);
            sendSmtpEmail.setSubject("Etat Bibliothèque");
            sendSmtpEmail.setHtmlContent("");

            // Envoi
            CreateSmtpEmail response = api.sendTransacEmail(sendSmtpEmail);
            return ResponseEntity.ok(response); // renvoie toute la liste
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

    }

}

