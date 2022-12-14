package br.unigran.p1;

import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.app.AlertDialog;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.unigran.p1.bancoDados.DBHelper;
import br.unigran.p1.bancoDados.abastecimentoDB;
import br.unigran.p1.Entidades.abastecimento;

public class MainActivity extends AppCompatActivity {

    EditText kmAtual;
    EditText qtdAbastecida;
    EditText dia;
    EditText valor;
    TextView media;
    ListView listagem;
    List<abastecimento> dados;
    DBHelper db;
    abastecimentoDB abasDB;
    Integer atualiza;
    Integer confirma = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //banco de dados
        db = new DBHelper(this);

        //mapeia campos da tela
        kmAtual = findViewById(R.id.quilometragemAtualID);
        qtdAbastecida = findViewById(R.id.qtdaAbastecidaID);
        dia = findViewById(R.id.diaAbastecimentoID);
        valor = findViewById(R.id.valorAbastecidoID);
        media = findViewById(R.id.mediaID);
        listagem = findViewById(R.id.listID);

        //aloca lista
        dados = new ArrayList();

        //vincula adapter
        ArrayAdapter adapter = new ArrayAdapter(this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, dados);
        listagem.setAdapter(adapter);
        abasDB = new abastecimentoDB(db);
        abasDB.lista(dados);//lista incial

        //ações de remover e editar
        acoes();
    }

    private void acoes() {
        confirma = null;
        //abre as opções que o usuário pode fazer com os dados
        listagem.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapterView,
                                                   View view, int i, long l) {
                        AlertDialog.Builder mensagem = new AlertDialog.Builder(view.getContext());
                        mensagem.setTitle("Opções");
                        mensagem.setMessage("Escolha a opção que deseja realizar");
                        //opção de remoção
                        mensagem.setPositiveButton("Remover", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                new AlertDialog.Builder(view.getContext())
                                        .setMessage("Deseja realmente remover")
                                        .setPositiveButton("Confirmar",
                                                new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface,
                                                                        int k) {
                                                        abasDB.remover(dados.get(i).getId());
                                                        abasDB.lista(dados);
                                                        ((ArrayAdapter) listagem.getAdapter()
                                                        ).notifyDataSetChanged();
                                                        String msg1 = "Dado removido com sucesso";
                                                        Toast.makeText(getApplicationContext(), msg1, Toast.LENGTH_SHORT).show();
                                                    }
                                                })
                                        .setNegativeButton("cancelar", null)
                                        .create().show();
                            }
                        });
                        //opção de editar
                        mensagem.setNegativeButton("Editar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                atualiza = dados.get(i).getId();
                                kmAtual.setText(dados.get(i).getKmAtual());
                                qtdAbastecida.setText(dados.get(i).getQtdAbastecida());
                                dia.setText(dados.get(i).getDia());
                                valor.setText(dados.get(i).getValor());

                                abasDB.atualizar(dados.get(i));
                                abasDB.lista(dados);

                                confirma = 1;

                            }
                        });
                        //cancela a escolha de opções
                        mensagem.setNeutralButton("Cancelar", null);
                        mensagem.show();
                        return false;
                    }
                });
    }
    //verifica se usuário entrou com dados, senão ele mostra uma mensagem
    public boolean verificar() {
        String s1 = kmAtual.getText().toString();
        String s2 = qtdAbastecida.getText().toString();
        String s3 = dia.getText().toString();
        String s4 = valor.getText().toString();
        if ((s1.equals(null) || s2.equals(null) || s3.equals(null) ||  s4.equals(null))
                || (s1.equals("") || s2.equals("") || s3.equals("")) || s4.equals("")) {
            Toast.makeText(this, "Preencha os campos", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }
    //limpa os dados dos campos de entrada
    private void limpar() {
        kmAtual.setText("");
        qtdAbastecida.setText("");
        valor.setText("");
        dia.setText("");
    }
    //salva os dados
    public void salvar(View view) {
        if (verificar()) { //se os dados estiverem preenchidos
            abastecimento abas = new abastecimento();
            if (atualiza != null) { //se a opção de atualizar estiver acionada
                abas.setId(atualiza);

                abasDB.lista(dados);
                Toast.makeText(this, "Salvo com sucesso", Toast.LENGTH_SHORT).show();
            }
            abas.setKmAtual(kmAtual.getText().toString());
            abas.setQtdAbastecida(qtdAbastecida.getText().toString());
            abas.setValor(valor.getText().toString());
            abas.setDia(dia.getText().toString());

            if (atualiza != null)
                abasDB.atualizar(abas);
            else {
                abasDB.inserir(abas);
                abasDB.lista(dados);
                Toast.makeText(this, "Salvo com sucesso", Toast.LENGTH_SHORT).show();
            }
            abasDB.lista(dados);
            listagem.invalidateViews();
            media.setText("calculo...");
            limpar();
            atualiza = null;
            confirma = null;
        }
    }
    //cancela a edição ou sai do app
    @Override
    public void onBackPressed() {
        if (confirma != null) {
            limpar();
            String msgCancelar = "Edição cancelada";
            Toast.makeText(getApplicationContext(), msgCancelar, Toast.LENGTH_SHORT).show();
            confirma = null;
        } else {
            super.onBackPressed();
            limpar();
            String msgSair = "Saindo...";
            Toast.makeText(getApplicationContext(), msgSair, Toast.LENGTH_SHORT).show();

        }
    }
    //calcula a média de consumo
    //private void calcularMedia(){}

}