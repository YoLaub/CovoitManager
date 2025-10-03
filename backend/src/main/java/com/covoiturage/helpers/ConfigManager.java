/**
 * La classe {@code ConfigManager} fournit des méthodes utilitaires pour
 * charger des fichiers de configuration au format JSON et les convertir
 * en objets Java de type {@link AppConfig}.
 * <p>
 * Cette classe utilise la bibliothèque <a href="https://github.com/FasterXML/jackson">Jackson</a>
 * pour la désérialisation des fichiers JSON.
 * </p>
 *
 * <p><b>Exemple d'utilisation :</b></p>
 * <pre>
 * try {
 *     AppConfig config = ConfigManager.loadConfig("conf/config.json");
 *     System.out.println("Serveur FTP : " + config.getServer());
 * } catch (IOException e) {
 *     System.err.println("Erreur de chargement de la configuration : " + e.getMessage());
 * }
 * </pre>
 *
 * @author [Votre Nom]
 * @version 1.0
 * @since 2025-09-24
 * @see AppConfig
 * @see com.fasterxml.jackson.databind.ObjectMapper
 */

package com.covoiturage.helpers;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;


/**
 * Charge un fichier de configuration JSON et le convertit en objet {@link AppConfig}.
 * <p>
 * Cette méthode utilise {@link ObjectMapper} pour désérialiser le contenu du fichier
 * JSON en un objet Java. Le fichier doit respecter la structure attendue par
 * la classe {@link AppConfig}.
 * </p>
 *
 * @param filePath Le chemin vers le fichier JSON de configuration.
 *                 <p>Exemple : {@code "conf/conf.json"}</p>
 * @return Une instance de {@link AppConfig} contenant les paramètres de configuration.
 * @throws IOException Si le fichier est introuvable, illisible ou mal formé.
 * @throws IllegalArgumentException Si le chemin du fichier est {@code null} ou vide.
 *
 * @see AppConfig#AppConfig()
 * @see ObjectMapper#readValue(File, Class)
 */
public class ConfigManager {
    public static AppConfig loadConfig(String filePath) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(new File(filePath), AppConfig.class);
    }
}
