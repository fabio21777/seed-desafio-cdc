package com.fsm.livraria.controllers.cupom;

import java.util.Random;
import java.util.regex.Pattern;

public class GeradorCodigoLegivel {

    private static final String VOGAIS = "aeiou";
    private static final String CONSOANTES = "bcdfghjklmnpqrstvwxyz";
    private static final String ALFABETO = "abcdefghijklmnopqrstuvwxyz";
    private static final Random random = new Random();

    private static final Pattern PADRAO_LEGIVEL = Pattern.compile(
            "^(?=.*[aeiouáàâãéêíóôõúüAEIOUÁÀÂÃÉÊÍÓÔÕÚÜ])" +
                    "(?!.*[bcdfghjklmnpqrstvwxyzçBCDFGHJKLMNPQRSTVWXYZÇ]{3,})" +
                    "([a-záàâãéêíóôõúüçA-ZÁÀÂÃÉÊÍÓÔÕÚÜÇ]+)$");

    public static String criarCodigoLegivel() {
        String palavra;
        do {
            // Gera uma palavra com tamanho entre 4 e 8 caracteres
            int tamanho = random.nextInt(5) + 4;
            palavra = gerarPalavraAleatoria(tamanho);
        } while (!PADRAO_LEGIVEL.matcher(palavra).matches());

        // Gera um número aleatório entre 0 e 100
        int numero = random.nextInt(101);

        // Combina a palavra com o número
        return palavra + numero;
    }

    private static String gerarPalavraAleatoria(int tamanho) {
        StringBuilder palavra = new StringBuilder(tamanho);

        // Garantir que temos pelo menos uma vogal na palavra
        int posicaoVogal = random.nextInt(tamanho);

        for (int i = 0; i < tamanho; i++) {
            char letra;
            if (i == posicaoVogal) {
                // Garantimos pelo menos uma vogal na posição aleatória
                letra = VOGAIS.charAt(random.nextInt(VOGAIS.length()));
            } else {
                // Verificamos se já temos duas consoantes consecutivas
                if (i >= 2 &&
                        ehConsoante(palavra.charAt(i-1)) &&
                        ehConsoante(palavra.charAt(i-2))) {
                    // Forçamos uma vogal para evitar 3 consoantes seguidas
                    letra = VOGAIS.charAt(random.nextInt(VOGAIS.length()));
                } else {
                    // Caso contrário, escolhemos uma letra aleatória do alfabeto
                    letra = ALFABETO.charAt(random.nextInt(ALFABETO.length()));
                }
            }
            palavra.append(letra);
        }

        return palavra.toString();
    }

    private static boolean ehConsoante(char c) {
        return CONSOANTES.indexOf(c) != -1;
    }

    public static boolean validarCodigoLegivel(String codigo) {
        // Extrai a parte alfabética do código (remove os números do final)
        String partePalavra = codigo.replaceAll("\\d+$", "");

        // Verifica se o código termina com um número entre 0 e 100
        String parteNumeroStr = codigo.substring(partePalavra.length());
        boolean numeroValido = false;

        try {
            int parteNumero = Integer.parseInt(parteNumeroStr);
            numeroValido = parteNumero >= 0 && parteNumero <= 100;
        } catch (NumberFormatException e) {
            return false;
        }

        return PADRAO_LEGIVEL.matcher(partePalavra).matches() && numeroValido;
    }
}