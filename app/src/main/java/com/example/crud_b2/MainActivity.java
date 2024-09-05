package com.example.crud_b2;

import android.os.Bundle;
import android.database.Cursor;
import android.view.View; // Corregido: 'android.View.View' a 'android.view.View'
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.app.AlertDialog; // Corregido: 'android.app..AlertDialog' a 'android.app.AlertDialog'
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper myDb;
    EditText editNAME, editSurname, ediMarks,editId;
    button btnADDData, btnViewAll,btnUpdate,btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        myDb = new DatabaseHelper(this);

        editName = findViewById(R.id.editText_name);
        editSurname = findViewById(R.id.editText_surname);
        editMarks = findViewById(R.id.editText_marks);
        editId = findViewById(R.id.editText_id);
        btnAddData = findViewById(R.id.button_add);
        btnViewAll = findViewById(R.id.button_view);
        btnUpdate = findViewById(R.id.button_update);
        btnDelete = findViewById(R.id.button_delete);

        addData();
        viewAll();
        updateData();
        deleteData();
    }

    public void addData() {
        btnAddData.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isInserted = myDb.insertData(editName.getText().toString(),
                                editSurname.getText().toString(),
                                editMarks.getText().toString());
                        if (isInserted)
                            Toast.makeText(MainActivity.this, "Datos insertados", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this, "Error al insertar datos", Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    public void viewAll() {
        btnViewAll.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Cursor res = myDb.getAllData();
                        if (res.getCount() == 0) {
                            showMessage("Error", "No se encontraron datos");
                            return;
                        }

                        StringBuffer buffer = new StringBuffer();
                        while (res.moveToNext()) {
                            buffer.append("ID :").append(res.getString(0)).append("\n");
                            buffer.append("Nombre :").append(res.getString(1)).append("\n");
                            buffer.append("Apellido :").append(res.getString(2)).append("\n");
                            buffer.append("Calificación :").append(res.getString(3)).append("\n\n");
                        }

                        // Mostrar todos los datos
                        showMessage("Datos", buffer.toString());
                    }
                }
        );
    }

    public void updateData() {
        btnUpdate.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isUpdate = myDb.updateData(editId.getText().toString(),
                                editName.getText().toString(),
                                editSurname.getText().toString(),
                                editMarks.getText().toString());
                        if (isUpdate)
                            Toast.makeText(MainActivity.this, "Datos actualizados", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this, "Error al actualizar datos", Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    public void deleteData() {
        btnDelete.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Integer deletedRows = myDb.deleteData(editId.getText().toString());
                        if (deletedRows > 0)
                            Toast.makeText(MainActivity.this, "Datos eliminados", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this, "Error al eliminar datos", Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    public void showMessage(String title, String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }
}

