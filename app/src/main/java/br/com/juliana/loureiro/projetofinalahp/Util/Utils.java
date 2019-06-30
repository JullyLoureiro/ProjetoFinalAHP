package br.com.juliana.loureiro.projetofinalahp.Util;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import br.com.juliana.loureiro.projetofinalahp.Activity.TelaPrincipal;
import br.com.juliana.loureiro.projetofinalahp.Bean.ComparaCriterioBean;
import br.com.juliana.loureiro.projetofinalahp.Bean.ComparaSubCriterioBean;
import br.com.juliana.loureiro.projetofinalahp.Bean.MatrizCriterioNormalizadaBean;
import br.com.juliana.loureiro.projetofinalahp.Bean.MatrizSubcriterioNormalizadaBean;
import br.com.juliana.loureiro.projetofinalahp.Bean.ObjetivoBean;
import br.com.juliana.loureiro.projetofinalahp.Bean.PesoCriteriosBean;
import br.com.juliana.loureiro.projetofinalahp.Bean.PesoSubcriterioBean;
import br.com.juliana.loureiro.projetofinalahp.Dao.AlternativaDao;
import br.com.juliana.loureiro.projetofinalahp.Dao.ComparaAlternativaDao;
import br.com.juliana.loureiro.projetofinalahp.Dao.ComparaCriterioDao;
import br.com.juliana.loureiro.projetofinalahp.Dao.ComparaSubcriterioDao;
import br.com.juliana.loureiro.projetofinalahp.Dao.CriterioDao;
import br.com.juliana.loureiro.projetofinalahp.Dao.MatrizCriterioNormalizadaDao;
import br.com.juliana.loureiro.projetofinalahp.Dao.ObjetivoDao;
import br.com.juliana.loureiro.projetofinalahp.Dao.PesoCriteriosDao;
import br.com.juliana.loureiro.projetofinalahp.Dao.SomaColunaDao;
import br.com.juliana.loureiro.projetofinalahp.Dao.SubcriteriosDao;
import br.com.juliana.loureiro.projetofinalahp.ListAdapter.ObjetivosList;
import br.com.juliana.loureiro.projetofinalahp.R;

public class Utils {

    public static void hideKeyboard(Context context, View editText) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    public static void ahowKeyboard(Context context, View editText) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
    }

    public static void alerta(final Activity activity, String mensag) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.alertdialog, null);

        TextView mensagem = alertLayout.findViewById(R.id.txtmensagem);
        mensagem.setText(mensag);
        Button yes = alertLayout.findViewById(R.id.yes);
        ImageView close = alertLayout.findViewById(R.id.close);
        yes.setText("Ok");
        AlertDialog.Builder alert = new AlertDialog.Builder(activity);
        alert.setView(alertLayout);
        alert.setCancelable(true);

        final AlertDialog dialog = alert.create();
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        dialog.show();

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                activity.finish();
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    public static void alerta(final Activity activity, String mensag, String titulo) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.alertdialog, null);

        TextView mensagem = alertLayout.findViewById(R.id.txtmensagem);
        TextView txttitulo = alertLayout.findViewById(R.id.txttitulo);
        mensagem.setText(mensag);
        txttitulo.setText(titulo);
        txttitulo.setVisibility(View.VISIBLE);
        Button yes = alertLayout.findViewById(R.id.yes);
        yes.setVisibility(View.GONE);
        ImageView close = alertLayout.findViewById(R.id.close);
        AlertDialog.Builder alert = new AlertDialog.Builder(activity);
        alert.setView(alertLayout);
        alert.setCancelable(true);

        final AlertDialog dialog = alert.create();
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        dialog.show();

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    public static void alertaExcluir(final Activity activity, final int idobjetivo) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.alertdialog, null);

        TextView mensagem = alertLayout.findViewById(R.id.txtmensagem);
        TextView txttitulo = alertLayout.findViewById(R.id.txttitulo);
        mensagem.setText("Tem certeza que deseja removê-lo?");
        txttitulo.setVisibility(View.VISIBLE);
        Button yes = alertLayout.findViewById(R.id.yes);
        yes.setText("REMOVER");
        ImageView close = alertLayout.findViewById(R.id.close);
        AlertDialog.Builder alert = new AlertDialog.Builder(activity);
        alert.setView(alertLayout);
        alert.setCancelable(true);

        final AlertDialog dialog = alert.create();
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        if (!activity.isFinishing()) {
            dialog.show();
        }

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ObjetivoDao(activity).deletaObjetivo(idobjetivo);
                List<ObjetivoBean> listaObjetivos = new ObjetivoDao(activity).carregaObjetivos();
                TelaPrincipal.listObjetivos.setAdapter(new ObjetivosList(listaObjetivos, activity));
                dialog.dismiss();
            }
        });
    }

    public static int returnLastId(SQLiteDatabase DB) {
        int codId = 0;
        try {
            Cursor cursor = DB.rawQuery("SELECT LAST_INSERT_ROWID()", null);
            if (cursor.moveToFirst()) {
                codId = cursor.getInt(cursor.getColumnIndex("LAST_INSERT_ROWID()"));
                cursor.close();
            }
        } catch (Exception ignored) {
        }
        return codId;
    }

    public static String DataHojeSemHorasBR() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        Date data = new Date();
        return sdf.format(data.getTime());
    }

    public static void calculacriteriosTemp(Activity activity) {
        //NORMALIZAÇÃO PARTE 1 - SOMA DAS COLUNAS
        new SomaColunaDao(activity).somaColunas();
        List<ComparaCriterioBean> listaComparacao = new ComparaCriterioDao(activity).carregaComparacoes2();

        for (int i = 0; i < listaComparacao.size(); i++) {
            float soma = new SomaColunaDao(activity).retornaSoma(listaComparacao.get(i).getIdcrit2());

            MatrizCriterioNormalizadaBean matrizCriterioNormalizadaBean = new MatrizCriterioNormalizadaBean();
            matrizCriterioNormalizadaBean.setIdcrit1(listaComparacao.get(i).getIdcrit1());
            matrizCriterioNormalizadaBean.setIdcrit2(listaComparacao.get(i).getIdcrit2());

            float importancia = listaComparacao.get(i).getImportancia() / soma;

            matrizCriterioNormalizadaBean.setImportancia(importancia);
            new MatrizCriterioNormalizadaDao(activity).insereMatrizNormalizada(matrizCriterioNormalizadaBean);

        }

        //NORMALIZAÇÃO PARTE 2 - MÉDIA ARITMÉTICA DAS LINHAS (PESO)
        int qtd = new CriterioDao(activity).retornaQtdCriterios();
        List<PesoCriteriosBean> pesos = new PesoCriteriosDao(activity).somaLinhas(qtd);

        for (int i = 0; i < pesos.size(); i++) {
            List<ComparaCriterioBean> listaComp = new ComparaCriterioDao(activity).carregaComparacoes(pesos.get(i).getIdcrit());
            for (int j = 0; j < listaComp.size(); j++) {
                float mult = pesos.get(i).getSoma() * listaComp.get(j).getImportancia();
                new PesoCriteriosDao(activity).atualizaYMax(listaComp.get(j).getIdcrit1(), mult);
            }
        }

        //Com o vetor obtido, deve-se dividi-lo pelos pesos de cada critério
        List<PesoCriteriosBean> listaYMax = new PesoCriteriosDao(activity).carregaYMax();
        for (int i = 0; i < listaYMax.size(); i++) {
            float div = listaYMax.get(i).getYmax() / listaYMax.get(i).getSoma();
            new PesoCriteriosDao(activity).atualizaTotalDivisao(listaYMax.get(i).getIdcrit(), div);
        }

    }

    public static void calculaSubcriteriosTemp(Activity activity) {
        //NORMALIZAÇÃO PARTE 1 - SOMA DAS COLUNAS
        new SomaColunaDao(activity).somaColunas3();
        List<ComparaSubCriterioBean> listaComparacao = new ComparaSubcriterioDao(activity).carregaComparacoes2();

        for (int i = 0; i < listaComparacao.size(); i++) {
            float soma = new SomaColunaDao(activity).retornaSomaSubCriterio(listaComparacao.get(i).getIdsubcrit2());

            MatrizSubcriterioNormalizadaBean matrizCriterioNormalizadaBean = new MatrizSubcriterioNormalizadaBean();
            matrizCriterioNormalizadaBean.setIdsubcrit1(listaComparacao.get(i).getIdsubcrit1());
            matrizCriterioNormalizadaBean.setIdsubcrit2(listaComparacao.get(i).getIdsubcrit2());
            matrizCriterioNormalizadaBean.setIdcriterio(listaComparacao.get(i).getIdcriterio());

            float importancia = listaComparacao.get(i).getImportancia() / soma;

            matrizCriterioNormalizadaBean.setImportancia(importancia);
            new MatrizCriterioNormalizadaDao(activity).insereMatrizNormalizadaSubcriterio(matrizCriterioNormalizadaBean);

        }

        //NORMALIZAÇÃO PARTE 2 - MÉDIA ARITMÉTICA DAS LINHAS (PESO)
        int qtd = new SubcriteriosDao(activity).retornaQtd();
        List<PesoSubcriterioBean> pesos = new PesoCriteriosDao(activity).somaLinhasSubcriterios(qtd);

        for (int i = 0; i < pesos.size(); i++) {
            List<ComparaSubCriterioBean> listaComp = new ComparaSubcriterioDao(activity).carregaComparacoes(pesos.get(i).getIdsubcrit());
            for (int j = 0; j < listaComp.size(); j++) {
                float mult = pesos.get(i).getPeso() * listaComp.get(j).getImportancia();
                new PesoCriteriosDao(activity).atualizaYMaxSubcriterio(listaComp.get(j).getIdsubcrit1(), mult);
            }
        }

        //Com o vetor obtido, deve-se dividi-lo pelos pesos de cada critério
        try {
            List<PesoSubcriterioBean> listaYMax = new PesoCriteriosDao(activity).carregaYMaxSub();
            float pesocrit = new PesoCriteriosDao(activity).carregaPesoCrit(listaYMax.get(0).getIdcrit());
            for (int i = 0; i < listaYMax.size(); i++) {
                float div = listaYMax.get(i).getYmax() / listaYMax.get(i).getPeso();
                float peso = pesocrit * listaYMax.get(i).getPeso();
                new PesoCriteriosDao(activity).atualizaTotalDivisaoSub(listaYMax.get(i).getIdcrit(), div, peso);
            }
        }catch (Exception ignored) {

        }

    }

    public static void calculacriterios(Activity activity, int idobjetivo) {
        //NORMALIZAÇÃO PARTE 1 - SOMA DAS COLUNAS
        new SomaColunaDao(activity).somaColunas2(idobjetivo);
        List<ComparaCriterioBean> listaComparacao = new ComparaCriterioDao(activity).carregaComparacoes3(idobjetivo);

        for (int i = 0; i < listaComparacao.size(); i++) {
            float soma = new SomaColunaDao(activity).retornaSoma(listaComparacao.get(i).getIdcrit2());

            MatrizCriterioNormalizadaBean matrizCriterioNormalizadaBean = new MatrizCriterioNormalizadaBean();
            matrizCriterioNormalizadaBean.setIdcrit1(listaComparacao.get(i).getIdcrit1());
            matrizCriterioNormalizadaBean.setIdcrit2(listaComparacao.get(i).getIdcrit2());

            float importancia = listaComparacao.get(i).getImportancia() / soma;

            matrizCriterioNormalizadaBean.setImportancia(importancia);
            new MatrizCriterioNormalizadaDao(activity).insereMatrizNormalizada(matrizCriterioNormalizadaBean);

        }

        //NORMALIZAÇÃO PARTE 2 - MÉDIA ARITMÉTICA DAS LINHAS (PESO)
        int qtd = new CriterioDao(activity).retornaQtdCriterios2(idobjetivo);
        List<PesoCriteriosBean> pesos = new PesoCriteriosDao(activity).somaLinhas(qtd);

        for (int i = 0; i < pesos.size(); i++) {
            List<ComparaCriterioBean> listaComp = new ComparaCriterioDao(activity).carregaComparacoes2(pesos.get(i).getIdcrit());
            for (int j = 0; j < listaComp.size(); j++) {
                float mult = pesos.get(i).getSoma() * listaComp.get(j).getImportancia();
                new PesoCriteriosDao(activity).atualizaYMax(listaComp.get(j).getIdcrit1(), mult);
            }
        }

        //Com o vetor obtido, deve-se dividi-lo pelos pesos de cada critério
        List<PesoCriteriosBean> listaYMax = new PesoCriteriosDao(activity).carregaYMax();
        for (int i = 0; i < listaYMax.size(); i++) {
            float div = listaYMax.get(i).getYmax() / listaYMax.get(i).getSoma();
            new PesoCriteriosDao(activity).atualizaTotalDivisao(listaYMax.get(i).getIdcrit(), div);
        }

    }

    public static void deletaTemp(Activity activity) {
        new ObjetivoDao(activity).deletaTemp();
        new CriterioDao(activity).deletaTemp();
        new AlternativaDao(activity).deletaTemp();
        new ComparaCriterioDao(activity).deletaTemp();
        new ComparaAlternativaDao(activity).deletaTemp();
        new ComparaSubcriterioDao(activity).deletaTemp();
        new MatrizCriterioNormalizadaDao(activity).deleta();
        new MatrizCriterioNormalizadaDao(activity).deletaAlternativa();
        new MatrizCriterioNormalizadaDao(activity).deletaSubcriterio();
        new PesoCriteriosDao(activity).deleta();
        new PesoCriteriosDao(activity).deletaAlternativa();
        new PesoCriteriosDao(activity).deletaSubcriterio();
        new SomaColunaDao(activity).deleta();
        new SomaColunaDao(activity).deletaAlternativa();
        new SomaColunaDao(activity).deletaSubcriterio();
        new SubcriteriosDao(activity).deletaTemp();
    }
}
