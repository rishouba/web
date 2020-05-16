package test;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import bd.LoadDataBase;
import services.AuthentificationServices;
import services.CommentaireService;

public class TestCommentaire {
	public static void main(String[] args) {
		LoadDataBase.loadSQLDataBase();
		String m_id = LoadDataBase.loadMongoDataBase();
		
		//String m_id = "5e5947e8f0c0c91310fca535";
		try {
			String key = AuthentificationServices.login("Pilan12", "251efzfe").getString("key");
			System.out.println("Le client est connecté avec la clé de session : " + key);
			String c_id = CommentaireService.createCommentaire(key, m_id, "Nouveau commentaire").getString("c_id");
			System.out.println("Le commentaire " + c_id + " a été crée");
			String c_id2 = CommentaireService.createCommentaire(key, m_id, "Nouveau commentaire numéro 2").getString("c_id");
			System.out.println("Le commentaire " + c_id2 + " a été crée");
			ArrayList<String> commentsId = (ArrayList<String>) CommentaireService.getCommentaireList(key, m_id).get("commentsId");
			System.out.println("Le client clique sur le message et voit les commentaires suivants : ");
			for(String id : commentsId) {
				System.out.println(id);
			}
			JSONObject jsonGet =  CommentaireService.getCommentaire(key, m_id, c_id);
			System.out.println("Client : Le commentaire " + c_id + " a bien été reçu. Voici son contenu :");
			System.out.println(jsonGet);
			CommentaireService.modifyCommentaire(key, m_id, c_id, "Mon nouveau commentaire");
			System.out.println("Le commentaire " + c_id + " a été modifié");
			JSONObject jsonmodify =  CommentaireService.getCommentaire(key, m_id, c_id);
			System.out.println("Client : Le commentaire " + c_id + " a bien été reçu. Voici son nouveau contenu :");
			System.out.println(jsonmodify);
			CommentaireService.deleteCommentaire(key, m_id, c_id);
			System.out.println("Le commentaire " + c_id + " a été supprimée");
		} catch (JSONException e) {
			
			e.printStackTrace();
		}
	}

}