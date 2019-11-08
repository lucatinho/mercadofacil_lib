/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fatec.mercado_lib.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Conversao {
    
    public static Date converterData(String data) throws ParseException {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        if(data == null || data.trim().equals("")){
        return null;    
        } else {
            Date date = fmt.parse(data);
            return date;
        }
    }
    
    public static String data2String(Date data) throws ParseException {
        SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");    
        String dataFormatada = fmt.format(data);
        
        return dataFormatada;
        }
    
    public static Date dataAtual() throws ParseException {
        SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");    
        Date novaData = new Date(System.currentTimeMillis());
        //Date data = fmt.format(novaData);
        return novaData;
    }

}

