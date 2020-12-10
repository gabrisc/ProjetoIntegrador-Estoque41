package com.example.estoque411;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.example.estoque411.Config.configFireBase;
import com.example.estoque411.Model.Produtovendido;
import com.example.estoque411.Model.Venda;
import com.example.estoque411.helper.Base64Custom;
import com.example.estoque411.helper.DataCustom;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class CadastrarVendaActivity extends AppCompatActivity implements AdapterProdutosSelecionados.OnProdutoSelecionadoListerner  {
	private EditText campo;
	private double desconto;
	private double valorCalculado;
	private EditText nomeCliente,dataDaCompra;
	private String	 mesAnoDaCompra;
	private DatabaseReference mDatabase;
	private Venda venda= new Venda();
	private TextView valorTotal;
	private RadioButton dinheiro,boleto,debito,credito,outro;
	private RadioGroup radioGroup;
	private String escolha;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//Para deixar em tela cheia

		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_cadastrar_venda);

		mDatabase = FirebaseDatabase.getInstance().getReference();
		nomeCliente = findViewById(R.id.nomeClienteText);
		dataDaCompra = findViewById(R.id.diaMesAnoText);
		valorTotal = findViewById(R.id.valorTotalText);

		radioGroup=findViewById(R.id.radioGroupFormaDePagamento);
		dinheiro=findViewById(R.id.radioButtonMoney);
		boleto=findViewById(R.id.radioButtonBoleto);
		credito=findViewById(R.id.radioButtonCredito);
		debito=findViewById(R.id.radioButtonDebito);


		DataCustom data=new DataCustom();
		dataDaCompra.setText(DataCustom.dataAtual());
		mesAnoDaCompra= DataCustom.mesAnoAtual();
		CarregarListaDeSelecionados();
		//calcularValorTotal();
		valorTotal.setText(ListagemDeProdutosParaCompras.Vtotal.toString());
		valorCalculado=Double.parseDouble(valorTotal.getText().toString());
		escolha=null;


	}
	//############################################################################# Seleção do pagamento #################################################################################################
	public boolean ValidarFormaDePagamento(){

				if (boleto.isChecked()){
					escolha ="Boleto";
					return true;
				}else if (credito.isChecked()){
					escolha ="Crédito";
					return true;
				}else if (debito.isChecked()){
					escolha ="Débito";
					return true;
				}if (dinheiro.isChecked()){
					escolha ="Dinheiro";
					return true;
				}

		return false;
	}
//############################################################################# Calculando  Total #################################################################################################

	public void calcularValorTotal(Double valorDeb){

		valorTotal.setText(""+(ListagemDeProdutosParaCompras.Vtotal-valorDeb));
		valorCalculado=Double.parseDouble(valorTotal.getText().toString());
	}

	public  void formaDePagamento(View view){

		if (!ValidarFormaDePagamento()){
			Toast toast = Toast.makeText(getApplicationContext(), "Preencha a forma de pagamento", Toast.LENGTH_SHORT);
			toast.show();
		}else{
			validando();
		}
	}

//############################################################################# Validando campos #################################################################################################

	public void validando() {

		if (nomeCliente.getText().toString().equals("")) {
			Toast toast = Toast.makeText(getApplicationContext(), "Preencha o campo cliente", Toast.LENGTH_SHORT);
			toast.show();
		} else if (dataDaCompra.getText().toString().equals("")) {
			Toast toast = Toast.makeText(getApplicationContext(), "Preencha o campo da data", Toast.LENGTH_SHORT);
			toast.show();
		}else{
			aplicarDesconto();
		}
	}

//############################################################################# Confirmação da compra #################################################################################################

	public void confirmarCompra(){

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("O valor da compra é: "+valorCalculado);
		builder.setTitle("Confirmar compra?");
		builder.setPositiveButton("sim",new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {

			salvarVenda(
					nomeCliente.getText().toString(),
					valorCalculado,
					desconto,
					mesAnoDaCompra,
					dataDaCompra.getText().toString(),
					escolha
			);
			}
		});

		builder.setNeutralButton("Voltar", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
			aplicarDesconto();
			}
		});
		builder.create();
		builder.show();
	}

//############################################################################# Caso tenha desconto #################################################################################################

	private void aplicarDesconto(){

		AlertDialog.Builder builder2 = new AlertDialog.Builder(this);



		builder2.setTitle("Deseja aplicar um desconto?");
		campo= new EditText(this);
		campo.setInputType(InputType.TYPE_CLASS_NUMBER);

		builder2.setView(campo);
		builder2.setMessage("O valor atual da compra é: "+(valorCalculado-desconto));
		builder2.setNegativeButton("Não", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
			confirmarCompra();
			}
		});
		builder2.setNeutralButton("Aplicar desconto", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

				if(campo.getText().toString().equals("0") ||campo.getText().toString().equals("")){
					Toast.makeText(CadastrarVendaActivity.this, "Digite um número válido", Toast.LENGTH_SHORT).show();
				}else{
					desconto= Double.parseDouble(campo.getText().toString());
					valorCalculado=valorCalculado-Double.parseDouble(campo.getText().toString());
					confirmarCompra();
				}
			}
		});

		builder2.create();
		builder2.show();
	}

//############################################################################# Salvar Venda - para concluir  #################################################################################################

	private void salvarVenda(String nomeCliente, double  precoFinal, double desconto ,String mesAnoAt,String dataCompra,String formaDePagamento) {

		FirebaseAuth autenticacion = configFireBase.getFireBaseAutenticacao();

		String idUsuario = Base64Custom.codificarBase64(autenticacion.getCurrentUser().getEmail());
		DatabaseReference VendaRef=mDatabase.child("vendas");


		venda.setIdVenda(VendaRef.push().getKey());
		venda.setNomeCliente(nomeCliente);
		venda.setValorTotal(precoFinal);
		venda.setMesAno(mesAnoAt);
		venda.setDesconto(desconto);
		venda.setDataCompra(dataCompra);
		venda.setLista(ListagemDeProdutosParaCompras.produtosSelecionados);
		venda.setFormaDePagamento(formaDePagamento);

		mDatabase.child("armazenamento").child(idUsuario).child("vendas").child(venda.getIdVenda()).setValue(venda).addOnCompleteListener(new OnCompleteListener<Void>()  {

			@Override
			public void onComplete(@NonNull Task<Void> task) {

			}

		});

		Toast.makeText(CadastrarVendaActivity.this, "Venda Realizada", Toast.LENGTH_SHORT).show();
		startActivity(new Intent(getApplicationContext(), ListagemDeVendasRealizadas.class));
		venda.subtrairVendas();

		this.finish();
	}

//############################################################################# Carregando produtos #################################################################################################

	private void CarregarListaDeSelecionados(){

		RecyclerView recyclerSelecionados= findViewById(R.id.recyclerSelecionados);
		recyclerSelecionados.setLayoutManager(new LinearLayoutManager(this));
		recyclerSelecionados.setHasFixedSize(true);
		AdapterProdutosSelecionados adapterProdutosSelecionados = new AdapterProdutosSelecionados(ListagemDeProdutosParaCompras.produtosSelecionados, this,this);
		recyclerSelecionados.setAdapter(adapterProdutosSelecionados);
	}

//############################################################################# Pegando produto clicado #################################################################################################

	@Override
	public void onProdutoSelecionadoClick(int position) {

		final Produtovendido produtovendido=ListagemDeProdutosParaCompras.produtosSelecionados.get(position);
		AlertDialog.Builder builder3 = new AlertDialog.Builder(this);
		builder3.setTitle("Deseja tirar esse produto da lista?");
		builder3.setNegativeButton("não", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {

			}
		});
		builder3.setNeutralButton("Sim", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				calcularValorTotal(produtovendido.getPrecoVendaUnidade()*produtovendido.getQuantidadeVendida());
				ListagemDeProdutosParaCompras.produtosSelecionados.remove(produtovendido);

				if (ListagemDeProdutosParaCompras.produtosSelecionados.size()<1){
					finish();
				}

			CarregarListaDeSelecionados();
			}
		});

		builder3.create();
		builder3.show();
	}

	@Override
	protected void onStop() {
		super.onStop();
		ListagemDeProdutosParaCompras.produtosSelecionados.clear();
		valorCalculado=0;
		ListagemDeProdutosParaCompras.Vtotal=0.0;
		finish();
	}

	@Override
	protected void onPause() {
		super.onPause();
		ListagemDeProdutosParaCompras.produtosSelecionados.clear();
		valorCalculado=0;
		ListagemDeProdutosParaCompras.Vtotal=0.0;
		finish();

	}
}