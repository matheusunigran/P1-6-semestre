package br.unigran.p1.bancoDados;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

import br.unigran.p1.Entidades.abastecimento;

public class abastecimentoDB {

    private DBHelper db;
    private SQLiteDatabase conexao;

    public abastecimentoDB(DBHelper db) {
        this.db = db;
    }

    public void inserir(abastecimento abas) {
        conexao = db.getWritableDatabase();//abre o bd
        ContentValues valores = new ContentValues();
        valores.put("kmAtual", abas.getKmAtual());
        valores.put("qtdAbastecida", abas.getQtdAbastecida());
        valores.put("dia", abas.getDia());
        valores.put("valor", abas.getValor());
        conexao.insertOrThrow("Lista", null, valores);
        conexao.close();
    }

    public void atualizar(abastecimento abas) {
        conexao = db.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("kmAtual", abas.getKmAtual());
        valores.put("qtdAbastecida", abas.getQtdAbastecida());
        valores.put("dia", abas.getDia());
        valores.put("valor", abas.getValor());
        conexao.update("Lista", valores, "id=?", new String[]{abas.getId().toString()});
        conexao.close();
    }

    public void remover(int id) {
        conexao = db.getWritableDatabase();
        conexao.delete("Lista", "id=?",
                new String[]{id + ""});
    }

    public void lista(List dados) {
        dados.clear();
        conexao = db.getReadableDatabase();
        String names[] = {"id", "kmAtual", "qtdAbastecida", "dia", "valor"};
        Cursor query = conexao.query("Lista", names,
                null, null, null,
                null, "nome");
        while (query.moveToNext()) {
            abastecimento abas = new abastecimento();
            abas.setId(Integer.parseInt(
                    query.getString(0)));
            abas.setKmAtual(
                    query.getString(1));
            abas.setQtdAbastecida(
                    query.getString(2));
            abas.setDia(
                    query.getString(3));
            abas.setValor(
                    query.getString(4));
            dados.add(abas);
        }
        conexao.close();
    }
}
