package mx.moya.ropappi.ropappi.util;

import android.content.Context;
import android.content.SharedPreferences;

import mx.moya.ropappi.ropappi.model.User;


/**
 * Created by jesusmiguelflores on 28/04/16.
 */
public class SessionStateManager {
    private SharedPreferences sharedPreferences;

    public SessionStateManager(Context context) {
        this.sharedPreferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
    }

    public void saveSession(User user) {
        SharedPreferences.Editor editor = this.sharedPreferences.edit();
        editor.putString(Keys.key_nombre, user.getNombre());
        editor.putString(Keys.key_correo, user.getCorreo());
        editor.putString(Keys.key_foto, user.getFoto());
        editor.putInt(Keys.key_id, user.getId());


        editor.apply();
    }

    public User getCurrentUser() {

        String correo = sharedPreferences.getString(Keys.key_correo, "");
        String nombre = sharedPreferences.getString(Keys.key_nombre, "");
        String foto = sharedPreferences.getString(Keys.key_foto, "");
        int id = sharedPreferences.getInt(Keys.key_id, -1);


        if (correo.isEmpty()) {
            return null;
        } else {
            User user = new User();
            user.setCorreo(correo);
            user.setNombre(nombre);
            user.setFoto(foto);
            user.setId(id);
            return user;
        }
    }

    public void logOut() {
        SharedPreferences.Editor editor = this.sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
