package persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;


/**
     * Ejemplo <b>SQLite</b>. Ejemplo de uso de SQLite.
     *
     * DAO para la tabla de valoracion.
     * Se encarga de abrir y cerrar la conexion, asi como hacer las consultas relacionadas con la tabla valoracion
     *

     */
    public class FavoritesDataSource {
        /**
         * Referencia para manejar la base de datos. Este objeto lo obtenemos a partir de MyDBHelper
         * y nos proporciona metodos para hacer operaciones
         * CRUD (create, read, update and delete)
         */
        private SQLiteDatabase database;
        /**
         * Referencia al helper que se encarga de crear y actualizar la base de datos.
         */
        private MyDBHelper dbHelper;
        /**
         * Columnas de la tabla
         */
        private final String[] allColumns = { MyDBHelper.COLUMN_ID, MyDBHelper.COLUMN_PLAYA,MyDBHelper.COLUMN_POSITION};
        /**
         * Constructor.
         *
         * @param context
         */
        public FavoritesDataSource(Context context) {
            dbHelper = new MyDBHelper(context);
        }

        /**
         * Abre una conexion para escritura con la base de datos.
         * Esto lo hace a traves del helper con la llamada a getWritableDatabase. Si la base de
         * datos no esta creada, el helper se encargara de llamar a onCreate
         *
         * @throws SQLException
         */
        public void open() throws SQLException {
            database = dbHelper.getWritableDatabase();
        }

        /**
         * Cierra la conexion con la base de datos
         */
        public void close() {
            dbHelper.close();
        }

        /**
         * Método que añade la playa a favoritos
         * @param name
         * @return
         */
        public long addFavorite(String name,int position) {
            ContentValues values = new ContentValues();
            values.put(MyDBHelper.COLUMN_PLAYA,name);
            values.put(MyDBHelper.COLUMN_POSITION,position);

            // Insertamos la playa
            long insertId = database.insert(MyDBHelper.TABLE_FAVORITES, null, values);

            return insertId;
        }

        /**
         *Método que elimina la plya de favoritos
         * @return
         */
        public long removeFavorite(int position){

            // Borramos la playa
            long removeId = database.delete(MyDBHelper.TABLE_FAVORITES, MyDBHelper.COLUMN_POSITION+"="+position,null);

            return removeId;

        }


        /**
         * Obtiene todas las playas marcadas como favoritas
         *
         * @return Lista de objetos de tipo Valoration
         */
        public List<String> getAllFavorites() {
            // Lista que almacenara el resultado
            List<String> favoritesList = new ArrayList<String>();
            //hacemos una query porque queremos devolver un cursor
            Cursor cursor = database.query(MyDBHelper.TABLE_FAVORITES, allColumns,
                    null, null, null, null,null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                final String favorite = cursor.getString(1);

                favoritesList.add(favorite);
                cursor.moveToNext();
            }

            cursor.close();

            return favoritesList;
        }

    }

